import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Question5 {

	int count;

	Question5() {
		count = 0;

	}

	boolean checkConnectivity(Graph g) {

		int numberOfVertices = g.numberOfVertices;

		// take the first node from the graph and run dfs because we can take
		// any node and start the dfs.
		dfs(g.verts.get(1));

		if (count == numberOfVertices) {

			return true;
		} else {

			return false;
		}

	}

	void dfs(Vertex v) {

		v.seen = true;
		count++;
		for (Edge e : v.Adj) {

			Vertex u = e.otherEnd(v);
			if (u.seen == false) {

				dfs(u);
			}

		}

	}

	ArrayList<Vertex> numberOfOddEdges(Graph g) {

		ArrayList<Vertex> al = new ArrayList<>();

		for (int i = 1; i <= g.numberOfVertices; i++) {

			Vertex v = g.verts.get(i);
			if (v.degree % 2 != 0) {

				al.add(v);
			}

		}

		return al;
	}

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc;

		if (args.length > 0) {

			sc = new Scanner(args[1]);

		} else {

			sc = new Scanner(System.in);
		}

		Boolean directed = false;

		Graph g = Graph.readGraph(sc, directed);
		Question5 q5 = new Question5();
		boolean connectedGraph = false;

		connectedGraph = q5.checkConnectivity(g);

		if (connectedGraph) {

			ArrayList<Vertex> al = q5.numberOfOddEdges(g);

			if (al.size() == 0) {

				System.out.println("Graph is Eulerian");
			} else if (al.size() == 2) {

				System.out.println("Eulerian path exists between " + al.get(0).name + " and " + al.get(1).name);
			} else {

				for (Vertex v : al) {

					System.out.println(v);
				}

				System.out.println("graph is not eulerian and it has " + al.size() + " vertices of odd degree");
			}

		} else {

			System.out.println("Graph is not connected.");

		}

	}

}
