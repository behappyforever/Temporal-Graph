package chronos.graph;

import java.util.LinkedList;

public class Vertex{
	private long id;
	private long in_degree;
	private long out_degree;
	private double pr;
	private double receiveSumPr;
	private LinkedList<Long> outGoing;//边表结点，以链表的形式存储该顶点所指向的边
	public Vertex(long id){
		setId(id);
		in_degree=0;
		out_degree=0;
		pr=0;
		outGoing=new LinkedList();
	}
	
	public boolean addEdge(long desId){//该顶点边表加入弧头顶点id
		if(!outGoing.contains(desId)){
			outGoing.add(desId);
			out_degree++;
			return true;
		}
		return false;//已存在
	}
	public boolean deleteEdge(long v){//传入顶点id
		int temp=outGoing.indexOf(v);
		if(temp!=-1){
			outGoing.remove(temp);//此处传入的是索引
			out_degree--;
			return true;
		}
		return false;//已删除
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIn_degree() {
		return in_degree;
	}
	
	public void setIn_degree(long in_degree) {
		this.in_degree = in_degree;
	}
	
	public long getOut_degree() {
		return out_degree;
	}
	
	public void setOut_degree(long out_degree) {
		this.out_degree = out_degree;
	}
	public LinkedList<Long> getOutGoingList(){
		return outGoing;
	}
	public void setOutGoingList(LinkedList<Long> link){
		outGoing.addAll(link);
	}
	
	public double getPr() {
		return pr;
	}
	public double setPr(double pr) {//此set方法返回值用于判断是否收敛
		double temp=Math.abs(this.pr-pr);
		this.pr=pr;
		return temp;
	}
	public double getReceiveSumPr() {
		return receiveSumPr;
	}

	public void setReceiveSumPr(double receiveSumPr) {
		this.receiveSumPr = receiveSumPr;
	}
	public void addReceiveSumpr(double temp) {
		receiveSumPr+=temp;
	}
	public void deleteReceiveSumpr(double temp) {
		receiveSumPr-=temp;
	}
}
