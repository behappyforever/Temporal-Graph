package com.tg.graph;

import com.tg.utils.LoadTG;

public class TGraph {
	public static GraphSnapshot graphSnapshot;
	public static SnapshotLog[] snapshotLogArr;
//	public static ArrayList<double[]> pr;
	public static void start(){
		
		graphSnapshot = new GraphSnapshot();
		LoadTG.loadGraph();//加载初始图快照
		LoadTG.readRawLog();
		LoadTG.preCompute();//计算虚拟快照
		graphSnapshot.afterCompute();
		
		//计算增量快照 △S0+ ... △S9+ 共十个增量快照
		snapshotLogArr=new SnapshotLog[10];
		for(int i=0;i<10;i++) {
			snapshotLogArr[i]=new SnapshotLog();
		}
		//S0
//		snapshotLogArr[0].setAddVertex(LoadTG.deleteVertexArr.get(0));
		snapshotLogArr[0].setAddEdge(LoadTG.deleteEdgeArr.get(0));
		//S1-S9
		for(int i=1;i<10;i++) {
			snapshotLogArr[i].setAddVertex(snapshotLogArr[i-1].getAddVertex());
			snapshotLogArr[i].setAddEdge(snapshotLogArr[i-1].getAddEdge());
			snapshotLogArr[i].setDeleteVertex(snapshotLogArr[i-1].getDeleteVertex());
			snapshotLogArr[i].setDeleteEdge(snapshotLogArr[i-1].getDeleteEdge());
			
			snapshotLogArr[i].setAddVertex(LoadTG.addVertexArr.get(i));
			snapshotLogArr[i].setAddEdge(LoadTG.addEdgeArr.get(i));
			snapshotLogArr[i].setDeleteVertex(LoadTG.deleteVertexArr.get(i));
			snapshotLogArr[i].setDeleteEdge(LoadTG.deleteEdgeArr.get(i));
		}
		
		
		//计算pagerank
//		PageRank.pageRank();
//		for(int i=0;i<10;i++){
//			Diameter.diameterCompute(i);
//		}
	}//start
}
