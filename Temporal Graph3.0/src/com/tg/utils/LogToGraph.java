package com.tg.utils;

import java.util.StringTokenizer;

import com.tg.graph.GraphSnapshot;
import com.tg.graph.TGraph;

public class LogToGraph {//log转变成图
	
	public static void transform() {
		TGraph.deltaGraphSnapshotArr=new GraphSnapshot[TGraph.timeRange];
		StringTokenizer token;
		for(int i=0;i<TGraph.timeRange;i++) {
			TGraph.deltaGraphSnapshotArr[i]=new GraphSnapshot();
			for(String string:TGraph.snapshotLogArr[i].getAddEdge()) {
				token=new StringTokenizer(string);
				TGraph.deltaGraphSnapshotArr[i].addEdge(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
			}
			System.out.println("增量图快照的顶点数和边数"+i);
			TGraph.deltaGraphSnapshotArr[i].countVerAndEdgeNum();
		}
	}

}

