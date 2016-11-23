
/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Index<Vertex> {
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public List<Edge> Adj; // adjacency list; use LinkedList or ArrayList
	public List<Edge> revAdj;
	public int inDegree; // degree of a vertex
	public boolean visited;
	public List<Edge> unvisitedEdges;
	public int distance;
	public Vertex parent;
	public int count;
	public int top;
	int index;
	int paths;
	boolean infinity;

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		distance = Integer.MAX_VALUE;
		parent = null;
		name = n;
		seen = false;
		visited = false;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>();
		inDegree = 0;
		unvisitedEdges = new ArrayList<Edge>();
		count = 0;
		top = 0;
		paths = 0;
		infinity = true; // to check if the weight is infinity
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Index#getIndex()
	 */
	@Override
	public int getIndex() {
		return index;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Index#setIndex()
	 */
	@Override
	public void setIndex(int index) {
		this.index = index;

	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}

}
