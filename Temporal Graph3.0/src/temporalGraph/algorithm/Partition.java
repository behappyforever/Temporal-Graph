package temporalGraph.algorithm;

import temporalGraph.graph.Edge;
import temporalGraph.graph.TGraph;
import temporalGraph.graph.Vertex;

import java.util.*;

public class Partition {

    /**
     *
     * @param n 表示划分的块数
     * @return 返回一个list数组    每个list存放每个线程需要处理的顶点Id
     */
    public static List<Long>[] partitionVS(int n){
        Map<Long, Vertex> vertexMap = TGraph.graphSnapshot.getHashMap();

        Set<Long> set = vertexMap.keySet();

        List<Long>[] listArr=new List[n];

        for (int i = 0; i < n; i++) {
            listArr[i]=new ArrayList();
        }

        long[] load=new long[n];

        for (Long vertexId : set) {
            int index=getMinIndex(load);
            listArr[index].add(vertexId);
            load[index]+=vertexMap.get(vertexId).getOut_degree();
        }

        return listArr;

    }

    /**
     * 返回最小负载的块的下标
     */
    private static int getMinIndex(long[] load){
        int rtn=0;
        long minLoad=load[0];
        for(int i=1;i<load.length;i++){
            if(minLoad>load[i]){
                minLoad=load[i];
                rtn=i;
            }
        }
        return rtn;
    }

    public static Set<Long>[] partitionDeltaSnapshot(int n,List<Long>[] listArr,Map<Long, List<Edge>> refMap){

        Set<Long>[] resSet=new Set[n];

        for (int i = 0; i < n; i++) {
            resSet[i]=new HashSet();
        }

        long[] load=new long[n];

        Set<Long> set=new HashSet(refMap.keySet());

        Iterator<Long> it = set.iterator();

        //源点在VS中
        while(it.hasNext()){
            long vertexId=it.next();
            for(int i=0;i<n;i++){
                if(listArr[i].contains(vertexId)){
                    resSet[i].add(vertexId);
                    it.remove();
                    break;
                }
            }
        }

        //源点不在VS中的，暂时先简单划分
        while(it.hasNext()){
            long vertexId=it.next();
            int index=getMinIndex(load);
            resSet[index].add(vertexId);
            load[index]+=refMap.get(vertexId).size();
        }

        return resSet;
    }
}
