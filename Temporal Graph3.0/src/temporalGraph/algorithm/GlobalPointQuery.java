package temporalGraph.algorithm;

import temporalGraph.graph.GraphSnapshot;
import temporalGraph.graph.TGraph;
import temporalGraph.graph.Vertex;
import temporalGraph.graph.vsEdge;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GlobalPointQuery {

    private static int threadNum = 6;

    //PageRank
    private static final double threshold = 0.0000000000000001;// 越小要求精度越高，迭代次数越大 10的-5
    private static final double alpha = 0.85f;
    private static final int maxStep = 30;

    private static Map<Long, Double> messageMap;

    public static void pageRank(int time) {
        pageRankVS();
    }

    //虚拟快照pagerank
    private static void pageRankVS() {

        resetPr(TGraph.graphSnapshot);

        //用ConcurrentHashMap存储消息,可以被多个线程并发访问
        messageMap = new ConcurrentHashMap();
        //划分数据
        List<Long>[] listArr = Partition.partitionVS(threadNum);

        //设置循环路障
        CyclicBarrier barrier = new CyclicBarrier(threadNum);

        //设置线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        //创建线程
        for (int i = 1; i <= threadNum; i++) {
            executor.submit(new Thread(new PageRankRunner(barrier, "thread" + i, listArr[i - 1])));
        }

        executor.shutdown();
    }

    static class PageRankRunner implements Runnable {
        private CyclicBarrier barrier;
        private String name;
        private List<Long> list;

        public PageRankRunner(CyclicBarrier barrier, String name, List<Long> list) {
            this.barrier = barrier;
            this.name = name;
            this.list = list;
        }

        @Override
        public void run() {
            try {
                GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
                Map<Long, Vertex> vertexMap = graphSnapshot.getHashMap();
                int numOfVertex = graphSnapshot.getNumOfVertex();

                int iterations = 1;


                while (iterations <= maxStep) {
                    if (iterations > 1) {//第一个超步不需要本地计算
                        for (Long vertexId : list) {
                            Vertex v = vertexMap.get(vertexId);
                            double total=0.0;
                            if(messageMap.get(vertexId)!=null){
                                total=messageMap.get(vertexId);
                            }
                            v.setPr((1 - alpha) * (1.0f / numOfVertex) + alpha *total);
                        }
                    }


                    //清空消息缓冲
                    messageMap.clear();
                    for (Long vertexId : TGraph.graphSnapshot.getHashMap().keySet()) {
                        messageMap.put(vertexId, 0.0);
                    }


                    //发消息
                    for (Long vertexId : list) {
                        Vertex v = vertexMap.get(vertexId);
                        List<vsEdge> outGoingList = v.getOutGoingList();
                        if (outGoingList.size() == 0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
                            for (Map.Entry<Long, Double> en : messageMap.entrySet()) {
                                messageMap.put(en.getKey(), en.getValue() + v.getPr() / (numOfVertex - 1));
                            }
                            if(messageMap.get(vertexId)!=null){
                                messageMap.put(vertexId, messageMap.get(vertexId) - v.getPr() / (numOfVertex - 1));
                            }
                        } else {// 如果该点出度不为0，则将pr值平分给其出边顶点
                            for (vsEdge e : outGoingList) {
                                if(messageMap.get(e.getDesId())!=null) {
                                    messageMap.put(e.getDesId(), messageMap.get(e.getDesId()) + v.getPr() / outGoingList.size());
                                }
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
            System.out.println(name+"完成");
        }
    }


    public static void resetPr(GraphSnapshot graphSnapshot) {
        for (Map.Entry<Long, Vertex> en : graphSnapshot.getHashMap().entrySet()) {
            en.getValue().setPr(1.0f / graphSnapshot.getNumOfVertex());
        }
    }
}