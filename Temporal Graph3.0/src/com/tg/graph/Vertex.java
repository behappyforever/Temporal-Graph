package com.tg.graph;

import java.util.Iterator;
import java.util.LinkedList;

public class Vertex{
	private int id;
	private LinkedList<Integer> outGoing;//�߱��㣬���������ʽ�洢�ö�����ָ��ı�
	public Vertex(int id){
		setId(id);
		outGoing=new LinkedList<Integer>();
	}
	public boolean addEdge(int v){
		if(!outGoing.contains(v)){
			outGoing.add(v);
			return true;
		}
		return false;//�Ѵ���
	}
	public boolean deleteEdge(int v){//���붥��id
//		if(linkedList.contains(v))
//		   linkedList.remove(v);
		int temp=outGoing.indexOf(v);
		if(temp!=-1){
			outGoing.remove(temp);
			return true;
		}
		return false;//��ɾ��
	}
	public LinkedList<Integer> getLinkedList(){
		return outGoing;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLinkedList(LinkedList<Integer> link){
		Iterator<Integer> iterator=link.iterator();
		while(iterator.hasNext()){
			outGoing.add(iterator.next());
		}
	}
}
