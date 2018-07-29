package temporalGraph.graph;

import java.util.ArrayList;
import java.util.List;

public class VSEdge {
    private long desId;
//    private long[] weight;//保存VS中带时间戳的权值,未压缩前
    private List<Long> weightDisplay;//权值表
    private byte[] weightIndex;//对应到权值表的索引


    public VSEdge(long id, long[] weightArr){
        desId=id;
        weightDisplay=new ArrayList();
        weightIndex=new byte[TGraph.timeRange];
        for (int i = 0; i < weightArr.length; i++) {
            if(!weightDisplay.contains(weightArr[i])){//权值表不能有重复元素，不能用set
                weightDisplay.add(weightArr[i]);
            }
            weightIndex[i]=(byte) weightDisplay.indexOf(weightArr[i]);
        }
    }

    public long getDesId() {
        return desId;
    }

    public void setDesId(long desId) {
        this.desId = desId;
    }

//    public long getWeight() {
//        return weight;
//    }
//
//    public void setWeight(long weight) {
//        this.weight = weight;
//    }
}
