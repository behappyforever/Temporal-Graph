package temporalGraph.algorithm;

import temporalGraph.graph.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class GlobalPointQuery {

    private static int threadNum = 6;

    //PageRank
    private static final double threshold = 0.0000000000000001;// 越小要求精度越高，迭代次数越大 10的-5
    private static final double alpha = 0.85f;
    private static final int maxStep = 10;
    private static final int maxDeltaStep = 5;

    private static List<Long>[] listArr;
    private static Set<Long>[] setArr;
    private static Map<Long, Double> messageMap;//key为顶点id，value为聚集值
    private static Map<Long, Double> prValueMap;//key为顶点id，value顶点pr值

    /**
     * 供外部调用的接口
     *
     * @param time 处理某个时间点的快照
     */
    public static void pageRank(int time) {
        pageRankVS();

        System.out.println("PageRank原始迭代完成---------");
        System.out.println(System.currentTimeMillis()-Main.startTime);

        pageRankDeltaSnapshot(time);


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
                                messageMap.put(e.getDesId(), messageMap.getOrDefault(e.getDesId(),1.0/numOfVertex) + prValueMap.get(vertexId) / outGoingList.size());
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


    /**
     * 增量快照的PageRank计算
     *
     * @param time
     */
    private static void pageRankDeltaSnapshot(int time) {

        Map<Long, List<Edge>> refMap = TGraph.strucLocalityDeltaSnapshot[time];

        setArr = Partition.partitionDeltaSnapshot(threadNum, listArr, refMap);

        //设置循环路障
        CyclicBarrier barrier = new CyclicBarrier(threadNum);

        //用来判断所有子线程是否结束
        CountDownLatch latch = new CountDownLatch(threadNum);

        //设置线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        //创建线程
        for (int i = 1; i <= threadNum; i++) {
            executor.submit(new Thread(new PageRankDeltaRunner(barrier, latch, "thread" + i, listArr[i - 1], setArr[i - 1], refMap)));
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

    static class PageRankDeltaRunner implements Runnable {

        private CyclicBarrier barrier;
        private CountDownLatch latch;
        private String name;
        private List<Long> list;//虚拟快照分块
        private Set<Long> set;//增量快照分块
        private Map<Long, List<Edge>> refMap;

        public PageRankDeltaRunner(CyclicBarrier barrier, CountDownLatch latch, String name, List<Long> list, Set<Long> set, Map<Long, List<Edge>> refMap) {
            this.barrier = barrier;
            this.latch = latch;
            this.name = name;
            this.list = list;
            this.set = set;
            this.refMap = refMap;
        }

        @Override
        public void run() {
            try {
                GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
                Map<Long, Vertex> vertexMap = graphSnapshot.getHashMap();
                int numOfVertex = graphSnapshot.getHashMap().size();

                //进行bsp迭代之前，用一轮同步迭代重分配pr值

                //将增量顶点加入prValueMap
                for (Long vertexId : set) {
                    if (!prValueMap.containsKey(vertexId)) {
                        numOfVertex++;
                        prValueMap.put(vertexId, 0.0);
                    }
                    for (Edge edge : refMap.get(vertexId)) {
                        if (!prValueMap.containsKey(edge.getDesId())) {
                            numOfVertex++;
                            prValueMap.put(edge.getDesId(), 0.0);
                        }
                    }
                }

                //遍历增量的每个源顶点,“转移”pr值
                for (Long vertexId : set) {
                    if (list.contains(vertexId)) {//属于关联边
                        //源顶点的增量边集合
                        List<Edge> edges = refMap.get(vertexId);
                        //源顶点的vs中边的集合
                        List<VSEdge> vsList = vertexMap.get(vertexId).getOutGoingList();
                        //源顶点的pr值
                        double srcPr = prValueMap.get(vertexId);

                        //重分配源顶点的pr值  1. vs  2. delta
                        long oldOutDegree = vertexMap.get(vertexId).getOutGoingList().size();
                        long newOutDegree = oldOutDegree + edges.size();
                        //1.对于vs中的每个目的顶点，pr值减少 alpha*(srcPr/oldOutDegree-srcPr/newOutDegree)
                        for (VSEdge vsEdge : vsList) {
                            prValueMap.put(vsEdge.getDesId(), prValueMap.get(vsEdge.getDesId()) - alpha * (srcPr / oldOutDegree - srcPr / newOutDegree));
                        }
                        //2.对于delta中的每个目的顶点，分两种情况：(1) 目的顶点在vs中，也就是没有引入新的顶点 (2) 目的顶点不在vs中，也就是引入了新的顶点
                        for (Edge edge : edges) {
                            if (prValueMap.get(edge.getDesId()) == 0) {
                                prValueMap.put(edge.getDesId(), 1.0 / numOfVertex + alpha * (srcPr / newOutDegree));
                            } else {
                                prValueMap.put(edge.getDesId(), prValueMap.get(edge.getDesId()) + alpha * (srcPr / newOutDegree));
                            }
                        }

                    }
                }

                barrier.await();
                System.out.println(System.currentTimeMillis()-Main.startTime);

                //开启bsp过程,全量迭代

                int iterations = 1;
                while (iterations <= maxDeltaStep) {

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
                    for (Long vertexId : set) {
                        messageMap.put(vertexId, 0.0);
                        for (Edge edge : refMap.get(vertexId)) {
                            messageMap.put(edge.getDesId(), 0.0);
                        }
                    }


                    //发消息
                    for (Long vertexId : list) {
                        List<VSEdge> outGoingList = vertexMap.get(vertexId).getOutGoingList();
                        long outDegree = outGoingList.size();
                        if (refMap.containsKey(vertexId)) {
                            outDegree += refMap.get(vertexId).size();
                        }

                        if (outDegree == 0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
                            for (Map.Entry<Long, Double> en : messageMap.entrySet()) {
                                messageMap.put(en.getKey(), en.getValue() + prValueMap.get(vertexId) / (numOfVertex - 1));
                            }
                            messageMap.put(vertexId, messageMap.get(vertexId) - prValueMap.get(vertexId) / (numOfVertex - 1));
                        } else {// 如果该点出度不为0，则将pr值平分给其出边顶点
                            for (VSEdge vsEdge : outGoingList) {//发送给VS中的边
                                messageMap.put(vsEdge.getDesId(), messageMap.get(vsEdge.getDesId()) + prValueMap.get(vertexId) / outDegree);
                            }
                            if (refMap.containsKey(vertexId)) {
                                for (Edge edge : refMap.get(vertexId)) {//发送给增量边
                                    messageMap.put(edge.getDesId(), messageMap.get(edge.getDesId()) + prValueMap.get(vertexId) / outDegree);
                                }
                            }
                        }
                    }

                    for (Long vertexId : set) {
                        List<Edge> edgeList = refMap.get(vertexId);
                        long outDegree = edgeList.size();
                        if (vertexMap.containsKey(vertexId)) {
                            outDegree += vertexMap.get(vertexId).getOutGoingList().size();
                        }

                        if (outDegree == 0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
                            for (Map.Entry<Long, Double> en : messageMap.entrySet()) {
                                messageMap.put(en.getKey(), en.getValue() + prValueMap.get(vertexId) / (numOfVertex - 1));
                            }
                            messageMap.put(vertexId, messageMap.get(vertexId) - prValueMap.get(vertexId) / (numOfVertex - 1));
                        } else {// 如果该点出度不为0，则将pr值平分给其出边顶点
                            if (prValueMap.containsKey(vertexId)) {
                                for (VSEdge vsEdge : vertexMap.get(vertexId).getOutGoingList()) {//发送给VS中的边
                                    messageMap.put(vsEdge.getDesId(), messageMap.get(vsEdge.getDesId()) + prValueMap.get(vertexId) / outDegree);
                                }
                            }

                            for (Edge edge : edgeList) {//发送给增量边
                                messageMap.put(edge.getDesId(), messageMap.get(edge.getDesId()) + prValueMap.get(vertexId) / outDegree);
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

    //---------------------------------------------------------------------------------

    //以下是SSSP算法

    private static Map<Long, SSSPBean> ssspMap;//key为顶点id,value为路径长度


    /**
     * 供外部调用的接口
     *
     * @param time 处理某个时间点的快照
     */
    public static void singleShortestPath(long sourceId, int time) {
        singleShortestPathVS(sourceId, time);

        System.out.println("最短路径原始迭代完成---------");
        System.out.println(System.currentTimeMillis()-Main.startTime);

        singleShortestPathDelta(sourceId,time);


    }


    static class SSSPBean {
        long pathLength;
        long message;
        boolean flag;//true表示激活

        public SSSPBean(long pathLength, long message, boolean flag) {
            this.pathLength = pathLength;
            this.message = message;
            this.flag = flag;
        }
    }

    private static void initSSSPMap(long sourceId) {
        ssspMap = new ConcurrentHashMap<>();

        Set<Long> set = TGraph.graphSnapshot.getHashMap().keySet();

        for (Long vertexId : set) {//所有顶点路径长度赋值为正无穷，激活状态设为否
            ssspMap.put(vertexId, new SSSPBean(Integer.MAX_VALUE, Integer.MAX_VALUE, false));
        }

        //对源点特殊处理
        SSSPBean source = ssspMap.get(sourceId);
        source.pathLength = 0;
        source.flag = true;

    }

    private static boolean checkActive(Collection<Long> col) {
        for (Long vertexId : col) {
            if (ssspMap.get(vertexId).flag)
                return true;
        }
        return false;
    }

    private static void singleShortestPathVS(long sourceId, int time) {

        initSSSPMap(sourceId);


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
            executor.submit(new Thread(new SSSPRunner(time, barrier, latch, "thread" + i, listArr[i - 1])));
        }

        executor.shutdown();

        try {
            latch.await();//当latch中的值变为0，执行之后的语句
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        ssspMap.entrySet().forEach(longSSSPBeanEntry -> {
//            System.out.print(longSSSPBeanEntry.getKey() + "  " + longSSSPBeanEntry.getValue().pathLength);
//            System.out.println();
//        });
    }

    static class SSSPRunner implements Runnable {

        private CyclicBarrier barrier;
        private CountDownLatch latch;
        private String name;
        private int time;
        private List<Long> list;//虚拟快照分块

        public SSSPRunner(int time, CyclicBarrier barrier, CountDownLatch latch, String name, List<Long> list) {
            this.time = time;
            this.barrier = barrier;
            this.latch = latch;
            this.name = name;
            this.list = list;
        }

        @Override
        public void run() {

            try {
                Map<Long, Vertex> map = TGraph.graphSnapshot.getHashMap();

                int iterations = 0;

                while (true) {
//                    if(iterations>0){//本地计算
//                        for(Long vertexId:list){
//                            SSSPBean bean = ssspMap.get(vertexId);
//                            if(bean.message<bean.pathLength){
//                                bean.pathLength=bean.message;
//                                bean.flag=true;
//                            }
//                        }
//                    }

//                    barrier.await();

                    for (Long vertexId : list) {//发消息
                        SSSPBean bean = ssspMap.get(vertexId);
                        if (bean.flag) {
                            List<VSEdge> vsEdges = map.get(vertexId).getOutGoingList();
                            for (VSEdge vsEdge : vsEdges) {
                                long newPathLength = bean.pathLength + vsEdge.getWeight(time);
//                                ssspMap.get(vsEdge.getDesId()).message=Math.min(ssspMap.get(vsEdge.getDesId()).message,newPathLength);
                                if(ssspMap.get(vsEdge.getDesId()).pathLength>newPathLength){
                                    ssspMap.get(vsEdge.getDesId()).pathLength=newPathLength;
                                    ssspMap.get(vsEdge.getDesId()).flag=true;
                                }
                            }
                            bean.flag=false;
                        }
                    }
                    iterations++;
                    System.out.println(name+"----"+iterations);

                    //路障同步
                    try {
                        barrier.await(500,TimeUnit.MILLISECONDS);
                        if(!checkActive(map.keySet())) {
                            break;
                        }
                    }catch (Exception e){
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name + "完成");

            latch.countDown();
        }
    }


    private static void singleShortestPathDelta(long sourceId,int time) {
        Map<Long, List<Edge>> refMap = TGraph.strucLocalityDeltaSnapshot[time];

        setArr = Partition.partitionDeltaSnapshot(threadNum, listArr, refMap);

        //设置循环路障
        CyclicBarrier barrier = new CyclicBarrier(threadNum);

        //用来判断所有子线程是否结束
        CountDownLatch latch = new CountDownLatch(threadNum);

        //设置线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        //创建线程
        for (int i = 1; i <= threadNum; i++) {
            executor.submit(new Thread(new SSSPDeltaRunner(barrier, latch, "thread" + i, listArr[i - 1], setArr[i - 1], refMap,time)));
        }

        executor.shutdown();

        try {
            latch.await();//当latch中的值变为0，执行之后的语句
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class SSSPDeltaRunner implements Runnable{
        private CyclicBarrier barrier;
        private CountDownLatch latch;
        private String name;
        private List<Long> list;//虚拟快照分块
        private Set<Long> set;//增量快照分块
        private Map<Long, List<Edge>> refMap;
        private int time;

        public SSSPDeltaRunner(CyclicBarrier barrier, CountDownLatch latch, String name, List<Long> list, Set<Long> set, Map<Long, List<Edge>> refMap,int time) {
            this.barrier = barrier;
            this.latch = latch;
            this.name = name;
            this.list = list;
            this.set = set;
            this.refMap = refMap;
            this.time=time;
        }

        @Override
        public void run() {
            try {
                GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
                Map<Long, Vertex> vertexMap = graphSnapshot.getHashMap();

                //将增量顶点加入ssspMap
                for (Long vertexId : set) {
                    if (!ssspMap.containsKey(vertexId)) {
                        ssspMap.put(vertexId, new SSSPBean(Integer.MAX_VALUE, Integer.MAX_VALUE, false));
                    }
                    for (Edge edge : refMap.get(vertexId)) {
                        if (!ssspMap.containsKey(edge.getDesId())) {
                            ssspMap.put(edge.getDesId(), new SSSPBean(Integer.MAX_VALUE, Integer.MAX_VALUE, false));
                        }
                    }
                }

                //遍历增量的每个源顶点,“转移”pr值
                for (Long vertexId : set) {
                    if (list.contains(vertexId)) {//属于关联边
                        //源顶点的增量边集合
                        List<Edge> edges = refMap.get(vertexId);

                        //对增量边中的顶点进行松弛操作
                        for (Edge edge : edges) {
                            long newPathLength = ssspMap.get(vertexId).pathLength + edge.getWeight();
                            if(ssspMap.get(edge.getDesId()).pathLength>newPathLength){
                                ssspMap.get(edge.getDesId()).pathLength=newPathLength;
                                ssspMap.get(edge.getDesId()).flag=true;
                            }
                        }

                    }
                }
                barrier.await();
                System.out.println(System.currentTimeMillis()-Main.startTime);

                //开启bsp过程,全量迭代

                Map<Long, Vertex> map = TGraph.graphSnapshot.getHashMap();

                int iterations = 0;

                while (true) {
//                    if(iterations>0){//本地计算
//                        for(Long vertexId:list){
//                            SSSPBean bean = ssspMap.get(vertexId);
//                            if(bean.message<bean.pathLength){
//                                bean.pathLength=bean.message;
//                                bean.flag=true;
//                            }
//                        }
//
//                    }

                    for (Long vertexId : list) {//发消息
                        SSSPBean bean = ssspMap.get(vertexId);
                        if (bean.flag) {
                            List<VSEdge> vsEdges = map.get(vertexId).getOutGoingList();
                            for (VSEdge vsEdge : vsEdges) {
                                long newPathLength = bean.pathLength + vsEdge.getWeight(time);
                                ssspMap.get(vsEdge.getDesId()).message=Math.min(ssspMap.get(vsEdge.getDesId()).message,newPathLength);
                            }
                            if (refMap.containsKey(vertexId)) {
                                for (Edge edge : refMap.get(vertexId)) {//发送给增量边
                                    long newPathLength = bean.pathLength + edge.getWeight();
                                    if(newPathLength<ssspMap.get(edge.getDesId()).pathLength){
                                        ssspMap.get(edge.getDesId()).pathLength=newPathLength;
                                        ssspMap.get(edge.getDesId()).flag=true;
                                    }
                                }
                            }
                            bean.flag=false;
                        }
                    }
                    iterations++;
                    System.out.println(name+"----"+iterations);

                    //路障同步
                    barrier.await(500,TimeUnit.MILLISECONDS);
                    if(!checkActive(map.keySet()))
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name + "完成");
            latch.countDown();
        }
    }
}