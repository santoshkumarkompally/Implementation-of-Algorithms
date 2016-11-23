import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Level_1 {

	public static int decideAlgorithm(Graph g) {

		boolean weightsEqual = true;
		boolean weightsPositive = true;
		int weight = g.edgeList.get(0).Weight;
		for (Edge e : g.edgeList) {

			// condition for Bellman ford.
			if (e.Weight < 0) {
				weightsPositive = false;
				// return 3;

			}

			// to check if all the weights are equal or not.
			if (weight != e.Weight) {
				weightsEqual = false;
			}

		}

		// after checking all the weights if they are equal then we can run bfs.
		if (weightsEqual == true && weightsPositive == true) {
			return 0;
		} else if (checkDAG(g))
			// run DAG shortest path.
			return 1;
		else if (weightsPositive)
			// run Dijkstra's Algorithm
			return 2;

		else
			return 3;
		// we have to check between DAG shortest path and Dijkstra's.
		// check for DAG.

	}

	public static boolean compareWeight(Vertex v, Vertex u, int weight) {

		if (v.infinity == true) {

			v.infinity = false;
			return true;

		} else {
			if (v.distance > u.distance + weight)
				return true;
			else
				return false;
		}
	}

	public static boolean checkDAG(Graph g) {
		initialize(g);
		for (Vertex u : g) {

			u.inDegree = u.revAdj.size();

		}
		List<Vertex> toVertexList = new ArrayList<>();
		Queue<Vertex> q_vertices = new LinkedList<>();
		for (Vertex v : g) {
			if (v.inDegree == 0) {
				q_vertices.add(v);
			}
		}
		if (q_vertices.isEmpty())
			return false;
		int top = 1;
		while (!q_vertices.isEmpty()) {
			Vertex v = q_vertices.remove();
			v.top = top++;
			toVertexList.add(v);
			for (Edge e : v.Adj) {
				Vertex other = e.otherEnd(v);
				other.inDegree--;
				if (other.inDegree == 0)
					q_vertices.add(other);
			}
		}
		for (Vertex u : g)
			if (u.top == 0) {
				return false;
			}
		return true;
	}

	public static void bfs(Graph g, Vertex source) {
		initialize(g);
		Queue<Vertex> bfsQ = new LinkedList<>();
		bfsQ.add(source);
		source.distance = 0;
		source.infinity = false; // adding this to check for infinity.
		source.seen = true;
		Vertex temp = null;
		while (!bfsQ.isEmpty()) {
			temp = bfsQ.remove();
			for (Edge e : temp.Adj) {
				Vertex other = e.otherEnd(temp);
				if (!other.seen) {
					other.infinity = false; // adding this for infinity.
					other.distance = temp.distance + e.Weight; // This should be
																// the
					// weight and not 1.
					other.parent = temp;
					other.seen = true;
					bfsQ.add(other);
				}
			}
		}
	}

	public static void Dijkstra(Graph g, Vertex source) {

		initialize(g);
		source.distance = 0;
		ComparatorPQ c = new ComparatorPQ();
		IndexedHeap<Vertex> h = new IndexedHeap<Vertex>(g.numNodes, c);
		Vertex u, v;

		// add all the vertices to the priority queue.

		for (int i = 1; i <= g.numNodes; i++) {

			h.add(g.verts.get(i));

		}

		while (h.size() > 0) {

			u = h.remove();
			u.seen = true;

			for (Edge e : u.Adj) {

				v = e.otherEnd(u);
				// v.distance > u.distance + e.Weight
				if (compareWeight(v, u, e.Weight) & v.seen == false) {
					v.distance = u.distance + e.Weight;
					v.parent = u;
					h.perculateUp(v.getIndex());

				}

			}

		}

	}

	public static boolean bellmanFord(Graph g, Vertex source) {
		initialize(g);
		Queue<Vertex> bellFQ = new LinkedList<>();
		bellFQ.add(source);
		source.distance = 0;
		source.infinity = false;
		source.seen = true;
		Vertex u = null;
		while (!bellFQ.isEmpty()) {
			u = bellFQ.remove();
			u.seen = false;
			u.count = u.count + 1;
			if (u.count >= g.numNodes) {
				System.out.println("Unable to solve problem. Graph has a negative cycle");
				return false;
			}
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (compareWeight(v, u, e.Weight)) {
					v.distance = u.distance + e.Weight;
					v.parent = u;
					if (!v.seen) {
						bellFQ.add(v);
						v.seen = true;
					}
				}
			}

		}
		return true;
	}

	public static void dagShortestPath(Graph g, Vertex source) {
		List<Vertex> topList = new ArrayList<>();

		topList = toplogicalOrder(g, g.verts.get(1));

		initialize(g);
		g.verts.get(1).distance = 0;
		g.verts.get(1).infinity = false;
		int i;

		// Iterate till the source node.
		for (i = 0; i < topList.size(); i++) {

			if (topList.get(i).name == source.name)
				break;

		}

		for (int j = i; j < topList.size(); j++) {
			Vertex u = topList.get(j);
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				// if (v.distance > u.distance + e.Weight) {
				if (compareWeight(v, u, e.Weight)) {
					v.distance = u.distance + e.Weight;
					v.parent = u;
				}
			}
		}

	}

	public static void Relax(Vertex u, Vertex v, Edge e) {

	}

	public static List<Vertex> toplogicalOrder(Graph g, Vertex source) {
		initialize(g);
		for (Vertex u : g) {
			for (Edge e : u.revAdj) {
				u.inDegree++;
			}
		}

		List<Vertex> toVertexList = new ArrayList<>();
		Queue<Vertex> q_vertices = new LinkedList<>();
		for (Vertex v : g) {
			if (v.inDegree == 0) {
				q_vertices.add(v);
			}
		}
		if (q_vertices.isEmpty())
			return null;
		int top = 1;
		while (!q_vertices.isEmpty()) {
			Vertex v = q_vertices.remove();
			v.top = top++;
			toVertexList.add(v);
			for (Edge e : v.Adj) {
				Vertex other = e.otherEnd(v);
				other.inDegree--;
				if (other.inDegree == 0)
					q_vertices.add(other);
			}
		}
		for (Vertex u : g)
			if (u.top == 0) {
				// findCycle(u);
				return null;
			}
		return toVertexList;
	}

	public static List<Vertex> toplogicalOrderCheckLoop(Graph g) {
		initialize(g);
		for (Vertex u : g) {
			for (Edge e : u.revAdj) {
				Vertex v = e.otherEnd(u);

				if (u.distance == v.distance + e.Weight)
					u.inDegree++;
			}
		}
		List<Vertex> toVertexList = new ArrayList<>();
		Queue<Vertex> q_vertices = new LinkedList<>();
		for (Vertex v : g) {
			if (v.inDegree == 0) {
				q_vertices.add(v);
			}
		}
		if (q_vertices.isEmpty())
			return null;
		int top = 1;
		while (!q_vertices.isEmpty()) {
			Vertex v = q_vertices.remove();
			v.top = top++;
			toVertexList.add(v);
			for (Edge e : v.Adj) {
				Vertex other = e.otherEnd(v);
				if (other.distance == v.distance + e.Weight)
					other.inDegree--;
				if (other.inDegree == 0)
					q_vertices.add(other);
			}
		}
		for (Vertex u : g)
			if (u.top == 0) {
				return null;
			}
		return toVertexList;
	}

	public static void initialize(Graph g) {
		for (Vertex u : g) {
			u.seen = false;
			u.parent = null;
			// u.distance = 0;
			u.infinity = true;
			u.top = 0;
			u.inDegree = 0;
			u.count = 0;
		}
	}

	public static void printResult(Graph g) {
		// System.out.println("Vertex\tDistance\tParent");
		int distance = 0;
		for (Vertex v : g)
			if (v != null && v.parent != null)
				distance += v.distance;

		System.out.println(distance);

		if (g.numNodes <= 100)
			for (Vertex v : g) {

				if (v.infinity == false) {

					if (v.parent == null)
						System.out.println(v + "\t" + v.distance + "\t" + "-");
					else
						System.out.println(v + "\t" + v.distance + "\t" + v.parent);
				} else {

					if (v.parent == null)
						System.out.println(v + "\t" + "INF" + "\t" + "-");
					else
						System.out.println(v + "\t" + "INF" + "\t" + v.parent);
				}
			}
	}



	public static void allShortestPaths(Graph g1) {

		dagShortestPath(g1, g1.verts.get(1));
		List<Vertex> cycleCheck = toplogicalOrder(g1, g1.verts.get(1));
		cycleCheck.get(0).paths = 1;
		System.out.println("coming here");
		if (cycleCheck != null) {

			for (Vertex v : cycleCheck) {

				for (Edge e : v.revAdj) {
					Vertex u = e.otherEnd(v);

					v.paths += u.paths;

				}

			}

		}

		int distance = 0;
		for (Vertex v : g1.verts) {

			if (v != null) {
				distance += v.paths;
				// System.out.println(v.name + " " + v.distance + " " +
				// v.paths);

			}

		}

		// call a function to do create a new graph g' after passing g
		// check if g' has a cycle.
		// if yes no solution else run the algorithm for finding all paths.

		// printResult(g);
		System.out.println("distance is:" + distance);

	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;
		int algo;
		if (args.length > 0) {
			File f1 = new File(args[0]);
			sc = new Scanner(f1);
		} else
			sc = new Scanner(System.in);
		Graph g = Graph.readGraph(sc, true);
		algo = decideAlgorithm(g);

		if (algo == 0) {
			System.out.print("BFS ");
			bfs(g, g.verts.get(1));

		} else if (algo == 1) {
			System.out.print("DAG ");
			dagShortestPath(g, g.verts.get(1));
		} else if (algo == 2) {

			System.out.print("Dij ");
			// Dijkstra's is yet to be implemented.
			Dijkstra(g, g.verts.get(1));

		} else {
			System.out.print("B-F ");
			bellmanFord(g, g.verts.get(1));

		}

		printResult(g);

		// Graph g1 = minimumSpanningGraph(g);
		//
		// // check if the given graph is a dag or not .
		// boolean dag = checkDAG(g1);
		//
		// if (dag == true) {
		//
		// allShortestPaths(g1);
		//
		// } else {
		//
		// System.out.println("cycle exists");
		//
		// }

	}
}