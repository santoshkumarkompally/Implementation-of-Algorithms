import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Roshni Saraogi, Rohini, Santosh Kompally
 *
 */
public class LP_0_b {

	/**
	 * @param g
	 *            - the input graph g
	 * @return - returns a boolean value: true, if the graph is connected and
	 *         vice-versa checkConnectivity method checks if the given graph g
	 *         is connected or disconnected. It implements an iterative version
	 *         of DFS
	 */
	public static boolean checkConnectivity(Graph g) {
		boolean isConnected = true;
		int count = 0; // the value of count suggests how many times the DFS has
						// run.
		for (Vertex u : g.verts) {
			if (u != null)
				if (u.seen == false) {
					count++;
					if (count > 1) { // if the dfs runs for the second time,
										// graph is disconnected so breaking out
										// and returning
						isConnected = false;
						break;
					}
					Stack<Vertex> stack = new Stack<>(); // the following code
															// is similar to the
															// DFS-Visit in DFS
					stack.push(u);
					while (!stack.isEmpty()) {
						Vertex v = stack.pop();
						if (v.seen != true) {
							v.seen = true;
							for (Edge e : v.Adj) {
								Vertex otherV = e.otherEnd(v);
								if (otherV.seen == false) {
									stack.push(otherV);
								}
							}

						}
					}

				}

		}
		return isConnected;
	}

	/**
	 * @param g
	 *            - the graph g in which Euler tour/path has to be found
	 * @param start
	 *            - the starting vertex of the Euler tour/path
	 * @return Array list of edges in order which composes the Euler tour/path
	 */

	static List<Edge> findEulerTour(Graph g) {
		Vertex start = findStart(g);
		Vertex u = null;
		Stack<Edge> subTour = new Stack<Edge>(); // a stack of a subtour
		Stack<Edge> fullTour = new Stack<Edge>(); // a stack for backtracking
													// each edge of the subtour
													// and finding subtours
													// associate with each of
													// them.
		Edge e = getUnvisitedEdge(start);
		while (e != null) { // finding the first subtour
			subTour.push(e);
			u = otherEnd(e, start);
			e = getUnvisitedEdge(u);
			start = u;
		}

		while ((!subTour.isEmpty())) { // backtracking the subtour
			Edge e1;
			e = subTour.pop();
			fullTour.push(e); // as this edge gets examined it is pushed into
								// fullTour stack, each edge from subtour is 
								//examined to check if there is a subtour associated with that edge
			u = e.To;
			e1 = getUnvisitedEdge(u);
			if (e1 == null) {
				u = e.From;
				e1 = getUnvisitedEdge(u);
			}
			while (e1 != null) { // calculating the subtour associated with each
									// edge in the stack subTour and pushing it
									// to subTour stack
				e.visited = true;
				subTour.push(e1);
				u = otherEnd(e1, u);
				e1 = getUnvisitedEdge(u);
			}
		}
		// at this point, all subtours and subtours within subtours are found
		// out
		// and all the edges are saved in order in fullTour stack
		List<Edge> tour = new ArrayList<Edge>();
		while (!fullTour.isEmpty()) {
			Edge edge = fullTour.pop();
			tour.add(edge);
		}
		return tour;
	}

	/**
	 * @param e
	 *            - the edge e whose other end needs to be found
	 * @param v
	 *            - v is one of the vertex of edge e and the other end needs to
	 *            be found
	 * @return - the other end (which is not v) of the edge e
	 */
	public static Vertex otherEnd(Edge e, Vertex v) {
		Vertex From = e.From;
		Vertex To = e.To;
		if (From.equals(v))
			return To;
		else if (To.equals(v))
			return From;
		else
			return null;

	}

	/**
	 * @param v
	 *            - the vertex v which may/may not lead to an unvisited edge
	 *            which is to be found
	 * @return - unvisited edge which is connected with vertex v
	 */
	public static Edge getUnvisitedEdge(Vertex v) {
		if (v.unvisitedEdges.size() > 0) // checks if there is any unvisited
											// edge remaining of vertex v
			for (Edge e : v.unvisitedEdges) {
				if (e.visited == false && (e.From.equals(v) || e.To.equals(v))) {
					// checking if e is not visited yet and v forms the From/To
					// of e
					e.visited = true;
					v.unvisitedEdges.remove(e); // removing the edge e from
												// unvisitedEdges list of
												// vertices(u,v) associated with
												// e
					Vertex u = e.otherEnd(v);
					u.unvisitedEdges.remove(e);
					return e;
				}
			}
		return null;
	}

