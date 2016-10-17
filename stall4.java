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
TASK: stall4
*/
/**
 * Thoughts: Build flow network and use Ford-Fulkerson Algorithm to find the max flow
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date October 16th, 2016
 */
public class stall4 {
	private static class FlowGraph {
		Map<Integer, List<FlowEdge>> graph;
		private final FlowEdge[] edge_to;
		private final int src_id, sink_id;
		public FlowGraph(int src_id, int sink_id) {
			this.graph = new HashMap<Integer, List<FlowEdge>>();
			this.edge_to = new FlowEdge[sink_id + 1];
			this.src_id = src_id;
			this.sink_id = sink_id;
		}
		
		public void addEdge(FlowEdge edge) {
			if (!this.graph.containsKey(edge.src)) {
				this.graph.put(edge.src, new ArrayList<FlowEdge>());
			}
			this.graph.get(edge.src).add(edge);
			
			if (!this.graph.containsKey(edge.dst)) {
				this.graph.put(edge.dst, new ArrayList<FlowEdge>());
			}
			this.graph.get(edge.dst).add(edge);
		}
		
		public int maxFlow() {
			int max_flow = 0;
			while (this.hasAugmentPath()) {
				max_flow += 1;
				for (int node = this.sink_id; node != this.src_id; node = this.edge_to[node].other(node)) {
					FlowEdge edge = this.edge_to[node];
					edge.addFlowTo(node);
				}
			}
			return max_flow;
		}
		
		private boolean hasAugmentPath() {
			Queue<Integer> queue = new LinkedList<Integer>();
			queue.add(this.src_id);
			boolean[] visited = new boolean[this.sink_id + 1];
			while (!queue.isEmpty()) {
				int queue_size = queue.size();
				for (int i = 0; i < queue_size; i++) {
					int node = queue.poll();
					for (FlowEdge edge : this.graph.get(node)) {
						int other = edge.other(node);
						if (edge.getResidualCapacityTo(other) > 0 && !visited[other]) {
							visited[other] = true;
							this.edge_to[other] = edge;
							queue.add(other);
						}
					}
				}
			}
			
			return visited[this.sink_id];
		}
	}
	
	private static class FlowEdge {
		private final int src, dst;
		private int flow;
		public FlowEdge(int src, int dst) {
			this.src = src;
			this.dst = dst;
			this.flow = 0;
		}
		
		public int other(int node) {
			return node != this.src ? this.src : this.dst; 
		}
		
		public int getResidualCapacityTo(int node) {
			return node != this.src ? (1 - this.flow) : this.flow; 
		}
		
		public void addFlowTo(int node) {
			this.flow += (node != this.src ? 1 : -1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader("stall4.in"));
		String[] N_M = input.readLine().split(" ");
		int cow_count = Integer.parseInt(N_M[0]);
		int stall_count = Integer.parseInt(N_M[1]);
		FlowGraph graph = new FlowGraph(0, cow_count + stall_count + 1);
		for (int cow_id = 1; cow_id <= cow_count; cow_id++) {
			String[] data = input.readLine().split(" ");
			int preferred_stall_count = Integer.parseInt(data[0]);
			for (int j = 1; j <= preferred_stall_count; j++) {
				int stall_id = Integer.parseInt(data[j]) + cow_count;
				FlowEdge edge = new FlowEdge(cow_id, stall_id);
				graph.addEdge(edge);
			}
		}
		// Add graph source(id = 0) and sink(id = #cow + #stall + 1)
		int src_id = 0;
		for (int cow_id = 1; cow_id <= cow_count; cow_id++) {
			graph.addEdge(new FlowEdge(src_id, cow_id));
		}
		int sink_id = cow_count + stall_count + 1;
		for (int stall_id = cow_count + 1; stall_id <= cow_count + stall_count; stall_id++) {
			graph.addEdge(new FlowEdge(stall_id, sink_id));
		}
		input.close();
		
		int max_flow = graph.maxFlow();
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("stall4.out")));
		output.write(max_flow + "\n");
		output.close();
	}
}