package com.tg.graph;

import java.util.HashMap;
import java.util.Map.Entry;

public class GraphSnapshot {
	private int numOfVertex;//顶点数
	private int numOfEdges;//边数
	private int iterations;

	
	private HashMap<Integer, Vertex> vertexMap;

	public GraphSnapshot() {
		vertexMap = new HashMap<Integer, Vertex>();
	}

	public boolean addEdge(int from, int to) {

		if (!vertexMap.containsKey(from)) {// 顶点不存在，先创建
			vertexMap.put(from, new Vertex(from));
		}
		if (!vertexMap.containsKey(to)) {// 顶点不存在，先创建
			vertexMap.put(to, new Vertex(to));
		}
		boolean flag=vertexMap.get(from).addEdge(to);// 找到from的出边表，加入to
		if(flag) {
			Vertex temp=vertexMap.get(to);
			temp.setIn_degree(temp.getIn_degree()+1);
			return true;
		}else {
			return false;
		}
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
			}else {
				return false;
			}
		} else {
			return false;
		}
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
		for(Entry<Integer,Vertex> en :vertexMap.entrySet()) {
			numOfEdges+=en.getValue().getOut_degree();//统计快照边数
		}
		System.out.println("顶点数:"+numOfVertex);
		System.out.println("边数:"+numOfEdges);
	}
	
	public HashMap<Integer, Vertex> getHashMap(){
		return vertexMap;
	}
	
	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
}
