package chronos.utils;

import chronos.graph.GraphSnapshot;
import chronos.graph.TGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class LoadTG {

    public static void loadGraph() {// 读初始图

        TGraph.graphSnapshot = new GraphSnapshot();
        BufferedReader br = null;
        try {
            File file = new File(TGraph.fileName);
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {// 按行读入原始图数据
                String[] split = str.split("\t");
                TGraph.graphSnapshot.addEdge(Long.parseLong(split[0]), Long.parseLong(split[1]));
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

    public static void readRawLog() {
        TGraph.logArr = new ArrayList();
        for (int i = 0; i < 9; i++) {// 9个日志
            TGraph.logArr.add(new HashSet());
        }

        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            for (int i = 0; i < 9; i++) {
                file = new File(TGraph.logList.get(i));
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String str;
                while ((str = br.readLine()) != null) {
                    TGraph.logArr.get(i).add(str);
                }
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
            }
        }
    }

//	public static void computeVirtualGraphSnapshot() {
//		Iterator<String> iterStr;
//		StringTokenizer stringTokenizer;
//		// }
//		for (int i = 0; i < 9; i++) {
//			iterStr = deleteEdgeArr.get(i).iterator();
//			while (iterStr.hasNext()) {
//				stringTokenizer = new StringTokenizer(iterStr.next());
//				TGraph.graphSnapshot.deleteEdge(Integer.parseInt(stringTokenizer.nextToken()),
//						Integer.parseInt(stringTokenizer.nextToken()));
//			}
//		}
//	}
//
//	public static void afterComputeVS() {// 加载快照之后做一些计算工作
//		GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
//		Map<Long, Vertex> vertexMap = graphSnapshot.getHashMap();
//		List<Long> delTemp = new ArrayList();
//		for (Entry<Long, Vertex> en : vertexMap.entrySet()) {// 删除孤立的点
//			if (en.getValue().getIn_degree() == 0 && en.getValue().getOut_degree() == 0) {
//				delTemp.add(en.getKey());
//			}
//		}
//		for (Long key : delTemp) {
//			vertexMap.remove(key);
//		}
//		System.out.println("虚拟快照顶点数和边数");
//		graphSnapshot.countVerAndEdgeNum();
//	}

//	public static void computeDeltaSnapshotLog() {
//		// 计算增量快照 △S0+ ... △S9+ 共十个增量快照
//		TGraph.snapshotLogArr = new SnapshotLog[10];
//		for (int i = 0; i < 10; i++) {
//			TGraph.snapshotLogArr[i] = new SnapshotLog();
//		}
//
//		for (int i = 0; i < 10; i++) {// i控制第几个增量快照,j控制第几个变化日志
//			for (int j = 0; j < i; j++) {
//				TGraph.snapshotLogArr[i].setAddEdge(LoadTG.addEdgeArr.get(j));
//			}
//			for (int j = i; j < 9; j++) {
//				TGraph.snapshotLogArr[i].setAddEdge(LoadTG.deleteEdgeArr.get(j));
//			}
//		}
//		System.out.println("增量日志边数:" + TGraph.snapshotLogArr[0].getAddEdgeSize());
//		System.out.println("增量日志边数:" + TGraph.snapshotLogArr[1].getAddEdgeSize());
//		System.out.println("增量日志边数:" + TGraph.snapshotLogArr[2].getAddEdgeSize());
//		System.out.println("增量日志边数:" + TGraph.snapshotLogArr[3].getAddEdgeSize());
//	}
}