package com.tg.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.tg.function.GetNumOfVertex;
import com.tg.graph.TGraph;

public class PageRankRangeQueryAllView extends JFrame{
private static final long serialVersionUID=1l;
	
	private JPanel jPanel;
	private JScrollPane jScrollPane;
	private JTable jTable;
	
	public PageRankRangeQueryAllView(int from,int to){
		setBounds(200,200,500,500);
		setTitle("展示所有顶点PageRank值");
		jPanel=new JPanel();
		jPanel.setLayout(new BorderLayout());
		int range=to-from+1;
		int num=GetNumOfVertex.getNumOfVertex(from);
		double[][] temp=new double[num][2];
		ArrayList<Integer> arrayList=TGraph.graph[from].getVertexId();
		for(int i=0;i<num;i++){
			temp[i][0]=arrayList.get(i);
			temp[i][1]=TGraph.graph[from].getPr()[i];
		}
		boolean flag=false;
		for(int j=0;j<num;j++){
			for(int i=from+1;i<=to;i++){
				arrayList=TGraph.graph[i].getVertexId();
				for(int k=0;k<arrayList.size();k++){
					if(arrayList.get(k)==temp[j][0]){
						temp[j][1]+=TGraph.graph[i].getPr()[k];
						flag=true;//顶点存在
						break;
					}
				}
				if(!flag){
					temp[j][0]=-1;
					flag=false;
					break;
				}
			}
		}
		int count=0;
		for(int i=0;i<num;i++){
			if(temp[i][0]!=-1){
				count++;
			}
		}
		double[][] results=new double[count][2];
		int b=0;
		for(int i=0;i<num;i++){
			if(temp[i][0]!=-1){
				results[b][0]=temp[i][0];
				results[b][1]=temp[i][1]/range;
				b++;
			}
		}
		for (int j = 0; j < results.length ; j++) {
			for (int i = results.length-2; i >=j; i--) {
				double[] a;
				if (results[i][1]<results[i+1][1]) {
					a = results[i];
					results[i] = results[i + 1];
					results[i + 1] = a;
				}
			}
		}
//		double sum=0;
//		for(int i=0;i<results.length;i++){
//			sum+=results[i][1];
//		}
		Object[][] obj=new Object[results.length][2];
		for (int j = 0; j < results.length ; j++) {
			for (int i = 0; i < 2 ; i++) {
				obj[j][i]=results[j][i];
			}
		}
		for (int j = 0; j < results.length ; j++) {
			obj[j][0]=(int)results[j][0];
		}
		String[] display={"顶点id    ","PageRank值        "};
		jScrollPane=new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(400, 200));
		jTable=new JTable();
		jTable=new JTable(obj,display);
		jScrollPane.setViewportView(jTable);
		jPanel.add(jScrollPane);
		this.add(jPanel);
		this.setVisible(true);
	}
}
