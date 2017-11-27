package com.tg.function;

import java.util.HashSet;
import java.util.Set;

import com.tg.graph.TGraph;

public class GetNumOfVertex {
	public static int getNumOfVertex(int day) {
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(TGraph.graphSnapshot.getVertexId());
		System.out.println(set.size());
		set.addAll(TGraph.snapshotLogArr[day].getAddVertex());
		set.removeAll(TGraph.snapshotLogArr[day].getDeleteVertex());
		return set.size();
	}
}
