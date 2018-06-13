package chronos.graph;

public class Edge {
    private long desId;
    private long weight;

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