	public static void printTour(List<Edge> tourEdges) {
		for (Edge e : tourEdges)
			System.out.println(e);
	}

	static boolean verifyTour(Graph g, List<Edge> tour) { 
		 boolean isEulerian= true; 
		 if ((g.oddDegVertices.size() != 0 || g.oddDegVertices.size() !=2)&& tour.size() != g.numEdges)
		// verifying if the no. of odd degree vertices are 0 or 2, and if the tour to be verified includes all the edges from the graph or not
			 return false; 
		 else if (!tour.isEmpty()) {
		  Vertex start = findStart(g); // getting the vertex from where the tour begins 
		//  Iterator<Edge> itr = tour.iterator(); 
		  Edge e1 = null, e2 = null;
	      e1 = tour.get(0); 
	      Vertex From = null; // this is the vertex which should match with one of the vertices of the successive edge
	      From = (start.equals(e1.From)) ? e1.To : e1.From; 
	      if (From == null)
	    	  return false; 
	      int i=1;
	      while (i<tour.size()) { // recursively checking the continuity of the tour by comparing the From vertex of the last edge with both the vertices of the current edge 
	    	  e2 = tour.get(i++); 
	    	  From =(From.equals(e2.From)) ? e2.To : e2.From; 
	    	  if (From == null) return false;
	      } 
	      if (!start.equals(From) && g.oddDegVertices.size() == 0) 
	    	  return false;
	  // checking in case of Euler tour, whether the start and end vertices are same or not 
	      else if (g.oddDegVertices.size() == 2) { 
	    	  Vertex u =g.oddDegVertices.get(0), v = g.oddDegVertices.get(1); 
	    	  if(!(start.equals(u) && From.equals(v) || start.equals(v) && From.equals(u))) // checking in case of Euler path,
	    		  //whether the start and end vertices corresponds to the 2 odd // degree vertices of the graph 
	    		  return false; 
	    	  } 
	      } 
		 return isEulerian; 
	}

	/**
	 * @param g
	 *            graph g
	 * @return- returns the start vertex from where tour/path begins The method
	 *          finds the starting vertex from where the tour s supposed to
	 *          begin according to the requirements
	 */
	public static Vertex findStart(Graph g) {
		Vertex start = null;
		if (g.oddDegVertices.size() == 0) // checks if it is a tour and assigns
											// vertex object 1 as the starting
											// vertex
			start = g.verts.get(1);
		else if (g.oddDegVertices.size() == 2) { // checks if it is a path and
													// assigns the smaller
													// vertex object
													// as the starting vertex
			Vertex u = g.oddDegVertices.get(0), v = g.oddDegVertices.get(1);
			start = (u.name < v.name) ? u : v;
		}
		return start;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;
		if (args.length > 0) {
			File f1 = new File(args[0]);
			sc = new Scanner(f1);
		} else
			sc = new Scanner(System.in);
		Graph g = Graph.readGraph(sc, false);
		boolean connectedGraph = false;
		long startTime = System.currentTimeMillis();
		connectedGraph = checkConnectivity(g);
		if (connectedGraph) {
			List<Edge> tour = new ArrayList<>();
			boolean isTour = false;
			if (g.oddDegVertices.size() == 0 || g.oddDegVertices.size() == 2) {
				System.out.println("Graph is Eulerian");
				tour = findEulerTour(g);
				isTour = verifyTour(g, tour);
			} else
				System.out.println("Graph is not Eulerian");

			long endTime = System.currentTimeMillis();
			// if (!tour.isEmpty())
			// printTour(tour);
			System.out.println("RT = " + (endTime - startTime)
					+ " milliseconds");
			System.out.println("Verify tour output:" + isTour);
		} else
			System.out.println("Graph is not Eulerian.");
	}
}
