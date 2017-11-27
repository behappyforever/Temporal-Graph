package com.tg.function;

import java.util.ArrayList;

import com.tg.graph.TGraph;

public class Diameter{
	private static int inf=9999;
	public static void diameterCompute(int day){
		int numOfVertex=GetNumOfVertex.getNumOfVertex(day);
		int[][] matrix=new int[numOfVertex][numOfVertex];
		int[][] tempMatrix=Transform.getMatrix(day);
		for(int i=0;i<numOfVertex;i++){
			 for(int j=0;j<numOfVertex;j++){
				 matrix[i][j]=tempMatrix[i][j];
			 }
		 }
		int diameter=0;		
		 for (int k=0; k<numOfVertex; k++){
		        for (int i=0; i<numOfVertex; i++){
		            for (int j=0; j<numOfVertex; j++){
		                matrix[i][j] =Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
		            }
		        }
		 }
		 int maxi=0,maxj=0;
		 for(int i=0;i<numOfVertex;i++){
			 for(int j=0;j<numOfVertex;j++){
				 if(matrix[i][j]>diameter & matrix[i][j]!=inf){
//				 if(matrix[i][j]>diameter){
					 diameter=matrix[i][j];
					 maxi=i;
					 maxj=j;
				 }
			 }
		 }
		 TGraph.graph[day].setDiameter(diameter);
		 TGraph.graph[day].setPath(TGraph.graph[day].getVertexId().get(maxi));
//		 System.out.print("图直径为：");
//		 System.out.println(diameter);
//		 System.out.println("路径为：");
//		 System.out.print(TGraph.graph[day].getVertexId().get(maxi)+"->");
		 ArrayList<Integer> temp=new ArrayList<Integer>();
		 temp=Dijkstra.computeShortestPath(day, maxi,maxj);
//		 int a=0;
		 for(int i=temp.size()-1;i>=0;i--){
			 TGraph.graph[day].setPath(TGraph.graph[day].getVertexId().get(temp.get(i)));
//			 a++;
//			 if(a>10){
//				 System.out.println();
//				 a=0;
//			 }
//			 System.out.print(TGraph.graph[day].getVertexId().get(temp.get(i))+"->");
		 }
		 TGraph.graph[day].setPath(TGraph.graph[day].getVertexId().get(maxj));
//		 System.out.println(TGraph.graph[day].getVertexId().get(maxj));
	}
}
