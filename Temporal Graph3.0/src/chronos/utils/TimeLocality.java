package chronos.utils;

import chronos.graph.SnapshotLog;
import chronos.graph.TGraph;

import java.util.*;

public class TimeLocality {

    //负责从结构局部性增量快照生成时间局部性增量快照
    public static void transform(){
        TGraph.timeLocalityDeltaSnapshot=new HashMap<>();
        Map<Long,List[]> map=TGraph.timeLocalityDeltaSnapshot;
        for(int i=0;i<TGraph.timeRange;i++){//i为组内时间
            SnapshotLog sl=TGraph.snapshotLogArr[i];
            Iterator<String> it = sl.getAddEdge().iterator();
            while(it.hasNext()){
                String[] split = it.next().split("\t");
                Long from=Long.parseLong(split[0]);
                Long to=Long.parseLong(split[1]);
                if(!map.containsKey(from)){
                    List[] l = new List[TGraph.timeRange];
                    l[i]=new ArrayList();
                    l[i].add(to);
                    map.put(from,l);
                }else{
                    List[] lt=map.get(from);
                    if(lt[i]==null){
                        lt[i]=new ArrayList();
                    }
                    lt[i].add(to);
                }
            }
        }
    }
}
