import java.util.ArrayList;
import java.util.List;

public class Vertex implements Index {

	int name;
	Boolean seen;
	Vertex parent;
	int distance; // distance from the source.
	List<Edge> Adj, revAdj;
	int indegree;
	int top;
	int outdegree;
	int componentNumber;
	int degree;
	int weight; // adding this variable for running Prim's Algorithm on indexed
				// heaps.
	int index;

	Vertex(int name) {

		this.name = name;
		seen = false;
		// distance = Integer.MAX_VALUE;
		degree = 0;
		top = 0;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>();
		weight = Integer.MAX_VALUE;
		index = name;
	}

	@Override
	public String toString() {
		return "Vertex [name=" + name + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Index#getIndex()
	 */
	@Override
	public int getIndex() {
		return index;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Index#setIndex()
	 */
	@Override
	public void setIndex(int index) {
		this.index = index;

	}

}
