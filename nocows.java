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
TASK: nocows
*/
/**
 * Thoughts: 
 *           
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date June 11th, 2016
 */
public class nocows {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("nocows.in"));
		StringTokenizer input = new StringTokenizer(f.readLine());
		int N = Integer.parseInt(input.nextToken());
		int K = Integer.parseInt(input.nextToken());
		f.close();
		
		int[][] memorization = new int[N+1][K+1];
		for (int i = 0; i < memorization.length; i++) {
			for (int j = 0; j < memorization[0].length; j++) {
				memorization[i][j] = -1;
			}
		}
		memorization[1][1] = 1; // Don't miss this base case!
		
		int ans = (N % 2 == 0) ? 0 : solve(N, K, memorization);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));
		out.println(ans);
		out.close();
	}
	
	private static int solve(int N, int K, int[][] memorization) {
		if (memorization[N][K] != -1) {
			return memorization[N][K];
		}
		if (N < 2*K-1) {
			memorization[N][K] = 0;
			return 0;
		}
		
		if (N > Math.pow(2, K) - 1) {
			memorization[N][K] = 0;
			return 0;
		}
		
		int ans = 0;
		for (int i = 1; i < N-1; i += 2) {
			int left_subtree_ans = solve(i, K-1, memorization) % 9901;
			if (left_subtree_ans != 0) {
				for (int right_subtree_height = K-1; right_subtree_height >= 1; right_subtree_height--) {
					int right_subtree_ans = solve(N-1-i, right_subtree_height, memorization);
					if (right_subtree_ans != 0) {
						if (K - 1 != right_subtree_height) {
							ans = (ans + 2 * (left_subtree_ans * right_subtree_ans)) % 9901;	
						} else {
							ans = (ans + left_subtree_ans * right_subtree_ans) % 9901;
						}
					}
				}
			}
		}
		
		memorization[N][K] = ans;
		return ans;
	}
}
