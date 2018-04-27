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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.tg.graph.TGraph;

public class PageRankPointQueryView extends JFrame{
	private static final long serialVersionUID=1l;
	//定义面板
	private JPanel pageRankQueryJP,buttonJP;
	//定义标签
	private JLabel vertexJL,timeJL;
	//定义文本框
	private JTextField vertexJTF;
	//时间组合框
	private JComboBox<String> timeJCB;
	//按钮
	private JButton queryJB,queryAllJB;
	//构造方法
	public PageRankPointQueryView(){
		setBounds(200,200,500,110);
		setTitle("查询顶点PageRank值");
		//按钮面板
		buttonJP=new JPanel();
		//查询面板设计
		pageRankQueryJP=new JPanel();
		pageRankQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 2);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		pageRankQueryJP.setLayout(gridLayout);
		getContentPane().add(pageRankQueryJP);
		//添加时间标签到查询面板
		timeJL=new JLabel("时间点:");
		timeJL.setHorizontalAlignment(SwingConstants.CENTER);
		pageRankQueryJP.add(timeJL);
		//时间点下拉列表
		String[] temp={"1","2","3","4","5","6","7","8","9","10"};
		timeJCB=new JComboBox<>(temp);
		pageRankQueryJP.add(timeJCB);
		//创建顶点标签和文本框并添加到查询面板
		vertexJL=new JLabel("顶点编号:");
		vertexJL.setHorizontalAlignment(SwingConstants.CENTER);
		pageRankQueryJP.add(vertexJL);
		vertexJTF=new JTextField();
		pageRankQueryJP.add(vertexJTF);
		//按钮面板设计
		queryJB=new JButton("查询");
		buttonJP.add(queryJB);
		queryAllJB=new JButton("查询所有");
		buttonJP.add(queryAllJB);
		//注册监听器
		queryJB.addActionListener(new PageRankPointQueryActionListener());
		queryAllJB.addActionListener(new PageRankQueryAllActionListener());
		//添加查询面板放在窗体的中部
		this.add(pageRankQueryJP,BorderLayout.CENTER);
		//添加按钮面板放在窗体的南部
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}
	class PageRankPointQueryActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			if(vertexJTF.getText().length()==0){
				JOptionPane.showMessageDialog(null, "顶点编号不能为空");
				return;
			}
			if(!vertexJTF.getText().matches("[0-9]+")){
				JOptionPane.showMessageDialog(null, "含有非法字符，请输入顶点数字编号");
				return;
			}
			int vertex=Integer.parseInt(vertexJTF.getText().trim());//顶点编号
			String day=(String)timeJCB.getSelectedItem();
			if(!TGraph.graph[Integer.parseInt(day)-1].vertexIsExist(vertex)){
				JOptionPane.showMessageDialog(null, "顶点不存在");
				return;
			}
			double[] temp=TGraph.graph[Integer.parseInt(day)-1].getPr();
//			DecimalFormat decimalFormat = new DecimalFormat("#,##0.000000000000000");//格式化设置  
			JOptionPane.showMessageDialog(null, "该顶点PageRank值为:"+temp[vertex]);
//			PointQueryView.this.setVisible(false);
		}
	}
	class PageRankQueryAllActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			String day=(String)timeJCB.getSelectedItem();
			new PageRankPointQueryAllView(Integer.parseInt(day)-1);
		}
	}
}
