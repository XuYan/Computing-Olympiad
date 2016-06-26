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
TASK: subset
*/
/**
 * Thoughts: This is a O(n^3) algorithm since we're trying to populate the sub-problem results in a N by N*(N-1)/4 matrix
 * Pitfalls: 
 * Take-away tips:
 * 
 * @author Xu Yan
 * @date May 30th, 2016
 */
public class subset {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("subset.in"));
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        f.close();
        
        subset solver = new subset();
        int ans = 0;
        int totalSum = N * (N+1) / 2;
        if (totalSum % 2 == 0) {
        	ans = solver.solve(N);
        }
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));
		out.println(ans);
		out.close();
	}
	
	private int solve(int N) {
		int targetSum = N * (N+1) / 4;
		int[][] ans = new int[1+targetSum][1+N];
		ans[0][0] = 1;
		for (int i = 1; i <= targetSum; i++) {
			for (int j = 1; j <= N; j++) {
				ans[i][j] += ans[i][j-1];
				if (i-j >= 0) {
					ans[i][j] += ans[i-j][j-1];
				}
			}
		}
		return ans[targetSum][N];
	}
}
