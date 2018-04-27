package com.tg.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.tg.graph.TGraph;

//初始化：点值表示 PageRank 的 rank 值（double 类型），初始时，所有点取值为 1/TotalNumVertices
//迭代公式：PageRank(i)=0.15/TotalNumVertices+0.85*sum，
//其中 sum 为所有指向 i 点的点（设为 j） PageRank(j)/out_degree(j) 的累加值
public class PageRank {
	//阈值
	private static double threshold=0.00001f;//越小要求精度越高，迭代次数越大 10的-5
	private static double alpha=0.85f;
	private static double[] pr;
	private static double[] last;
	private static int iterations=1;
	private static double sumOfPr=0;
	
	public static void pageRank(){
		//第一天
		int numOfVertex=GetNumOfVertex.getNumOfVertex(0);
		pr=new double[numOfVertex];
		last=new double[numOfVertex];
		Arrays.fill(pr, 1.0f/numOfVertex);
		compute(0, numOfVertex);
		while(checkThreshold()!=true){
			compute(0, numOfVertex);
			iterations++;
		}
		for(int i=0;i<pr.length;i++){
			sumOfPr+=pr[i];
		}
		TGraph.graph[0].setIterations(iterations);
		TGraph.graph[0].setSumOfPr(sumOfPr);
		TGraph.graph[0].setPr(pr);
		//之后几天
		for(int day=1;day<10;day++){
			int num=GetNumOfVertex.getNumOfVertex(day);
			double[] temp=new double[pr.length];
			System.arraycopy(pr, 0, temp, 0, pr.length);
			pr=new double[num];
			last=new double[num];
			iterations=1;
			sumOfPr=0;
			Arrays.fill(pr, 1.0f/num);
			for(int i=0;i<Math.min(pr.length,temp.length);i++){
				pr[i]=temp[i];
			}
			compute(day, num);
			while(checkThreshold()!=true){
				compute(day, num);
				iterations++;
			}
			for(int i=0;i<pr.length;i++){
				sumOfPr+=pr[i];
			}
			TGraph.graph[day].setIterations(iterations);
			TGraph.graph[day].setSumOfPr(sumOfPr);	
			TGraph.graph[day].setPr(pr);
		}

	}
	private static void compute(int day,int numOfVertex){
		System.arraycopy(pr, 0, last, 0, numOfVertex);
//		int id;
		int index;
//		Iterator<Integer> setIterator=GetVertexId.getVertexId(day).iterator();
//		prId=new ArrayList<Integer>();
//		prId.clear();
//		while(setIterator.hasNext()){
//			id=setIterator.next();
//			prId.add(id);
//		}
		ArrayList<Integer> prId=TGraph.graph[day].getVertexId();
		ArrayList<Double> sumList=new ArrayList<Double>();
		for(int i=0;i<numOfVertex;i++){
			sumList.add(0.0);
		}
		for(int i=0;i<numOfVertex;i++){
			int size;
			size=PointQuery.pointQueryOutDegree(day,prId.get(i)).size();
			if(size==0){//如果该点出度为0，则将pr值平分给其他n-1个顶点
				for(int j=0;j<numOfVertex;j++){
					sumList.set(j, sumList.get(j)+pr[i]/(numOfVertex-1));
				}
				sumList.set(i, sumList.get(i)-pr[i]/(numOfVertex-1));
			}
			else{//如果该点出度不为0，则将pr值平分给其出边顶点
				Iterator<Integer> iterator=PointQuery.pointQueryOutDegree(day,prId.get(i)).iterator();
				while(iterator.hasNext()){
					int temp=iterator.next();
					index=prId.indexOf(temp);
					if(index!=-1)
						sumList.set(index, sumList.get(index)+pr[i]/size);
				}
			}
		}
		for(int i=0;i<numOfVertex;i++){
			pr[i]=(1-alpha)*(1.0f/numOfVertex)+alpha*sumList.get(i);
		}
//		int inId;
//		double sum;
//		for(int i=0;i<numOfVertex;i++){
//			sum=0;
//			Iterator<Integer> iterator=PointQuery.pointQueryInDegree(day,prId.get(i)).iterator();
//			while(iterator.hasNext()){
//				inId=iterator.next();
//				index=prId.indexOf(inId);
//				sum+=last[index]/PointQuery.pointQueryOutDegree(day, inId).size();
//			}
//			pr[i]=(1-alpha)*(1.0f/numOfVertex)+alpha*sum;
//			if(PointQuery.pointQueryOutDegree(day,prId.get(i)).size()==0){
//				pr[i]+=last[i]*alpha;
//			}
//		}
	}
	 
	private static boolean checkThreshold(){
		boolean flag = true;  
        for (int i = 0; i < pr.length; i++) {  
            if (Math.abs(pr[i] - last[i]) > threshold) {  
                flag = false;  //只要有一个大于阈值的，即继续迭代
                break;  
            }  
        }  
        return flag;
	}
}
