package chronos.algorithm;

import chronos.graph.TGraph;

public class GlobalRangeQuery {

    public static void pageRank() {

        GlobalPointQuery.pageRankVS();

        for (int i = 0; i < TGraph.timeRange-1; i++) {
            GlobalPointQuery.pageRankDeltaSnapshot(i);
        }
    }

    public static void singleShortestPath(long sourceId) {

        GlobalPointQuery.singleShortestPathVS(sourceId,0);

        for (int i = 0; i < TGraph.timeRange-1; i++) {
            GlobalPointQuery.singleShortestPathDelta(sourceId,i);
        }
    }
}
