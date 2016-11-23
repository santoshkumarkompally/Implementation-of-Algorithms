import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Level_3 {

	Graph g;
	boolean verbose;

	Level_3(Graph g, boolean verbose) {

		this.g = g;
		// this.zeroGraph = null;
		this.verbose = verbose;

	}

	void findMaxWeightMatching() {

		// why pass the input graph we are using the same graph all the time.All
		// are using the same object right.Any way this should not cause any
		// problem in the output.
		markNodes();
		labelNodes();
		createZeroGraph();
		maximalMatching();

		while (true) {

			if (isMaximalMatching()) {
				if (verbose)
					verbose(g);
				int maxWt = 0;
				for (Edge e : g.edgeList) {
					if (e.existInZeroG) {
						maxWt = maxWt + e.Weight;
						System.out.println(e.toString());
					}
				}
				System.out.println(maxWt);
				break;
			}

			// delta logic should be implemented here.
			// Find a free Outer node.
			for (Vertex v : g) {
				if (!v.inner && v.mate == null) {
					bfs(g, v);
				}
				// Traverse using that free Outer node dfs/bfs

			}

			createZeroGraph();
			maximalMatching();

		}

	}

	public static void bfs(Graph g, Vertex source) {
		initialize(g);
		Queue<Vertex> bfsQ = new LinkedList<>();
		bfsQ.add(source);
		List<Vertex> treeVertices = new ArrayList<>();
		treeVertices.add(source);
		source.seen = true;
		Vertex temp = null;
		int minSlack = Integer.MAX_VALUE;
		int delta = 0;
		while (!bfsQ.isEmpty()) {
			temp = bfsQ.remove();
			for (Edge e : temp.Adj) {
				if (e.existInZeroG) {
					Vertex other = e.otherEnd(temp);
					if (!other.seen) {
						// Find all Outer nodes.
						if (!other.inner) {
							// For each outer node found apply the delta logic.
							for (Edge e2 : other.Adj) {
								int slack = 0;
								if (!e2.existInZeroG)
									slack = e2.From.label + e2.To.label - e2.Weight;

								if (slack < minSlack)
									minSlack = slack;

							}
						}
						other.parent = temp;
						other.seen = true;
						bfsQ.add(other);
						treeVertices.add(other);
					}
				}
			}
		}
		for (Vertex v2 : treeVertices) {
			if (v2.inner)
				v2.label += minSlack;
			else
				v2.label -= minSlack;
		}
	}

	public static void initialize(Graph g) {
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
			// u.distance = 0;
			u.infinity = true;

		}
	}

	boolean isMaximalMatching() {

		boolean allOuterNodesHaveZeroWeight = true;
		boolean freeInnerNodeFound = false;
		for (Vertex v : g) {

			// its a free outer node with weight !=0
			if (!v.inner && v.mate == null && v.label != 0) {

				allOuterNodesHaveZeroWeight = false;
			}
			// its a free inner node.
			if (v.inner && v.mate == null) {

				freeInnerNodeFound = true;
			}

		}
		// no free Inner node is found || All free outer nodes have label 0.
		if (allOuterNodesHaveZeroWeight || !freeInnerNodeFound) {

			return true;

		} else {

			return false;
		}

	}

	/*
	 * void findMaximalMatching(Graph g) { markNodes(); print(g); applyGreedy();
	 * // System.out.println(g.matchingSize); maximalMatching(); if
	 * ((g.matchingSize * 2) == g.numNodes - 1) { System.out.println(
	 * "Perfect Macthing"); if (verbose) verbose(g); } else imperfectMatching();
	 * 
	 * }
	 * 
	 * void imperfectMatching() { for (Vertex v : g) { String s = v.inner ?
	 * "Innner" : "Outer"; System.out.println(v.name + "\t" + s); } int numOuter
	 * = 0, numInner = 0; for (Vertex v : g) { if (v.inner) numInner++; else if
	 * (!v.inner) numOuter++; } if (numOuter > numInner) System.out.println(
	 * "outer > innner"); }
	 */
	void print(Graph g) {
		for (Edge e : g.edgeList)
			System.out.println(e.toString() + "\t" + e.existInZeroG);
	}

	void labelNodes() {
		for (Vertex v : g) {
			int maxWt = 0;
			if (!v.inner) {
				for (Edge e : v.Adj) {
					if (e.Weight > maxWt)
						maxWt = e.Weight;
				}
			}
			v.label = maxWt;
		}

	}

	/**
	 * 
	 */
	void createZeroGraph() {
		for (Edge e : g.edgeList) {
			Vertex from = e.From;
			Vertex to = e.To;
			if ((from.label + to.label) == e.Weight)
				e.existInZeroG = true;
		}
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
	 * Print the nodes are inner or outer.
	 */
	void printNodeType() {

		for (int i = 1; i <= g.numNodes; i++) {

			Vertex v = g.verts.get(i);
			String nodeType = v.inner == true ? "inner" : "outer";
			System.out.println(v.name + "\t" + nodeType + "\t" + v.label);

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
				g.matchingSize++;
			}
		}

	}

	void maximalMatching() {

		while (true) {

			int counter = 0;
			Queue<Vertex> q = new LinkedList<>();
			for (Vertex v : g) {

				v.parent = null;
				v.seen = false;
				v.frozen = false;
				// free outer node.
				if (!v.inner && v.mate == null) {/// v.exists
					v.seen = true;
					q.add(v);
				}

			}

			while (q.size() > 0) {

				Vertex outer = q.remove();

				for (Edge e : outer.Adj) {
					// if e.exists
					if (e.existInZeroG) {
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

			}
			if (counter == 0) {

				break;
			}
			// what is the terminating condition.

		}
	}

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
			if (x != null)
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
			g.matchingSize++;
		}
	}

	void verbose(Graph g) {

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
