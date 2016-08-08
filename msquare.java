import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: msquare
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 10th, 2016
 */
public class msquare {
	private static class Node {
		String id;
		char transform_id;
		Node prev;
		
		public Node(String id, char transform_id) {
			this.id = id;
			this.transform_id = transform_id;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("msquare.in"));
		StringTokenizer inputs = new StringTokenizer(f.readLine());
		String target = "";
		for (int i = 0; i < 8; i++) {
			target += inputs.nextToken();
		}
		f.close();

		Node ans = null;
		boolean answer_found = false;
		char[] ops = new char[] {'A', 'B', 'C'};
		Set<String> set = new HashSet<String>();
		set.add("12345678");
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(new Node("12345678", '/'));
		while (!queue.isEmpty() && !answer_found) {
			int level_size = queue.size();
			for (int i = 0; i < level_size; i++) {
				Node n = queue.poll();
				if (n.id.equals(target)) {
					ans = n;
					answer_found = true;
					break;
				}
				for (int op = 0; op < ops.length; op++) {
					String after_transform = transform(n.id, ops[op]);
					if (!set.contains(after_transform)) {
						set.add(after_transform);
						Node next_level_node = new Node(after_transform, ops[op]);
						next_level_node.prev = n;
						queue.add(next_level_node);
					}
				}
			}
		}
		backTrack(ans);
	}
	
	private static void backTrack(Node n) throws IOException {
		int op_count = 0;
		StringBuilder ops = new StringBuilder();
		while (n.transform_id != '/') {
			op_count ++;
			ops.append(n.transform_id);
			n = n.prev;
		}
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
		out.println(op_count);
		String ops_str = ops.reverse().toString();
		for (int i = 0; i < ops_str.length(); i++) {
			if (i != 0 && i % 60 == 0) {
				out.print("\n");
			}
			out.print(ops_str.charAt(i));
		}
		out.print("\n");
		out.close();
	}
	
	private static String transform(String s, char op) {
		StringBuilder after_transform = new StringBuilder();
		switch (op) {
		case 'A':
			return new StringBuilder(s).reverse().toString();
		case 'B':
			return after_transform.append(s.charAt(3)).append(s.substring(0,3)) 
					.append(s.substring(5,8)).append(s.charAt(4)).toString();
		default/*C*/:
			return after_transform.append(s.charAt(0)).append(s.charAt(6))
					.append(s.charAt(1)).append(s.charAt(3)).append(s.charAt(4))
					.append(s.charAt(2)).append(s.charAt(5)).append(s.charAt(7)).toString();
		}
	}
}
