package temporalGraph.graph;

import java.util.HashSet;
import java.util.Set;

public class SnapshotLog {//相对于虚拟快照VS的delta+增量快照
	private Set<String> addEdge;

	public SnapshotLog() {
		addEdge=new HashSet();
	}
	public Set<String> getAddEdge() {
		return addEdge;
	}
	public void setAddEdge(Set<String> hashSet) {
		addEdge.addAll(hashSet);
	}
	public int getAddEdgeSize() {
		return addEdge.size();
	}
}
