package com.tg.algorithm;

import java.util.Map.Entry;

import com.tg.graph.GraphSnapshot;
import com.tg.graph.Vertex;

//��ʼ������ֵ��ʾ PageRank �� rank ֵ��double ���ͣ�����ʼʱ�����е�ȡֵΪ 1/TotalNumVertices
//������ʽ��PageRank(i)=0.15/TotalNumVertices+0.85*sum��
//���� sum Ϊ����ָ�� i ��ĵ㣨��Ϊ j�� PageRank(j)/out_degree(j) ���ۼ�ֵ
public class PageRank {
	// ��ֵ
	private static final double threshold = 0.0000000000000001;// ԽСҪ�󾫶�Խ�ߣ���������Խ�� 10��-5
	private static final double alpha = 0.85f;

	public static void resetPr(GraphSnapshot graphSnapshot) {
		for(Entry<Integer,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			en.getValue().setPr(1.0f/graphSnapshot.getNumOfVertex());
		}
	}
	
	public static void pageRank(GraphSnapshot graphSnapshot) {
		int numOfVertex = graphSnapshot.getNumOfVertex();
		
		int iterations=1;
		for(Entry<Integer,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			en.getValue().setPr(1.0f/numOfVertex);
			en.getValue().setReceiveSumPr(0.0);
		}
		boolean flag=compute(graphSnapshot, numOfVertex);
		while (flag != true) {//δ����
//			System.out.println(flag);
			for(Entry<Integer,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
				en.getValue().setReceiveSumPr(0.0);
			}
			flag=compute(graphSnapshot, numOfVertex);
			iterations++;
		}
		graphSnapshot.setIterations(iterations);
	}

	private static boolean compute(GraphSnapshot graphSnapshot, int numOfVertex) {
		boolean flag=true;//�����ж��Ƿ�����
		for(Entry<Integer,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			if(en.getValue().getOutGoingList().size()==0) {// ����õ����Ϊ0����prֵƽ�ָ�����n-1������
				for(Entry<Integer,Vertex> enInner : graphSnapshot.getHashMap().entrySet()) {
					enInner.getValue().addReceiveSumpr(en.getValue().getPr()/(numOfVertex-1));
				}
			   en.getValue().deleteReceiveSumpr(en.getValue().getPr()/(numOfVertex-1));
			}else {// ����õ���Ȳ�Ϊ0����prֵƽ�ָ�����߶���
				for(Integer integer:en.getValue().getOutGoingList()) {
					graphSnapshot.getHashMap().get(integer).addReceiveSumpr(en.getValue().getPr()/en.getValue().getOutGoingList().size());
				}
			}
		}
		for(Entry<Integer,Vertex> en : graphSnapshot.getHashMap().entrySet()) {
			double absTemp=en.getValue().setPr((1 - alpha) * (1.0f / numOfVertex) + alpha * en.getValue().getReceiveSumPr());
			if(absTemp>threshold) {
				flag=false;
			}
//			System.out.println(absTemp);
		}
		return flag;
	}
}
