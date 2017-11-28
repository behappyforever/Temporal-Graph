package com.tg.graph;

import java.util.LinkedList;

public class Vertex{
	private int id;
	private int in_degree;
	private int out_degree;
	private LinkedList<Integer> outGoing;//边表结点，以链表的形式存储该顶点所指向的边
	public Vertex(int id){
		setId(id);
		in_degree=0;
		out_degree=0;
		outGoing=new LinkedList<Integer>();
	}
	
	public boolean addEdge(int v){//该顶点边表加入弧头顶点id
		if(!outGoing.contains(v)){
			outGoing.add(v);
			out_degree++;
			return true;
		}
		return false;//已存在
	}
	public boolean deleteEdge(int v){//传入顶点id
		int temp=outGoing.indexOf(v);
		if(temp!=-1){
			outGoing.remove(temp);//此处传入的是索引
			out_degree--;
			return true;
		}
		return false;//已删除
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIn_degree() {
		return in_degree;
	}
	
	public void setIn_degree(int in_degree) {
		this.in_degree = in_degree;
	}
	
	public int getOut_degree() {
		return out_degree;
	}
	
	public void setOut_degree(int out_degree) {
		this.out_degree = out_degree;
	}
	public LinkedList<Integer> getOutGoingList(){
		return outGoing;
	}
	public void setOutGoingList(LinkedList<Integer> link){
		outGoing.addAll(link);
	}

}
