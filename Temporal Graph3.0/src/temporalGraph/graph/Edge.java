package temporalGraph.graph;

public class Edge {
    private long desId;
    private long weight;//保存该边的权值

    public Edge(long id,long w){
        desId=id;
        weight=w;
    }

    public long getDesId() {
        return desId;
    }

    public void setDesId(long desId) {
        this.desId = desId;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
