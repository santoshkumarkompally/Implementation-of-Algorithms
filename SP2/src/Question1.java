import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Question1 {

	Deque<Vertex> s = new LinkedList<>();

	void Top_DFS(Graph g) {

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

	void Top_Remove(Graph g) {

		Queue<Vertex> q = new LinkedList<>();
		int count = 0;
		// adding elements to the queue with in degree 0.
		for (int i = 1; i <= g.numberOfVertices; i++) {

			Vertex v = g.verts.get(i);
			if (v.indegree == 0) {

				q.add(v);
			}

		}

		while (!q.isEmpty()) {

			Vertex v = q.remove();
			s.add(v);
			count++;
			for (Edge e : v.Adj) {

				Vertex u = e.otherEnd(v);
				u.indegree--;
				if (u.indegree == 0) {

					q.add(u);

				}
			}

		}

	}

	public void display() {

		// copying the data in another array.
		Deque<Vertex> s1 = new LinkedList<>();

		for (Vertex v : s) {

			s1.push(v);

		}

		while (!s1.isEmpty()) {

			System.out.println(s1.pop());

		}
	}

	public void display1() {

		// copying the data in another array.
		Deque<Vertex> s1 = new LinkedList<>();

		for (Vertex v : s) {

			s1.add(v);

		}

		while (!s1.isEmpty()) {

			System.out.println(s1.remove());

		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		File f1 = new File("/Users/santoshkompally/Documents/workspace/SP2/src/input.txt");
		Scanner sc = new Scanner(f1);
		Boolean directed = true;

		Graph g = Graph.readGraph(sc, directed);

		Question1 q = new Question1();
		// q.Top_DFS(g);
		q.Top_Remove(g);
		q.display1();
	}

}
