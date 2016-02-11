import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// pending analysis how to find a loop or check that the given input is not  tree.

public class Question2 {

	Queue<Vertex> q = new LinkedList<>();
	int counter;

	Question2() {

		counter = 0;

	}

	void maxLength(Graph g, Graph g1) {
		int temp = 0, position = 1;
		bfs(g, position);

		System.out.println("counter value is:" + counter + "numeber of node is" + g.numberOfVertices);
		// find maximum distance.
		for (int i = 1; i <= g.numberOfVertices; i++) {

			if (g.verts.get(i).distance > temp) {

				temp = g.verts.get(i).distance;
				position = g.verts.get(i).name;
			}

		}

		bfs(g1, position);

		temp = 0;
		position = 1;
		for (int i = 1; i <= g1.numberOfVertices; i++) {

			Vertex v = g1.verts.get(i);
			if (v.distance > temp) {

				temp = v.distance;
				position = i;
			}
		}

		System.out.println("maximum distance is:" + g1.verts.get(position).distance);
	}

	void bfs(Graph g, int position) {

		// add a node to the queue;
		q.add(g.verts.get(position));

		while (!q.isEmpty()) {

			counter++;
			Vertex v = q.remove();
			v.seen = true;
			for (Edge e : v.Adj) {

				if (e.otherEnd(v).seen == false) {

					e.otherEnd(v).distance = v.distance + 1;
					q.add(e.otherEnd(v));

				}

			}

		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		File f1 = new File("/Users/santoshkompally/Documents/workspace/SP2/src/input.txt");
		File f2 = new File("/Users/santoshkompally/Documents/workspace/SP2/src/input.txt");
		Scanner sc = new Scanner(f1);
		Scanner sc1 = new Scanner(f2);
		Boolean directed = false;

		Graph g = Graph.readGraph(sc, directed);
		Graph g1 = Graph.readGraph(sc1, directed);

		Question2 q2 = new Question2();
		q2.maxLength(g, g1);

	}
}
