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
		setTitle("ʱ��ͼ���ݵĹ���ͷ���ϵͳ");
//		setResizable(false); ���ô����С���ɵ�
		JMenuBar jmenuBar=createJMenuBar();
		setJMenuBar(jmenuBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	//�Զ��巽��createJMenuBar(),����һ��JMenuBar������ʵ�ִ����˵����Ĺ��ܣ��ڹ��췽���е��ø÷�����
	private JMenuBar createJMenuBar(){
		JMenuBar jMenuBar=new JMenuBar();//�˵���
		//��ѯ����
		JMenu queryJMenu=new JMenu("��ѯ����");
		//���ڵ�
		JMenuItem pointQueryJMI=new JMenuItem("���ڵ��ѯ");
		queryJMenu.add(pointQueryJMI);
		//���¼�Դע�������
		pointQueryJMI.addActionListener(new PointQueryListener());
		
		//���ڷ�Χ
		JMenuItem rangeQueryJMI=new JMenuItem("����ʱ�䷶Χ��ѯ");
		queryJMenu.add(rangeQueryJMI);
		//ע�������
		rangeQueryJMI.addActionListener(new RangeQueryListener());
		
		//��ѯ����pagerankֵ
		JMenu pagerankJMenu=new JMenu("��ѯPageRankֵ");
		JMenuItem pageRankPointJMI=new JMenuItem("���ն���PageRankֵ");
		JMenuItem pageRankRangeJMI=new JMenuItem("ʱ��ζ���ƽ��PageRankֵ");
		pagerankJMenu.add(pageRankPointJMI);
		pagerankJMenu.add(pageRankRangeJMI);
		//ע�������
		pageRankPointJMI.addActionListener(new PageRankPointListener());
		pageRankRangeJMI.addActionListener(new PageRankRangeListener());
		//��ɾ����
		JMenu changeJMenu=new JMenu("ά��");
		JMenuItem infJMI=new JMenuItem("������Ϣ");
		//ע�������
		changeJMenu.add(infJMI);
		infJMI.addActionListener(new DisplayListener());
		//��ѯͼֱ��
		JMenu diameterJMenu=new JMenu("��ѯͼֱ��");
		JMenuItem diaPointJMI=new JMenuItem("ʱ������ֱ��");
		JMenuItem diaRangeJMI=new JMenuItem("ʱ���ƽ��ֱ��");
		diameterJMenu.add(diaPointJMI);
		diameterJMenu.add(diaRangeJMI);
		//ע�������
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
