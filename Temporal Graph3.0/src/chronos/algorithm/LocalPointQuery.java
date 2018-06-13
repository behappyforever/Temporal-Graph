package chronos.algorithm;

import chronos.graph.TGraph;

public class LocalPointQuery {

    public static long query(long vertexId,int time){
        long res=0;
        res+=TGraph.graphSnapshot.getNeighborNum(vertexId);//VS
        res+=TGraph.deltaGraphSnapshotArr[time].getNeighborNum(vertexId);//增量快照
        return res;
    }
}
