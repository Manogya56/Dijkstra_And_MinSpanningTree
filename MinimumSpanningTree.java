import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MinimumSpanningTree {

	public static void main(String args[]) throws FileNotFoundException {
		long start = System.currentTimeMillis();
		count_function(10000000); 
		long end = System.currentTimeMillis(); 
        System.out.println((end - start) + "ms"); 
		
		
		
		Graph graph = new Graph();
    	
    	File input = new File("C:\\Users\\puliv\\OneDrive\\Documents\\Java Programs\\input2.txt");
//    	File input = new File(args[0]);
    	Scanner sc = new Scanner(input);
    	sc.nextLine();
    	while(sc.hasNextLine()) {
    		String[] line = sc.nextLine().split("\\s");
    		if(line.length == 3)
    			graph.addEdge(line[0], line[1], Integer.parseInt(line[2]));
    	}

    	// Print MSTEdges, the list that has all the edges and lastly, total // cost of minimum spanning tree
    	for (Edge edge: graph.kruskalMST()) {
    	    System.out.println(edge.source + " to " + edge.des + " -> " + edge.cost);
    	}
    	System.out.println(graph.getTotalCost());
    }
	
	public static void count_function(long x) 
    { 
        
        for (long i = 0; i < x; i++) 
            ; 
     
    } 
    
}