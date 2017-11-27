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
	//�������
	private JPanel rangeQueryJP,buttonJP;
	//�����ǩ
	private JLabel timeFromJL,timeToJL;
	//ʱ����Ͽ�
	private JComboBox<String> timeFromJCB,timeToJCB;
	//��ť
	private JButton queryJB;
	//���췽��
	public DiameterRangeQueryView(){
		setBounds(200,200,500,110);
		setTitle("����ʱ�䷶Χ��ѯ");
		//��ť���
		buttonJP=new JPanel();
		//����ʱ�䷶Χ��ѯ������
		rangeQueryJP=new JPanel();
		rangeQueryJP.setBorder(new EmptyBorder(5, 10, 5, 10));
		final GridLayout gridLayout=new GridLayout(1, 3);
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		rangeQueryJP.setLayout(gridLayout);
		getContentPane().add(rangeQueryJP);
		//���ʱ���ǩ����ѯ���
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
		//��ť������
		queryJB=new JButton("��ѯ");
		buttonJP.add(queryJB);
		//ע�������
		queryJB.addActionListener(new RangeQueryActionListener());
		//��Ӳ�ѯ�����ڴ�����в�
		this.add(rangeQueryJP,BorderLayout.CENTER);
		//��Ӱ�ť�����ڴ�����ϲ�
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
		setResizable(false);
	}	
	class RangeQueryActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			String from=(String)timeFromJCB.getSelectedItem();
			String to=(String)timeToJCB.getSelectedItem();
			if(Integer.parseInt(from)>=Integer.parseInt(to)){
				JOptionPane.showMessageDialog(null,"ʱ���ѡ������");
				return;
			}
			double diameter=0;
			int range=Integer.parseInt(to)-Integer.parseInt(from)+1;
			for(int i=Integer.parseInt(from)-1;i<=Integer.parseInt(to)-1;i++){
				diameter+=TGraph.graph[i].getDiameter();
			}
			diameter=diameter/range;
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
			JOptionPane.showMessageDialog(null, "�ö�ʱ����ͼֱ��Ϊ��"+decimalFormat.format(diameter),"��ѯ���",1);
		}
	}
}
