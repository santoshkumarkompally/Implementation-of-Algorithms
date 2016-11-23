import java.util.LinkedList;
import java.util.Queue;

public class Level1 {

	Graph g;
	int matchingSize;
	Vertex dummy;
	int i;
	boolean verbose;

	Level1(Graph g, boolean verbose) {

		this.g = g;
		matchingSize = 0;
		this.verbose = verbose;

	}

	/**
	 * This method checks if the graph is bipartite or not. If the graph is
	 * bipartite then nodes are marked as inner and outer. if distance%2 is zero
	 * node is inner else outer.Initially all nodes are marked as inner.
	 */
	void markNodes() {

		Queue<Vertex> q = new LinkedList<>();
		for (Vertex verts : g.verts) {

			if (verts != null && !verts.seen) {

				verts.distance = 0;
				if (verts.Adj.size() != 0) {
					verts.inner = false;
				}
				verts.seen = true;
				q.add(verts);

				while (q.size() > 0) {

					Vertex v = q.remove();

					for (Edge e : v.Adj) {

						Vertex u = e.otherEnd(v);

						if (!u.seen) {

							u.seen = true;
							u.distance = v.distance + 1;
							// u.parent = v;
							if (u.distance % 2 == 0) {

								u.inner = false;
							}

							q.add(v);
						} else {

							if (v.distance == u.distance) {

								System.out.println("G not bipartite.");
								System.exit(0);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Finds the maximal matching of the given graph.
	 */
	void findMaximalMatching() {

		markNodes();
		applyGreedy();
		maximalMatching();
		System.out.println(matchingSize);
		if (verbose)
			verbose();
	}

	/**
	 * Print the nodes are inner or outer.
	 */
	void printNodeType() {

		for (int i = 1; i <= g.numNodes; i++) {

			Vertex v = g.verts.get(i);
			String nodeType = v.inner == true ? "inner" : "outer";
			System.out.println(v.name + "is: " + nodeType);
		}

	}

	/**
	 * Applying greedy to find matching.
	 */
	void applyGreedy() {

		for (Edge e : g.edgeList) {

			Vertex v = e.From;
			Vertex u = e.To;

			if (v.mate == null && u.mate == null) {
				u.mate = v;
				v.mate = u;
				matchingSize++;
			}
		}

	}

	/**
	 * Find Maximal matching after applying greedy.
	 * 
	 */
	void maximalMatching() {

		while (true) {

			int counter = 0;
			Queue<Vertex> q = new LinkedList<>();
			for (Vertex v : g) {

				v.parent = null;
				v.seen = false;
				v.frozen = false;
				// free outer node.
				if (!v.inner && v.mate == null) {
					v.seen = true;
					q.add(v);
				}

			}

			while (q.size() > 0) {

				Vertex outer = q.remove();

				for (Edge e : outer.Adj) {

					Vertex inner = e.otherEnd(outer);
					if (!inner.seen) {
						inner.seen = true;
						inner.parent = outer;
						// we found a free inner node.
						if (inner.mate == null) {
							counter++;
							processAugmentingPath(inner);
							// freezeNodes(inner);

						} else {
							Vertex matchedOuter = inner.mate;
							matchedOuter.parent = inner;
							matchedOuter.seen = true;
							q.add(matchedOuter);
						}
					}
				}

			}
			if (counter == 0) {

				break;
			}
			// what is the terminating condition.

		}
	}

	/**
	 * @param freeInner
	 * 
	 *            Process Augmenting path. This increases the matching by 1.
	 */
	void processAugmentingPath(Vertex freeInner) {

		Vertex temp = freeInner;
		boolean frozen = false;

		while (temp != null) {

			if (temp.frozen == true) {
				frozen = true;
				break;
			}
			temp = temp.parent;
		}

		if (!frozen) {
			Vertex freeInnerNode = freeInner;
			freeInnerNode.frozen = true;
			Vertex OuterNode = freeInnerNode.parent;
			OuterNode.frozen = true;
			Vertex x = OuterNode.parent;
			x.frozen = true;
			freeInnerNode.mate = OuterNode;
			OuterNode.mate = freeInnerNode;
			while (x != null) {

				Vertex nmx = x.parent;
				if (nmx != null) {
					nmx.frozen = true;
				}
				Vertex y = nmx.parent;
				if (y != null) {
					y.frozen = true;
				}
				nmx.mate = x;
				x.mate = nmx;
				x = y;

			}
			matchingSize++;
		}
	}

	/**
	 * Print the matching edges.
	 */
	void verbose() {

		for (Vertex v : g) {

			for (Edge e : v.Adj) {

				Vertex u = e.otherEnd(v);
				if (u.mate == v && v.mate == u) {

					if (u.name < v.name)
						System.out.println(u + " " + v + " " + e.Weight);
				}

			}

		}

	}

}
