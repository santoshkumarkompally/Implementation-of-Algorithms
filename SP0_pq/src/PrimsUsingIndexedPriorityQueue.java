import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author santosh kompally
 *
 */
public class PrimsUsingIndexedPriorityQueue {

	static void minimumWeight(Graph g) {
		ComparatorPQ c = new ComparatorPQ();
		IndexedHeap<Vertex> h = new IndexedHeap<Vertex>(g.numberOfVertices, c);
		int weight = 0;
		g.verts.get(1).weight = 0;
		// make the weight of first vertex to be 0 so that it will not cause
		// problems in the algorithm.

		for (int i = 1; i <= g.numberOfVertices; i++) {

			h.add(g.verts.get(i));

		}

		while (h.size > 0) {

			Vertex v = h.remove();
			v.seen = true;
			weight += v.weight;

			for (Edge e : v.Adj) {

				Vertex u = e.otherEnd(v);

				if (u.seen == false && u.weight > e.weight) {

					u.weight = e.weight;

				}
				h.perculateUp(u.getIndex());
			}

		}

		// starts from position and spans till the end

		System.out.println("weight:" + weight);
	}

	public static void main(String[] args) throws FileNotFoundException {

		File f1 = new File("/Users/santoshkompally/git/Implementation/SP0_pq/src/input.txt");

		Scanner sc = new Scanner(f1);

		Graph g = Graph.readGraph(sc, false);
		System.out.println(System.currentTimeMillis());
		minimumWeight(g);
		System.out.println(System.currentTimeMillis());

	}

}
