package chronos.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class PrintVertexId {
	public static void printVertexId(Set<Integer> set){
		System.out.println("顶点个数为："+set.size());
		System.out.println("顶点id如下：");
		Iterator<Integer> iterator=set.iterator();
		ArrayList<Integer> list=new ArrayList();
		while(iterator.hasNext()){
			list.add(iterator.next());
		}
		list.sort(null);
		iterator=list.iterator();
		int i=0;
		while(iterator.hasNext()){
			if(i>20){
				System.out.println();
				i=0;
			}
			System.out.print(iterator.next()+" ");
			i++;
		}
	}
}
