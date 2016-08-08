import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
ID: Xu Yan
LANG: JAVA
TASK: heritage
*/
/**
 * Thoughts: 
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date August 1st, 2016
 */
public class heritage {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("heritage.in"));
		int[] char_in_order_index = new int[26];
		String in_order = f.readLine();
		String pre_order = f.readLine();
		f.close();
		
		int tree_len = in_order.length();
		for (int i = 0; i < tree_len; i++) {
			char c = in_order.charAt(i);
			char_in_order_index[c-'A'] = i;
		}
		String post_order = recur(in_order, 0, tree_len - 1, pre_order, 0, tree_len - 1, char_in_order_index);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("heritage.out")));
		out.println(post_order);
		out.close();
	}
	
	/**
	 * Calculate the post order serialization of current sub-tree
	 * @param in_order in_order serialization of tree
	 * @param i in_order start index
	 * @param j in_order end index
	 * @param pre_order pre_order serialization of tree
	 * @param m pre_order start index
	 * @param n pre_order end index
	 * @param char_in_order_index the index of a character in in_order serialization of the tree
	 * @return post_order serialization of current sub-tree 
	 */
	private static String recur(
			String in_order, int i, int j, String pre_order, int m, int n, int[] char_in_order_index) {
		StringBuilder builder = new StringBuilder();
		if (m > n) {
			return "";
		} else if (m == n) {
			return pre_order.charAt(m) + "";
		} else { /*m < n*/
			char tree_root = pre_order.charAt(m);
			int root_in_order_index = char_in_order_index[tree_root - 'A'];
			int left_sub_tree_len = root_in_order_index - i;
			
			int left_i_next = i;
			int left_j_next = root_in_order_index - 1;
			int right_i_next = root_in_order_index + 1;
			int right_j_next = j;
			
			int left_m_next = m + 1;
			int left_n_next = m + left_sub_tree_len;
			int right_m_next = m + left_sub_tree_len + 1;
			int right_n_next = n;
			
			String left_sub_tree 
				= recur(in_order, left_i_next, left_j_next, pre_order, left_m_next, left_n_next, char_in_order_index);
			String right_sub_tree
				= recur(in_order, right_i_next, right_j_next, pre_order, right_m_next, right_n_next, char_in_order_index);
			builder.append(left_sub_tree);
			builder.append(right_sub_tree);
			builder.append(tree_root);
		}
		
		return builder.toString();
	}
}
