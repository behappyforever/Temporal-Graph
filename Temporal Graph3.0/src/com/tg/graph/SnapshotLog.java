package com.tg.graph;

import java.util.HashSet;

public class SnapshotLog {//相对于虚拟快照VS的delta+增量快照
	private HashSet<String> addEdge;

	public SnapshotLog() {
		addEdge=new HashSet();
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
