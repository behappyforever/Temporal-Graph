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
	//�������
	private JPanel pointQueryJP,buttonJP;
	//�����ǩ
	private JLabel vertexJL,timeJL;
	//�����ı���
	private JTextField vertexJTF;
	//ʱ����Ͽ�
	private JComboBox<String> timeJCB;
	//��ť
	private JButton queryJB;
	//���췽��
	public PointQueryView(){
		setBounds(200,200,500,110);
		setTitle("����ʱ����ѯ");
		//��ť���
		buttonJP=new JPanel();
		//����ʱ����ѯ������
		pointQueryJP=new JPanel();
		pointQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 2);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		pointQueryJP.setLayout(gridLayout);
		getContentPane().add(pointQueryJP);
		//���ʱ���ǩ����ѯ���
		timeJL=new JLabel("ʱ���:");
		timeJL.setHorizontalAlignment(SwingConstants.CENTER);
		pointQueryJP.add(timeJL);
		//ʱ��������б�
		String[] temp={"1","2","3","4","5","6","7","8","9","10"};
		timeJCB=new JComboBox<>(temp);
		pointQueryJP.add(timeJCB);
		//���������ǩ���ı�����ӵ���ѯ���
		vertexJL=new JLabel("������:");
		vertexJL.setHorizontalAlignment(SwingConstants.CENTER);
		pointQueryJP.add(vertexJL);
		vertexJTF=new JTextField();
		pointQueryJP.add(vertexJTF);
		//��ť������
		queryJB=new JButton("��ѯ");
		buttonJP.add(queryJB);
		//ע�������
		queryJB.addActionListener(new PointQueryActionListener());
		//��Ӳ�ѯ�����ڴ�����в�
		this.add(pointQueryJP,BorderLayout.CENTER);
		//��Ӱ�ť�����ڴ�����ϲ�
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}	
	class PointQueryActionListener implements ActionListener{
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
			if(!GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(vertex)){
				JOptionPane.showMessageDialog(null, "���㲻����");
				return;
			}
			JOptionPane.showMessageDialog(null, "�ö����ע�Ķ�����"+PointQuery.pointQueryOutDegree(Integer.parseInt(day)-1, vertex)
										  +"\n"+"��ע�ö���Ķ�����"+PointQuery.pointQueryInDegree(Integer.parseInt(day)-1, vertex)
										  +"\n"+"�����ע�Ķ�����"+PointQuery.pointQuery(),"��ѯ���",1);
			//			PointQueryView.this.setVisible(false);
		}
	}
}
