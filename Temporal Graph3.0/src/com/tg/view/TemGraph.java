package com.tg.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TemGraph extends JFrame {
	private static final long serialVersionUID=1l;
	public TemGraph(){
		setSize(800,600);
		setTitle("时序图数据的管理和分析系统");
//		setResizable(false); 设置窗体大小不可调
		JMenuBar jmenuBar=createJMenuBar();
		setJMenuBar(jmenuBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	//自定义方法createJMenuBar(),返回一个JMenuBar对象，来实现创建菜单栏的功能，在构造方法中调用该方法。
	private JMenuBar createJMenuBar(){
		JMenuBar jMenuBar=new JMenuBar();//菜单栏
		//查询好友
		JMenu queryJMenu=new JMenu("查询好友");
		//基于点
		JMenuItem pointQueryJMI=new JMenuItem("基于点查询");
		queryJMenu.add(pointQueryJMI);
		//给事件源注册监听器
		pointQueryJMI.addActionListener(new PointQueryListener());
		
		//基于范围
		JMenuItem rangeQueryJMI=new JMenuItem("基于时间范围查询");
		queryJMenu.add(rangeQueryJMI);
		//注册监听器
		rangeQueryJMI.addActionListener(new RangeQueryListener());
		
		//查询顶点pagerank值
		JMenu pagerankJMenu=new JMenu("查询PageRank值");
		JMenuItem pageRankPointJMI=new JMenuItem("快照顶点PageRank值");
		JMenuItem pageRankRangeJMI=new JMenuItem("时间段顶点平均PageRank值");
		pagerankJMenu.add(pageRankPointJMI);
		pagerankJMenu.add(pageRankRangeJMI);
		//注册监听器
		pageRankPointJMI.addActionListener(new PageRankPointListener());
		pageRankRangeJMI.addActionListener(new PageRankRangeListener());
		//增删功能
		JMenu changeJMenu=new JMenu("维护");
		JMenuItem infJMI=new JMenuItem("基本信息");
		//注册监听器
		changeJMenu.add(infJMI);
		infJMI.addActionListener(new DisplayListener());
		//查询图直径
		JMenu diameterJMenu=new JMenu("查询图直径");
		JMenuItem diaPointJMI=new JMenuItem("时间点快照直径");
		JMenuItem diaRangeJMI=new JMenuItem("时间段平均直径");
		diameterJMenu.add(diaPointJMI);
		diameterJMenu.add(diaRangeJMI);
		//注册监听器
		diaPointJMI.addActionListener(new DiameterPointListener());
		diaRangeJMI.addActionListener(new DiameterRangeListener());
		
		jMenuBar.add(queryJMenu);
		jMenuBar.add(pagerankJMenu);
		jMenuBar.add(diameterJMenu);
		jMenuBar.add(changeJMenu);
		return jMenuBar;
	}
	class PointQueryListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new PointQueryView();
		}
	}
	class RangeQueryListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new RangeQueryView();
		}
	}
	
	class PageRankPointListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new PageRankPointQueryView();
		}
	}
	class PageRankRangeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new PageRankRangeQueryView();
		}
	}
	class DisplayListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			new DisplayView();
		}
	}
	class DiameterPointListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			new DiameterPointQueryView();
		}
	}
	class DiameterRangeListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			new DiameterRangeQueryView();
		}
	}
}
