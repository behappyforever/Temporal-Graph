package temporalGraph.algorithm;

import temporalGraph.graph.*;

import java.util.HashMap;
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
        pageRankDeltaSnapshot(time);
    }

    /**
     * 虚拟快照 page rank
     */
    private static void pageRankVS() {

        //存放顶点pr值
        prValueMap = new ConcurrentHashMap();

        resetPr(TGraph.graphSnapshot,prValueMap);

        //用ConcurrentHashMap存储消息,可以被多个线程并发访问
        messageMap = new ConcurrentHashMap();

        //划分虚拟快照
        listArr = Partition.partitionVS(threadNum);

        //设置循环路障
        CyclicBarrier barrier = new CyclicBarrier(threadNum);

        //用来判断所有子线程是否结束
        CountDownLatch latch=new CountDownLatch(threadNum);

        //设置线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        //创建线程
        for (int i = 1; i <= threadNum; i++) {
            executor.submit(new Thread(new PageRankRunner(barrier,latch, "thread" + i, listArr[i - 1])));
        }

        executor.shutdown();

        try {
            latch.await();//当latch中的值变为0，执行之后的语句
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //所有子线程结束，输出pagerank值
        double total=0.0;
        for (Double aDouble : prValueMap.values()) {
            total+=aDouble;
        }
        System.out.println(total);
    }

    /**
     * 实现每个线程的执行逻辑
     */
    static class PageRankRunner implements Runnable {
        private CyclicBarrier barrier;
        private CountDownLatch latch;
        private String name;
        private List<Long> list;//虚拟快照分块

        public PageRankRunner(CyclicBarrier barrier,CountDownLatch latch, String name, List<Long> list) {
            this.barrier = barrier;
            this.latch= latch;
            this.name = name;
            this.list = list;
        }

        @Override
        public void run() {
            try {
                GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
                Map<Long, Vertex> vertexMap = graphSnapshot.getHashMap();
                int numOfVertex = graphSnapshot.getHashMap().size();

                int iterations = 1;


                while (iterations <= maxStep) {
                    if (iterations > 1) {//第一个超步不需要本地计算
                        for (Long vertexId : list) {
                            double total = messageMap.get(vertexId);
                            prValueMap.put(vertexId,(1 - alpha) * (1.0f / numOfVertex) + alpha * total);
                        }
                    }


                    //清空消息缓冲
                    for (Long vertexId : list) {
                        messageMap.put(vertexId, 0.0);
                    }


                    //发消息
                    for (Long vertexId : list) {
                        Vertex v = vertexMap.get(vertexId);
                        List<vsEdge> outGoingList = v.getOutGoingList();
                        if (outGoingList.size() == 0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
                            for (Map.Entry<Long, Double> en : messageMap.entrySet()) {
                                messageMap.put(en.getKey(), en.getValue() + prValueMap.get(vertexId) / (numOfVertex - 1));
                            }
                            messageMap.put(vertexId, messageMap.get(vertexId) -  prValueMap.get(vertexId) / (numOfVertex - 1));
                        } else {// 如果该点出度不为0，则将pr值平分给其出边顶点
                            for (vsEdge e : outGoingList) {
                                messageMap.put(e.getDesId(), messageMap.get(e.getDesId()) +prValueMap.get(vertexId) / outGoingList.size());
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


    private static void resetPr(GraphSnapshot graphSnapshot,Map<Long,Double> prValueMap) {
        for (Long id : graphSnapshot.getHashMap().keySet()) {
            prValueMap.put(id,1.0/graphSnapshot.getHashMap().size());
        }
    }

    private static void pageRankDeltaSnapshot(int time) {

        Map<Long, List<Edge>> refMap = TGraph.strucLocalityDeltaSnapshot[time];

        setArr=Partition.partitionDeltaSnapshot(threadNum,listArr,refMap);

    }

    static class PageRankDeltaRunner implements Runnable {

        @Override
        public void run() {
            
        }
    }
}