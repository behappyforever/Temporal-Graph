package com.tg.graph;

import java.util.ArrayList;

import com.tg.function.GetNumOfVertex;

//"DataSets/p2p-Gnutella09.txt";
//"DataSets/p2p-Gnutella08.txt";
//"DataSets/facebook_combined.txt";
//"DataSets/test.txt";
public class Main {
	public static String fileName;
//	public static ArrayList<String> addVerList;
	public static ArrayList<String> addEdgeList;
//	public static ArrayList<String> delVerList;
	public static ArrayList<String> delEdgeList;
	
	public static void main(String[] args) {
		//�������ݼ�·��
		fileName="DataSets/test.txt";
//		addVerList=new ArrayList<String>();
		addEdgeList=new ArrayList<String>();
//		delVerList=new ArrayList<String>();
		delEdgeList=new ArrayList<String>();
		for(int i=1;i<10;i++){
//			addVerList.add("DataSets/addVertexDay"+String.valueOf(i)+".txt");
			addEdgeList.add("DataSets/addEdgesDay"+String.valueOf(i)+".txt");
//			delVerList.add("DataSets/deleteVertexDay"+String.valueOf(i)+".txt");
			delEdgeList.add("DataSets/deleteEdgesDay"+String.valueOf(i)+".txt");
		}
		
		TGraph.start();//����ʱ��ͼ�洢�ṹ
//		PrintVertexId.printVertexId(GetVertexId.getVertexId(3));
//		long start=System.currentTimeMillis();
//		Diameter.diameterCompute(6);
//		long time = System.currentTimeMillis() - start;
//		System.out.println("���к�ʱ= "+time+" ����");
//		System.out.println(TGraph.graph[0].getPath());
//		new TemGraph();
		System.out.println(GetNumOfVertex.getNumOfVertex(7));
		
	}
}
