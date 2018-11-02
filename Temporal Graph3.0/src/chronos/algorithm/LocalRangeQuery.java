package chronos.algorithm;

import chronos.graph.TGraph;

public class LocalRangeQuery {

    public static void twoHopNeighborQuery(long[] arr) {

        LocalPointQuery.twoHopNeighborVS(arr);

        for(int i = 0; i< TGraph.timeRange-1; i++){
            LocalPointQuery.deltaTwoHopNeighbor(arr,i);
        }
    }
    public static void oneHopNeighborQuery(long[] arr ) {

        LocalPointQuery.oneHopNeighborVS(arr);

        for(int i = 0; i< TGraph.timeRange-1; i++){
            LocalPointQuery.deltaOneHopNeighbor(arr,i);
        }
    }
}
