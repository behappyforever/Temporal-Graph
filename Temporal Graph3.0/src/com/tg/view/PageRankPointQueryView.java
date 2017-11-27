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
	//�������
	private JPanel pageRankQueryJP,buttonJP;
	//�����ǩ
	private JLabel vertexJL,timeJL;
	//�����ı���
	private JTextField vertexJTF;
	//ʱ����Ͽ�
	private JComboBox<String> timeJCB;
	//��ť
	private JButton queryJB,queryAllJB;
	//���췽��
	public PageRankPointQueryView(){
		setBounds(200,200,500,110);
		setTitle("��ѯ����PageRankֵ");
		//��ť���
		buttonJP=new JPanel();
		//��ѯ������
		pageRankQueryJP=new JPanel();
		pageRankQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 2);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		pageRankQueryJP.setLayout(gridLayout);
		getContentPane().add(pageRankQueryJP);
		//���ʱ���ǩ����ѯ���
		timeJL=new JLabel("ʱ���:");
		timeJL.setHorizontalAlignment(SwingConstants.CENTER);
		pageRankQueryJP.add(timeJL);
		//ʱ��������б�
		String[] temp={"1","2","3","4","5","6","7","8","9","10"};
		timeJCB=new JComboBox<>(temp);
		pageRankQueryJP.add(timeJCB);
		//���������ǩ���ı�����ӵ���ѯ���
		vertexJL=new JLabel("������:");
		vertexJL.setHorizontalAlignment(SwingConstants.CENTER);
		pageRankQueryJP.add(vertexJL);
		vertexJTF=new JTextField();
		pageRankQueryJP.add(vertexJTF);
		//��ť������
		queryJB=new JButton("��ѯ");
		buttonJP.add(queryJB);
		queryAllJB=new JButton("��ѯ����");
		buttonJP.add(queryAllJB);
		//ע�������
		queryJB.addActionListener(new PageRankPointQueryActionListener());
		queryAllJB.addActionListener(new PageRankQueryAllActionListener());
		//��Ӳ�ѯ�����ڴ�����в�
		this.add(pageRankQueryJP,BorderLayout.CENTER);
		//��Ӱ�ť�����ڴ�����ϲ�
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}
	class PageRankPointQueryActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			if(vertexJTF.getText().length()==0){
				JOptionPane.showMessageDialog(null, "�����Ų���Ϊ��");
				return;
			}
			if(!vertexJTF.getText().matches("[0-9]+")){
				JOptionPane.showMessageDialog(null, "���зǷ��ַ��������붥�����ֱ��");
				return;
			}
			int vertex=Integer.parseInt(vertexJTF.getText().trim());//������
			String day=(String)timeJCB.getSelectedItem();
			if(!TGraph.graph[Integer.parseInt(day)-1].vertexIsExist(vertex)){
				JOptionPane.showMessageDialog(null, "���㲻����");
				return;
			}
			double[] temp=TGraph.graph[Integer.parseInt(day)-1].getPr();
//			DecimalFormat decimalFormat = new DecimalFormat("#,##0.000000000000000");//��ʽ������  
			JOptionPane.showMessageDialog(null, "�ö���PageRankֵΪ:"+temp[vertex]);
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
