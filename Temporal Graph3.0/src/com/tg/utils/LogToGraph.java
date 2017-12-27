package com.tg.utils;

import java.util.HashSet;
import java.util.StringTokenizer;
import com.tg.graph.GraphSnapshot;

public class LogToGraph {//log转变成图
	
	public static GraphSnapshot[] logTographSnapshotArr=new GraphSnapshot[10];
//	public static void init() {
//		logTographSnapshotArr=new GraphSnapshot[10];
//		for(int i=0;i<10;i++) {
//			logTographSnapshotArr[i]=new GraphSnapshot();
//		}
//	}
//	
	public static void transform(int day,HashSet<String> addEdge) {
		logTographSnapshotArr[day]=new GraphSnapshot();
		StringTokenizer token;
		for(String string:addEdge) {
			token=new StringTokenizer(string);
			logTographSnapshotArr[day].addEdge(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
		}
		logTographSnapshotArr[day].countVerAndEdgeNum();
		System.out.println("转换后的顶点数:"+logTographSnapshotArr[day].getNumOfVertex());
		System.out.println("转换后的边数:"+logTographSnapshotArr[day].getNumOfEdges());
	}

}

