package com.tg.graph;

import com.tg.utils.LoadTG;

public class TGraph {
//	public static GraphSnapshot graphSnapshot;
	public static Snapshot snapshot;
	public static SnapshotLog[] snapshotLogArr;
//	public static ArrayList<double[]> pr;
	public static void start(){
		
		snapshot = new Snapshot();
		LoadTG.loadGraph();//���س�ʼͼ����
		LoadTG.readRawLog();//��ȡ��־
		LoadTG.preCompute();//�����������
		snapshot.afterCompute();
		
		//������������ ��S0+ ... ��S9+ ��ʮ����������
		snapshotLogArr=new SnapshotLog[10];
		for(int i=0;i<10;i++) {
			snapshotLogArr[i]=new SnapshotLog();
		}
		//S0
//		snapshotLogArr[0].setAddVertex(LoadTG.deleteVertexArr.get(0));
		for(int i=0,j=0;i<10;i++) {//i���Ƶڼ�����������,j���Ƶڼ����仯��־
			while(j<9&&j<i) {	
				snapshotLogArr[i].setAddEdge(LoadTG.addEdgeArr.get(j));
				j++;
			}
			while(j<9&&j>=i) {
				snapshotLogArr[i].setAddEdge(LoadTG.deleteEdgeArr.get(j));
				j++;
			}
		}
		
		
		//����pagerank
//		PageRank.pageRank();
//		for(int i=0;i<10;i++){
//			Diameter.diameterCompute(i);
//		}
	}//start
}
