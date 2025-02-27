package chronos.graph;

import chronos.utils.LoadTG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TGraph {
	public static final int timeRange=10;
	public static String inputDir="";
	public static String fileName="";//初始图文件路径
	public static List<String> logList;//原始日志路径
	//时序图所有组件    1个组的
	public static GraphSnapshot graphSnapshot;//全图快照
	public static List<HashSet<String>> logArr;

	private static void loadDataSetsPath() {
		// 加载数据集路径
		fileName+=inputDir;
		fileName+="/data.txt";
		logList = new ArrayList();
		for (int i = 1; i < timeRange; i++) {
			logList.add(inputDir+"/day" +i+ ".txt");
		}
	}

	public static void start(String dir) {
		inputDir+=dir;
		loadDataSetsPath();
		LoadTG.loadGraph();// 加载初始图快照
		LoadTG.readRawLog();// 读取日志
//		LoadTG.computeVirtualGraphSnapshot();// 计算虚拟图快照
//		LoadTG.afterComputeVS();//对虚拟快照做一些处理
//		LoadTG.computeDeltaSnapshotLog();//计算增量日志快照
//
//		LogToGraph.transform();//将增量日志快照转变成增量图快照
//
//		TimeLocality.transform();//从结构局部性增量快照生成时间局部性增量快照
		
	}
}
