/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Vertex {
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public List<Edge> Adj; // adjacency list; use LinkedList or ArrayList
	public int degree; // degree of a vertex
	public boolean visited;
	public List<Edge> unvisitedEdges;

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		name = n;
		seen = false;
		visited = false;
		Adj = new ArrayList<Edge>();
		degree = 0;
		unvisitedEdges = new ArrayList<Edge>();
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}

}
