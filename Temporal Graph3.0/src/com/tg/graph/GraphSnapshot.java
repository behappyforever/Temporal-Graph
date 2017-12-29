package com.tg.graph;

import java.util.HashMap;
import java.util.Map.Entry;

public class GraphSnapshot {
	private int numOfVertex;//������
	private int numOfEdges;//����
	private int iterations;

	
	private HashMap<Integer, Vertex> vertexMap;

	public GraphSnapshot() {
		vertexMap = new HashMap<Integer, Vertex>();
	}

	public boolean addEdge(int from, int to) {

		if (!vertexMap.containsKey(from)) {// ���㲻���ڣ��ȴ���
			vertexMap.put(from, new Vertex(from));
		}
		if (!vertexMap.containsKey(to)) {// ���㲻���ڣ��ȴ���
			vertexMap.put(to, new Vertex(to));
		}
		boolean flag=vertexMap.get(from).addEdge(to);// �ҵ�from�ĳ��߱�����to
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
		if (o != null) {// Դ�������
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
			numOfEdges+=en.getValue().getOut_degree();//ͳ�ƿ��ձ���
		}
		System.out.println("������:"+numOfVertex);
		System.out.println("����:"+numOfEdges);
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
