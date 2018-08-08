package temporalGraph.algorithm;

import temporalGraph.graph.Edge;
import temporalGraph.graph.TGraph;
import temporalGraph.graph.VSEdge;

import java.util.List;

public class LocalPointQuery {

    private static long originalCostTime;
    private static long deltaCostTime;


    public static long oneHopNeighborQuery(long sourceVertex, int time) {//1跳邻居个数查询
        long res = 0;
        res += TGraph.graphSnapshot.getNeighborNum(sourceVertex);//VS
        res += TGraph.strucLocalityDeltaSnapshot[time].get(sourceVertex).size();
        return res;
    }


    //2跳邻居原始结果计算(VS)
    private static long twoHopNeighborVS(long sourceVertex) {
        long startTime=System.currentTimeMillis();

        long res = 0;
        List<VSEdge> tmpRef = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合

        for (VSEdge e : tmpRef) {
            res += TGraph.graphSnapshot.getNeighborNum(e.getDesId());
        }

        originalCostTime=System.currentTimeMillis()-startTime;
        return res;
    }

    //2跳邻居增量结果计算
    private static long deltaTwoHopNeighbor(long sourceVertex, int time) {

        long startTime=System.currentTimeMillis();

        long res = 0;
        List<VSEdge> vsEdgeList = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合

        for (VSEdge e : vsEdgeList) {
            List<Edge> edgeList = TGraph.strucLocalityDeltaSnapshot[time].get(e.getDesId());
            if(edgeList!=null){//存在
                res += edgeList.size();
            }
        }

        List<Edge> edgeList = TGraph.strucLocalityDeltaSnapshot[time].get(sourceVertex);

        for (Edge e : edgeList) {
            res += TGraph.graphSnapshot.getNeighborNum(e.getDesId());
            List<Edge> tmp = TGraph.strucLocalityDeltaSnapshot[time].get(e.getDesId());
            if(tmp!=null){//存在
                res += tmp.size();
            }
        }

        deltaCostTime=System.currentTimeMillis()-startTime;
        return res;
    }


    //2跳邻居查询最终结果
    public static long twoHopNeighborQuery(long sourceVertex, int time) {
        long res = 0;

        res += twoHopNeighborVS(sourceVertex);//原始结果
        res += deltaTwoHopNeighbor(sourceVertex, time);
        return res;
    }


}
