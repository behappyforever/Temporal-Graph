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
		setTitle("��ѯ����PageRankֵ");
		buttonJP=new JPanel();
		rangeQueryJP=new JPanel();
		rangeQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 2);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		rangeQueryJP.setLayout(gridLayout);
		getContentPane().add(rangeQueryJP);
		timeFromJL=new JLabel("ʱ��:From");
		timeFromJL.setHorizontalAlignment(SwingConstants.CENTER);
		rangeQueryJP.add(timeFromJL);
		//ʱ�䷶Χ�����б�
		String[] temp={"1","2","3","4","5","6","7","8","9","10"};
		timeFromJCB=new JComboBox<>(temp);
		rangeQueryJP.add(timeFromJCB);
		//���to
		timeToJL=new JLabel("To");
		timeToJL.setHorizontalAlignment(SwingConstants.CENTER);
		rangeQueryJP.add(timeToJL);
		//ʱ�䷶Χ�����б�
		timeToJCB=new JComboBox<>(temp);
		rangeQueryJP.add(timeToJCB);
		//���������ǩ���ı�����ӵ���ѯ���
		vertexJL=new JLabel("������:");
		vertexJL.setHorizontalAlignment(SwingConstants.CENTER);
		rangeQueryJP.add(vertexJL);
		vertexJTF=new JTextField();
		rangeQueryJP.add(vertexJTF);
		//��ť������
		queryJB=new JButton("��ѯ");
		buttonJP.add(queryJB);
		queryAllJB=new JButton("��ѯ����");
		buttonJP.add(queryAllJB);
		//ע�������
		queryJB.addActionListener(new PageRankRangeQueryActionListener());
		queryAllJB.addActionListener(new PageRankRangeQueryAllActionListener());
		//��Ӳ�ѯ�����ڴ�����в�
		this.add(rangeQueryJP,BorderLayout.CENTER);
		//��Ӱ�ť�����ڴ�����ϲ�
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}
	class PageRankRangeQueryActionListener implements ActionListener{
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
				JOptionPane.showMessageDialog(null, "���㲻����");
				return;
			}
			if(Integer.parseInt(from)>=Integer.parseInt(to)){
				JOptionPane.showMessageDialog(null,"ʱ���ѡ������");
				return;
			}
			double count=0;
			for(int i=Integer.parseInt(from)-1;i<=Integer.parseInt(to)-1;i++){
				count+=TGraph.graph[i].getPr()[vertex];
			}
			count=count/(Integer.parseInt(to)-Integer.parseInt(from)+1);
			JOptionPane.showMessageDialog(null, "�ö���PageRankֵΪ:"+count);
		}
	}
	class PageRankRangeQueryAllActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			String from=(String)timeFromJCB.getSelectedItem();
			String to=(String)timeToJCB.getSelectedItem();
			if(Integer.parseInt(from)>=Integer.parseInt(to)){
				JOptionPane.showMessageDialog(null,"ʱ���ѡ������");
				return;
			}
			new PageRankRangeQueryAllView(Integer.parseInt(from)-1,Integer.parseInt(to)-1);
		}
	}
}
