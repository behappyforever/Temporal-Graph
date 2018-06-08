package com.tg.graph;

import com.tg.algorithm.LocalPointQuery;
import com.tg.algorithm.PageRank;
import com.tg.utils.MergeLogIntoGraph;

//"DataSets/p2p-Gnutella09.txt";
//"DataSets/p2p-Gnutella08.txt";
//"DataSets/facebook_combined.txt";
//"DataSets/test.txt";
public class Main {
	
	
	public static void main(String[] args) {
		
		TGraph.loadDataSetsPath();//加载数据集
		
		TGraph.start();//构建时序图存储结构
		System.err.println(LocalPointQuery.query(0, 2));
//		long start1=System.currentTimeMillis();
//		PageRank.resetPr(TGraph.graphSnapshot);
//		PageRank.pageRank(TGraph.graphSnapshot);
//		System.out.println(TGraph.graphSnapshot.getIterations());
//		long time1= System.currentTimeMillis() - start1;
//		System.out.println("运行耗时= "+time1+" 毫秒");
//
//		long start2=System.currentTimeMillis();
//		PageRank.pageRank(TGraph.deltaGraphSnapshotArr[0]);
//		System.out.println(TGraph.deltaGraphSnapshotArr[0].getIterations());
//		long time2 = System.currentTimeMillis() - start2;
//		System.out.println("运行耗时= "+time2+" 毫秒");
//		long start3=System.currentTimeMillis();
//		MergeLogIntoGraph.mergeLogIntoGraph(0);
//		PageRank.pageRank(TGraph.graphSnapshot);
//		System.out.println(TGraph.graphSnapshot.getIterations());
//		long time3 = System.currentTimeMillis() - start3;
//		System.out.println("运行耗时= "+time3+" 毫秒");
	}
}
