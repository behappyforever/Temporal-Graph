package com.tg.graph;

import java.util.HashSet;

public class SnapshotLog {
//	private HashSet<Integer> addVertex;// 存储变化,存之前进行判断，仅存入有效变化
//	private HashSet<Integer> deleteVertex;// 当变化累计到一定值(100)时，存新的完整图快照
	private HashSet<String> addEdge; // 变化数等于删除顶点的入度，出度和加增加，删除边数
//	private HashSet<String> deleteEdge;

	public SnapshotLog() {
//		addVertex=new HashSet<Integer>();
//		deleteVertex=new HashSet<Integer>();
		addEdge=new HashSet<String>();
//		deleteEdge=new HashSet<String>();
		
	}
	// get
//	public HashSet<Integer> getAddVertex() {
//		return addVertex;
//	}
//
//
//	public HashSet<Integer> getDeleteVertex() {
//		return deleteVertex;
//	}
//
//	public HashSet<String> getAddEdge() {
//		return addEdge;
//	}
//
//	public HashSet<String> getDeleteEdge() {
//		return deleteEdge;
//	}
//
//	//set
//	public void setAddVertex(HashSet<Integer> hashSet) {
//		addVertex.addAll(hashSet);
//	}
	public void setAddEdge(HashSet<String> hashSet) {
		addEdge.addAll(hashSet);
	}
//	public void setDeleteVertex(HashSet<Integer> hashSet) {
//		deleteVertex.addAll(hashSet);
//	}
//	public void setDeleteEdge(HashSet<String> hashSet) {
//		deleteEdge.addAll(hashSet);
//	}
//	
	// get set
	public int getAddEdgeSize() {
		return addEdge.size();
	}

//	public int getDeleteEdgeSize() {
//		return deleteEdge.size();
//	}
}
