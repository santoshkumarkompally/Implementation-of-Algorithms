import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class EulerTour {

	static ArrayList<ArrayList<Vertex>> findEulerianPath(Graph g, Vertex v1) {

		Vertex temp;
		ArrayList<Vertex> oneTour = new ArrayList<>();
		ArrayList<ArrayList<Vertex>> completePath = new ArrayList<>();
		DLinkedList<Vertex> d = new DLinkedList<>();
		d.insertFirst(v1);

		return null;
	}

	public static void main(String[] args) throws FileNotFoundException {
		File f1 = new File("/Users/santoshkompally/Documents/workspace/SP2/src/input.txt");
		Scanner sc = new Scanner(f1);
		Boolean directed = true;
		Graph g = Graph.readGraph(sc, directed);

		ArrayList<ArrayList<Vertex>> list = EulerTour.findEulerianPath(g, g.verts.get(1));
		// add first element to the doubly linked list in the beginning.

	}

}
