package temporalGraph.graph;

public class vsEdge {
    private long desId;
    private long[] weight;//保存VS中带时间戳的权值

    public vsEdge(long id, long[] w){
        desId=id;
        weight=w;
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
