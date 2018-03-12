package com.tg.function;

import java.util.ArrayList;

import com.tg.graph.TGraph;

public class Dijkstra {
	private static int inf=9999;
	public static ArrayList<Integer> computeShortestPath(int day,int v,int end){
		int numOfVertex=GetNumOfVertex.getNumOfVertex(day);
		int[][] matrix=new int[numOfVertex][numOfVertex];
		int[][] tempMatrix=Transform.getMatrix(day);
		for(int i=0;i<numOfVertex;i++){
			 for(int j=0;j<numOfVertex;j++){
				 matrix[i][j]=tempMatrix[i][j];
			 }
		 }
		int[] dist=new int[numOfVertex];
		int[] prev=new int[numOfVertex];
		boolean[] s=new boolean[numOfVertex];
		for(int i=1;i<numOfVertex;i++){
			dist[i]=matrix[v][i];
			s[i]=false;
			if(dist[i]==inf){
				prev[i]=0;
			}
			else{
				prev[i]=v;
			}
		}
		dist[v]=0;
		s[v]=true;
		for(int i=1;i<numOfVertex-1;i++){
			int temp=inf;
			int u=v;
			for(int j=1;j<numOfVertex;j++){
				if((!s[j])&&(dist[j]<temp)){
					u=j;
					temp=dist[j];//找最小的
				}
			}
			s[u]=true;
			for(int j=1;j<numOfVertex;j++){
				if((!s[j])&&(matrix[u][j]<inf)){
					int newdist=dist[u]+matrix[u][j];
					if(newdist<dist[j]){
						dist[j]=newdist;
						prev[j]=u;
					}
				}
			}
		}
		ArrayList<Integer> arrayList=new ArrayList<Integer>();
		int pre=prev[end];
//		while(pre!=v){
//			arrayList.add(pre);
//			pre=prev[pre];
//		}
		for(int i=0;i<TGraph.graph[day].getDiameter()-1;i++){
			arrayList.add(pre);
			pre=prev[pre];
		}
		return arrayList;
	}
}
