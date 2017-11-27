package com.tg.graph;

import java.util.HashMap;

public class Snapshot {
	private HashMap<Integer, Vertex> vertexArr;
	public Snapshot() {
		vertexArr=new HashMap<Integer, Vertex>();
	}
	
	public void afterCompute() {//加载快照之后做一些计算工作

	}
	
	public boolean addEdge(int from,int to) {
		
		if(!vertexArr.containsKey(from)) {//顶点不存在，先创建
			vertexArr.put(from, new Vertex(from));
		}
		if(!vertexArr.containsKey(to)) {//顶点不存在，先创建
			vertexArr.put(to, new Vertex(to));
		}
		return vertexArr.get(from).addEdge(to);//找到from的出边表，加入to
	}
	public boolean delEdge(int from,int to) {
		if(vertexArr.containsKey(from)!=null) {
			
		}
		
		return false;
	}
}
