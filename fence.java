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
TASK: fence
*/
/**
 * Thoughts: Approach the problem with Eulerian Tour Algo.
 *           The time complexity if O(|E| + |V|).
 *           Since the graph is not guaranteed to have a Eulerian Tour, I'll first check each node's degree.
 *           (1) If all of them have even degree, the graph must have a Eulerian Tour. It's straightforward that
 *           Eulerian Tour is not unique. So I'll find out the node with smallest id to start algorithm. 
 *           (2) If any of the nodes has odd degree, 
 *           two nodes will have odd degree. Since we're trying to figure out the Eulerian Trail
 *           with smallest magnitude when interpreted as a base 500 number, I'll choose the smaller of the two odd
 *           degree node to start the algorithm.
 *           
 * Pitfalls: Be careful that the algo will output the visit sequence in reverse order!
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 19th, 2016
 */
public class fence {
	private static class MinHeap {
		private List<IP> ips;
		private int counter; // Indicates the next available slot
		public MinHeap() {
			this.ips = new ArrayList<IP>();
			ips.add(null); // To make it more convenient to find parent and children indices
			this.counter = 1;
		}
		
		public boolean isEmpty() {
			return this.counter == 1;
		}
		
		public void add(IP ip) {
			ips.add(ip);
			swim(counter++);
		}
		
		public IP poll() throws Exception {
			if (this.isEmpty()) {
				throw new Exception("Trying to poll element from an empty heap");
			}
			IP min = this.ips.get(1);
			swap(1, this.counter-1);
			this.ips.set(counter-1, null);
			this.counter--;
			sink(1);
			
			return min;
		}
		
		/**
		 * TODO: This should be replaced with a more efficient implementation 
		 */
		public void remove(IP ip) {
			for (int i = 1; i < this.ips.size(); i++) {
				if (ip.id == this.ips.get(i).id) {
					swap(i, this.counter-1);
					this.ips.set(this.counter-1, null);
					this.counter --;
					sink(i);
					break;
				}
			}
		}
		
		private void swap(int pos1, int pos2) {
			IP temp = this.ips.get(pos1);
			this.ips.set(pos1, this.ips.get(pos2));
			this.ips.set(pos2, temp);
		}
		
		private void sink(int pos) {
			while (pos < this.counter) {
				IP current = this.ips.get(pos);
				IP smaller_child = null;
				int smaller_child_pos = -1;
				if (pos * 2 < this.counter) {
					smaller_child = this.ips.get(pos * 2);
					smaller_child_pos = pos * 2;
					if (pos * 2 + 1 < this.counter 
						&& smaller_child.id > this.ips.get(pos * 2 + 1).id) {
						smaller_child = this.ips.get(pos * 2 + 1);
						smaller_child_pos = pos * 2 + 1;
					}
					if (current.id > smaller_child.id) {
						swap(pos, smaller_child_pos);
						pos = smaller_child_pos;
					} else {
						break;
					}
				} else {
					break;
				}
			}
		}
		
		private void swim(int pos) {
			while (pos > 1) {
				IP parent = this.ips.get(pos/2);
				IP current = this.ips.get(pos);
				if (parent.id > current.id) {
					swap(pos/2, pos);
					pos /= 2;
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * Fence Intersection Point 
	 */
	private static class IP {
		int id;
		int degree;
		MinHeap neighbors;
		public IP(int id) {
			this.id = id;
			this.degree = 0;
			this.neighbors = new MinHeap();
		}
	}
	
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("fence.in"));
		StringTokenizer input_tokens = new StringTokenizer(f.readLine());
		int F = Integer.parseInt(input_tokens.nextToken());
		IP[] ips = new IP[501];
		for (int edge_index = 0; edge_index < F; edge_index++) {
			input_tokens = new StringTokenizer(f.readLine());
			int from_ip = Integer.parseInt(input_tokens.nextToken());
			int to_ip = Integer.parseInt(input_tokens.nextToken());
			if (ips[from_ip] == null) {
				ips[from_ip] = new IP(from_ip);
			}
			if (ips[to_ip] == null) {
				ips[to_ip] = new IP(to_ip);
			}
			ips[from_ip].degree ++;
			ips[to_ip].degree ++;
			ips[from_ip].neighbors.add(ips[to_ip]);
			ips[to_ip].neighbors.add(ips[from_ip]);
		}
		f.close();
		
		int start_point = 501; // There are at most 500 intersections
		int min_point = 501;
		for (int i = 1; i <= 500; i++) {
			if (ips[i] != null) {
				min_point = Math.min(min_point, i);
				if (ips[i].degree % 2 == 1) {
					start_point = Math.min(start_point, i); 
				}
			}
		}
		// Euler circuit!
		if (start_point == 501) {
			start_point = min_point;
		}
		
		// Implement Eulerian Tour algorithm
		List<Integer> tour = new ArrayList<Integer>();
		findEulerTour(ips[start_point], tour);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence.out")));
		for (int i = tour.size() - 1; i >= 0; i--) {
			out.println(tour.get(i));
		}
		out.close();
	}
	
	private static void findEulerTour(IP ip, List<Integer> tour) throws Exception {
		while (!ip.neighbors.isEmpty()) {
			IP next = ip.neighbors.poll();
			next.neighbors.remove(ip);
			findEulerTour(next, tour);
		}
		tour.add(ip.id);
	}
}