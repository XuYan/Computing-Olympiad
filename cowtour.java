import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: cowtour
*/
/**
 * Thoughts:  
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 25th, 2016
 */
public class cowtour {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("cowtour.in"));
		int pastureCount = Integer.parseInt(f.readLine());
		int[][] pastureCoordinate = new int[pastureCount][2];
		for (int i = 0; i < pastureCount; i++) {
			StringTokenizer coordinate_token = new StringTokenizer(f.readLine());
			pastureCoordinate[i] = new int[]{Integer.parseInt(coordinate_token.nextToken()), Integer.parseInt(coordinate_token.nextToken())};
		}
		char[][] adj_matrix = new char[pastureCount][pastureCount];
		for (int row = 0; row < pastureCount; row++) {
			String line = f.readLine();
			for (int col = 0; col < pastureCount; col++) {
				adj_matrix[row][col] = line.charAt(col);
			}
		}
		f.close();
		
		List<List<Integer>> subgraphs = new ArrayList<List<Integer>>();
		boolean[] visited = new boolean[pastureCount];
		for (int pasture = 0; pasture < pastureCount; pasture++) {
			if (!visited[pasture]) {
				List<Integer> subgraph = new ArrayList<Integer>();
				DFS(subgraph, adj_matrix, pasture, visited);
				subgraphs.add(subgraph);
			}
		}

		double[] max_distance_from_node_in_subgraph = new double[pastureCount];
		double[] subgraph_diameters = new double[subgraphs.size()];
		for (int i = 0; i < subgraphs.size(); i++) {
			subgraph_diameters[i] = preprocessing(subgraphs.get(i), adj_matrix, pastureCoordinate, max_distance_from_node_in_subgraph);
		}
		
		double subgraph_pair_diameter = 0;
		double answer = Double.MAX_VALUE;
		for (int i = 0; i < subgraphs.size(); i++) {
			List<Integer> subgraph1 = subgraphs.get(i);
			for (int j = i + 1; j < subgraphs.size(); j++) {
				List<Integer> subgraph2 = subgraphs.get(j);
				subgraph_pair_diameter = Math.max(subgraph_diameters[i], subgraph_diameters[j]);
				for (int p1 : subgraph1) {
					for (int p2 : subgraph2) {
						answer = Math.min(answer, Math.max(max_distance_from_node_in_subgraph[p1] + calculateDistance(pastureCoordinate[p1], pastureCoordinate[p2]) + max_distance_from_node_in_subgraph[p2], subgraph_pair_diameter));
					}
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("cowtour.out")));
		out.println(String.format("%.6f", answer));
		out.close();
	}
	
	private static void DFS(List<Integer> subgraph, char[][] adj_matrix, int pasture, boolean[] visited) {
		int pastureCount = visited.length;
		subgraph.add(pasture);
		visited[pasture] = true;
		for (int i = 0; i < pastureCount; i++) {
			if (!visited[i] && adj_matrix[pasture][i] != '0') {
				DFS(subgraph, adj_matrix, i, visited);
			}
		}
	}
	
	private static double preprocessing(List<Integer> subgraph, char[][] adj_matrix, int[][] pastureCoordinate, double[] max_distance_from_node) {
		/* Floyd-Warshall algorithm starts ... */
		// Initialize every value in distance matrix to Double.POSITIVE_INFINITY 
		double[][] distance_matrix = initMatrix(adj_matrix.length, adj_matrix[0].length);
		
		// For each edge in subgraph, calculate its length and put into distance_matrix
		for (int i = 0; i < subgraph.size(); i++) {
			int p1 = subgraph.get(i);
			for (int j = 0; j < subgraph.size(); j++) {
				int p2 = subgraph.get(j);
				if (p1 == p2) { // Be careful about this case
					distance_matrix[p1][p2] = 0;
					distance_matrix[p2][p1] = 0;
				} else if (p1 < p2 /*This aims to avoid duplicate calculation*/ && adj_matrix[p1][p2] == '1') {
					double euclidean_distance = calculateDistance(pastureCoordinate[p1], pastureCoordinate[p2]);
					distance_matrix[p1][p2] = euclidean_distance;
					distance_matrix[p2][p1] = euclidean_distance;
				}
			}
		}
		
		for (int k = 0; k < subgraph.size(); k++) {
			int intermediate =subgraph.get(k);
			for (int i = 0; i < subgraph.size(); i++) {
				int p1 = subgraph.get(i);
				for (int j = 0; j < subgraph.size(); j++) {
					int p2 = subgraph.get(j);
					if (distance_matrix[p1][intermediate] != Double.POSITIVE_INFINITY && distance_matrix[intermediate][p2] != Double.POSITIVE_INFINITY) {
						double alternative_distance = distance_matrix[p1][intermediate] + distance_matrix[intermediate][p2]; 
						if (distance_matrix[p1][p2] > alternative_distance) {
							distance_matrix[p1][p2] = alternative_distance;
						}
					}
					 
				}
			}
		}
		/* Floyd-Warshall algorithm end ... */
		
		double subgraph_diameter = 0;
		for (int i = 0; i < subgraph.size(); i++) {
			int p1 = subgraph.get(i);
			double p1_max_distance = 0;
			for(int j = 0; j < subgraph.size(); j++) {
				int p2 = subgraph.get(j);
				p1_max_distance = Math.max(p1_max_distance, distance_matrix[p1][p2]);
				subgraph_diameter = Math.max(subgraph_diameter, distance_matrix[p1][p2]);
			}
			max_distance_from_node[p1] = p1_max_distance;
		}
		
		return subgraph_diameter;
	}
	
	private static double calculateDistance(int[] coordinate1, int[] coordinate2) {
		return Math.sqrt(Math.pow(coordinate1[0]-coordinate2[0], 2) + Math.pow(coordinate1[1] - coordinate2[1], 2));
	}
	
	private static double[][] initMatrix(int rowCount, int colCount) {
		double[][] matrix = new double[rowCount][colCount];
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				matrix[row][col] = Double.POSITIVE_INFINITY;
			}
		}
		return matrix;
	}
}
