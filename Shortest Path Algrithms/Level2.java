import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Level2 {

	
	public static void initialize(Graph g) {
		for (Vertex u : g) {
			u.seen = false;
		//	u.parent = null;
			// u.distance = 0;
			//u.infinity = true;
			u.top = 0;
			u.inDegree = 0;
			u.count = 0;
		}
	}
	
	public static List<Vertex> toplogicalOrder(Graph g, Vertex source) {
		initialize(g);
		for (Vertex u : g) {
			u.inDegree = u.revAdj.size();
		}

		List<Vertex> toVertexList = new ArrayList<>();
		Queue<Vertex> q_vertices = new LinkedList<>();
		for (Vertex v : g) {
			if (v.inDegree == 0) {
				q_vertices.add(v);
			}
		}
		if (q_vertices.isEmpty()){
			System.out.println("Non-positive cycle in graph. DAC is not applicable");
			
			findCycle(g.verts.get(1),g);
			return null;}
		int top = 1;
		while (!q_vertices.isEmpty()) {
			Vertex v = q_vertices.remove();
			v.top = top++;
			toVertexList.add(v);
			for (Edge e : v.Adj) {
				Vertex other = e.otherEnd(v);
				other.inDegree--;
				if (other.inDegree == 0)
					q_vertices.add(other);
			}
		}
		for (Vertex u : g)
			if (u.top == 0 ) {
				System.out.println("Non-positive cycle in graph. DAC is not applicable");
				
				findCycle(u,g);
				
				return null;
			}
		return toVertexList;
	}
	
	public static boolean compareWeight(Vertex v, Vertex u, int weight) {

		if (v.infinity == true) {

			v.infinity = false;
			return true;

		} else {
			if (v.distance > u.distance + weight)
				return true;
			else
				return false;
		}
	}
	
	static void findCycle(Vertex v, Graph g){

		for(Vertex u:g){
			u.seen=false;
			u.parent=null;
		}
		boolean cycleFound=false;
		//Stack<Vertex> stack = new Stack<>();
		for (Vertex src : g) {
			if (!src.seen) {
				List<Vertex> cycleVertices = new ArrayList();
				cycleVertices.add(src);
				cycleFound=DFS(g, src, cycleVertices);
				if(cycleFound) break;
			}
		}
	
	
	}
	
	static boolean DFS(Graph g, Vertex v, List<Vertex> stack) {
		//System.out.println("Inside DFS");
		boolean rValue=false;
		v.seen = true;
		for (Edge e : v.Adj) {
			Vertex u = e.otherEnd(v);
			if (u.seen != true) {
				u.parent = v;
				stack.add(u);
				DFS(g, u, stack);
			} else {
				for(Vertex temp:stack) {
					if(u==temp.parent){
					printCycle(u,temp,stack);
					rValue=true;
					return true;
					}
					
				}
			}
		}
		return rValue;

	}
	
	static void printCycle(Vertex u,Vertex v, List<Vertex> cycleVertices){
		Iterator<Vertex> itr = cycleVertices.listIterator();
		Vertex s=null;
		while(itr.hasNext()){
			s=itr.next();
			if(s==v)
				break;
		}
		System.out.print(s+"-> ");
		while(itr.hasNext()){
			s=itr.next();
		//	System.out.println(s);
			System.out.print(s+"-> ");
		
		}
		System.out.println(v);
		
	}
	
	
	public static void dagShortestPath(Graph g, Vertex source) {
		List<Vertex> topList = new ArrayList<>();

		topList = toplogicalOrder(g, g.verts.get(1));

		initialize(g);
		g.verts.get(1).distance = 0;
		g.verts.get(1).infinity = false;
		int i;

		// Iterate till the source node.
		for (i = 0; i < topList.size(); i++) {

			if (topList.get(i).name == source.name)
				break;

		}

		for (int j = i; j < topList.size(); j++) {
			Vertex u = topList.get(j);
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				// if (v.distance > u.distance + e.Weight) {
				if (compareWeight(v, u, e.Weight)) {
					v.distance = u.distance + e.Weight;
					v.parent = u;
				}
			}
		}

	}
	
	public static void allShortestPaths(Graph g1) {

		dagShortestPath(g1, g1.verts.get(1));
		List<Vertex> cycleCheck = toplogicalOrder(g1, g1.verts.get(1));
		cycleCheck.get(0).paths = 1;
		
		if (cycleCheck != null) {

			for (Vertex v : cycleCheck) {

				for (Edge e : v.revAdj) {
					Vertex u = e.otherEnd(v);

					v.paths += u.paths;

				}

			}

		}

		int distance = 0;
		for (Vertex v : g1.verts) {

			if (v != null) {
				distance += v.paths;
			}

		}

		System.out.println(distance);

		if(g1.numNodes<100){
						
			
			for (Vertex v : g1.verts) {

					if(v!=null){
				
					if (v.infinity == true)
						System.out.println(v + " " + "INF" + " " + v.paths);
					else
						System.out.println(v + " " + v.distance + " " + v.paths);
				} 			
			}
			
		}
		
	}
	
	static Graph minimumSpanningGraph(Graph g) {

		Graph g1 = new Graph(g.numNodes);

		// iterate through all the vertices and then form the new graph.

		for (Vertex from : g.verts) {
			// v id from
			if (from != null) {

				for (Edge e : from.Adj) {
					// u is to .
					Vertex to = e.otherEnd(from);

					// add only needed edges.

					if (to.distance == from.distance + e.Weight) {

						g1.addDirectedEdge(from.name, to.name, e.Weight);
					}

				}

			}
		}
		
		
		return g1;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner sc;
		int algo;
		if (args.length > 0) {
	
			File f1 = new File(args[0]);
			
			sc = new Scanner(f1);
		} else
			sc = new Scanner(System.in);
		Graph g = Graph.readGraph(sc, true);
		algo = Level_1.decideAlgorithm(g);

		if (algo == 0) {
		
			Level_1.bfs(g, g.verts.get(1));

		} else if (algo == 1) {
			
			Level_1.dagShortestPath(g, g.verts.get(1));
		} else if (algo == 2) {

			Level_1.Dijkstra(g, g.verts.get(1));

		} else 
			
		{
			Level_1.bellmanFord(g, g.verts.get(1));

		}

		// create a new graph from the old graph.
		Graph g1 = minimumSpanningGraph(g);

		
		// check if the given graph is a dag or not .
				
				 List<Vertex> v= toplogicalOrder(g1,g1.verts.get(1));
				 
				 if (v!=null) {
				
				 allShortestPaths(g1);
				
				 } else {
				
				// System.out.println("Non-positive cycle in graph. DAC is not applicable");
				
				 }
		
	}
	
}