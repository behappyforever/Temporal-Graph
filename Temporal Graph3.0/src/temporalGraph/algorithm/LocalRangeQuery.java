package temporalGraph.algorithm;

import temporalGraph.graph.Edge;
import temporalGraph.graph.TGraph;
import temporalGraph.graph.vsEdge;

import java.util.Arrays;
import java.util.List;

public class LocalRangeQuery {


    public static long[] twoHopNeighborQuery(long sourceVertex){//默认算一组快照
        long tmp = twoHopNeighborVS(sourceVertex);
        long[] res=new long[TGraph.timeRange];
        Arrays.fill(res,tmp);

        long[] tmpArr = deltaTwoHopNeighbor(sourceVertex);
        for (int i = 0; i < TGraph.timeRange; i++) {
            res[i]+=tmpArr[i];
        }
        return res;
    }

    //2跳邻居原始结果计算(VS)
    private static long twoHopNeighborVS(long sourceVertex) {
        long res = 0;
        List<vsEdge> tmpRef = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合

        for (vsEdge e : tmpRef) {
            res += TGraph.graphSnapshot.getNeighborNum(e.getDesId());
        }
        return res;
    }

    private static long[] deltaTwoHopNeighbor(long sourceVertex) {

        int n = TGraph.timeRange;

        long[] res = new long[n];

        for (int i = 0; i < n; i++) {
            new Thread(new WorkThread(i, sourceVertex, res)).start();
        }

        return res;

    }

    static class WorkThread implements Runnable {

        private int timePoint;
        private long sourceVertex;
        private long[] res;

        public WorkThread(int t, long s, long[] r) {
            timePoint = t;
            sourceVertex = s;
            res = r;
        }

        @Override
        public void run() {
            List<vsEdge> vsEdgeList = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合

            for (vsEdge e : vsEdgeList) {
                List<Edge>[] listArr = TGraph.timeLocalityDeltaSnapshot.get(e.getDesId());
                if(listArr!=null){
                    if(listArr[timePoint]!=null){
                        res[timePoint] += listArr[timePoint].size();
                    }
                }
            }

            List<Edge> edgeList = TGraph.timeLocalityDeltaSnapshot.get(sourceVertex)[timePoint];
            if(edgeList!=null) {
                for (Edge e : edgeList) {
                    res[timePoint] += TGraph.graphSnapshot.getNeighborNum(e.getDesId());
                    List<Edge>[] listArr = TGraph.timeLocalityDeltaSnapshot.get(e.getDesId());
                    if (listArr != null) {
                        if (listArr[timePoint] != null) {
                            res[timePoint] += listArr[timePoint].size();
                        }
                    }
                }
            }
        }
    }
}

