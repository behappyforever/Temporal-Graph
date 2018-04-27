package com.tg.graph;

import java.util.ArrayList;

import com.tg.utils.LoadTG;
import com.tg.utils.LogToGraph;

public class TGraph {
	public static final int timeRange=10;
	public static String fileName;
	public static ArrayList<String> addEdgeList;
	public static ArrayList<String> delEdgeList;
	public static GraphSnapshot graphSnapshot;
	public static SnapshotLog[] snapshotLogArr;
	public static GraphSnapshot[] deltaGraphSnapshotArr;

	public static void loadDataSetsPath() {
		// 加载数据集路径
		TGraph.fileName = "DataSets/test.txt";
		TGraph.addEdgeList = new ArrayList<String>();
		TGraph.delEdgeList = new ArrayList<String>();
		for (int i = 1; i < 10; i++) {
			TGraph.addEdgeList.add("DataSets/addEdgesDay" + String.valueOf(i) + ".txt");
			TGraph.delEdgeList.add("DataSets/deleteEdgesDay" + String.valueOf(i) + ".txt");
		}
	}

	public static void start() {

		LoadTG.loadGraph();// 加载初始图快照
		LoadTG.readRawLog();// 读取日志
		LoadTG.computeVirtualGraphSnapshot();// 计算虚拟图快照
		LoadTG.afterComputeVS();//对虚拟快照做一些处理
		LoadTG.computeDeltaSnapshotLog();//计算增量日志快照
		
		LogToGraph.transform();//将增量日志快照转变成增量图快照
		
	}
}
