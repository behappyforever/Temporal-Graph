package dataSet;

import java.io.*;
import java.util.*;

public class DataUtils {

    public static void undirectedToDirected() {
        //读文件
        BufferedReader br = null;
        try {
            File file = new File("DataSets/facebook.txt");
            FileReader fr = new FileReader(file);
            FileOutputStream out = null;
            BufferedOutputStream bos = null;
            br = new BufferedReader(fr);
            out = new FileOutputStream(new File("facebook.txt"));
            bos = new BufferedOutputStream(out);
            String str;
            while ((str = br.readLine()) != null) {// 按行读入原始图数据
                Scanner sc = new Scanner(str);
                long source=sc.nextLong();
                long des=sc.nextLong();
                bos.write(String.valueOf(source).getBytes());
                bos.write("\t".getBytes());
                bos.write(String.valueOf(des).getBytes());
                bos.write("\r\n".getBytes());
                bos.write(String.valueOf(des).getBytes());
                bos.write("\t".getBytes());
                bos.write(String.valueOf(source).getBytes());
                bos.write("\r\n".getBytes());
                bos.flush();
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

    public static void sortEdge(){
        //读文件
        BufferedReader br = null;
        try {
            File file = new File("DataSets/facebook.txt");
            FileReader fr = new FileReader(file);
            FileOutputStream out = null;
            BufferedOutputStream bos = null;
            br = new BufferedReader(fr);
            out = new FileOutputStream(new File("facebook.txt"));
            bos = new BufferedOutputStream(out);
            List<XY> list=new ArrayList<>();
            String str;
            while ((str = br.readLine()) != null) {// 按行读入原始图数据
                Scanner sc = new Scanner(str);
                long source=sc.nextLong();
                long des=sc.nextLong();
                list.add(new XY(source,des));
                Collections.sort(list, new Comparator<XY>() {
                    @Override
                    public int compare(XY o1, XY o2) {
                        return o1.source==o2.source?(int)(o1.des-o2.des):(int)(o1.source-o2.source);
                    }
                });
            }
            for (XY xy : list) {
                bos.write(String.valueOf(xy.source).getBytes());
                bos.write("\t".getBytes());
                bos.write(String.valueOf(xy.des).getBytes());
                bos.write("\r\n".getBytes());
            }
            bos.flush();
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
    static class XY{
        long source;
        long des;

        public XY(long source, long des) {
            this.source = source;
            this.des = des;
        }
    }
}