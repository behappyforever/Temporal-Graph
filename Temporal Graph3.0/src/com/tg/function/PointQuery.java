package com.tg.function;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import com.tg.graph.TGraph;

public class PointQuery {
	private static Set<Integer> set1,set2,set3;
	public static Set<Integer> pointQueryOutDegree(int day,int v){//存取快照的时间点
		set1=new HashSet<Integer>();
		if(TGraph.graph[day].getFlag()==true){
			if(TGraph.graph[day].getOutDegree(v)!=null)
				set1.addAll(TGraph.graph[day].getOutDegree(v));
		}
		else{
			int i=day-1;
			int pTemp,qTemp;
			StringTokenizer tokenizer;
			Iterator<String> addEdgeTemp;
			Iterator<String> deleteEdgeTemp;
			Iterator<Integer> deleteVertexTemp;
			while(TGraph.graph[i].getFlag()!=true)
				i--;
			set1.addAll(pointQueryOutDegree(i, v));
			for(int p=i+1;p<=day;p++){
				addEdgeTemp=TGraph.graph[p].getSnapLog().getAddEdge().iterator();
				while (addEdgeTemp.hasNext()) {
					tokenizer=new StringTokenizer(addEdgeTemp.next());
					pTemp=Integer.parseInt(tokenizer.nextToken());
					qTemp=Integer.parseInt(tokenizer.nextToken());
					if(pTemp==v){
						set1.add(qTemp);
					}
				}
				deleteEdgeTemp=TGraph.graph[p].getSnapLog().getDeleteEdge().iterator();
				while (deleteEdgeTemp.hasNext()) {
					tokenizer=new StringTokenizer(deleteEdgeTemp.next());
					pTemp=Integer.parseInt(tokenizer.nextToken());
					qTemp=Integer.parseInt(tokenizer.nextToken());
					if(pTemp==v){
						if(set1.contains(qTemp))
							set1.remove(qTemp);
					}
				}
				deleteVertexTemp=TGraph.graph[p].getSnapLog().getDeleteVertex().iterator();
				while(deleteVertexTemp.hasNext()){
					pTemp=deleteVertexTemp.next();
					if(pTemp==v){
						set1.clear();
					}
					if(set1.contains(pTemp)){
						set1.remove(pTemp);
					}
				}
			}
		}
		return set1;
	}
	
	public static Set<Integer> pointQueryInDegree(int day,int v){//存取快照的时间点
		set2=new HashSet<Integer>();
		set2.clear();
		if(TGraph.graph[day].getFlag()==true){
			set2.addAll(TGraph.graph[day].getInDegree(v));
		}
		else{
			int i=day-1;
			int pTemp,qTemp;
			StringTokenizer tokenizer;
			Iterator<String> addEdgeTemp;
			Iterator<String> deleteEdgeTemp;
			Iterator<Integer> deleteVertexTemp;
			while(TGraph.graph[i].getFlag()!=true)
				i--;
			set2.addAll(pointQueryInDegree(i, v));
			for(int p=i+1;p<=day;p++){
				addEdgeTemp=TGraph.graph[p].getSnapLog().getAddEdge().iterator();
				while (addEdgeTemp.hasNext()) {
					tokenizer=new StringTokenizer(addEdgeTemp.next());
					pTemp=Integer.parseInt(tokenizer.nextToken());
					qTemp=Integer.parseInt(tokenizer.nextToken());
					if(qTemp==v){
						set2.add(pTemp);
					}
				}
				deleteEdgeTemp=TGraph.graph[p].getSnapLog().getDeleteEdge().iterator();
				while (deleteEdgeTemp.hasNext()) {
					tokenizer=new StringTokenizer(deleteEdgeTemp.next());
					pTemp=Integer.parseInt(tokenizer.nextToken());
					qTemp=Integer.parseInt(tokenizer.nextToken());
					if(qTemp==v){
						if(set2.contains(pTemp))
							set2.remove(pTemp);
					}
				}
				deleteVertexTemp=TGraph.graph[p].getSnapLog().getDeleteVertex().iterator();
				while(deleteVertexTemp.hasNext()){
					pTemp=deleteVertexTemp.next();
					if(pTemp==v){
						set2.clear();
					}
					if(set2.contains(pTemp)){
						set2.remove(pTemp);
					}
				}
			}
		}
		return set2;
	}
	public static Set<Integer> pointQuery( ){
		set3=new HashSet<Integer>();
		int temp;
		Iterator<Integer> iterator=set1.iterator();
		while (iterator.hasNext()) {
			temp=iterator.next();
			if(set2.contains(temp)){
				set3.add(temp);
			}
		}
		return set3;
	}
}
