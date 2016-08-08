import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: butter
*/
/**
 * Thoughts: I approached the problem with Floyd-Warshall algorithm firstly. 
 *           The graph vertex is pasture in this problem (2 <= |V| <= 800). 
 *           Since the time complexity of Floyd-Warshall algorithm is O(|V|^3), I got a TLE.
 *           After a second thought, this graph is pretty sparse, because
 *           the number of edges in graph is |C|, which is in [1,1450] (|E| << |V|^2). 
 *           Therefore, a shortest path algorithm using |E| is desirable
 *           => Bellman-Ford/SPFA: O(|V| * |E|)
 *           => Dijkstra: O(|E| + |V|log|V|)
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 11th, 2016
 */
public class butter {
	private static class Pasture {
		int id;
		int distance_to_source;
		Map<Integer,Integer> neighbors; // id <-> distance
		public Pasture(int id) {
			this.id = id;
			this.distance_to_source = Integer.MAX_VALUE;
			this.neighbors = new HashMap<Integer,Integer>();
		}
	}
	
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("butter.in"));
		StringTokenizer inputs = new StringTokenizer(f.readLine());
		int N = Integer.parseInt(inputs.nextToken());
		int P = Integer.parseInt(inputs.nextToken());
		int C = Integer.parseInt(inputs.nextToken());
		
		Pasture[] pastures = new Pasture[P+1];
		for (int i = 1; i <= P; i++) {
			pastures[i] = new Pasture(i);
		}
		
		int[] cow_in_pasture = new int[P+1]; // Record how many cows in each pasture
		Set<Integer> p_c = new HashSet<Integer>();
		for (int i = 0; i < N; i++) {
			int pasture_id = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
			cow_in_pasture[pasture_id] ++;
			p_c.add(pasture_id);
		}
		
		for (int path_index = 0; path_index < C; path_index++) {
			StringTokenizer path_token = new StringTokenizer(f.readLine());
			int source_pasture_index = Integer.parseInt(path_token.nextToken());
			int destination_pasture_index = Integer.parseInt(path_token.nextToken());
			int path_length = Integer.parseInt(path_token.nextToken());
			
			pastures[source_pasture_index].neighbors.put(destination_pasture_index, path_length);
			pastures[destination_pasture_index].neighbors.put(source_pasture_index, path_length);
		}
		f.close();
		
		int min_distance = Integer.MAX_VALUE;
		// Run SPFA Algorithm P times to find the SP between all pairs of pastures
		for (int source_id = 1; source_id <= P; source_id++) {
			Queue<Integer> queue = new LinkedList<Integer>();
			boolean[] in_queue = new boolean[P+1];
			pastures[source_id].distance_to_source = 0;
			queue.add(source_id);
			
			while (!queue.isEmpty()) {
				int pasture_id = queue.poll();
				in_queue[pasture_id] = false;
				Pasture current_pasture = pastures[pasture_id];
				for (int neighbor_id : current_pasture.neighbors.keySet()) {
					int new_distance = current_pasture.distance_to_source + current_pasture.neighbors.get(neighbor_id); 
					if (new_distance < pastures[neighbor_id].distance_to_source) {
						pastures[neighbor_id].distance_to_source = new_distance;
						if (!in_queue[neighbor_id]) {
							queue.add(neighbor_id);
							in_queue[neighbor_id] = true;
						}
					}
				}
			}
			
			// Be careful there may be multiple cows in a pasture
			// Place butter in pasture 'source_id'
			int distance_candidate = 0;
			for (int p_id : p_c) {
				distance_candidate += pastures[p_id].distance_to_source * cow_in_pasture[p_id];
			}
			min_distance = Math.min(min_distance, distance_candidate);
			
			resetDistanceToSource(pastures);
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));
		out.println(min_distance);
		out.close();
	}
	
	private static void resetDistanceToSource(Pasture[] pastures) {
		for (int i = 1; i < pastures.length; i++) {
			pastures[i].distance_to_source = Integer.MAX_VALUE;
		}
	}
}