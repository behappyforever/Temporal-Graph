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
    public static List<String>[] strucLocalityDelta;//增量日志数组 n个
    public static GraphSnapshot[] deltaGraphSnapshotArr;//增量快照 与上面日志对应
    public static Map<Long, List[]> timeLocalityDeltaSnapshot;//增量快照的时间局部性布局


    public static void setPath() {
        // 加载数据集路径
        vsFileName = "Persistence/VS.txt";
        deltaLogPath = new ArrayList();
        for (int i = 1; i < timeRange; i++) {
            deltaLogPath.add("Persistence/day" + i + ".txt");
        }
    }

    public static void start() {
        setPath();//设置文件路径

        loadVS();// 加载VS

        readDeltaLog();// 读取增量日志,构造结构局部性布局

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
        strucLocalityDelta = new ArrayList[timeRange];//读入增量日志，即为结构局部性布局

        for (int i = 0; i < timeRange; i++) {//初始化list数组
            strucLocalityDelta[i] = new ArrayList();
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
                    strucLocalityDelta[i].add(str);
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

    public static void buildTimeLocality() {
        //负责从结构局部性增量快照生成时间局部性增量快照
        timeLocalityDeltaSnapshot = new HashMap();
        Map<Long, List[]> map = timeLocalityDeltaSnapshot;
        for (int i = 0; i < timeRange; i++) {//i为组内时间
            List<String> list = strucLocalityDelta[i];
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                Scanner sc = new Scanner(it.next());
                Long from = sc.nextLong();
                Long to = sc.nextLong();
//                Long weight=sc.nextLong();权值待处理
                if (!map.containsKey(from)) {
                    List[] l = new List[timeRange];
                    l[i] = new ArrayList();
                    l[i].add(to);
                    map.put(from, l);
                } else {
                    List[] lt = map.get(from);
                    if (lt[i] == null) {
                        lt[i] = new ArrayList();
                    }
                    lt[i].add(to);
                }
            }
        }
    }
}
