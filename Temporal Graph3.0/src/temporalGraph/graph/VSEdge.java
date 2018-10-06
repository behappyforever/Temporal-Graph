package temporalGraph.graph;

import java.util.*;

public class VSEdge {
    private long desId;
//    private long[] weight;//保存VS中带时间戳的权值,未压缩前
    private long[] weightDisplay;//权值表
    private byte[] weightIndex;//对应到权值表的索引


    public VSEdge(long id, long[] weightArr){
        desId=id;
        Set<Long> set=new HashSet();
        for (long w : weightArr) {
            set.add(w);
        }
        weightDisplay=new long[set.size()];
        Iterator<Long> it = set.iterator();
        for(int i=0;i<weightDisplay.length;i++){
            weightDisplay[i]=it.next();
        }
        weightIndex=new byte[TGraph.timeRange];
        for (int i = 0; i < weightArr.length; i++) {
            for(int k=0;k<weightDisplay.length;k++){
                if(weightArr[i]==weightDisplay[k]){
                    weightIndex[i]=(byte)k;
                    break;
                }
            }
        }
    }

    public long getDesId() {
        return desId;
    }

    public void setDesId(long desId) {
        this.desId = desId;
    }

    public long getWeight(int time){
        return weightDisplay[weightIndex[time]];
    }

//    public long getWeight() {
//        return weight;
//    }
//
//    public void setWeight(long weight) {
//        this.weight = weight;
//    }
}
