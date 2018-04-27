package com.tg.function;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tg.graph.TGraph;
import com.tg.graph.Vertex;

//求PageRank时用到
public class GetVertexId {
	public static Set<Integer> getVertexId(int day){
		Set<Integer> set=new HashSet<Integer>();
		set.clear();
		if(TGraph.graph[day].getFlag()==true){
			Iterator<Vertex> iterator=TGraph.graph[day].getVertexList().iterator();
			while(iterator.hasNext()){
				int id=iterator.next().getId();
				set.add(id);
			}
		}
		else{
			int i=day-1;
			Iterator<Integer> addVertexTemp;
			Iterator<Integer> deleteVertexTemp;
			while(TGraph.graph[i].getFlag()!=true)
				i--;
			Iterator<Vertex> iterator=TGraph.graph[i].getVertexList().iterator();
			while(iterator.hasNext()){
				set.add(iterator.next().getId());
			}
			for(int p=i+1;p<=day;p++){
				addVertexTemp=TGraph.graph[p].getSnapLog().getAddVertex().iterator();
				while(addVertexTemp.hasNext()){
					set.add(addVertexTemp.next());
				}
				deleteVertexTemp=TGraph.graph[p].getSnapLog().getDeleteVertex().iterator();
				while(deleteVertexTemp.hasNext()){
					set.remove(deleteVertexTemp.next());
				}
			}
		}
		return set;
	}
}
