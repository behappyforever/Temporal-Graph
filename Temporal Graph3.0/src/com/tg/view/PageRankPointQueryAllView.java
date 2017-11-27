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

public class PageRankPointQueryAllView extends JFrame{
	private static final long serialVersionUID=1l;
	
	private JPanel jPanel;
	private JScrollPane jScrollPane;
	private JTable jTable;
	
	public PageRankPointQueryAllView(int day){
		setBounds(200,200,500,500);
		setTitle("展示所有顶点PageRank值");
		jPanel=new JPanel();
		jPanel.setLayout(new BorderLayout());
		String[] display={"顶点id  迭代次数"+TGraph.graph[day].getIterations(),"PageRank值   总和"+TGraph.graph[day].getSumOfPr()};
		double[][] results=new double[GetNumOfVertex.getNumOfVertex(day)][display.length];
		ArrayList<Integer> arrayList=TGraph.graph[day].getVertexId();
		for(int i=0;i<GetNumOfVertex.getNumOfVertex(day);i++){
			results[i][0]=arrayList.get(i);
			results[i][1]=TGraph.graph[day].getPr()[i];
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
		Object[][] obj=new Object[GetNumOfVertex.getNumOfVertex(day)][display.length];
		for (int j = 0; j < results.length ; j++) {
			for (int i = 0; i < 2 ; i++) {
				obj[j][i]=results[j][i];
			}
		}
		for (int j = 0; j < results.length ; j++) {
			obj[j][0]=(int)results[j][0];
		}
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
