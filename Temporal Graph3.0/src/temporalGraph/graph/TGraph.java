package temporalGraph.graph;

import temporalGraph.utils.LoadTG;
import temporalGraph.utils.LogToGraph;
import temporalGraph.utils.TimeLocality;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TGraph {
	public static final int timeRange=10;
	private static String vsFileName;//初始图文件路径
	public static List<String> deltaLogList;//原始日志路径
	//时序图所有组件    1个组的
	public static GraphSnapshot graphSnapshot;//全图快照VS
	public static SnapshotLog[] snapshotLogArr;//增量日志数组 n个
	public static GraphSnapshot[] deltaGraphSnapshotArr;//增量快照 与上面日志对应
	public static Map<Long,List[]> timeLocalityDeltaSnapshot;//增量快照的时间局部性布局


	public static void loadDataSetsPath() {
		// 加载数据集路径
		vsFileName = "Persistence/VS.txt";
		deltaLogList = new ArrayList();
		for (int i = 1; i < timeRange; i++) {
			deltaLogList.add("Persistence/day" +i+ ".txt");
		}
	}

	public static void start() {
		loadDataSetsPath();
		loadVS();// 加载VS
		readDeltaLog();// 读取增量日志
		LoadTG.computeVirtualGraphSnapshot();// 计算虚拟图快照
		LoadTG.afterComputeVS();//对虚拟快照做一些处理
		LoadTG.computeDeltaSnapshotLog();//计算增量日志快照
		
		LogToGraph.transform();//将增量日志快照转变成增量图快照

		TimeLocality.transform();//从结构局部性增量快照生成时间局部性增量快照
		
	}


	public static void loadVS() {// 读VS

		graphSnapshot = new GraphSnapshot();
		BufferedReader br = null;
		try {
			File file = new File(vsFileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String str;
			while ((str = br.readLine()) != null) {// 按行VS
				graphSnapshot.addEdge(str);//交给addEdge解析string
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // if
		} // finally
	}

	private static void readDeltaLog() {
		snapshotLogArr=new SnapshotLog[timeRange];//组织内存数据布局 todo
//		deleteEdgeArr = new ArrayList();
//		for (int i = 0; i < 9; i++) {// 9个日志
//			addEdgeArr.add(new HashSet());
//			deleteEdgeArr.add(new HashSet());
//		}
//
//		File file = null;
//		FileReader fr = null;
//		BufferedReader br = null;
//		try {
//			for (int i = 0; i < 9; i++) {
//				file = new File(TGraph.addEdgeList.get(i));
//				fr = new FileReader(file);
//				br = new BufferedReader(fr);
//				String str;
//				while ((str = br.readLine()) != null) {
//					addEdgeArr.get(i).add(str);
//				}
//			}
//			for (int i = 0; i < 9; i++) {
//				file = new File(TGraph.delEdgeList.get(i));
//				fr = new FileReader(file);
//				br = new BufferedReader(fr);
//				String str;
//				while ((str = br.readLine()) != null) {
//					deleteEdgeArr.get(i).add(str);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

}
