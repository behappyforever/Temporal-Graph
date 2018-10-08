package preprocessing;

import chronos.graph.TGraph;

import java.io.*;
import java.util.*;


//srcId descId1 w1 w2...wn
//      descId2 w1 w2...wn
public class Preprocess {
    private static String filePath;//初始图路径
    private static List<String> logList;//原始日志路径
    private static Map<Long, Map<Long, long[]>> map;//预处理 暂存图快照
    private static List<Set<String>> addLog;//预处理 暂存日志
    private static List<Set<String>> delLog;
    private static List<Set<String>> modLog;
    private static List<Set<String>> deltaLog;
    private static String persistenceDir="Persistence2";
    private static String inputFileName="web-NotreDame";


    public static void loadPath() {
        filePath = "DataSets/"+inputFileName+".txt";
        logList = new ArrayList();
        for (int i = 1; i < TGraph.timeRange; i++) {
            logList.add("DataSets/day" + i + ".txt");
        }
    }

    public static void readFile() {
        map = new TreeMap<>();
        //读文件
        BufferedReader br = null;
        try {
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {// 按行读入原始图数据
                String[] split = str.split("\t");
                long srcId = Long.parseLong(split[0]);
                long desId = Long.parseLong(split[1]);
//                long weight=Long.parseLong(split[2]);
                long weight = 1;//数据集无权值，暂时模拟为1
                //装载数据
                Map<Long, long[]> innerMap;
                if (!map.containsKey(srcId)) {//源顶点不存在于map
                    innerMap = new TreeMap<>();
                } else {//map中存在源顶点
                    innerMap = map.get(srcId);
                }
                long[] weightList = new long[TGraph.timeRange];
                for (int i = 0; i < weightList.length; i++) {
                    weightList[i] = weight;
                }
                innerMap.put(desId, weightList);
                map.put(srcId, innerMap);
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

    public static void readLog() {
        //读日志
        addLog = new ArrayList();
        delLog = new ArrayList();
        modLog = new ArrayList();
        for (int i = 0; i < TGraph.timeRange - 1; i++) {// 9个日志
            addLog.add(new HashSet());
            delLog.add(new HashSet());
            modLog.add(new HashSet());
        }

        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            for (int i = 0; i < TGraph.timeRange - 1; i++) {
                file = new File(logList.get(i));
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String str;
                while ((str = br.readLine()) != null) {

                    switch (str.charAt(0)) {
                        case 'A':
                            addLog.get(i).add(str.substring(2)+"\t"+1);
                            break;
                        case 'D':
                            delLog.get(i).add(str.substring(2)+"\t"+1);
                            break;
                        case 'M':
                            modLog.get(i).add(str.substring(2));
                            break;
                    }
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

    public static void computeVS() {

        Iterator<String> iter;
        //处理删除边
        for (int i = 0; i < TGraph.timeRange - 1; i++) {
            iter = delLog.get(i).iterator();
            while (iter.hasNext()) {
                Scanner sc = new Scanner(iter.next());//解析每一行的源顶点和目的顶点，无需权值
                long srcId = sc.nextLong();
                long desId = sc.nextLong();
                if (map.containsKey(srcId)) {
                    map.get(srcId).remove(desId);
                }
            }
        }

        //处理修改权重
        for (int i = 0; i < TGraph.timeRange - 1; i++) {
            iter = modLog.get(i).iterator();
            while (iter.hasNext()) {
                Scanner sc = new Scanner(iter.next());
                long srcId = sc.nextLong();
                long desId = sc.nextLong();
                long weight = sc.nextLong();
                map.get(srcId).get(desId)[i] = weight;
            }
        }
    }

    public static void persistVS() {
        FileOutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            out = new FileOutputStream(new File(persistenceDir+"/VS.txt"));
            bos = new BufferedOutputStream(out);
            for (Map.Entry<Long, Map<Long, long[]>> en : map.entrySet()) {
                for (Map.Entry<Long, long[]> innerEn : en.getValue().entrySet()) {
                    bos.write(en.getKey().toString().getBytes());
                    bos.write("\t".getBytes());
                    bos.write(innerEn.getKey().toString().getBytes());
                    long[] weightArr = innerEn.getValue();
                    for (long weight : weightArr) {
                        bos.write("\t".getBytes());
                        bos.write(String.valueOf(weight).getBytes());
                    }
                    bos.write("\r\n".getBytes());
                }
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void computeDeltaLog() {
        // 计算增量快照 △S0+ ... △S9+ 共十个增量快照
        deltaLog=new ArrayList();
        for (int i = 0; i < TGraph.timeRange; i++) {
            deltaLog.add(new HashSet());
        }

        for (int i = 0; i < TGraph.timeRange; i++) {// i控制第几个增量快照,j控制第几个变化日志
            for (int j = 0; j < i; j++) {
                deltaLog.get(i).addAll(addLog.get(j));
            }
            for (int j = i; j < TGraph.timeRange - 1; j++) {
                deltaLog.get(i).addAll(delLog.get(j));
            }
        }
    }

    public static void persistDeltaLog() {
        //不区分布局，简单持久化
        List<Map<Long,Map<Long,Long>>> mapList=new ArrayList();
        Iterator<String> iter;
        for(int i=0;i<TGraph.timeRange;i++){
            mapList.add(new TreeMap<>());//利用TreeMap保证key有序
            iter= deltaLog.get(i).iterator();
            while(iter.hasNext()){
                Scanner sc = new Scanner(iter.next());
                long srcId = sc.nextLong();
                long desId = sc.nextLong();
                long weight = sc.nextLong();
                if(!mapList.get(i).containsKey(srcId)){
                    Map<Long,Long> tmp=new TreeMap();
                    tmp.put(desId,weight);
                    mapList.get(i).put(srcId,tmp);
                }else{
                    mapList.get(i).get(srcId).put(desId,weight);
                }
            }
        }

        FileOutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            for (int i = 0; i < mapList.size(); i++) {
                out = new FileOutputStream(new File(persistenceDir+"/delta"+i+".txt"));
                bos = new BufferedOutputStream(out);
                for (Map.Entry<Long, Map<Long, Long>> en : mapList.get(i).entrySet()) {
                    for (Map.Entry<Long,Long> innerEn : en.getValue().entrySet()) {
                        bos.write(en.getKey().toString().getBytes());
                        bos.write("\t".getBytes());
                        bos.write(innerEn.getKey().toString().getBytes());
                        bos.write("\t".getBytes());
                        bos.write(innerEn.getValue().toString().getBytes());
                        bos.write("\r\n".getBytes());
                    }
                }
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
