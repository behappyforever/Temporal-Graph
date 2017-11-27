package com.tg.graph;

import java.util.ArrayList;
import java.util.Iterator;

public class GraphSnapshot {//快照图

	//快照基本信息
	private int numOfVertex;//顶点数
	private int numOfEdges;//边数
//	private int diameter;
//	private ArrayList<Integer> path=new ArrayList<Integer>();
//	private double[] pr;
//	private double sumOfPr;
//	private int iterations;
	
	private ArrayList<Integer> vertexId;//顶点ID
	private ArrayList<Vertex> vertexList;//顶点表
	
	public GraphSnapshot() {
		vertexId=new ArrayList<Integer>();
		vertexList=new ArrayList<Vertex>();
	}
	
	public void afterCompute() {//加载快照之后做一些计算工作
		//处理vertexid表
		Iterator<Vertex> iterator=vertexList.iterator();
		while(iterator.hasNext()) {
			vertexId.add(iterator.next().getId());
		}
	}
	
	
	//基本信息get set
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
	
	


	
//	public LinkedList<Integer> getOutDegree(int id){//返回该顶点出边集合
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==id){
//				return vertexList.get(i).getLinkedList();
//			}
//		}
//		return null;
//	}
//	
//	public ArrayList<Integer> getInDegree(int v){//返回该顶点入边集合
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
//				return false;//该顶点已经存在
//			}
//		}
//		Vertex temp=new Vertex(id);
//		vertexList.add(temp);
//		return true;
//	}
//	
//	public boolean deleteVertex(int id){
//		for(int i=0;i<vertexList.size();i++){
//			if(vertexList.get(i).getId()==id){//该顶点存在，成功删除
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
	public boolean addEdge(int from,int to){//顶点不存在，无法加边，先加点，再加边
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
		if(p!=-1 && q!=-1){//两个顶点都存在
			temp=vertexList.get(p).getLinkedList().indexOf(to);
			if(temp==-1){//该边不存在，则添加成功
				vertexList.get(p).getLinkedList().add(to);
				numOfEdges++;
				return true;
			}
			else
				return false;//该边已存在
		}
		return false;//两个顶点不都存在
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
		if(p!=-1 && q!=-1){//两个顶点都存在
			temp=vertexList.get(p).getLinkedList().indexOf(to);
			if(temp!=-1){//该边存在，则删除成功
				vertexList.get(p).getLinkedList().remove(temp);//careful
				numOfEdges--;
				return true;
			}
			else
				return false;//该边不存在，删除失败
		}
		return false;//两个顶点不都存在
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

