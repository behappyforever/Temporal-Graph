package temporalGraph.algorithm;

import temporalGraph.graph.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class GlobalRangeQuery {

    private static int threadNum = 10;//对应一组快照

    //PageRank
    private static final double threshold = 0.0000000000000001;// 越小要求精度越高，迭代次数越大 10的-5
    private static final double alpha = 0.85f;
    private static final int maxStep = 10;
    private static final int maxDeltaStep = 5;

    private static List<Long>[] listArr;
    private static Map<Long, Double> messageMap;//key为顶点id，value为聚集值
    private static Map<Long, Double> prValueMap;//key为顶点id，value顶点pr值

    private static Map<Long, Double>[] prValueMapArr = new Map[threadNum];

    /**
     * 供外部调用的接口
     * <p>
     * 处理一组快照
     */
    public static void pageRank() {
        pageRankVS();

        System.out.println("原始迭代---------");

        pageRankDeltaSnapshot();


    }

    /**
     * 线程并行处理一组增量快照
     */
    private static void pageRankDeltaSnapshot() {
        //设置循环路障
        CyclicBarrier barrier = new CyclicBarrier(threadNum);

        //用来判断所有子线程是否结束
        CountDownLatch latch = new CountDownLatch(threadNum);

        //设置线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        //创建线程
        for (int i = 0; i < threadNum; i++) {
            executor.submit(new Thread(new PageRankDeltaRunner(barrier, latch, "thread" + i, TGraph.timeLocalityDeltaSnapshot, i)));
        }

        executor.shutdown();

        try {
            latch.await();//当latch中的值变为0，执行之后的语句
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 每个线程执行逻辑
     */
    static class PageRankDeltaRunner implements Runnable {

        private CyclicBarrier barrier;
        private CountDownLatch latch;
        private String name;
        private int time;

        private Map<Long, List<Edge>[]> refMap;

        public PageRankDeltaRunner(CyclicBarrier barrier, CountDownLatch latch, String name, Map<Long, List<Edge>[]> refMap, int time) {
            this.barrier = barrier;
            this.latch = latch;
            this.name = name;
            this.refMap = refMap;
            this.time = time;
        }

        @Override
        public void run() {
            try {

                prValueMapArr[time] = new ConcurrentHashMap();

                //拷贝一份pr值
                for (Map.Entry<Long, Double> en : prValueMap.entrySet()) {
                    prValueMapArr[time].put(en.getKey(), en.getValue());
                }

                GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
                Map<Long, Vertex> vertexMap = graphSnapshot.getHashMap();
                int numOfVertex = graphSnapshot.getHashMap().size();


                //处理数据
                for (Map.Entry<Long, List<Edge>[]> en : refMap.entrySet()) {
                    long vertexId = en.getKey();//当前循环 当前线程需要处理的源点id
                    List<Edge> edges = en.getValue()[time];//当前循环 当前线程需要处理的边表list

                    //将增量顶点加入prValueMap
                    if (!prValueMapArr[time].containsKey(vertexId)) {
                        numOfVertex++;
                        prValueMapArr[time].put(vertexId, 0.0);
                    }

                    if (edges != null) {


                        for (Edge edge : edges) {
                            if (!prValueMapArr[time].containsKey(edge.getDesId())) {
                                numOfVertex++;
                                prValueMapArr[time].put(edge.getDesId(), 0.0);
                            }
                        }


                        //遍历增量的每个源顶点,“转移”pr值
                        if (vertexMap.containsKey(vertexId)) {//属于关联边
                            //源顶点的vs中边的集合
                            List<VSEdge> vsList = vertexMap.get(vertexId).getOutGoingList();
                            //源顶点的pr值
                            double srcPr = prValueMapArr[time].get(vertexId);

                            //重分配源顶点的pr值  1. vs  2. delta
                            long oldOutDegree = vertexMap.get(vertexId).getOutGoingList().size();
                            long newOutDegree = oldOutDegree + edges.size();
                            //1.对于vs中的每个目的顶点，pr值减少 alpha*(srcPr/oldOutDegree-srcPr/newOutDegree)
                            for (VSEdge vsEdge : vsList) {
                                prValueMapArr[time].put(vsEdge.getDesId(), prValueMapArr[time].get(vsEdge.getDesId()) - alpha * (srcPr / oldOutDegree - srcPr / newOutDegree));
                            }
                            //2.对于delta中的每个目的顶点，分两种情况：(1) 目的顶点在vs中，也就是没有引入新的顶点 (2) 目的顶点不在vs中，也就是引入了新的顶点
                            for (Edge edge : edges) {
                                if (prValueMapArr[time].get(edge.getDesId()) == 0) {
                                    prValueMapArr[time].put(edge.getDesId(), 1.0 / numOfVertex + alpha * (srcPr / newOutDegree));
                                } else {
                                    prValueMapArr[time].put(edge.getDesId(), prValueMapArr[time].get(edge.getDesId()) + alpha * (srcPr / newOutDegree));
                                }
                            }

                        }
                    }

                }

                barrier.await();

                //开启bsp过程,全量迭代

//                int iterations = 1;
//                while (iterations <= maxDeltaStep) {
//
//                    if (iterations > 1) {//第一个超步不需要本地计算
//                        for (Long vertexId : list) {
//                            double total = messageMap.get(vertexId);
//                            prValueMap.put(vertexId, (1 - alpha) * (1.0f / numOfVertex) + alpha * total);
//                        }
//                    }
//
//
//                    //初始化或者清空消息缓冲
//                    for (Long vertexId : list) {
//                        messageMap.put(vertexId, 0.0);
//                    }
//                    for (Long vertexId : set) {
//                        messageMap.put(vertexId, 0.0);
//                        for (Edge edge : refMap.get(vertexId)) {
//                            messageMap.put(edge.getDesId(), 0.0);
//                        }
//                    }
//
//
//                    //发消息
//                    for (Long vertexId : list) {
//                        List<VSEdge> outGoingList = vertexMap.get(vertexId).getOutGoingList();
//                        long outDegree = outGoingList.size();
//                        if (refMap.containsKey(vertexId)) {
//                            outDegree += refMap.get(vertexId).size();
//                        }
//
//                        if (outDegree == 0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
//                            for (Map.Entry<Long, Double> en : messageMap.entrySet()) {
//                                messageMap.put(en.getKey(), en.getValue() + prValueMap.get(vertexId) / (numOfVertex - 1));
//                            }
//                            messageMap.put(vertexId, messageMap.get(vertexId) - prValueMap.get(vertexId) / (numOfVertex - 1));
//                        } else {// 如果该点出度不为0，则将pr值平分给其出边顶点
//                            for (VSEdge vsEdge : outGoingList) {//发送给VS中的边
//                                messageMap.put(vsEdge.getDesId(), messageMap.get(vsEdge.getDesId()) + prValueMap.get(vertexId) / outDegree);
//                            }
//                            if (refMap.containsKey(vertexId)) {
//                                for (Edge edge : refMap.get(vertexId)) {//发送给增量边
//                                    messageMap.put(edge.getDesId(), messageMap.get(edge.getDesId()) + prValueMap.get(vertexId) / outDegree);
//                                }
//                            }
//                        }
//                    }
//
//                    for (Long vertexId : set) {
//                        List<Edge> edgeList = refMap.get(vertexId);
//                        long outDegree = edgeList.size();
//                        if (vertexMap.containsKey(vertexId)) {
//                            outDegree += vertexMap.get(vertexId).getOutGoingList().size();
//                        }
//
//                        if (outDegree == 0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
//                            for (Map.Entry<Long, Double> en : messageMap.entrySet()) {
//                                messageMap.put(en.getKey(), en.getValue() + prValueMap.get(vertexId) / (numOfVertex - 1));
//                            }
//                            messageMap.put(vertexId, messageMap.get(vertexId) - prValueMap.get(vertexId) / (numOfVertex - 1));
//                        } else {// 如果该点出度不为0，则将pr值平分给其出边顶点
//                            if (prValueMap.containsKey(vertexId)) {
//                                for (VSEdge vsEdge : vertexMap.get(vertexId).getOutGoingList()) {//发送给VS中的边
//                                    messageMap.put(vsEdge.getDesId(), messageMap.get(vsEdge.getDesId()) + prValueMap.get(vertexId) / outDegree);
//                                }
//                            }
//
//                            for (Edge edge : edgeList) {//发送给增量边
//                                messageMap.put(edge.getDesId(), messageMap.get(edge.getDesId()) + prValueMap.get(vertexId) / outDegree);
//                            }
//                        }
//                    }
//                    iterations++;
//                    System.out.println(iterations);
//                    //路障同步
//                    barrier.await();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name + "完成");
            latch.countDown();
        }
    }

    /**
     * 虚拟快照 page rank
     */
    private static void pageRankVS() {

        //存放顶点pr值,线程共用
        prValueMap = new ConcurrentHashMap();

        resetPr(TGraph.graphSnapshot, prValueMap);

        //用ConcurrentHashMap存储消息,可以被多个线程并发访问
        messageMap = new ConcurrentHashMap();

        //划分虚拟快照
        listArr = Partition.partitionVS(threadNum);

        //设置循环路障
        CyclicBarrier barrier = new CyclicBarrier(threadNum);

        //用来判断所有子线程是否结束
        CountDownLatch latch = new CountDownLatch(threadNum);

        //设置线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        //创建线程
        for (int i = 1; i <= threadNum; i++) {
            executor.submit(new Thread(new PageRankRunner(barrier, latch, "thread" + i, listArr[i - 1])));
        }

        executor.shutdown();

        try {
            latch.await();//当latch中的值变为0，执行之后的语句
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //所有子线程结束，输出pagerank值
        double total = 0.0;
        for (Double aDouble : prValueMap.values()) {
            total += aDouble;
        }
        System.out.println(total);
        System.out.println(prValueMap.size());
    }

    /**
     * 实现每个线程的执行逻辑
     */
    static class PageRankRunner implements Runnable {
        private CyclicBarrier barrier;
        private CountDownLatch latch;
        private String name;
        private List<Long> list;//虚拟快照分块

        public PageRankRunner(CyclicBarrier barrier, CountDownLatch latch, String name, List<Long> list) {
            this.barrier = barrier;
            this.latch = latch;
            this.name = name;
            this.list = list;
        }

        @Override
        public void run() {

            //bsp 逻辑
            try {
                GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
                Map<Long, Vertex> vertexMap = graphSnapshot.getHashMap();
                int numOfVertex = graphSnapshot.getHashMap().size();

                int iterations = 1;


                while (iterations <= maxStep) {
                    if (iterations > 1) {//第一个超步不需要本地计算
                        for (Long vertexId : list) {
                            double total = messageMap.get(vertexId);
                            prValueMap.put(vertexId, (1 - alpha) * (1.0f / numOfVertex) + alpha * total);
                        }
                    }


                    //初始化或者清空消息缓冲
                    for (Long vertexId : list) {
                        messageMap.put(vertexId, 0.0);
                    }


                    //发消息
                    for (Long vertexId : list) {
                        Vertex v = vertexMap.get(vertexId);
                        List<VSEdge> outGoingList = v.getOutGoingList();
                        if (outGoingList.size() == 0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
                            for (Map.Entry<Long, Double> en : messageMap.entrySet()) {
                                messageMap.put(en.getKey(), en.getValue() + prValueMap.get(vertexId) / (numOfVertex - 1));
                            }
                            messageMap.put(vertexId, messageMap.get(vertexId) - prValueMap.get(vertexId) / (numOfVertex - 1));
                        } else {// 如果该点出度不为0，则将pr值平分给其出边顶点
                            for (VSEdge e : outGoingList) {
                                messageMap.put(e.getDesId(), messageMap.get(e.getDesId()) + prValueMap.get(vertexId) / outGoingList.size());
                            }
                        }
                    }
                    iterations++;
                    System.out.println(iterations);
                    //路障同步
                    barrier.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name + "完成");
            latch.countDown();
        }
    }

    /**
     * 重置所有顶点pr值为1/numofvertex
     *
     * @param graphSnapshot
     * @param prValueMap
     */
    private static void resetPr(GraphSnapshot graphSnapshot, Map<Long, Double> prValueMap) {
        for (Long id : graphSnapshot.getHashMap().keySet()) {
            prValueMap.put(id, 1.0 / graphSnapshot.getHashMap().size());
        }
    }

}
