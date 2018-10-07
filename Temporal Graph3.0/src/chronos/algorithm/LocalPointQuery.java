package chronos.algorithm;


import chronos.graph.TGraph;

import java.util.*;

public class LocalPointQuery {

    /**
     * 供外部调用的接口
     *
     * @param sourceVertex
     * @param time
     * @return
     */
    public static long twoHopNeighborQuery(long sourceVertex, int time) {
        long res = 0;

        res += twoHopNeighborVS(sourceVertex);//原始结果
        res += deltaTwoHopNeighbor(sourceVertex, time);
        return res;
    }

    //2跳邻居原始结果计算(VS)
    public static long twoHopNeighborVS(long sourceVertex) {
        long res = 0;
        List<Long> tmpRef = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合

        for (Long e : tmpRef) {
            res += TGraph.graphSnapshot.getNeighborNum(e);
        }
        return res;
    }

    //2跳邻居增量结果计算
    public static long deltaTwoHopNeighbor(long sourceVertex, int time) {
        long res = 0;
        List<Long> vsEdgeList = TGraph.graphSnapshot.getNeighborList(sourceVertex);//取到源点的1跳邻居集合
        Set<String> set = TGraph.logArr.get(time);
        Map<Long,Integer> addMap=new HashMap<>();
        Map<Long,Integer>  delMap=new HashMap<>();

        for (String s : set) {
            if (s.charAt(0) == 'A') {
                String[] split = s.substring(2).split(" ");
                try {
                    long currentVertexId = Long.parseLong(split[0]);
                    addMap.put(currentVertexId,addMap.getOrDefault(currentVertexId,0)+1);
                }catch (Exception e){
                }
            }else{
                String[] split = s.substring(2).split(" ");
                long currentVertexId = Long.parseLong(split[0]);
                delMap.put(currentVertexId,addMap.getOrDefault(currentVertexId,0)+1);
            }
        }

        for (Long e : vsEdgeList) {
            res+=addMap.getOrDefault(e,0);
            res-=delMap.getOrDefault(e,0);
//            for (String s : set) {
//                if (s.charAt(0) == 'A') {
//                    String[] split = s.substring(2).split(" ");
//                    long currentVertexId = Long.parseLong(split[0]);
//                    if (e == currentVertexId)
//                        res++;
//                } else {
//                    String[] split = s.substring(2).split(" ");
//                    long currentVertexId = Long.parseLong(split[0]);
//                    if (e == currentVertexId)
//                        res--;
//                }
//            }
        }

        for(String s:set){
            if(s.charAt(0)=='A'){
                String[] split = s.substring(2).split(" ");
                try {
                    long currentVertexId = Long.parseLong(split[0]);
                    if (sourceVertex == currentVertexId)
                        res++;
                }catch (Exception e){
                }
            }else {
                String[] split = s.substring(2).split(" ");
                long currentVertexId = Long.parseLong(split[0]);
                if (sourceVertex == currentVertexId){
                    long desId=Long.parseLong(split[1]);
                    if(TGraph.graphSnapshot.getHashMap().containsKey(desId)){
                        res-=TGraph.graphSnapshot.getNeighborList(desId).size();
                    }
                }
            }
        }


        return res;
    }
}
