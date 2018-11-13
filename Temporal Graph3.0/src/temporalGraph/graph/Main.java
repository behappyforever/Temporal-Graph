package temporalGraph.graph;

import temporalGraph.algorithm.*;

public class Main {
	
	public static long startTime;
	public static void main(String[] args) {
		
		TGraph.start("Persistence5");//构建时序图存储结构
		System.out.println("数据加载完成");
//		long[] arr=new long[5000];
//		for (int i = 0; i < 5000; i++) {
//			arr[i]=i;
//		}
		startTime=System.currentTimeMillis();

//		for(int i=0;i<5000;i++){
//			LocalPointQuery.oneHopNeighborQuery(i,1);
//		}


//		for(int i=0;i<5000;i++){
//			LocalPointQuery.twoHopNeighborQuery(i,1);
//		}
//		LocalRangeQuery.oneHopNeighborQuery(arr);
//		for (int i = 0; i < 10; i++) {
//			LocalRangeQuery.twoHopNeighborQuery(arr);
//		}


//		GlobalPointQuery.pageRank(1);
//		GlobalRangeQuery.pageRank();

		GlobalPointQuery.singleShortestPath(0,0);
//		GlobalRangeQuery.singleShortestPath(0);
		System.out.println("总执行时间为:"+(System.currentTimeMillis()-startTime)+"毫秒");





//		long[] longs = LocalRangeQuery.twoHopNeighborQuery(0);
//		for (long aLong : longs) {
//			System.out.println(aLong);
//		}
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

//		GlobalPointQuery.singleShortestPath(0,0);

//		GlobalRangeQuery.singleShortestPath(0);
	}
}
