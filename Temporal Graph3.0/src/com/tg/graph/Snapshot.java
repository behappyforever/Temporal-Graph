package com.tg.graph;

import java.util.HashMap;

public class Snapshot {
	private HashMap<Integer, Vertex> vertexArr;

	public Snapshot() {
		vertexArr = new HashMap<Integer, Vertex>();
	}

	public void afterCompute() {// ���ؿ���֮����һЩ���㹤��

	}

	public boolean addEdge(int from, int to) {

		if (!vertexArr.containsKey(from)) {// ���㲻���ڣ��ȴ���
			vertexArr.put(from, new Vertex(from));
		}
		if (!vertexArr.containsKey(to)) {// ���㲻���ڣ��ȴ���
			vertexArr.put(to, new Vertex(to));
		}
		return vertexArr.get(from).addEdge(to);// �ҵ�from�ĳ��߱�����to
	}

	public boolean deleteEdge(int from, int to) {
		Object o = vertexArr.get(from);
		if (o != null) {// Դ�������
			Vertex vertex = (Vertex) o;
			return vertex.deleteEdge(to);
		} else {
			return false;
		}
	}
}
