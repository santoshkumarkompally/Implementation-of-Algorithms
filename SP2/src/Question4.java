
/**
 * 
 * @author rbk
 * Example to illustrate an algorithm that tests if a given
 * undirected graph is bipartite.  Nodes are classified into
 * Outer and Inner nodes, the two sides of the bipartition.
 * Rewritten: Vertex, Edge, and Graph classes into their own files
 */

import java.io.*;
import java.util.*;

public class Question4 {

	static void cycleSearch(Vertex u, Vertex v) {
		List<Vertex> l = new ArrayList<>();
		if (!u.equals(v)) {

			l.add(u);
			l.add(v);
			u = u.parent;
			v = v.parent;
		}
		l.add(u);

		for (int i = 0; i < l.size(); i++) {

			if (i % 2 == 0) {

				System.out.println(l.get(i));
			}

		}

		for (int i = 0; i < l.size(); i++) {

			if (i % 2 != 0) {

				System.out.println(l.get(i));
			}

		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;

		if (args.length > 0) {

			sc = new Scanner(args[1]);

		} else {

			sc = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(sc, false); // read undirected graph from
												// stream "in"
		Queue<Vertex> Q = new LinkedList<>();

		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
			u.distance = Integer.MAX_VALUE;
		}

		// Run BFS on every component
		for (Vertex src : g) {
			if (!src.seen) {
				src.distance = 0;
				Q.add(src);
				src.seen = true;

				while (!Q.isEmpty()) {
					Vertex u = Q.remove();
					for (Edge e : u.Adj) {
						Vertex v = e.otherEnd(u);
						if (!v.seen) {
							v.seen = true;
							v.parent = u;
							v.distance = u.distance + 1;
							Q.add(v);
						} else {
							if (u.distance == v.distance) {
								cycleSearch(u, v);
								return;
							}
						}
					}
				}
			}
		}
		System.out.println("Graph is bipartite");
		for (Vertex u : g) {
			if (u.distance % 2 == 0) {
				System.out.println(u + " Outer");
			} else {
				System.out.println(u + " Inner");
			}
		}
	}
}