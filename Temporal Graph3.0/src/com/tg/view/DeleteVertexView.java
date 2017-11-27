package com.tg.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class DeleteVertexView extends JFrame{
	private static final long serialVersionUID=1l;
	public DeleteVertexView(int day,int vertex){
		BufferedWriter bw=null;
		try {
			File file=new File("DataSets/deleteVertexDay"+String.valueOf(day)+".txt");
			FileWriter fw=new FileWriter(file,true);
			bw = new BufferedWriter(fw);
			if(file.length()!=0){
				bw.newLine();
			}
			bw.write(String.valueOf(vertex));
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
