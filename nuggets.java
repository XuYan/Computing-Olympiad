import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/*
ID: Xu Yan
LANG: JAVA
TASK: nuggets
*/
/**
 * Thoughts: This problem uses knowledge about "Number Theory: Forbenius number".
 *           Given two natural numbers, is there a largest number that cannot be formed by a linear combination of
 *           the two numbers(The coefficients are natural numbers).
 *           For example, w0,w1,a,b are natural numbers. What is the largest number that cannot be formed by
 *           a*w0 + b*w1?
 *           The answer is when w0 and w1 are not coprime (gcd(w0,w1) != 1), the largest number doesn't exist.
 *           Otherwise, the largest number is w0 * w1 - (w0 + w1).
 *           This program extends the theory from two numbers to multiple numbers. 
 * Pitfalls: Be careful since I'm reusing the representable array in the period of 256, to avoid interference,
 *           @line 50, I cannot use representable[i%256] since it can be true from the beginning!
 *           Use a boolean state initialized to false and set its value to representable[i%256] after all processing.
 *           
 * Take-away tips:
 * 
 * @author Xu Yan
 * @date August 4th, 2016
 */
public class nuggets {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("nuggets.in"));
		int N = Integer.parseInt(f.readLine()); // # of packaging options
		int[] nuggets_in_box = new int[N];
		for (int i = 0; i < N; i++) {
			nuggets_in_box[i] = Integer.parseInt(f.readLine());
		}
		f.close();
		Arrays.sort(nuggets_in_box);
		
		int answer = 0; // The largest number of nuggets that cannot be represented
		boolean[] representable = new boolean[256];
		representable[0] = true;
		if (gcd(nuggets_in_box) == 1) {
			for (int i = 1; i < 2000000000; i++) { // 'i' is the current target number of nuggets we want to buy
				if (i - nuggets_in_box[N-1] > answer) {
					break; // Early termination: [i, ...) must be representable!
				}
				boolean state = false;
				for (int j = N - 1; j >= 0; j--) { // 'j' is the index of package
					int nuggets = nuggets_in_box[j]; // "nuggets" in the number of nuggets in package 'j'
					int prev = i - nuggets; // we can get 'i' nuggets if we could get "prev" nuggets, where prev < i
					if (prev >= 0) {
						 // Cannot replace state with representable[i%256] since it can be true from beginning!!!
						state |= representable[prev%256];
						if (state) {
							break;
						}
					}
				}
				representable[i%256] = state;
				if (!representable[i%256]) {
					answer = i;
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nuggets.out")));
		out.println(answer);
		out.close();
	}
	
	/**
	 * Find the greatest common divisor of numbers in the given array 
	 */
	private static int gcd(int[] nuggets_in_box) {
		if (nuggets_in_box.length == 1) {
			return nuggets_in_box[0];
		}
		int prev = nuggets_in_box[0];
		for (int i = 1; i < nuggets_in_box.length; i++) {
			int current = nuggets_in_box[i];
			prev = gcd(prev, current);
		}
		
		return prev;
	}
	
	/**
	 * Use Euclid's algorithm to compute the greate common divisor of two numbers 
	 */
	private static int gcd(int smaller, int larger) {
		return (smaller == 0) ? larger : gcd(larger % smaller, smaller);
	}
}
