import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Graph implements Iterable<Vertex> {

	List<Vertex> verts;
	int numberOfVertices;
	int numberOfEdges;

	Graph(int size, int noOfEdges) {

		numberOfVertices = size;
		numberOfEdges = noOfEdges;
		verts = new ArrayList<>(size + 1);
		// we do not use the first vertex.
		verts.add(0, null);

		// creating vertex objects.
		for (int i = 1; i <= size; i++) {

			verts.add(i, new Vertex(i));
		}
	}

	void addEdge(int a, int b, int weight) {

		Vertex u = verts.get(a);
		Vertex v = verts.get(b);
		Edge edge = new Edge(u, v, weight);
		u.Adj.add(edge);
		v.Adj.add(edge);
		u.degree++;
		v.degree++;
	}

	void addDirectedEdge(int a, int b, int weight) {

		Vertex u = verts.get(a); // tail
		Vertex v = verts.get(b); // head
		Edge edge = new Edge(u, v, weight);
		u.Adj.add(edge); // tail
		v.revAdj.add(edge); // head
		u.outdegree++;
		v.indegree++;
	}

	@Override
	public Iterator<Vertex> iterator() {
		// TODO Auto-generated method stub
		return new VertexIterator();
	}

	private class VertexIterator implements Iterator<Vertex> {

		private Iterator<Vertex> it;

		private VertexIterator() {

			it = verts.iterator();
			it.next();
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return it.hasNext();
		}

		@Override
		public Vertex next() {
			// TODO Auto-generated method stub
			return it.next();
		}

	}

	// function to take the input.

	public static Graph readGraph(Scanner in, boolean directed) {
		// read the graph related parameters
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph
		// create a graph instance
		Graph g = new Graph(n, m);
		for (int i = 0; i < m; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int w = in.nextInt();
			if (directed) {
				g.addDirectedEdge(u, v, w);
			} else {
				g.addEdge(u, v, w);
			}
		}
		in.close();
		return g;
	}

}
