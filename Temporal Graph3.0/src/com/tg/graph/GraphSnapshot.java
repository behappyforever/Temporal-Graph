package com.tg.graph;

import java.util.ArrayList;
import java.util.Iterator;

public class GraphSnapshot {//����ͼ

	//���ջ�����Ϣ
	private int numOfVertex;//������
	private int numOfEdges;//����
//	private int diameter;
//	private ArrayList<Integer> path=new ArrayList<Integer>();
//	private double[] pr;
//	private double sumOfPr;
//	private int iterations;
	
	private ArrayList<Integer> vertexId;//����ID
	private ArrayList<Vertex> vertexList;//�����
	
	public GraphSnapshot() {
		vertexId=new ArrayList<Integer>();
		vertexList=new ArrayList<Vertex>();
	}
	
	public void afterCompute() {//���ؿ���֮����һЩ���㹤��
		//����vertexid��
		Iterator<Vertex> iterator=vertexList.iterator();
		while(iterator.hasNext()) {
			vertexId.add(iterator.next().getId());
		}
	}
	
	
	//������Ϣget set
	public int getNumOfVertex(){
		return vertexList.size();
	}
	public void setNumOfVertex(int numOfVertex){
		this.numOfVertex=numOfVertex;
	}
	public int getNumOfEdges() {
		return numOfEdges;
	}
	public void setNumOfEdges(int numOfEdges) {
		this.numOfEdges = numOfEdges;
	}
	public ArrayList<Integer> getVertexId() {
		return vertexId;
	}
//	public void setVertexId(Set<Integer> set) {
//		vertexId.addAll(set);
//	}
	public ArrayList<Vertex> getVertexList(){
		return vertexList;
	}
	public void setVertexList(int numOfEdges){
		this.numOfEdges=numOfEdges;
	}
//	public int getDiameter() {
//		return diameter;
//	}
//	public void setDiameter(int diameter) {
//		this.diameter = diameter;
//	}
//	public void setPath(int id) {
//		path.add(id);
//	}

//	public double[] getPr() {
//		return pr;
//	}
//	public void setPr(double[] temp) {
//		pr=new double[temp.length];
//		for(int i=0;i<temp.length;i++)
//			pr[i]=temp[i];
//	}
//	public double getSumOfPr() {
//		return sumOfPr;
//	}
//	public void setSumOfPr(double sumOfPr) {
//		this.sumOfPr = sumOfPr;
//	}
//	public int getIterations() {
//		return iterations;
//	}
//	public void setIterations(int iterations) {
//		this.iterations = iterations;
//	}
	
//	public ArrayList<Integer> getPath() {
//		return path;
//	}
	
	


	
//	public LinkedList<Integer> getOutDegree(int id){//���ظö�����߼���
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==id){
//				return vertexList.get(i).getLinkedList();
//			}
//		}
//		return null;
//	}
//	
//	public ArrayList<Integer> getInDegree(int v){//���ظö�����߼���
//		ArrayList<Integer> arrayTemp=new ArrayList<Integer>();
//		Vertex vTemp;
//		Iterator<Vertex> vertexTemp=vertexList.iterator();
//		while(vertexTemp.hasNext()){
//			vTemp=vertexTemp.next();
//			Iterator<Integer> edgeTemp=vTemp.getLinkedList().iterator();
//			while(edgeTemp.hasNext()){
//				if(edgeTemp.next()==v)
//					arrayTemp.add(vTemp.getId());
//			}
//		}
//		return arrayTemp;
//	}
//	
//	public boolean addVertex(int id){
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==id){
//				return false;//�ö����Ѿ�����
//			}
//		}
//		Vertex temp=new Vertex(id);
//		vertexList.add(temp);
//		return true;
//	}
//	
//	public boolean deleteVertex(int id){
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==id){//�ö�����ڣ��ɹ�ɾ��
//				vertexList.remove(i);
//				numOfEdges-=vertexList.get(i).getLinkedList().size();
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean edgeIsExist(int from,int to){
//		int temp=0;
//		int p=-1;
//		int q=-1;
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==from){
//				p=i;
//				break;
//			}
//		}
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==to){
//				q=i;
//				break;
//			}
//		}
//		if(p!=-1 && q!=-1){
//			temp=vertexList.get(p).getLinkedList().indexOf(to);
//			if(temp!=-1)
//				return true;
//		}
//		return false;
//		
//			
//		
//	}
	public boolean addEdge(int from,int to){//���㲻���ڣ��޷��ӱߣ��ȼӵ㣬�ټӱ�
		int temp;
		int p=-1;
		int q=-1;
		for(int i=0;i<vertexList.size();i++){
			if(vertexList.get(i).getId()==from){
				p=i;
				break;
			}
		}
		for(int i=0;i<vertexList.size();i++){
			if(vertexList.get(i).getId()==to){
				q=i;
				break;
			}
		}
		if(p!=-1 && q!=-1){//�������㶼����
			temp=vertexList.get(p).getLinkedList().indexOf(to);
			if(temp==-1){//�ñ߲����ڣ�����ӳɹ�
				vertexList.get(p).getLinkedList().add(to);
				numOfEdges++;
				return true;
			}
			else
				return false;//�ñ��Ѵ���
		}
		return false;//�������㲻������
	}
	
	public boolean deleteEdge(int from,int to){
		int temp;
		int p=-1;
		int q=-1;
		for(int i=0;i<vertexList.size();i++){
			if(vertexList.get(i).getId()==from){
				p=i;
				break;
			}
		}
		for(int i=0;i<vertexList.size();i++){
			if(vertexList.get(i).getId()==to){
				q=i;
				break;
			}
		}
		if(p!=-1 && q!=-1){//�������㶼����
			temp=vertexList.get(p).getLinkedList().indexOf(to);
			if(temp!=-1){//�ñߴ��ڣ���ɾ���ɹ�
				vertexList.get(p).getLinkedList().remove(temp);//careful
				numOfEdges--;
				return true;
			}
			else
				return false;//�ñ߲����ڣ�ɾ��ʧ��
		}
		return false;//�������㲻������
	}
//	
//	public boolean vertexIsExist(int id){
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==id){
//				return true;
//			}
//		}
//		return false;
//	}

	
	



}

