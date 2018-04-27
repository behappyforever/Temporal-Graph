package com.tg.function;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import com.tg.graph.TGraph;

public class RangeQuery {
	private static Set<Integer> set1,set2,set3;
	public static Set<Integer> rangeQueryOutDegree(int from,int to,int vertex){
		//第一种情况 from与to之间存在存储快照的时间戳，记第一个为i，
		//遍历from到i之间的delete记录和i到to的add记录
		set1=new HashSet<Integer>();
		set1.clear();
		int i;
		int p,q;
		boolean flag=false;
		StringTokenizer token;
		for(i=from;i<=to;i++){
			if(TGraph.graph[i].getFlag()==true){
				flag=true;
				break;
			}
		}
		if(flag){//第一种情况
			set1.addAll(PointQuery.pointQueryOutDegree(i,vertex));
			for(int j=from;j<i;j++){//存在过就加入set
				Iterator<String> iterator=TGraph.graph[j].getSnapLog().getDeleteEdge().iterator();
				while (iterator.hasNext()) {
					token=new StringTokenizer(iterator.next());
					p=Integer.parseInt(token.nextToken());
					q=Integer.parseInt(token.nextToken());
					if(p==vertex){
						set1.add(q);
					}
				}
				Iterator<Integer> iterator2=TGraph.graph[j].getSnapLog().getDeleteVertex().iterator();
				while (iterator2.hasNext()) {//删除顶点的出边
					p=iterator2.next();
					if(p==vertex){
						set1.addAll(PointQuery.pointQueryOutDegree(j, p));
					}
				}
			}
			for(int j=i+1;j<=to;j++){
				Iterator<String> iterator=TGraph.graph[j].getSnapLog().getAddEdge().iterator();
				while (iterator.hasNext()) {
					token=new StringTokenizer(iterator.next());
					p=Integer.parseInt(token.nextToken());
					q=Integer.parseInt(token.nextToken());
					if(p==vertex){
						set1.add(q);
					}
				}
			}
		 }//if
		else{//第二种情况，from到to之间不存在存储完整快照的时间戳
			set1.addAll(PointQuery.pointQueryOutDegree(from,vertex));
			for(int j=from+1;j<=to;j++){
				Iterator<String> iterator=TGraph.graph[j].getSnapLog().getAddEdge().iterator();
				while (iterator.hasNext()) {
					token=new StringTokenizer(iterator.next());
					p=Integer.parseInt(token.nextToken());
					q=Integer.parseInt(token.nextToken());
					if(p==vertex){
						set1.add(q);
					}  
				}
			}
		}//else
		return set1;
	}
	public static Set<Integer> rangeQueryInDegree(int from,int to,int vertex){
		set2=new HashSet<Integer>();
		set2.clear();
		int i;
		int p,q;
		boolean flag=false;
		StringTokenizer token;
		for(i=from;i<=to;i++){
			if(TGraph.graph[i].getFlag()==true){
				flag=true;
				break;
			}
		}
		if(flag){
			set2.addAll(PointQuery.pointQueryInDegree(i,vertex));
			for(int j=from;j<i;j++){//存在过就加入set
				Iterator<String> iterator=TGraph.graph[j].getSnapLog().getDeleteEdge().iterator();
				while (iterator.hasNext()) {
					token=new StringTokenizer(iterator.next());
					p=Integer.parseInt(token.nextToken());
					q=Integer.parseInt(token.nextToken());
					if(q==vertex){
						set2.add(p);
					}
				}
				Iterator<Integer> iterator2=TGraph.graph[j].getSnapLog().getDeleteVertex().iterator();
				while (iterator2.hasNext()) {//删除顶点的出边
					p=iterator2.next();
					if(p==vertex){
						set2.addAll(PointQuery.pointQueryInDegree(j, p));
					}
				}
			}
			for(int j=i+1;j<=to;j++){
				Iterator<String> iterator=TGraph.graph[j].getSnapLog().getAddEdge().iterator();
				while (iterator.hasNext()) {
					token=new StringTokenizer(iterator.next());
					p=Integer.parseInt(token.nextToken());
					q=Integer.parseInt(token.nextToken());
					if(q==vertex){
						set2.add(p);
					}
				}
			}
		}
		else{//第二种情况，from到to之间不存在存储完整快照的时间戳
			set2.addAll(PointQuery.pointQueryInDegree(from,vertex));
			for(int j=from+1;j<=to;j++){
				Iterator<String> iterator=TGraph.graph[j].getSnapLog().getAddEdge().iterator();
				while (iterator.hasNext()) {
					token=new StringTokenizer(iterator.next());
					p=Integer.parseInt(token.nextToken());
					q=Integer.parseInt(token.nextToken());
					if(q==vertex){
						set2.add(p);
					}  
				}
			}
		}//else
		return set2;
	}
	public static Set<Integer> rangeQuery( ){
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
