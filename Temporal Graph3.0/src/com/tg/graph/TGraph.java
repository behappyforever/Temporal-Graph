package com.tg.graph;

import java.util.ArrayList;

import com.tg.utils.LoadTG;
import com.tg.utils.LogToGraph;

public class TGraph {
	public static final int timeRange=10;
	public static String fileName;
	public static ArrayList<String> addEdgeList;
	public static ArrayList<String> delEdgeList;
	public static GraphSnapshot graphSnapshot;
	public static SnapshotLog[] snapshotLogArr;
	public static GraphSnapshot[] deltaGraphSnapshotArr;

	public static void loadDataSetsPath() {
		// �������ݼ�·��
		TGraph.fileName = "DataSets/test.txt";
		TGraph.addEdgeList = new ArrayList<String>();
		TGraph.delEdgeList = new ArrayList<String>();
		for (int i = 1; i < 10; i++) {
			TGraph.addEdgeList.add("DataSets/addEdgesDay" + String.valueOf(i) + ".txt");
			TGraph.delEdgeList.add("DataSets/deleteEdgesDay" + String.valueOf(i) + ".txt");
		}
	}

	public static void start() {

		LoadTG.loadGraph();// ���س�ʼͼ����
		LoadTG.readRawLog();// ��ȡ��־
		LoadTG.computeVirtualGraphSnapshot();// ��������ͼ����
		LoadTG.afterComputeVS();//�����������һЩ����
		LoadTG.computeDeltaSnapshotLog();//����������־����
		
		LogToGraph.transform();//��������־����ת�������ͼ����
		
	}
}
