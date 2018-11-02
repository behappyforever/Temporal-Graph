package chronos.algorithm;


import chronos.graph.TGraph;

import java.util.*;

public class LocalPointQuery {

    /**
     * 供外部调用的接口
     *
     * @param
     * @param time
     * @return
     */
    public static long twoHopNeighborQuery(long[] arr, int time) {
        long res = 0;

        res += twoHopNeighborVS(arr);//原始结果
        res += deltaTwoHopNeighbor(arr, time);
        return res;
    }

    //2跳邻居原始结果计算(VS)
    public static long twoHopNeighborVS(long[] arr) {
        long res = 0;
        for (int i = 0; i < arr.length; i++) {
            List<Long> tmpRef = TGraph.graphSnapshot.getNeighborList(arr[i]);//取到源点的1跳邻居集合
            if (tmpRef != null) {
                for (Long e : tmpRef) {
                    res += TGraph.graphSnapshot.getNeighborNum(e);
                }
            }
        }
        return res;
    }

    //2跳邻居增量结果计算
    public static long deltaTwoHopNeighbor(long[] arr, int time) {
        long res = 0;
        List<XY> listA = new ArrayList<>();
        List<XY> listD = new ArrayList<>();
        Set<String> set = TGraph.logArr.get(time);
        try {
            for (String s : set) {
                if (s.charAt(0) == 'A') {
                    String[] split = s.substring(2).split("\t");
                    listA.add(new XY(Long.parseLong(split[0]), Long.parseLong(split[1])));

                } else {
                    String[] split = s.substring(2).split("\t");
                    listD.add(new XY(Long.parseLong(split[0]), Long.parseLong(split[1])));
                }
            }
        } catch (Exception e) {
        }
        for (int i = 0; i < arr.length; i++) {
            List<Long> vsEdgeList = TGraph.graphSnapshot.getNeighborList(arr[i]);//取到源点的1跳邻居集合
            Map<Long, Integer> addMap = new HashMap<>();
            Map<Long, Integer> delMap = new HashMap<>();
            for (XY xy : listA) {
                addMap.put(xy.source,addMap.getOrDefault(xy.source,0)+1);
            }
            for (XY xy : listD) {
                delMap.put(xy.source, addMap.getOrDefault(xy.source, 0) + 1);
            }
            if (vsEdgeList != null) {
                for (Long e : vsEdgeList) {
                    res += addMap.getOrDefault(e, 0);
                    res -= delMap.getOrDefault(e, 0);
                }
            }

            for (XY xy : listA) {
                if(xy.source==arr[i])
                    res++;
            }
            for (XY xy : listD) {
                if (TGraph.graphSnapshot.getHashMap().containsKey(xy.des)) {
                    res -= TGraph.graphSnapshot.getNeighborList(xy.des).size();
                }
            }
        }
        return res;
    }

    public static long oneHopNeighborQuery(long[] arr, int time) {
        long res = 0;

        oneHopNeighborVS(arr);//原始结果
        deltaOneHopNeighbor(arr, time);
        return res;

    }

    public static void oneHopNeighborVS(long[] arr) {

        for (int i = 0; i < arr.length; i++) {
            TGraph.graphSnapshot.getNeighborNum(arr[i]);
        }
    }

    public static long deltaOneHopNeighbor(long[] arr, int time) {
        long res = 0;
        Set<String> set = TGraph.logArr.get(time);
        List<XY> listA = new ArrayList<>();
        List<XY> listD = new ArrayList<>();
        try {
            for (String s : set) {
                if (s.charAt(0) == 'A') {
                    String[] split = s.substring(2).split("\t");
                    listA.add(new XY(Long.parseLong(split[0]), Long.parseLong(split[1])));

                } else {
                    String[] split = s.substring(2).split("\t");
                    listD.add(new XY(Long.parseLong(split[0]), Long.parseLong(split[1])));
                }
            }
        } catch (Exception e) {
        }
        for (long vertex : arr) {
            for (XY xy : listA) {
                if (xy.source == vertex)
                    res++;
            }
            for (XY xy : listD) {
                if (xy.des == vertex)
                    res--;
            }
        }
        return res;
    }

    static class XY {
        long source;
        long des;

        public XY(long source, long des) {
            this.source = source;
            this.des = des;
        }
    }
}
