import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/*
ID: Xu Yan
LANG: JAVA
TASK: ditch
*/
/**
 * Thoughts: Use Ford-Fulkerson Algorithm to find max-flow
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date October 15th, 2016
 */
public class ditch {
	private static class FlowGraph {
		private final Map<Integer, List<FlowEdge>> graph;
		private int intersection_count;
		private FlowEdge[] aug_path;
		
		public FlowGraph(int intersection_count) {
			this.graph = new HashMap<Integer, List<FlowEdge>>();
			this.intersection_count = intersection_count;
			this.aug_path = new FlowEdge[this.intersection_count + 1];
		}
		
		public void addEdge(int node, FlowEdge edge) {
			if (!this.graph.containsKey(node)) {
				this.graph.put(node, new ArrayList<FlowEdge>());
			}
			this.graph.get(node).add(edge);
		}
		
		public int getMaxFlow() {
			int max_flow = 0;
			int src = 1;
			int sink = this.intersection_count;
			while (this.hasAugmentedPath(src, sink)) {
				int flow_to_add = Integer.MAX_VALUE;
				for (int n = sink; n != src; n = this.aug_path[n].other(n)) {
					FlowEdge edge = this.aug_path[n];
					flow_to_add = Math.min(flow_to_add, edge.getResidualCapacityTo(n));
				}
				
				for (int n = sink; n != src; n = this.aug_path[n].other(n)) {
					FlowEdge edge = this.aug_path[n];
					edge.addFlowTo(n, flow_to_add);
				}
				
				max_flow += flow_to_add;
			}
			
			return max_flow;
		}
		
		private boolean hasAugmentedPath(int n1, int n2) {
			boolean[] visited = new boolean[this.intersection_count + 1];
			Queue<Integer> queue = new LinkedList<Integer>();
			queue.add(n1);
			visited[n1] = true;
			while (!queue.isEmpty()) {
				int size = queue.size();
				int i = 0;
				while (i < size) {
					int n = queue.poll();
					// Be careful to deal with the corner case where there is no ditch
					// between pond and stream (input: 0 2)
					if (this.graph.containsKey(n)) {
						for (FlowEdge edge : this.graph.get(n)) {
							int other = edge.other(n);
							if (edge.getResidualCapacityTo(other) != 0 && !visited[other]) {
								this.aug_path[other] = edge;
								visited[other] = true;
								queue.add(other);
							}
						}
					}
					i++;
				}
			}
			return visited[n2];
		}
	}
	
	private static class FlowEdge {
		private final int v, w;
		private final int capacity;
		private int flow;
		
		public FlowEdge(int v, int w, int capacity) {
			this.v = v;
			this.w = w;
			this.capacity = capacity;
		}
		
		public int other(int n) {
			return n == this.v ? this.w : this.v;
		}
		
		public int getResidualCapacityTo(int n) {
			return n == this.w ? (this.capacity - this.flow) : this.flow;
		}
		
		public void addFlowTo(int n, int value) {
			int delta = (n == this.w) ? value : -value;
			this.flow += delta;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader("ditch.in"));
		String[] N_M = input.readLine().split(" ");
		int ditch_count = Integer.parseInt(N_M[0]);
		int intersection_count = Integer.parseInt(N_M[1]);
		FlowGraph graph = new FlowGraph(intersection_count);
		for (int i = 0; i < ditch_count; i++) {
			String[] src_dst_cap = input.readLine().split(" ");
			int src = Integer.parseInt(src_dst_cap[0]);
			int dst = Integer.parseInt(src_dst_cap[1]);
			int cap = Integer.parseInt(src_dst_cap[2]);
			FlowEdge edge = new FlowEdge(src, dst, cap);
			graph.addEdge(src, edge);
			graph.addEdge(dst, edge);
		}
		input.close();
		
		int max_flow = graph.getMaxFlow();
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("ditch.out")));
		// Be careful max_flow needs to be converted to string before writing to file !!!
		// Otherwise, max_flow will be considered as the index of the character in ASCII.
		output.write(max_flow + "\n");
		output.close();
	}
}