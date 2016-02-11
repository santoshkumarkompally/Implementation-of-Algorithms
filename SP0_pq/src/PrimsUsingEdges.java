import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author santoshkompally
 *
 */
public class PrimsUsingEdges {

	public static void main(String[] args) throws FileNotFoundException {

		File f1 = new File("/Users/santoshkompally/Documents/workspace/SP0_pq/src/input.txt");

		Scanner sc = new Scanner(f1);

		Graph g = Graph.readGraph(sc, false);
		minimumWeight(g);

	}

	static int minimumWeight(Graph g) {

		Heap<Edge> h = new Heap<>(g.numberOfEdges);

		// take a vertex v. lets say the first vertex.
		int weight = 0;
		Vertex v = g.verts.get(1);

		for (Edge e : v.Adj) {

			h.add(e);
		}
		v.seen = true;
		while (h.size() > 0) {

			Edge e = h.remove();
			if (e.from.seen == true && e.to.seen == true) // both are seen
															// continue
				continue;
			weight += e.weight; // add weight
			Vertex to;
			if (e.from.seen == true) { // if this end is seen then point to to
										// the other end

				to = e.to;
			} else {
				to = e.from;
			}
			to.seen = true;
			for (Edge e1 : to.Adj) {

				if (!(e1.from.seen && e1.to.seen)) {

					h.add(e1);
				}

			}

		}
		System.out.println("weight is:" + weight);
		return 0;
	}

}
