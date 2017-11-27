package com.tg.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.tg.function.GetNumOfEdges;
import com.tg.function.GetNumOfVertex;
import com.tg.function.GetVertexId;
import com.tg.graph.Main;
import com.tg.graph.TGraph;

public class DisplayView extends JFrame{
private static final long serialVersionUID=1l;
	
	private JPanel jPanel,buttonJP,buttonChangeJP;
	private JTable jTable;
	private JScrollPane jScrollPane;
	private JButton refreshJB,addEdgeJB,deleteEdgeJB,addVertexJB,deleteVertexJB;
	private JComboBox<String> timeJCB;
	private JTextField vertexJTF,fromEdgeJTF,toEdgeJTF;
	private JLabel vertexJL,timeJL,fromEdgeJL,toEdgeJL;
	
	public DisplayView(){
		setBounds(200,200,500,500);
		setTitle("չʾͼ�Ļ�����Ϣ");
		jPanel=new JPanel();
		jPanel.setLayout(new BorderLayout());
		String[] display={"ʱ���","������","����","�Ƿ�洢����ͼ����"};
		Object[][] results=new Object[10][display.length];
		for(int i=0;i<10;i++){
			results[i][0]=i+1;
			results[i][1]=GetNumOfVertex.getNumOfVertex(i);
			results[i][2]=GetNumOfEdges.getNumOfEdges(i);
			results[i][3]=TGraph.graph[i].getFlag();
		}
		jScrollPane= new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(400, 200));
		jTable=new JTable();
		jTable=new JTable(results,display);
		jScrollPane.setViewportView(jTable);
		jPanel.add(jScrollPane);
		this.add(jPanel,BorderLayout.NORTH);		
	
		buttonChangeJP=new JPanel();
		buttonChangeJP.setLayout(null);
		timeJL=new JLabel("ʱ���:");
		buttonChangeJP.add(timeJL);
		//ʱ��������б�
		String[] temp={"2","3","4","5","6","7","8","9","10"};
		timeJCB=new JComboBox<>(temp);
		buttonChangeJP.add(timeJCB);
		timeJCB.setBounds(10, 10, 50, 40);
		addEdgeJB=new JButton("���ӱ�");
		deleteEdgeJB=new JButton("ɾ����");
		addVertexJB=new JButton("���Ӷ���");
		deleteVertexJB=new JButton("ɾ������");
		buttonChangeJP.add(addEdgeJB);
		buttonChangeJP.add(deleteEdgeJB);
		buttonChangeJP.add(addVertexJB);
		buttonChangeJP.add(deleteVertexJB);
		addEdgeJB.setBounds(10, 150, 100, 40);
		deleteEdgeJB.setBounds(130, 150, 100, 40);
		addVertexJB.setBounds(250, 150, 100, 40);
		deleteVertexJB.setBounds(370, 150, 100, 40);
		addEdgeJB.addActionListener(new AddEdgeActionListener());
		deleteEdgeJB.addActionListener(new DeleteEdgeActionListener());
		addVertexJB.addActionListener(new AddVertexActionListener());
		deleteVertexJB.addActionListener(new DeleteVertexActionListener());
		fromEdgeJL=new JLabel("��:From");
		buttonChangeJP.add(fromEdgeJL);
		fromEdgeJL.setBounds(100, 10, 100, 40);
		fromEdgeJTF=new JTextField();
		buttonChangeJP.add(fromEdgeJTF);
		fromEdgeJTF.setBounds(150, 10, 50, 40);
		toEdgeJL=new JLabel("To");
		buttonChangeJP.add(toEdgeJL);
		toEdgeJL.setBounds(210,10,100,40);
		toEdgeJTF=new JTextField();
		buttonChangeJP.add(toEdgeJTF);
		toEdgeJTF.setBounds(235, 10, 50, 40);
		vertexJL=new JLabel("����:");
		buttonChangeJP.add(vertexJL);
		vertexJL.setBounds(100,70,100,40);
		vertexJTF=new JTextField();
		buttonChangeJP.add(vertexJTF);
		vertexJTF.setBounds(150, 70, 50, 40);
		this.add(buttonChangeJP,BorderLayout.CENTER);
		
		buttonJP=new JPanel();
		refreshJB=new JButton("Refresh");
		buttonJP.add(refreshJB);
		refreshJB.addActionListener(new RefreshActionListener());
		this.add(buttonJP,BorderLayout.SOUTH);
		this.setVisible(true);
	}
	class RefreshActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			TGraph.start(Main.fileName);
			dispose();
			new DisplayView();
		}
	}
	class AddEdgeActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			if(fromEdgeJTF.getText().length()==0|toEdgeJTF.getText().length()==0){
				JOptionPane.showMessageDialog(null, "���벻��ȷ");
				return;
			}
			if(!fromEdgeJTF.getText().matches("[0-9]+")|!toEdgeJTF.getText().matches("[0-9]+")){
				JOptionPane.showMessageDialog(null, "���зǷ��ַ�");
				return;
			}
			int from=Integer.parseInt(fromEdgeJTF.getText().trim());//������
			int to=Integer.parseInt(toEdgeJTF.getText().trim());
			String day=(String)timeJCB.getSelectedItem();
			if(!GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(from)){
				JOptionPane.showMessageDialog(null, "����"+from+"�����ڣ����ȼ��붥��");
				return;
			}
			if(!GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(to)){
				JOptionPane.showMessageDialog(null, "����"+to+"�����ڣ����ȼ��붥��");
				return;
			}
			new AddEdgeView(Integer.parseInt(day)-1,from,to);
		}
	}
	class DeleteEdgeActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			if(fromEdgeJTF.getText().length()==0|toEdgeJTF.getText().length()==0){
				JOptionPane.showMessageDialog(null, "���벻��ȷ");
				return;
			}
			if(!fromEdgeJTF.getText().matches("[0-9]+")|!toEdgeJTF.getText().matches("[0-9]+")){
				JOptionPane.showMessageDialog(null, "���зǷ��ַ�");
				return;
			}
			int from=Integer.parseInt(fromEdgeJTF.getText().trim());//������
			int to=Integer.parseInt(toEdgeJTF.getText().trim());
			String day=(String)timeJCB.getSelectedItem();
			if(!GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(from)){
				JOptionPane.showMessageDialog(null, "����"+from+"�����ڣ����ȼ��붥��");
				return;
			}
			if(!GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(to)){
				JOptionPane.showMessageDialog(null, "����"+to+"�����ڣ����ȼ��붥��");
				return;
			}
			new DeleteEdgeView(Integer.parseInt(day)-1,from,to);
		}
	}
	class AddVertexActionListener implements ActionListener{
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
			if(GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(vertex)){
				JOptionPane.showMessageDialog(null, "����"+vertex+"�Ѵ��ڣ��޷����");
				return;
			}
			new AddVertexView(Integer.parseInt(day)-1,vertex);
		}
	}
	class DeleteVertexActionListener implements ActionListener{
		public void actionPerformed(final ActionEvent e){
			int vertex=Integer.parseInt(vertexJTF.getText().trim());//������
			String day=(String)timeJCB.getSelectedItem();
			if(!GetVertexId.getVertexId(Integer.parseInt(day)-1).contains(vertex)){
				JOptionPane.showMessageDialog(null, "����"+vertex+"�����ڣ��޷�ɾ��");
				return;
			}
			new DeleteVertexView(Integer.parseInt(day)-1,vertex);
		}
	}
}
