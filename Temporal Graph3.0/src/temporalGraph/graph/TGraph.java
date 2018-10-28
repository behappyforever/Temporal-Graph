package temporalGraph.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TGraph {
    public static final int timeRange = 10;
    public static String vsFileName;//初始图文件路径
    public static List<String> deltaLogPath;//原始日志路径
    //时序图所有组件    1个组的
    public static GraphSnapshot graphSnapshot;//全图快照VS
    public static List<String>[] deltaLogList;//增量日志数组 n个
    public static GraphSnapshot[] deltaGraphSnapshotArr;//增量快照 与上面日志对应

    //数组下标对应时间(时间为一级索引),数组放入Map，Map的key为顶点Id，value为顶点的出边表(List<Edge>)
    public static Map<Long,List<Edge>>[] strucLocalityDeltaSnapshot;

    //增量快照的时间局部性布局,key为顶点Id,value为一个长度为timeRange的数组，数组放入一个顶点的出边表(List<Edge>)
    public static Map<Long, List<Edge>[]> timeLocalityDeltaSnapshot;

    private static String inputDir="Persistence";

    public static void setPath() {
        // 加载数据集路径
        vsFileName = inputDir+"/VS.txt";
        deltaLogPath = new ArrayList();
        for (int i = 0; i < timeRange; i++) {
            deltaLogPath.add(inputDir+"/delta" + i + ".txt");
        }
    }

    public static void start() {
        setPath();//设置文件路径

        loadVS();// 加载VS

        readDeltaLog();// 读取增量日志

        buildStructureLocality();//构造结构局部性布局

        buildTimeLocality();//构造时间局部性布局

    }


    public static void loadVS() {// 读VS

        graphSnapshot = new GraphSnapshot();
        BufferedReader br = null;
        try {
            File file = new File(vsFileName);
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {// 按行VS
                graphSnapshot.addEdge(str);//交给addEdge解析string
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

    private static void readDeltaLog() {//边集
        deltaLogList = new ArrayList[timeRange];//读入增量日志，即为结构局部性布局

        for (int i = 0; i < timeRange; i++) {//初始化list数组
            deltaLogList[i] = new ArrayList();
        }
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            for (int i = 0; i < timeRange; i++) {
                file = new File(deltaLogPath.get(i));
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String str;
                while ((str = br.readLine()) != null) {
                    deltaLogList[i].add(str);
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

    public static void buildStructureLocality(){
        //生成结构局部性增量快照
        strucLocalityDeltaSnapshot=new Map[timeRange];
        for (int i = 0; i < timeRange; i++) {//初始化list数组
            strucLocalityDeltaSnapshot[i]=new HashMap();
            for(String s:deltaLogList[i]){
                Scanner sc = new Scanner(s);
                Long srcId=sc.nextLong();
                Long desId=sc.nextLong();
                Long weight=sc.nextLong();
                Map<Long, List<Edge>> tmpRef = strucLocalityDeltaSnapshot[i];
                if(!tmpRef.containsKey(srcId)){
                    List<Edge> tmpList=new LinkedList();
                    tmpList.add(new Edge(desId,weight));
                    tmpRef.put(srcId,tmpList);
                }else{
                    tmpRef.get(srcId).add(new Edge(desId,weight));
                }
            }

        }
    }

    public static void buildTimeLocality() {
        //生成时间局部性增量快照
        timeLocalityDeltaSnapshot = new HashMap();
        for (int i = 0; i < timeRange; i++) {//i为组内时间
            for(String s:deltaLogList[i]){
                Scanner sc = new Scanner(s);
                Long srcId=sc.nextLong();
                Long desId=sc.nextLong();
                Long weight=sc.nextLong();
                if (!timeLocalityDeltaSnapshot.containsKey(srcId)) {
                    List<Edge>[] tmpList = new List[timeRange];
                    tmpList[i] = new ArrayList();
                    tmpList[i].add(new Edge(desId,weight));
                    timeLocalityDeltaSnapshot.put(srcId, tmpList);
                } else {
                    List<Edge>[] tmpList = timeLocalityDeltaSnapshot.get(srcId);
                    if (tmpList[i] == null) {
                        tmpList[i] = new ArrayList();
                    }
                    tmpList[i].add(new Edge(desId,weight));
                }
            }
        }
    }
}
