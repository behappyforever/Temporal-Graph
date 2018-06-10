package preprocessing;

import com.tg.graph.TGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//srcId descId1 w1 w2...wn
//      descId2 w1 w2...wn
public class Preprocess {
    private static  String filePath;
    private static List<String> logList;
    private static Map<Long,Map<Long,long[]>> map;

    public static void loadPath(){
        filePath="DataSets/test.txt";
        logList = new ArrayList();
        for (int i = 1; i < TGraph.timeRange; i++) {
           logList.add("DataSets/day" +i+ ".txt");
        }
    }

    public static void main(String[] args) {
        loadPath();
        //读文件 todo
    }
}
