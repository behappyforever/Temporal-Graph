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
import com.tg.function.PointQuery;

public class PointQueryView extends JFrame {
	private static final long serialVersionUID=1l;
	//定义面板
	private JPanel pointQueryJP,buttonJP;
	//定义标签
	private JLabel vertexJL,timeJL;
	//定义文本框
	private JTextField vertexJTF;
	//时间组合框
	private JComboBox<String> timeJCB;
	//按钮
	private JButton queryJB;
	//构造方法
	public PointQueryView(){
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
		//创建顶点标签和文本框并添加到查询面板
		vertexJL=new JLabel("顶点编号:");
		vertexJL.setHorizontalAlignment(SwingConstants.CENTER);
		pointQueryJP.add(vertexJL);
		vertexJTF=new JTextField();
		pointQueryJP.add(vertexJTF);
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
			if(!GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(vertex)){
				JOptionPane.showMessageDialog(null, "顶点不存在");
				return;
			}
			JOptionPane.showMessageDialog(null, "该顶点关注的顶点有"+PointQuery.pointQueryOutDegree(Integer.parseInt(day)-1, vertex)
										  +"\n"+"关注该顶点的顶点有"+PointQuery.pointQueryInDegree(Integer.parseInt(day)-1, vertex)
										  +"\n"+"互相关注的顶点有"+PointQuery.pointQuery(),"查询结果",1);
			//			PointQueryView.this.setVisible(false);
		}
	}
}
