package com.tg.utils;

import java.util.Map.Entry;

import com.tg.graph.GraphSnapshot;
import com.tg.graph.TGraph;
import com.tg.graph.Vertex;

public class MergeLogIntoGraph {
	public static void mergeLogIntoGraph(int day) {
		GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
		GraphSnapshot deltaGraphSnapshot = TGraph.deltaGraphSnapshotArr[day];
		for (Entry<Long, Vertex> en : deltaGraphSnapshot.getHashMap().entrySet()) {
			if (graphSnapshot.getHashMap().containsKey(en.getKey())) {
				for (Long l : en.getValue().getOutGoingList()) {
					graphSnapshot.addEdge(en.getKey(), l);
				}
			}else {
				graphSnapshot.getHashMap().put(en.getKey(), new Vertex(en.getKey()));
				for (Long l : en.getValue().getOutGoingList()) {
					graphSnapshot.addEdge(en.getKey(), l);
				}
				graphSnapshot.getHashMap().get(en.getKey()).setPr(en.getValue().getPr());
			}
		}

		System.out.println("合并后顶点数和边数");
		graphSnapshot.countVerAndEdgeNum();
	}
}
