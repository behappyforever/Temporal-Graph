package com.tg.function;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import com.tg.graph.TGraph;

public class RangeQuery {
	private static Set<Integer> set1,set2,set3;
	public static Set<Integer> rangeQueryOutDegree(int from,int to,int vertex){
		//��һ����� from��to֮����ڴ洢���յ�ʱ������ǵ�һ��Ϊi��
		//����from��i֮���delete��¼��i��to��add��¼
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
		if(flag){//��һ�����
			set1.addAll(PointQuery.pointQueryOutDegree(i,vertex));
			for(int j=from;j<i;j++){//���ڹ��ͼ���set
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
				while (iterator2.hasNext()) {//ɾ������ĳ���
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
		else{//�ڶ��������from��to֮�䲻���ڴ洢�������յ�ʱ���
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
			for(int j=from;j<i;j++){//���ڹ��ͼ���set
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
				while (iterator2.hasNext()) {//ɾ������ĳ���
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
		else{//�ڶ��������from��to֮�䲻���ڴ洢�������յ�ʱ���
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
