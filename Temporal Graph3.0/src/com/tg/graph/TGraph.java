package com.tg.graph;

import com.tg.utils.LoadTG;

public class TGraph {
//	public static GraphSnapshot graphSnapshot;
	public static GraphSnapshot graphSnapshot;
	public static SnapshotLog[] snapshotLogArr;
//	public static ArrayList<double[]> pr;
	public static void start(){
		
		graphSnapshot = new GraphSnapshot();
		LoadTG.loadGraph();//���س�ʼͼ����
		LoadTG.readRawLog();//��ȡ��־
		LoadTG.preCompute();//�����������
		graphSnapshot.afterCompute();
		
		//������������ ��S0+ ... ��S9+ ��ʮ����������
		snapshotLogArr=new SnapshotLog[10];
		for(int i=0;i<10;i++) {
			snapshotLogArr[i]=new SnapshotLog();
		}
		
		for(int i=0;i<10;i++) {//i���Ƶڼ�����������,j���Ƶڼ����仯��־
			for(int j=0;j<i;j++) {
				snapshotLogArr[i].setAddEdge(LoadTG.addEdgeArr.get(j));
			}
			for(int j=i;j<9;j++) {
				snapshotLogArr[i].setAddEdge(LoadTG.deleteEdgeArr.get(j));
			}
		}
		System.out.println("������־����:"+snapshotLogArr[0].getAddEdgeSize());
		System.out.println("������־����:"+snapshotLogArr[1].getAddEdgeSize());
		System.out.println("������־����:"+snapshotLogArr[2].getAddEdgeSize());
		System.out.println("������־����:"+snapshotLogArr[3].getAddEdgeSize());
		//����pagerank
//		PageRank.pageRank();
//		for(int i=0;i<10;i++){
//			Diameter.diameterCompute(i);
//		}
	}//start
}
