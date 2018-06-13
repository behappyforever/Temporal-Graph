package temporalGraph.algorithm;

import temporalGraph.graph.GraphSnapshot;
import temporalGraph.graph.Vertex;

import java.util.Map.Entry;

//初始化：点值表示 PageRank 的 rank 值（double 类型），初始时，所有点取值为 1/TotalNumVertices
//迭代公式：PageRank(i)=0.15/TotalNumVertices+0.85*sum，
//其中 sum 为所有指向 i 点的点（设为 j） PageRank(j)/out_degree(j) 的累加值
public class PageRank {
	// 阈值
	private static final double threshold = 0.0000000000000001;// 越小要求精度越高，迭代次数越大 10的-5
	private static final double alpha = 0.85f;

	public static void resetPr(GraphSnapshot graphSnapshot) {
		for(Entry<Long,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			en.getValue().setPr(1.0f/graphSnapshot.getNumOfVertex());
		}
	}
	
	public static void pageRank(GraphSnapshot graphSnapshot) {
		int numOfVertex = graphSnapshot.getNumOfVertex();
		
		int iterations=1;
		for(Entry<Long,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			en.getValue().setPr(1.0f/numOfVertex);
			en.getValue().setReceiveSumPr(0.0);
		}
		boolean flag=compute(graphSnapshot, numOfVertex);
		while (flag != true) {//未收敛
//			System.out.println(flag);
			for(Entry<Long,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
				en.getValue().setReceiveSumPr(0.0);
			}
			flag=compute(graphSnapshot, numOfVertex);
			iterations++;
		}
		graphSnapshot.setIterations(iterations);
	}

	private static boolean compute(GraphSnapshot graphSnapshot, int numOfVertex) {
		boolean flag=true;//用于判断是否收敛
		for(Entry<Long,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			if(en.getValue().getOutGoingList().size()==0) {// 如果该点出度为0，则将pr值平分给其他n-1个顶点
				for(Entry<Long,Vertex> enInner : graphSnapshot.getHashMap().entrySet()) {
					enInner.getValue().addReceiveSumpr(en.getValue().getPr()/(numOfVertex-1));
				}
			   en.getValue().deleteReceiveSumpr(en.getValue().getPr()/(numOfVertex-1));
			}else {// 如果该点出度不为0，则将pr值平分给其出边顶点
				for(Long l:en.getValue().getOutGoingList()) {
					graphSnapshot.getHashMap().get(l).addReceiveSumpr(en.getValue().getPr()/en.getValue().getOutGoingList().size());
				}
			}
		}
		for(Entry<Long,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			double absTemp=en.getValue().setPr((1 - alpha) * (1.0f / numOfVertex) + alpha * en.getValue().getReceiveSumPr());
			if(absTemp>threshold) {
				flag=false;
			}
//			System.out.println(absTemp);
		}
		return flag;
	}
}
