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

import com.tg.function.GetVertexId;
import com.tg.graph.TGraph;

public class PageRankRangeQueryView extends JFrame{
	private static final long serialVersionUID=1l;
	private JPanel rangeQueryJP,buttonJP;
	private JLabel vertexJL,timeFromJL,timeToJL;
	private JTextField vertexJTF;
	private JComboBox<String> timeFromJCB,timeToJCB;
	private JButton queryJB,queryAllJB;
	
	public PageRankRangeQueryView(){
		setBounds(200,200,500,110);
		setTitle("查询顶点PageRank值");
		buttonJP=new JPanel();
		rangeQueryJP=new JPanel();
		rangeQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 2);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		rangeQueryJP.setLayout(gridLayout);
		getContentPane().add(rangeQueryJP);
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
		//创建顶点标签和文本框并添加到查询面板
		vertexJL=new JLabel("顶点编号:");
		vertexJL.setHorizontalAlignment(SwingConstants.CENTER);
		rangeQueryJP.add(vertexJL);
		vertexJTF=new JTextField();
		rangeQueryJP.add(vertexJTF);
		//按钮面板设计
		queryJB=new JButton("查询");
		buttonJP.add(queryJB);
		queryAllJB=new JButton("查询所有");
		buttonJP.add(queryAllJB);
		//注册监听器
		queryJB.addActionListener(new PageRankRangeQueryActionListener());
		queryAllJB.addActionListener(new PageRankRangeQueryAllActionListener());
		//添加查询面板放在窗体的中部
		this.add(rangeQueryJP,BorderLayout.CENTER);
		//添加按钮面板放在窗体的南部
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}
	class PageRankRangeQueryActionListener implements ActionListener{
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
			String from=(String)timeFromJCB.getSelectedItem();
			String to=(String)timeToJCB.getSelectedItem();
			boolean temp=true;
			for(int x=Integer.parseInt(from);x<=Integer.parseInt(to);x++){
				if(!GetVertexId.getVertexId(x-1).contains(vertex)){
					temp=false;
					break;
				}
			}
			if(!temp){
				JOptionPane.showMessageDialog(null, "顶点不存在");
				return;
			}
			if(Integer.parseInt(from)>=Integer.parseInt(to)){
				JOptionPane.showMessageDialog(null,"时间段选择有误");
				return;
			}
			double count=0;
			for(int i=Integer.parseInt(from)-1;i<=Integer.parseInt(to)-1;i++){
				count+=TGraph.graph[i].getPr()[vertex];
			}
			count=count/(Integer.parseInt(to)-Integer.parseInt(from)+1);
			JOptionPane.showMessageDialog(null, "该顶点PageRank值为:"+count);
		}
	}
	class PageRankRangeQueryAllActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			String from=(String)timeFromJCB.getSelectedItem();
			String to=(String)timeToJCB.getSelectedItem();
			if(Integer.parseInt(from)>=Integer.parseInt(to)){
				JOptionPane.showMessageDialog(null,"时间段选择有误");
				return;
			}
			new PageRankRangeQueryAllView(Integer.parseInt(from)-1,Integer.parseInt(to)-1);
		}
	}
}
