import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author santoshkompally,Roshini,Rohini
 *
 */
public class KruskalsImplementation {

	void makeSet(Vertex v) {

		v.parent = v;
		v.rank = 0;

	}

	Vertex findX(Vertex v) {

		if (v != v.parent) {

			v.parent = findX(v.parent);
		}
		return v.parent;

	}

	void Union(Vertex x, Vertex y) {

		if (x.rank > y.rank)
			y.parent = x;
		else if (y.rank > x.rank)
			x.parent = y;
		else {
			x.parent = y;
			y.rank++;
		}

	}

	void mimimumWeightSpanningTree(Graph g) {

		Vertex x, y;
		Edge removed;
		int minimumWeight = 0;

		for (int i = 1; i < g.verts.size(); i++) {

			makeSet(g.verts.get(i));

		}

		ComparatorQ q = new ComparatorQ();
		// Inputting the total number of edges.
		Heap<Edge> h = new Heap<>(g.numberOfEdges, q);

		// add all the edges to the priority queue.
		for (int i = 1; i < g.verts.size(); i++) {

			for (Edge e : g.verts.get(i).Adj) {
				h.add(e);
			}
		}

		while (h.size() > 0) {

			removed = h.remove();
			x = findX(removed.from);
			y = findX(removed.to);

			if (!x.equals(y)) {

				minimumWeight += removed.weight;
				Union(x, y);

			}

		}
		System.out.println("Weight of minimal spanning tree is:" + minimumWeight);

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
		Graph g = Graph.readGraph(sc, true);
		KruskalsImplementation k = new KruskalsImplementation();
		time.start();
		k.mimimumWeightSpanningTree(g);
		time.end();
		System.out.println(time.toString());
	}

}
