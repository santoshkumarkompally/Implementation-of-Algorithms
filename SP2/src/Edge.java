
public class Edge {

	int weight;
	Vertex from;
	Vertex to;

	// constructor
	Edge(Vertex from, Vertex to, int weight) {

		this.from = from;
		this.to = to;
		this.weight = weight;

	}

	public Vertex otherEnd(Vertex u) {

		if (u == to)
			return from;
		else
			return to;
	}

	@Override
	public String toString() {
		return "Edge [weight=" + weight + ", from=" + from + ", to=" + to + "]";
	}

}
