package com.tg.graph;

import java.util.HashSet;

public class SnapshotLog {//������������VS��delta+��������
	private HashSet<String> addEdge;

	public SnapshotLog() {
		addEdge=new HashSet<String>();
	}
	public HashSet<String> getAddEdge() {
		return addEdge;
	}
	public void setAddEdge(HashSet<String> hashSet) {
		addEdge.addAll(hashSet);
	}
	public int getAddEdgeSize() {
		return addEdge.size();
	}
}
