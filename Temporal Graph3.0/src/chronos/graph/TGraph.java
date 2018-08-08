package chronos.graph;

import chronos.utils.LoadTG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TGraph {
	public static final int timeRange=10;
	public static String fileName;//初始图文件路径
	public static List<String> logList;//原始日志路径
	//时序图所有组件    1个组的
	public static GraphSnapshot graphSnapshot;//全图快照
	public static List<HashSet<String>> logArr;// 0 1,1 2,2 3...


	public static GraphSnapshot[] deltaGraphSnapshotArr;//增量快照 与上面日志对应
	public static Map<Long,List[]> timeLocalityDeltaSnapshot;//增量快照的时间局部性布局


	private static void loadDataSetsPath() {
		// 加载数据集路径
		TGraph.fileName = "DataSets/test.txt";
		TGraph.logList = new ArrayList();
		for (int i = 1; i < timeRange; i++) {
			TGraph.logList.add("DataSets/day" +i+ ".txt");
		}
	}

	public static void start() {
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
