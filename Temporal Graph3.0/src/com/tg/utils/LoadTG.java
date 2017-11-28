package com.tg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.tg.graph.Main;
import com.tg.graph.TGraph;

public class LoadTG {

//	public static ArrayList<HashSet<Integer>> addVertexArr;// 0,1,2,3...
	public static ArrayList<HashSet<String>> addEdgeArr;// 0 1,1 2,2 3...
//	public static ArrayList<HashSet<Integer>> deleteVertexArr;
	public static ArrayList<HashSet<String>> deleteEdgeArr;

	public static void loadGraph() {// 读初始图
		BufferedReader br = null;
		try {
			File file = new File(Main.fileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String str;
			StringTokenizer token;
			while ((str = br.readLine()) != null) {// 按行读入Datasets
				token = new StringTokenizer(str);// 以空格作为分隔符得到两个顶点from->to
//				TGraph.graphSnapshot.addVertex(p);
//				TGraph.graphSnapshot.addVertex(q);
				TGraph.snapshot.addEdge(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // if
		} // finally
	}

	public static void readRawLog() {
//		addVertexArr=new ArrayList<HashSet<Integer>>();
		addEdgeArr=new ArrayList<HashSet<String>>() ;
//		deleteVertexArr=new ArrayList<HashSet<Integer>>();
		deleteEdgeArr=new ArrayList<HashSet<String>>();
		for (int i = 0; i < 9; i++) {//9个日志
//			addVertexArr.add(new HashSet<Integer>());
			addEdgeArr.add(new HashSet<String>());
//			deleteVertexArr.add(new HashSet<Integer>());
			deleteEdgeArr.add(new HashSet<String>());
		}

		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
//			for (int i = 0; i < 9; i++) {
//				file = new File(Main.addVerList.get(i));
//				fr = new FileReader(file);
//				br = new BufferedReader(fr);
//				String str;
//				while ((str = br.readLine()) != null) {
//					addVertexArr.get(i).add(Integer.parseInt(str));
//				}
//			}
			for (int i = 0; i < 9; i++) {
				file = new File(Main.addEdgeList.get(i));
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String str;
				while ((str = br.readLine()) != null) {
					addEdgeArr.get(i).add(str);
				}
			}
//			for (int i = 0; i < 9; i++) {
//				file = new File(Main.delVerList.get(i));
//				fr = new FileReader(file);
//				br = new BufferedReader(fr);
//				String str;
//				while ((str = br.readLine()) != null) {
//					deleteVertexArr.get(i).add(Integer.parseInt(str));
//				}
//			}
			for (int i = 0; i < 9; i++) {
				file = new File(Main.delEdgeList.get(i));
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String str;
				while ((str = br.readLine()) != null) {
					deleteEdgeArr.get(i).add(str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void preCompute() {
		Iterator<String> iterStr;
		StringTokenizer stringTokenizer;
//		for (int i = 0; i < 10; i++) {
//			iterInt = deleteVertexArr.get(i).iterator();
//			while (iterInt.hasNext()) {
//				TGraph.graphSnapshot.deleteVertex(iterInt.next());
//			}
//		}
		for (int i = 0; i < 9; i++) {
			iterStr = deleteEdgeArr.get(i).iterator();
			while (iterStr.hasNext()) {
				stringTokenizer = new StringTokenizer(iterStr.next());
				TGraph.snapshot.deleteEdge(Integer.parseInt(stringTokenizer.nextToken()),
						Integer.parseInt(stringTokenizer.nextToken()));
			}
		}
		
	}
}