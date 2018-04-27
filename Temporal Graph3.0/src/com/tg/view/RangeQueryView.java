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
import com.tg.function.RangeQuery;

public class RangeQueryView extends JFrame {
	private static final long serialVersionUID=1l;
	//定义面板
	private JPanel rangeQueryJP,buttonJP;
	//定义标签
	private JLabel vertexJL,timeFromJL,timeToJL;
	//定义文本框
	private JTextField vertexJTF;
	//时间组合框
	private JComboBox<String> timeFromJCB,timeToJCB;
	//按钮
	private JButton queryJB;
	//构造方法
	public RangeQueryView(){
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
		//创建顶点标签和文本框并添加到查询面板
		vertexJL=new JLabel("顶点编号:");
		vertexJL.setHorizontalAlignment(SwingConstants.CENTER);
		rangeQueryJP.add(vertexJL);
		vertexJTF=new JTextField();
		rangeQueryJP.add(vertexJTF);
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
			JOptionPane.showMessageDialog(null, "该顶点关注的顶点有"+RangeQuery.rangeQueryOutDegree(Integer.parseInt(from)-1,Integer.parseInt(to)-1,vertex)
			  +"\n"+"关注该顶点的顶点有"+RangeQuery.rangeQueryInDegree(Integer.parseInt(from)-1,Integer.parseInt(to)-1,vertex)
			  +"\n"+"互相关注的顶点有"+RangeQuery.rangeQuery(),"查询结果",1);
		}
	}
}
