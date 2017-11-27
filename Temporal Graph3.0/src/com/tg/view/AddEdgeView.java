package com.tg.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class AddEdgeView extends JFrame{
	private static final long serialVersionUID=1l;
	public AddEdgeView(int day,int from,int to){
		BufferedWriter bw=null;
		try {
			File file=new File("DataSets/addEdgesDay"+String.valueOf(day)+".txt");
			FileWriter fw=new FileWriter(file,true);
			bw = new BufferedWriter(fw);
			if(file.length()!=0){
				bw.newLine();
			}
			bw.write(String.valueOf(from));
			bw.write(" ");
			bw.write(String.valueOf(to));
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
