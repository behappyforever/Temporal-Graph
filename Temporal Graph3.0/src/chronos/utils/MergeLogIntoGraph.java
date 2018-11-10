package chronos.utils;

import java.util.HashSet;
import java.util.Map.Entry;

import chronos.graph.GraphSnapshot;
import chronos.graph.TGraph;
import chronos.graph.Vertex;

public class MergeLogIntoGraph {
    public static void mergeLogIntoGraph(int day) {
        try {
            GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
            HashSet<String> set = TGraph.logArr.get(day);
            for (String s : set) {
                if (s.charAt(0) == 'A') {
                    String[] split = s.substring(2).split("\t");
                    long sourceId = Long.parseLong(split[0]);
                    long desId = Long.parseLong(split[1]);
                    graphSnapshot.addEdge(sourceId, desId);
                }else{
                    String[] split = s.substring(2).split("\t");
                    long sourceId = Long.parseLong(split[0]);
                    long desId = Long.parseLong(split[1]);
                    graphSnapshot.deleteEdge(sourceId,desId);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println("合并后顶点数和边数");
//        graphSnapshot.countVerAndEdgeNum();
    }
}
