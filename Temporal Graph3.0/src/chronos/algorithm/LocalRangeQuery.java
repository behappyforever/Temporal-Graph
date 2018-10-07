package chronos.algorithm;

import chronos.graph.TGraph;

public class LocalRangeQuery {

    public static void twoHopNeighborQuery(long sourceVertex) {

        LocalPointQuery.twoHopNeighborVS(sourceVertex);

        for(int i = 0; i< TGraph.timeRange-1; i++){
            LocalPointQuery.twoHopNeighborQuery(sourceVertex,i);
        }
    }
}
