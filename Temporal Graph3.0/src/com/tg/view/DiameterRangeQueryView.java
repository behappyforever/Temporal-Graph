package com.tg.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.tg.graph.TGraph;

public class DiameterRangeQueryView extends JFrame{
	private static final long serialVersionUID=1l;
	//定义面板
	private JPanel rangeQueryJP,buttonJP;
	//定义标签
	private JLabel timeFromJL,timeToJL;
	//时间组合框
	private JComboBox<String> timeFromJCB,timeToJCB;
	//按钮
	private JButton queryJB;
	//构造方法
	public DiameterRangeQueryView(){
		setBounds(200,200,500,110);
		setTitle("基于时间范围查询");
		//按钮面板
		buttonJP=new JPanel();
		//基于时间范围查询面板设计
		rangeQueryJP=new JPanel();
		rangeQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 3);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		rangeQueryJP.setLayout(gridLayout);
		getContentPane().add(rangeQueryJP);
		//添加时间标签到查询面板
		timeFromJL=new JLabel("时间:From");
		timeFromJL.setHorizontalAlignment(SwingConstants.CENTER);
		rangeQueryJP.add(timeFromJL);
		//时间范围下拉列表
		String[] temp={"1","2","3","4","5","6","7","8","9","10"};
		timeFromJCB=new JComboBox<>(temp);
		rangeQueryJP.add(timeFromJCB);
		//添加to
		timeToJL=new JLabel("To");
		timeToJL.setHorizontalAlignment(SwingConstants.CENTER);
		rangeQueryJP.add(timeToJL);
		//时间范围下拉列表
		timeToJCB=new JComboBox<>(temp);
		rangeQueryJP.add(timeToJCB);
		//按钮面板设计
		queryJB=new JButton("查询");
		buttonJP.add(queryJB);
		//注册监听器
		queryJB.addActionListener(new RangeQueryActionListener());
		//添加查询面板放在窗体的中部
		this.add(rangeQueryJP,BorderLayout.CENTER);
		//添加按钮面板放在窗体的南部
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}	
	class RangeQueryActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			String from=(String)timeFromJCB.getSelectedItem();
			String to=(String)timeToJCB.getSelectedItem();
			if(Integer.parseInt(from)>=Integer.parseInt(to)){
				JOptionPane.showMessageDialog(null,"时间段选择有误");
				return;
			}
			double diameter=0;
			int range=Integer.parseInt(to)-Integer.parseInt(from)+1;
			for(int i=Integer.parseInt(from)-1;i<=Integer.parseInt(to)-1;i++){
				diameter+=TGraph.graph[i].getDiameter();
			}
			diameter=diameter/range;
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
			JOptionPane.showMessageDialog(null, "该段时间内图直径为："+decimalFormat.format(diameter),"查询结果",1);
		}
	}
}
