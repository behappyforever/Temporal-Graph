package chronos.algorithm;


import chronos.graph.TGraph;
import temporalGraph.graph.Edge;
import temporalGraph.graph.VSEdge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalPointQuery {


    //2跳邻居原始结果计算(VS)
    private static long twoHopNeighborVS(long sourceVertex) {
        long res = 0;
        List<Long> tmpRef = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合

        for (Long e : tmpRef) {
            res += TGraph.graphSnapshot.getNeighborNum(e);
        }
        return res;
    }

    //2跳邻居增量结果计算
    private static long deltaTwoHopNeighbor(long sourceVertex, int time) {
        long res = 0;
        List<Long> vsEdgeList = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合

        for (Long e : vsEdgeList) {
            Set<String> set = TGraph.logArr.get(time);

            for (String s : set) {
                
            }


            List<Edge> edgeList = TGraph.strucLocalityDeltaSnapshot[time].get(e.getDesId());
            if(edgeList!=null){//存在
                res += edgeList.size();
            }
        }

        List<Edge> edgeList = temporalGraph.graph.TGraph.strucLocalityDeltaSnapshot[time].get(sourceVertex);

        for (Edge e : edgeList) {
            res += temporalGraph.graph.TGraph.graphSnapshot.getNeighborNum(e.getDesId());
            List<Edge> tmp = temporalGraph.graph.TGraph.strucLocalityDeltaSnapshot[time].get(e.getDesId());
            if(tmp!=null){//存在
                res += tmp.size();
            }
        }
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
