package com.tg.graph;

import java.util.ArrayList;

import com.tg.algorithm.PageRank;
import com.tg.function.GetNumOfVertex;

//"DataSets/p2p-Gnutella09.txt";
//"DataSets/p2p-Gnutella08.txt";
//"DataSets/facebook_combined.txt";
//"DataSets/test.txt";
public class Main {
	public static String fileName;
	public static ArrayList<String> addEdgeList;
	public static ArrayList<String> delEdgeList;
	
	public static void main(String[] args) {
		//�������ݼ�·��
		fileName="DataSets/test.txt";
		addEdgeList=new ArrayList<String>();
		delEdgeList=new ArrayList<String>();
		for(int i=1;i<10;i++){
			addEdgeList.add("DataSets/addEdgesDay"+String.valueOf(i)+".txt");
			delEdgeList.add("DataSets/deleteEdgesDay"+String.valueOf(i)+".txt");
		}
		
		TGraph.start();//����ʱ��ͼ�洢�ṹ
		long start=System.currentTimeMillis();
		PageRank.pageRank(TGraph.graphSnapshot);
		System.out.println(TGraph.graphSnapshot.getIterations());
		long time = System.currentTimeMillis() - start;
		System.out.println("���к�ʱ= "+time+" ����");
//		PrintVertexId.printVertexId(GetVertexId.getVertexId(3));
//		Diameter.diameterCompute(6);
//		System.out.println(TGraph.graph[0].getPath());
//		new TemGraph();
//		System.out.println(GetNumOfVertex.getNumOfVertex(7));
		
	}
}
