package com.tg.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.tg.graph.TGraph;

public class DiameterPointQueryView extends JFrame{
	private static final long serialVersionUID=1l;
	//定义面板
	private JPanel pointQueryJP,buttonJP;
	//定义标签
	private JLabel timeJL;
	//时间组合框
	private JComboBox<String> timeJCB;
	//按钮
	private JButton queryJB;
	//构造方法
	public DiameterPointQueryView(){
		setBounds(200,200,500,110);
		setTitle("基于时间点查询");
		//按钮面板
		buttonJP=new JPanel();
		//基于时间点查询面板设计
		pointQueryJP=new JPanel();
		pointQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 2);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		pointQueryJP.setLayout(gridLayout);
		getContentPane().add(pointQueryJP);
		//添加时间标签到查询面板
		timeJL=new JLabel("时间点:");
		timeJL.setHorizontalAlignment(SwingConstants.CENTER);
		pointQueryJP.add(timeJL);
		//时间点下拉列表
		String[] temp={"1","2","3","4","5","6","7","8","9","10"};
		timeJCB=new JComboBox<>(temp);
		pointQueryJP.add(timeJCB);
		//按钮面板设计
		queryJB=new JButton("查询");
		buttonJP.add(queryJB);
		//注册监听器
		queryJB.addActionListener(new PointQueryActionListener());
		//添加查询面板放在窗体的中部
		this.add(pointQueryJP,BorderLayout.CENTER);
		//添加按钮面板放在窗体的南部
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}	
	class PointQueryActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			String day=(String)timeJCB.getSelectedItem();
			JOptionPane.showMessageDialog(null, "该时刻快照的直径为："+TGraph.graph[Integer.parseInt(day)-1].getDiameter()+"\n"+"路径为："+TGraph.graph[Integer.parseInt(day)-1].getPath(),"查询结果",1);
		}
	}
}
