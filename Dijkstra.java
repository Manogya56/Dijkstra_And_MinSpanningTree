import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dijkstra {
	
	public static void main(String[] args) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		count_function(10000000); 
		long end = System.currentTimeMillis(); 
        System.out.println((end - start) + "ms"); 
		File input = new File("C:\\Users\\puliv\\OneDrive\\Documents\\Java Programs\\input2.txt");
//		File input = new File(args[0]);
		Scanner sc = new Scanner(input);
		String[] fLine = sc.nextLine().split("\\s");

		Graph.Edge[] graph = new Graph.Edge[Integer.parseInt(fLine[1])];

		String isDirected = fLine[2];
		String st = "A";
		int i = 0;
		while (sc.hasNextLine()) {
			String[] eachInp = sc.nextLine().split("\\s");
			if(eachInp.length == 3) {
				graph[i] = new Graph.Edge(eachInp[0], eachInp[1], Integer.parseInt(eachInp[2]));				
			} else if(eachInp.length == 1) {
				st = eachInp[0];
			}
			i++;
		}

		Graph gp = new Graph(graph, isDirected);
		gp.dijkstra(st);
	    gp.printPaths();
	}
	
	public static void count_function(long x) 
    { 
        
        for (long i = 0; i < x; i++)
			;			
        
    }
}

class Graph {
	private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges

	/* One edge of the graph (only used by Graph constructor) */
	public static class Edge {
		public final String v1, v2;
		public final int dist;

		public Edge(String v1, String v2, int dist) {
			this.v1 = v1;
			this.v2 = v2;
			this.dist = dist;
		}
	}

	/*One vertex of the graph, complete with mappings to neighbouring vertices */
	public static class Vertex implements Comparable<Vertex> {
		public final String name;
		public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
		public Vertex previous = null;
		public final Map<Vertex, Integer> neighbours = new HashMap<>();

		public Vertex(String name) {
			this.name = name;
		}

		private void pPath() {
			if (this == this.previous) {
				System.out.printf("%s", this.name);
			} else if (this.previous == null) {
				System.out.printf("%s(unreached)", this.name);
			} else {
				this.previous.pPath();
				System.out.printf(" -> %s(%d)", this.name, this.dist);
			}
		}

		public int compareTo(Vertex other) {
			if (dist == other.dist)
				return name.compareTo(other.name);

			return Integer.compare(dist, other.dist);
		}

		@Override
		public String toString() {
			return "(" + name + ", " + dist + ")";
		}
	}


	public Graph(Edge[] edges, String isDirected) {
		graph = new HashMap<>(edges.length);

		// one pass to find all vertices
		for (Edge e : edges) {
			if (!graph.containsKey(e.v1))
				graph.put(e.v1, new Vertex(e.v1));
			if (!graph.containsKey(e.v2))
				graph.put(e.v2, new Vertex(e.v2));
		}

		if (isDirected.equals("U")) {
			for (Edge e : edges) {
				graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
				graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
			}
		} else {
			for (Edge e : edges) {
				graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
			}
		}
	}

	/** Runs dijkstra using a specified source vertex */
	public void dijkstra(String startName) {
		if (!graph.containsKey(startName)) {
			System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
			return;
		}
		final Vertex source = graph.get(startName);
		NavigableSet<Vertex> q = new TreeSet<>();

		
		for (Vertex v : graph.values()) {
			v.previous = v == source ? source : null;
			v.dist = v == source ? 0 : Integer.MAX_VALUE;
			q.add(v);
		}

		dijkstra(q);
	}

	/** Implementation of dijkstra's algorithm using a binary heap. */
	private void dijkstra(final NavigableSet<Vertex> q) {
		Vertex u, v;
		while (!q.isEmpty()) {

			u = q.pollFirst(); // vertex with shortest distance (first iteration will return the source one)
			if (u.dist == Integer.MAX_VALUE)
				break; // we could ignore u (and any other remaining vertices) since they are not reachable

			// look at distances to each neighbour
			for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
				v = a.getKey(); // the neighbour in this iteration

				final int otherDist = u.dist + a.getValue();
				if (otherDist < v.dist) { // shorter path to neighbour found
					q.remove(v);
					v.dist = otherDist;
					v.previous = u;
					q.add(v);
				}
			}
		}
	}

	/** Prints a path from the source to the specified vertex */
	public void pPath(String endName) {
		if (!graph.containsKey(endName)) {
			System.err.printf("Graph doesn't contain end vertex \"%s\"\n", endName);
			return;
		}

		graph.get(endName).pPath();
		System.out.println();
	}

	/*Prints the path from the source to every vertex (output order is not
	  guaranteed)
	 */
	public void printPaths() {
		for (Vertex v : graph.values()) {
			v.pPath();
			System.out.println();
		}
	}
	 
}