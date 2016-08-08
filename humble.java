import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
 ID: Xu Yan
 LANG: JAVA
 TASK: humble
 */
/**
 * Thoughts: Observations: Suppose we have a prime list {a,b,c,...}, where a < b < c < ...
 *           1. Each humble number m must be the minimum of the numbers constructed by some humble number a * h (h < m), b * i (i < m), c * j (j < m)
 *           2. Given a humble number m, m*a must appear before m*b, and m*b must appear before m*c, etc.
 * 
 * Pitfalls: 
 *           
 * Take-away tips:
 * 
 * @author Xu Yan
 * @date July 4th, 2016
 */
public class humble {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("humble.in"));
		StringTokenizer inputs = new StringTokenizer(f.readLine());
		int K = Integer.parseInt(inputs.nextToken());
		int N = Integer.parseInt(inputs.nextToken());
		StringTokenizer primeTokens = new StringTokenizer(f.readLine());
		int[] primeSet = new int[K];
		for (int i = 0; i < K; i++) {
			primeSet[i] = Integer.parseInt(primeTokens.nextToken());
		}
		f.close();

		long[] humble_numbers = new long[N+1];
		humble_numbers[0] = 1;
		int[] prime_index = new int[K];
		for (int i = 1; i <= N; i++) {
			long min_humble_candidate = Long.MAX_VALUE;
			int min_prime_index = -1;
			for (int j = 0; j < K; j++) {
				while (primeSet[j] * humble_numbers[prime_index[j]] <= humble_numbers[i-1]) {
					prime_index[j] ++;
				}
				long humble_candidate = primeSet[j] * humble_numbers[prime_index[j]];
				if (humble_candidate < min_humble_candidate) {
					min_humble_candidate = humble_candidate;
					min_prime_index = j;
				}
			}
			humble_numbers[i] = min_humble_candidate;
			prime_index[min_prime_index] ++;
		}

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("humble.out")));
		out.println(humble_numbers[N]);
		out.close();
	}
}
