package com.tg.graph;

import java.util.HashSet;

public class SnapshotLog {
//	private HashSet<Integer> addVertex;// �洢�仯,��֮ǰ�����жϣ���������Ч�仯
//	private HashSet<Integer> deleteVertex;// ���仯�ۼƵ�һ��ֵ(100)ʱ�����µ�����ͼ����
	private HashSet<String> addEdge; // �仯������ɾ���������ȣ����Ⱥͼ����ӣ�ɾ������
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
