package com.tg.graph;

import java.util.LinkedList;

public class Vertex{
	private int id;
	private int in_degree;
	private int out_degree;
	private LinkedList<Integer> outGoing;//�߱��㣬���������ʽ�洢�ö�����ָ��ı�
	public Vertex(int id){
		setId(id);
		in_degree=0;
		out_degree=0;
		outGoing=new LinkedList<Integer>();
	}
	
	public boolean addEdge(int v){//�ö���߱���뻡ͷ����id
		if(!outGoing.contains(v)){
			outGoing.add(v);
			out_degree++;
			return true;
		}
		return false;//�Ѵ���
	}
	public boolean deleteEdge(int v){//���붥��id
		int temp=outGoing.indexOf(v);
		if(temp!=-1){
			outGoing.remove(temp);//�˴������������
			out_degree--;
			return true;
		}
		return false;//��ɾ��
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
