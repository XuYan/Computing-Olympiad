import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: comehome
*/
/**
 * Thoughts: The graph is a connected graph since all cows have a path to the barn. The single source is the barn Z. All paths have weight within [1,1000].
 *           Therefore, we can use Dijkstra's algorithm to figure out the problem
 *           
 * Pitfalls:  
 *           
 * Take-away tips:  
 * 
 * @author Xu Yan
 * @date June 19th, 2016
 */
public class comehome {
	private static class Node implements Comparable<Node> {
		char id; // a..z to represent pasture without cow; A..Z to represent pasture with cow
		Map<Character,Integer> distance_to_neighbor; // Key is another pasture's id; Value is the distance between the two pastures  
		int distance_to_barn;
		
		public Node(char id) {
			this.id = id;
			this.distance_to_neighbor = new HashMap<Character,Integer>();
			this.distance_to_barn = Integer.MAX_VALUE;
		}

		@Override
		public int compareTo(Node that) {
			return this.distance_to_barn - that.distance_to_barn;
		}
		
		@Override
		public int hashCode() {
			return this.id;
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj instanceof Node && this.id == ((Node) obj).id;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader f = new BufferedReader(new FileReader("comehome.in"));
		
		// Create graph
		Map<Character,Node> graph = new HashMap<Character,Node>(); // Key is node id and value is node
		Set<Character> nodes_with_cow = new HashSet<Character>(); // Record the id of nodes that have cow in it
		int paths = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
		for (int i = 1; i <= paths; i++) {
			StringTokenizer line_tokens = new StringTokenizer(f.readLine());
			char node1_id = line_tokens.nextToken().charAt(0);
			char node2_id = line_tokens.nextToken().charAt(0);
			
			if (isCowInPasture(node1_id)) {
				nodes_with_cow.add(node1_id);
			}
			
			if (isCowInPasture(node2_id)) {
				nodes_with_cow.add(node2_id);
			}
			
			if (!graph.containsKey(node1_id)) {
				graph.put(node1_id, new Node(node1_id));
			}
			
			if (!graph.containsKey(node2_id)) {
				graph.put(node2_id, new Node(node2_id));
			}
			
			int distance = Integer.parseInt(line_tokens.nextToken());
			Node node1 = graph.get(node1_id);
			Node node2 = graph.get(node2_id);
			// Since it's possible to have multiple paths between two pastures, we take the smallest path as final distance
			if (!node1.distance_to_neighbor.containsKey(node2.id) || distance < node1.distance_to_neighbor.get(node2_id)) {
				node1.distance_to_neighbor.put(node2_id, distance);
				node2.distance_to_neighbor.put(node1_id, distance);
			}
		}
		f.close();
		
		// Run Dijkstra's algorithm with 'barn' as the source
		Dijkstra(graph, nodes_with_cow);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("comehome.out")));
		out.println(calcNearestPasture(graph, nodes_with_cow));
		out.close();
	}
	
	private static boolean isCowInPasture(char node_id) {
		return node_id >= 'A' && node_id <= 'Y';
	}
	
	private static String calcNearestPasture(Map<Character,Node> graph, Set<Character> nodes_with_cow) {
		int nearest_distance = Integer.MAX_VALUE;
		char nearest_pasture_id = '/';
		for (char node_id : nodes_with_cow) {
			Node cow_node = graph.get(node_id); 
			if (cow_node.distance_to_barn < nearest_distance) {
				nearest_distance = cow_node.distance_to_barn;
				nearest_pasture_id = cow_node.id;
			}
		}
		
		return nearest_pasture_id + " " + nearest_distance;
	}
	
	private static void Dijkstra(Map<Character,Node> graph, Set<Character> nodes_with_cow) throws Exception {
		Heap<Node> min_heap = new Heap<Node>(52);
		
		// The 'cow nodes' that we have already found the minimum distance to barn
		Set<Character> cared_nodes = new HashSet<Character>();

		// Set barn's distance_to_barn to 0
		graph.get('Z').distance_to_barn = 0;
				
		// Add barn and all pastures to min heap
		for (char id : graph.keySet()) {
			min_heap.insert(graph.get(id));
		}
		
		while (min_heap.size() > 0 && cared_nodes.size() < nodes_with_cow.size()) {
			Node cur_nearest_to_barn = min_heap.deleteMin();
			if (isCowInPasture(cur_nearest_to_barn.id)) {
				cared_nodes.add(cur_nearest_to_barn.id);
			}
			
			for (char neighbor_node_id : cur_nearest_to_barn.distance_to_neighbor.keySet()) {
				// relax their distance to barn if closer
				Node neighbor = graph.get(neighbor_node_id);
				int distance = cur_nearest_to_barn.distance_to_barn + cur_nearest_to_barn.distance_to_neighbor.get(neighbor_node_id);
				if (neighbor.distance_to_barn > distance) {
					neighbor.distance_to_barn = distance;
					min_heap.adjust(neighbor);	// Since we relax the value of neighbor, we need to adjust its position in min heap according to the new value
				}
			}
		}
	}
	
	private static class Heap<DataType extends Comparable<DataType>> {
		private Object[] data; // data is used to represent the heap (complete binary tree with parent's value smaller than children's values)
		private Map<DataType,Integer> index; // index is used to map an element in heap to its index in 'data' array
		private int N; // The number of elements in the heap
		
		public Heap(int capacity) {
			this.data = new Object[capacity + 1];
			this.index = new HashMap<DataType,Integer>();
			this.N = 0;
		}
		
		public boolean isEmpty() {
			return this.N == 0;
		}
		
		public int size() {
			return this.N;
		}
		
		public void adjust(DataType element) {
			int index = this.index.get(element);
			swim(index);
		}
		
		public void insert(DataType element) {
			if (this.N == this.data.length - 1) {
				doubleSize();
			}
			this.N ++;
			
			this.data[this.N] = element;
			this.index.put(element, this.N);
			
			swim(this.N);
		}
		
		public DataType deleteMin() throws Exception {
			if (this.isEmpty()) {
				throw new Exception("Trying to delete min from an empty heap");
			}
			DataType minElement = (DataType) this.data[1];
			this.swap(1, this.N);
			
			this.data[this.N] = null;
			this.index.remove(minElement);
			
			this.N --;
			sink(1);
			
			return minElement;
		}
		
		/**
		 * Double the capacity of heap
		 */
		private void doubleSize() {
			Object[] new_data = new Object[this.data.length * 2];
			for (int i = 0; i < this.data.length; i++) {
				new_data[i] = this.data[i];
			}
			this.data = new_data;
		}
		
		/**
		 * @return true if e1 is less than e2
		 */
		private boolean less(DataType e1, DataType e2) {
			return e1.compareTo(e2) < 0;
		}
		
		/**
		 * Repeatedly comparing the element at current position (starts with N) with its parent element.
		 * Exchange current element and its parent element if current element is smaller than its parent element
		 * 
		 * @param position the position to start swimming up
		 */
		private void swim(int position) {
			int current_position = position;
			while (current_position > 1) {
				int parent_position = current_position / 2;
				if (this.less((DataType) this.data[current_position], (DataType) this.data[parent_position])) {
					this.swap(parent_position, current_position);
					current_position = parent_position;
				} else {
					break;
				}
			}
		}
		
		/**
		 * Repeated comparing the element at current position (starts with 1) with its children elements.
		 * If current element is smallest, min binary heap is satisfied.
		 * Otherwise, swap current element with the larger child element and go on repeating the same process.  
		 * 
		 * @param position the position to start sinking down
		 */
		private void sink(int position) {
			int current_position = position;
			while (current_position * 2 <= this.N) {
				int child_position = 2 * current_position;
				if (child_position < this.N && this.less((DataType) this.data[child_position + 1], (DataType) this.data[child_position])) {
					child_position ++;
				}
				if (this.less((DataType) this.data[child_position], (DataType) this.data[current_position])) {
					swap(current_position, child_position);
					current_position = child_position;
				} else {
					break;
				}
			}
		}
		
		private void swap(int position1, int position2) {
			Object temp = this.data[position1];
			this.data[position1] = this.data[position2];
			this.index.put((DataType)this.data[position2], position1);
			
			this.data[position2] = temp;
			this.index.put((DataType)temp, position2);
		}
	}
}
