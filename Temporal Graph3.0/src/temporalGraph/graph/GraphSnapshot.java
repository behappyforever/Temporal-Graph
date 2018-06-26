package temporalGraph.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class GraphSnapshot {
	private int numOfVertex;//顶点数
	private int numOfEdges;//边数
	private int iterations;

	
	private Map<Long, Vertex> vertexMap;//为了更快地找到顶点对象

	public GraphSnapshot() {
		vertexMap = new HashMap();
	}

	public boolean addEdge(String str) {

		Scanner sc = new Scanner(str);
		long from=sc.nextLong();
		long to=sc.nextLong();
		long[] weightList=new long[TGraph.timeRange];
		for (int i = 0; i < weightList.length; i++) {
			weightList[i]=sc.nextLong();
		}
		if (!vertexMap.containsKey(from)) {// 顶点不存在，先创建
			vertexMap.put(from, new Vertex(from));
		}
		if (!vertexMap.containsKey(to)) {// 顶点不存在，先创建
			vertexMap.put(to, new Vertex(to));
		}
		boolean flag=vertexMap.get(from).addEdge(to,weightList);// 找到from的出边表，加入to
		if(flag) {
			Vertex temp=vertexMap.get(to);
			temp.setIn_degree(temp.getIn_degree()+1);
			return true;
		}
		return false;

	}

	public boolean deleteEdge(int from, int to) {
		Object o = vertexMap.get(from);
		if (o != null) {// 源顶点存在
			Vertex vertex = (Vertex) o;
			boolean flag= vertex.deleteEdge(to);
			if(flag) {
				Vertex temp=vertexMap.get(to);
				temp.setIn_degree(temp.getIn_degree()-1);
				return true;
			}
		}
		return false;

	}
	public int getNumOfVertex() {
		return numOfVertex;
	}

	public int getNumOfEdges() {
		return numOfEdges;
	}
	
	public void countVerAndEdgeNum() {
		numOfVertex=vertexMap.keySet().size();
		numOfEdges=0;
		for(Entry<Long,Vertex> en :vertexMap.entrySet()) {
			numOfEdges+=en.getValue().getOut_degree();//统计快照边数
		}
		System.out.println("顶点数:"+numOfVertex);
		System.out.println("边数:"+numOfEdges);
	}
	
	public Map<Long, Vertex> getHashMap(){
		return vertexMap;
	}
	
	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public long getNeighborNum(long vertexId){
		return vertexMap.get(vertexId).getOut_degree();
	}

	public List<vsEdge> getNeighborList(long vertexId){
		return vertexMap.get(vertexId).getOutGoingList();
	}
}
