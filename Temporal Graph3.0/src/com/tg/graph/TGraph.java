package com.tg.graph;

import com.tg.utils.LoadTG;

public class TGraph {
//	public static GraphSnapshot graphSnapshot;
	public static Snapshot snapshot;
	public static SnapshotLog[] snapshotLogArr;
//	public static ArrayList<double[]> pr;
	public static void start(){
		
		snapshot = new Snapshot();
		LoadTG.loadGraph();//加载初始图快照
		LoadTG.readRawLog();//读取日志
		LoadTG.preCompute();//计算虚拟快照
		snapshot.afterCompute();
		
		//计算增量快照 △S0+ ... △S9+ 共十个增量快照
		snapshotLogArr=new SnapshotLog[10];
		for(int i=0;i<10;i++) {
			snapshotLogArr[i]=new SnapshotLog();
		}
		//S0
//		snapshotLogArr[0].setAddVertex(LoadTG.deleteVertexArr.get(0));
		for(int i=0,j=0;i<10;i++) {//i控制第几个增量快照,j控制第几个变化日志
			while(j<9&&j<i) {	
				snapshotLogArr[i].setAddEdge(LoadTG.addEdgeArr.get(j));
				j++;
			}
			while(j<9&&j>=i) {
				snapshotLogArr[i].setAddEdge(LoadTG.deleteEdgeArr.get(j));
				j++;
			}
		}
		
		
		//计算pagerank
//		PageRank.pageRank();
//		for(int i=0;i<10;i++){
//			Diameter.diameterCompute(i);
//		}
	}//start
}
