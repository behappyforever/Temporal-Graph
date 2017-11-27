package com.tg.function;

import java.util.ArrayList;

import com.tg.graph.TGraph;

public class Transform {
	private static int inf=9999;
	public static int[][] getMatrix(int day){
		int numOfVertex=GetNumOfVertex.getNumOfVertex(day);
		int[][] matrix=new int[numOfVertex][numOfVertex];
		for(int i=0;i<numOfVertex;i++){
			for(int j=0;j<numOfVertex;j++){
				matrix[i][j]=inf;
			}
		}
		ArrayList<Integer> vertex=new ArrayList<Integer>();
		vertex.addAll(TGraph.graph[day].getVertexId());
		ArrayList<Integer> outEdge=new ArrayList<Integer>();
		ArrayList<Integer> inEdge=new ArrayList<Integer>();
		int index;
		for(int i=0;i<vertex.size();i++){
			outEdge.addAll(PointQuery.pointQueryOutDegree(day, vertex.get(i)));
			for(int j=0;j<outEdge.size();j++){
				index=vertex.indexOf(outEdge.get(j));
				if(index!=-1){
					matrix[i][index]=1;
				}
			}
			outEdge.clear();
			inEdge.addAll(PointQuery.pointQueryInDegree(day, vertex.get(i)));
			for(int j=0;j<outEdge.size();j++){
				index=vertex.indexOf(outEdge.get(j));
				if(index!=-1){
					matrix[index][i]=1;
				}
			}
			inEdge.clear();
		}
		for(int i=0;i<numOfVertex;i++){
			matrix[i][i]=0;
		}
//		for(int i=0;i<numOfVertex;i++){
//			for(int j=0;j<numOfVertex;j++){
//				if(matrix[i][j]==inf){
//					matrix[i][j]=matrix[j][i];
//				}
//			}
//		}
		return matrix;
	}
}
//		if(TGraph.graph[day].getFlag()==true){
//		}
//			int id=0;
//			int index=0;
//			for(int p=0;p<numOfVertex;p++){
//				Iterator<Integer> iterator=TGraph.graph[day].getArrayList().get(p).getLinkedList().iterator();
//				while (iterator.hasNext()) {
//					System.err.println("d");
//					id=iterator.next();
//					for(int i=0;i<numOfVertex;i++){
//						if(TGraph.graph[day].getArrayList().get(i).getId()==id){
//							index=i;
//							matrix[p][index]=1;
//							break;
//						}
//					}
//				}
//			}
//		}
//		else{
//			
