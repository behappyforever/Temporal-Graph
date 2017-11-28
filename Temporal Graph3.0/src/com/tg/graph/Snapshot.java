package com.tg.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Snapshot {
	private int numOfVertex;//������
	private int numOfEdges;//����

	private HashMap<Integer, Vertex> vertexMap;

	public Snapshot() {
		vertexMap = new HashMap<Integer, Vertex>();
	}

	public void afterCompute() {// ���ؿ���֮����һЩ���㹤��
		ArrayList<Integer> delTemp=new ArrayList<Integer>();
		for(Entry<Integer,Vertex> en : vertexMap.entrySet()) {//ɾ�������ĵ�
			numOfEdges+=en.getValue().getOut_degree();
			if(en.getValue().getIn_degree()==0&&en.getValue().getOut_degree()==0) {
				delTemp.add(en.getKey());
			}
		}
		for(Integer key:delTemp) {
			vertexMap.remove(key);
		}
		numOfVertex=vertexMap.keySet().size();
		System.out.println(numOfVertex);
		System.out.println(numOfEdges);
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
}
