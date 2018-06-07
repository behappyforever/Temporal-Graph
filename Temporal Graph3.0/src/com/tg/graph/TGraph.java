package com.tg.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tg.utils.LoadTG;
import com.tg.utils.LogToGraph;
import com.tg.utils.TimeLocality;

public class TGraph {
	public static final int timeRange=10;
	public static String fileName;
	public static ArrayList<String> addEdgeList;
	public static ArrayList<String> delEdgeList;
	//时序图所有组件    1个组的
	public static GraphSnapshot graphSnapshot;//全图快照
	public static SnapshotLog[] snapshotLogArr;//增量日志数组 n个
	public static GraphSnapshot[] deltaGraphSnapshotArr;//增量快照 与上面日志对应
	public static Map<Long,List[]> timeLocalityDeltaSnapshot;//增量快照的时间局部性布局


	public static void loadDataSetsPath() {
		// 加载数据集路径
		TGraph.fileName = "DataSets/test.txt";
		TGraph.addEdgeList = new ArrayList();
		TGraph.delEdgeList = new ArrayList();
		for (int i = 1; i < timeRange; i++) {
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

		TimeLocality.transform();//从结构局部性增量快照生成时间局部性增量快照
		
	}
}
