package com.tg.graph;

import java.util.HashSet;

public class SnapshotLog {
	private HashSet<String> addEdge; // 变化数等于删除顶点的入度，出度和加增加，删除边数

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
