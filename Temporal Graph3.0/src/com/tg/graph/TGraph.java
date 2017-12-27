package com.tg.graph;

import com.tg.utils.LoadTG;

public class TGraph {
//	public static GraphSnapshot graphSnapshot;
	public static GraphSnapshot graphSnapshot;
	public static SnapshotLog[] snapshotLogArr;
//	public static ArrayList<double[]> pr;
	public static void start(){
		
		graphSnapshot = new GraphSnapshot();
		LoadTG.loadGraph();//加载初始图快照
		LoadTG.readRawLog();//读取日志
		LoadTG.preCompute();//计算虚拟快照
		graphSnapshot.afterCompute();
		
		//计算增量快照 △S0+ ... △S9+ 共十个增量快照
		snapshotLogArr=new SnapshotLog[10];
		for(int i=0;i<10;i++) {
			snapshotLogArr[i]=new SnapshotLog();
		}
		
		for(int i=0;i<10;i++) {//i控制第几个增量快照,j控制第几个变化日志
			for(int j=0;j<i;j++) {
				snapshotLogArr[i].setAddEdge(LoadTG.addEdgeArr.get(j));
			}
			for(int j=i;j<9;j++) {
				snapshotLogArr[i].setAddEdge(LoadTG.deleteEdgeArr.get(j));
			}
		}
		System.out.println("增量日志边数:"+snapshotLogArr[0].getAddEdgeSize());
		System.out.println("增量日志边数:"+snapshotLogArr[1].getAddEdgeSize());
		System.out.println("增量日志边数:"+snapshotLogArr[2].getAddEdgeSize());
		System.out.println("增量日志边数:"+snapshotLogArr[3].getAddEdgeSize());
		//计算pagerank
//		PageRank.pageRank();
//		for(int i=0;i<10;i++){
//			Diameter.diameterCompute(i);
//		}
	}//start
}
