package com.tg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import com.tg.graph.GraphSnapshot;
import com.tg.graph.SnapshotLog;
import com.tg.graph.TGraph;
import com.tg.graph.Vertex;

public class LoadTG {

	public static ArrayList<HashSet<String>> addEdgeArr;// 0 1,1 2,2 3...
	public static ArrayList<HashSet<String>> deleteEdgeArr;

	public static void loadGraph() {// ����ʼͼ
		
		TGraph.graphSnapshot = new GraphSnapshot();
		BufferedReader br = null;
		try {
			File file = new File(TGraph.fileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String str;
			StringTokenizer token;
			while ((str = br.readLine()) != null) {// ���ж���Datasets
				token = new StringTokenizer(str);// �Կո���Ϊ�ָ����õ���������from->to
				TGraph.graphSnapshot.addEdge(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
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
		addEdgeArr = new ArrayList<HashSet<String>>();
		deleteEdgeArr = new ArrayList<HashSet<String>>();
		for (int i = 0; i < 9; i++) {// 9����־
			addEdgeArr.add(new HashSet<String>());
			deleteEdgeArr.add(new HashSet<String>());
		}

		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			for (int i = 0; i < 9; i++) {
				file = new File(TGraph.addEdgeList.get(i));
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String str;
				while ((str = br.readLine()) != null) {
					addEdgeArr.get(i).add(str);
				}
			}
			for (int i = 0; i < 9; i++) {
				file = new File(TGraph.delEdgeList.get(i));
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

	public static void computeVirtualGraphSnapshot() {
		Iterator<String> iterStr;
		StringTokenizer stringTokenizer;
		// }
		for (int i = 0; i < 9; i++) {
			iterStr = deleteEdgeArr.get(i).iterator();
			while (iterStr.hasNext()) {
				stringTokenizer = new StringTokenizer(iterStr.next());
				TGraph.graphSnapshot.deleteEdge(Integer.parseInt(stringTokenizer.nextToken()),
						Integer.parseInt(stringTokenizer.nextToken()));
			}
		}
	}

	public static void afterComputeVS() {// ���ؿ���֮����һЩ���㹤��
		GraphSnapshot graphSnapshot = TGraph.graphSnapshot;
		HashMap<Integer, Vertex> vertexMap = graphSnapshot.getHashMap();
		ArrayList<Integer> delTemp = new ArrayList<Integer>();
		for (Entry<Integer, Vertex> en : vertexMap.entrySet()) {// ɾ�������ĵ�
			if (en.getValue().getIn_degree() == 0 && en.getValue().getOut_degree() == 0) {
				delTemp.add(en.getKey());
			}
		}
		for (Integer key : delTemp) {
			vertexMap.remove(key);
		}
		System.out.println("������ն������ͱ���");
		graphSnapshot.countVerAndEdgeNum();
	}

	public static void computeDeltaSnapshotLog() {
		// ������������ ��S0+ ... ��S9+ ��ʮ����������
		TGraph.snapshotLogArr = new SnapshotLog[10];
		for (int i = 0; i < 10; i++) {
			TGraph.snapshotLogArr[i] = new SnapshotLog();
		}

		for (int i = 0; i < 10; i++) {// i���Ƶڼ�����������,j���Ƶڼ����仯��־
			for (int j = 0; j < i; j++) {
				TGraph.snapshotLogArr[i].setAddEdge(LoadTG.addEdgeArr.get(j));
			}
			for (int j = i; j < 9; j++) {
				TGraph.snapshotLogArr[i].setAddEdge(LoadTG.deleteEdgeArr.get(j));
			}
		}
		System.out.println("������־����:" + TGraph.snapshotLogArr[0].getAddEdgeSize());
		System.out.println("������־����:" + TGraph.snapshotLogArr[1].getAddEdgeSize());
		System.out.println("������־����:" + TGraph.snapshotLogArr[2].getAddEdgeSize());
		System.out.println("������־����:" + TGraph.snapshotLogArr[3].getAddEdgeSize());
	}
}