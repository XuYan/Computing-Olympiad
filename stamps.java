import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: stamps
*/
/**
 * Thoughts: I approach the problem with dynamic programming. The state space is 2-dimensional. One is the value we can get. The other is the number of stamps used to get the value.
 *           We can get a value N with at most K stamps if we can get a value (N - Vx) with at most K-1 stamps.
 *           f(N, K) = f(N-V1, K-1) || f(N-V2, K-1) || ... || f(N-Vx, K-1), assume we have x kinds of stamps.
 *           
 *           The time complexity is O(N*K)
 *           The space complexity is O(???)
 *           
 * Pitfalls: Consider the problem that given a value, how to decide if it can be formed by using no more than K stamps from the stamp set?
 *           Can we approach it in a greedy way? In other words, if a value can be formed by using m stamps (the largest value in the m stamps is v), 
 *           can it be guaranteed to form with less than m stamps by using a stamp whose value is larger than v?
 *           Not necessary!
 *           For example, we have stamps of value 2, 3, 4, 8. We want to form value 9 with at most 3 stamps.
 *           9 = 2 + 3 + 4
 *           If we approach it in a greedy way: we first pick stamp of value 8. Then there is no way to continue. 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 2nd, 2016
 */
public class stamps {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("stamps.in"));
		StringTokenizer inputs = new StringTokenizer(f.readLine());
		int K = Integer.parseInt(inputs.nextToken());
		int N = Integer.parseInt(inputs.nextToken());
		int[] stamps = new int[N];
		int max_stamp_value = 0;
		StringTokenizer stamps_tokens = null;
		for (int i = 0; i < N; i++) {
			if (i % 15 == 0) {
				stamps_tokens = new StringTokenizer(f.readLine());
			}
			stamps[i] = Integer.parseInt(stamps_tokens.nextToken());
			if (stamps[i] > max_stamp_value) {
				max_stamp_value = stamps[i];
			}
		}
		
		boolean[] canForm = new boolean[1 + K * max_stamp_value]; // The max value when can form with K stamps is K * max_stamp_value
		canForm[0] = true;
		Set<Integer> values = new HashSet<Integer>(); // This set contains values that can only be formed with exactly x steps 
		values.add(0);
		// Variable 'x' specifies how many stamps we're using
		for (int x = 1; x <= K; x++) {
			Set<Integer> next_values = new HashSet<Integer>(); // This set contains values that can only be formed with exactly 'x' stamps
			for (int value : values) { // This set contains values that can only be formed with exactly 'x-1' stamps
				for (int stamp_index = 0; stamp_index < N; stamp_index++) {
					int new_value = value + stamps[stamp_index]; 
					if (!canForm[new_value]) { // If new_value can already be formed with smaller than x stamps, there is no need to include new_value in next round calculation since form it with x stamps is not the optimal solution  
						canForm[new_value] = true;
						next_values.add(new_value);
					}
				}
				values = next_values;
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("stamps.out")));
		int i = 0;
		for (; i < canForm.length; i++) {
			if (!canForm[i]) {
				break;
			}
		}
		out.println(i-1);
		out.close();
	}
}
