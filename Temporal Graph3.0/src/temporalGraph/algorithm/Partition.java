package temporalGraph.algorithm;

import temporalGraph.graph.TGraph;
import temporalGraph.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Partition {

    public static List<Long>[] partitionVS( int n){
        Map<Long, Vertex> vertexMap = TGraph.graphSnapshot.getHashMap();

        Set<Long> set = vertexMap.keySet();

        List<Long> list=new ArrayList(set);

        List<Long>[] listArr=new List[n];//每个list存放每个线程需要处理的顶点Id

        for (int i = 0; i < n; i++) {
            listArr[i]=new ArrayList();
        }

        for (int i = 0; i < list.size(); i++) {
            listArr[i%6].add(list.get(i));
        }

        return listArr;

    }
}
