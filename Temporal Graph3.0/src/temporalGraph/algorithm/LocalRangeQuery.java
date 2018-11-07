package temporalGraph.algorithm;

import temporalGraph.graph.Edge;
import temporalGraph.graph.Main;
import temporalGraph.graph.TGraph;
import temporalGraph.graph.VSEdge;

import java.util.Arrays;
import java.util.List;

public class LocalRangeQuery {


    public static long[] twoHopNeighborQuery(long[] arr){//默认算一组快照
        long[] tmp = twoHopNeighborVS(arr);
        long[] res=new long[TGraph.timeRange];

        long[] tmpArr = deltaTwoHopNeighbor(arr);
        for (int i = 0; i < TGraph.timeRange; i++) {
            res[i]+=tmpArr[i];
        }
        return res;
    }

    //2跳邻居原始结果计算(VS)
    private static long[] twoHopNeighborVS(long[] arr) {

        long[] res = new long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            List<VSEdge> tmpRef = TGraph.graphSnapshot.getNeighborList(arr[i]);//取到源点的1跳邻居集合

            if(tmpRef!=null) {
                for (VSEdge e : tmpRef) {
                    res[i] += TGraph.graphSnapshot.getNeighborNum(e.getDesId());
                }
            }
        }
        return res;
    }

    private static long[] deltaTwoHopNeighbor(long[] arr) {

        int n = TGraph.timeRange;

        long[] res = new long[n];

        for (int i = 0; i < n; i++) {
            new Thread(new WorkThread(i, arr, res)).start();
        }

        return res;

    }

    static class WorkThread implements Runnable {

        private int timePoint;
        private long[] arr;
        private long[] res;

        public WorkThread(int t, long[] s, long[] r) {
            timePoint = t;
            arr = s;
            res = r;
        }

        @Override
        public void run() {
            for (int i = 0; i < arr.length; i++) {
                List<VSEdge> vsEdgeList = TGraph.graphSnapshot.getNeighborList(arr[i]);//取到源点的1跳邻居集合
                if(vsEdgeList!=null) {
                    for (VSEdge e : vsEdgeList) {
                        List<Edge>[] listArr = TGraph.timeLocalityDeltaSnapshot.get(e.getDesId());
                        if (listArr != null) {
                            if (listArr[timePoint] != null) {
                                res[timePoint] += listArr[timePoint].size();
                            }
                        }
                    }
                }
                List<Edge>[] lists = TGraph.timeLocalityDeltaSnapshot.get(arr[i]);
                if(lists!=null) {
                    List<Edge> edgeList = lists[timePoint];
                    if (edgeList != null) {
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
    }

    public static long[] oneHopNeighborQuery(long[] arr){//默认算一组快照
        long[] rtn = oneHopNeighborVS(arr);

//        deltaOneHopNeighbor(arr,rtn);

        return rtn;
    }

    private static long[] oneHopNeighborVS(long[] arr) {
        long[] rtn=new long[arr.length];
        for (int i = 0; i < rtn.length; i++) {
            rtn[i]=TGraph.graphSnapshot.getNeighborNum(arr[i]);
        }
        return rtn;
    }

    private static long[] deltaOneHopNeighbor(long[] arr,long[] rtn) {
        int n = TGraph.timeRange;

        long[] res = new long[n];

        for (int i = 0; i < n; i++) {
            new Thread(new WorkThreadOneHop(i, arr, rtn)).start();
        }

        return res;
    }

    static class WorkThreadOneHop implements Runnable{
        private int timePoint;
        private long[] arr;
        private long[] rtn;

        public WorkThreadOneHop(int t, long[] a, long[] r) {
            timePoint = t;
            arr = a;
            rtn = r;
        }

        @Override
        public void run() {
            for (int i = 0; i < arr.length; i++) {

                List<Edge>[] lists = TGraph.timeLocalityDeltaSnapshot.get(arr[i]);
                if (lists != null) {

                    List<Edge> edges = lists[timePoint];
                    if (edges != null) {
                        rtn[timePoint] += edges.size();
                    }
                }
            }
        }
    }
}

