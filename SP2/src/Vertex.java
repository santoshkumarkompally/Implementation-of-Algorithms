import java.util.ArrayList;
import java.util.List;

public class Vertex {

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

	Vertex(int name) {

		this.name = name;
		seen = false;
		distance = 0;
		degree = 0;
		top = 0;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>();

	}

	@Override
	public String toString() {
		return "Vertex [name=" + name + "]";
	}

}
