import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: kimbits
*/
/**
 * Thoughts: 
 *           
 * Pitfalls: Be careful about overflow!!!
 *           
 * Take-away tips: Factorial(n) will increase dramatically as n increases.
 *                 13! is too much to be represented as an int!
 *                 21! is too much to be represented as a long!
 *                 Use BigInteger library to get around the problem
 * 
 * @author Xu Yan
 * @date July 14th, 2016
 */
public class kimbits {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("kimbits.in"));
		StringTokenizer input_tokens = new StringTokenizer(f.readLine());
		int N = Integer.parseInt(input_tokens.nextToken());
		int L = Integer.parseInt(input_tokens.nextToken());
		long I = Long.parseLong(input_tokens.nextToken());
		f.close();
		
		String answer = solve(N, L, I);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
		out.println(answer);
		out.close();
	}
	
	private static String solve(int N, int L, long I) throws Exception {
		if (N == 1) {
			return I == 1 ? "0" : "1";
		}
		String ret = "";
		// We'll put a '1' at position N if putting at most L '1's in position 1-(N-1) is less than I
		long smaller_count = count(N-1, L); // Attention: We need to ensure N-1 > 0. So N == 1 is specially considered!
		if (smaller_count < I) {
			ret = "1" + solve(N-1, L-1, I - smaller_count);
		} else if (smaller_count > I) {
			ret = "0" + solve(N-1, L, I);
		} else { // smaller_count == I
			ret += "0";
			for (int i = 0; i < N - 1; i++) {
				ret += (i < L) ? "1" : "0";
			}
		}
		return ret;
	}
	
	private static long count(int N, int L) throws Exception {
		long ret = 0;
		for (int i = 0; i <= Math.min(N, L); i++) { // Attention: i <= Math.min(N, L)
			ret += combination(N, i);
		}
		return ret;
	}
	
	/**
	 * Calculate C(n,r)
	 */
	private static long combination(int n, int r) throws Exception {
		if (r == 0) {
			return 1;
		}
		r = Math.min(r, n-r);
		BigInteger numerator = new BigInteger("1");
		for (int i = n; i > n - r; i--) {
			numerator = numerator.multiply(new BigInteger(i+""));
		}
		long denominator = factorial(r);
		return numerator.divide(new BigInteger(denominator+"")).longValue();
	}
	
	private static long factorial(int n) {
		long ret = 1;
		while (n > 0) {
			ret *= n;
			n--;
		}
		return ret;
	}
}