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

	static int minimumWeight(Graph g) {
		ComparatorQ q = new ComparatorQ();
		// pass a array of edges.
		Heap<Edge> h = new Heap<>(g.numberOfEdges, q);

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

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc;
		Timer time = new Timer();

		if (args.length > 0) {
			File f = new File(args[0]);
			sc = new Scanner(f);
		} else {
			sc = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(sc, false);
		time.start();
		minimumWeight(g);
		time.end();
		System.out.println(time.toString());
	}

}
