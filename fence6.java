import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: fence6
*/
/**
 * Thoughts: The first idea I had is DFS + pruning.
 *           Simply speaking, dfs the graph from any vertex (let's name it as 's'). In each recursive step,
 *           we maintain the accumulated distance from current vertex to 's' and record the value in a distance array
 *           (I named it as "distance_to_start" in program). The array elements are initialized to '0'. Since all
 *           edges in graph are guaranteed to be positive, I'm safe to say when we hit a vertex, whose distance from
 *           's' is not zero, we find a cycle (There is one exception, see 'pitfalls'). The perimeter of the cycle can
 *           be easily calculated as 'accumulated distance - distance_to_start[vertex]'.
 *           For improve my program, I maintain a global variable('min_perimeter') that records the minimum perimeter
 *           I currently found. In a recursive procedure, if the accumulated distance is larger than the current
 *           minimum perimeter, we can early return.
 *             
 * Pitfalls: (1) When meeting a vertex whose distance to 's' is not 0, we can say cycle is found
 *               only with one exception. Consider the graph A --1-- B --2-- C --3-- D,
 *               when I get to 'C' from 'B', I have updated distance_to_start[B] to 1. In the next recursive call,
 *               I need to go to C's neighbors, which is 'B' and 'D' one by one. However, going back to 'B' shouldn't
 *               be allowed since 'B' -- 'C' -- 'B' is not a cycle since the graph is a simple graph!
 *               To resolve this problem, I maintained another argument in the recursive method to record the previous
 *               vertex from which I get to the current vertex and let the next vertex I'm going to cannot be 
 *               the previous vertex.
 *           
 * Take-away tips: It seems we can dfs based on graph edges (since the input is given with edge information). But what
 *                 I did is to convert the edge information to vertex information to built adjacency list to represent
 *                 graph (Sigh...)
 *                 What I did is maintaining a map from edge_id to edge_vertice. When processing an edge, check if any
 *                 of its neighbor edge has been processed. If yes, we must have create the vertex when processing
 *                 that edge. Otherwise, create a vertex and put it into the vertex list of each of its neighbor edges.
 * 
 * @author Xu Yan
 * @date August 6th, 2016
 */
public class fence6 {
	private static int node_count = 0; // The number of vertices in graph
	private static int min_perimeter = Integer.MAX_VALUE; // The answer to the problem. Initialized as max integer
	
	private static class Node {
		private static int node_id = 0; 
		int id;
		Map<Integer,Integer> neighbors;
		public Node() {
			this.id = node_id++;
			this.neighbors = new HashMap<Integer,Integer>();
		}
	}
	
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("fence6.in"));
		int N = Integer.parseInt(f.readLine()); // number of edges in graph
		int[] edges = new int[N+1];
		boolean[] isProcessed = new boolean[N+1];
		Node[][] edge_nodes = new Node[N+1][2];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer tokens = new StringTokenizer(f.readLine());
			int edge_id = Integer.parseInt(tokens.nextToken());
			int edge_length = Integer.parseInt(tokens.nextToken());
			int n1 = Integer.parseInt(tokens.nextToken());
			int n2 = Integer.parseInt(tokens.nextToken());
			
			edges[edge_id] = edge_length;
			
			parseNeighbors(f, n1, edge_id, isProcessed, edge_nodes);
			parseNeighbors(f, n2, edge_id, isProcessed, edge_nodes);
			isProcessed[edge_id] = true;
		}
		
		Node[] vertices = new Node[node_count];
		for (int i = 1; i <= N; i++) {
			Node n1 = edge_nodes[i][0];
			Node n2 = edge_nodes[i][1];
			n1.neighbors.put(n2.id, edges[i]);
			n2.neighbors.put(n1.id, edges[i]);
			if (vertices[n1.id] == null) {
				vertices[n1.id] = n1;
			}
			if (vertices[n2.id] == null) {
				vertices[n2.id] = n2;
			}
		}
		// Graph Construction Complete
		
		Node startNode = edge_nodes[1][0];
		int[] distance_to_start = new int[node_count];
		DFS(null, startNode, distance_to_start, 0, vertices);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence6.out")));
		out.println(min_perimeter);
		out.close();
	}
	
	private static void DFS(Node prevNode, Node curNode, int[] distance_to_start, int distance, Node[] vertices) {
		if (distance > min_perimeter) {
			return;
		}
		if (distance_to_start[curNode.id] != 0 /*A cycle is found*/) {
			min_perimeter = Math.min(min_perimeter, distance - distance_to_start[curNode.id]);
			return;
		}
		distance_to_start[curNode.id] = distance;
		for (int neighbor_node_id : curNode.neighbors.keySet()) {
			Node nextNode = vertices[neighbor_node_id];
			if (prevNode != nextNode) { // Pay attention to pitfall(1)!
				DFS(curNode, nextNode, distance_to_start,
						distance + curNode.neighbors.get(neighbor_node_id), vertices);	
			}
		}
		distance_to_start[curNode.id] = 0;
	}
	
	private static void parseNeighbors(
			BufferedReader f, 
			int neighbor_count, 
			int current_edge_id, 
			boolean[] isProcessed,
			Node[][] edge_nodes) throws IOException {
		StringTokenizer neighbor_edges = new StringTokenizer(f.readLine());
		List<Integer> neighbors = new ArrayList<Integer>();
		neighbors.add(current_edge_id);
		
		for (int j = 0; j < neighbor_count; j++) {
			int neighbor_edge_id = Integer.parseInt(neighbor_edges.nextToken());
			if (isProcessed[neighbor_edge_id]) {
				return;
			}
			neighbors.add(neighbor_edge_id);
		}
		Node node = new Node();
		node_count ++;
		for (int edge_id : neighbors) {
			if (edge_nodes[edge_id][0] == null) {
				edge_nodes[edge_id][0] = node;
			} else {
				edge_nodes[edge_id][1] = node;
			}
		}
	}
}