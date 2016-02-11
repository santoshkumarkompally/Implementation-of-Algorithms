import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Question3 {

	Stack<Vertex> s = new Stack<>(); // for first dfs run.
	ArrayList<Vertex> v = new ArrayList<>(); // for second dfs run.

	void stronglyConnected(Graph g, Graph g1) {

		// now run dfs.
		DFS_outer(g);

		while (!s.isEmpty()) {
			v.add(s.pop());
		}

		int component = 0;
		// // running dfs with the elements in the order found by the previous
		// dfs.
		for (Vertex temp : v) {
			if (g1.verts.get(temp.name).seen == false)
				dfs_rev(g1.verts.get(temp.name), ++component);

		}

	}

	void DFS_outer(Graph g) {

		for (int i = 1; i <= g.numberOfVertices; i++) {

			if (g.verts.get(i).seen == false)
				dfs(g.verts.get(i));

		}

	}

	void dfs(Vertex v) {

		v.seen = true;

		for (Edge e : v.Adj) {

			if (e.otherEnd(v).seen == false) {
				e.otherEnd(v).parent = v;
				dfs(e.otherEnd(v));
			}
		}

		s.add(v);

	}

	void dfs_rev(Vertex v, int component) {

		v.seen = true;
		v.componentNumber = component;
		for (Edge e : v.revAdj) {

			if (e.otherEnd(v).seen == false) {
				e.otherEnd(v).parent = v;
				dfs_rev(e.otherEnd(v), component);
			}
		}

		s.add(v);
	}

	public void display() {

		for (Vertex temp : s) {

			System.out.println(temp + "component: " + temp.componentNumber);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		File f1 = new File("/Users/santoshkompally/Documents/workspace/SP2/src/input.txt");
		File f2 = new File("/Users/santoshkompally/Documents/workspace/SP2/src/input.txt");
		Scanner sc = new Scanner(f1);
		Scanner sc1 = new Scanner(f2);
		Boolean directed = true;

		Graph g = Graph.readGraph(sc, directed);
		Graph g1 = Graph.readGraph(sc1, directed);
		Question3 q3 = new Question3();
		q3.stronglyConnected(g, g1);
		q3.display();
	}

}
