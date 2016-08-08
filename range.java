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
TASK: range
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 26th, 2016
 */
public class range {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("range.in"));
		StringTokenizer input_tokens = new StringTokenizer(f.readLine());
		int miles = Integer.parseInt(input_tokens.nextToken()); // The number of miles on a side

		boolean[][] field = new boolean[miles][miles];
		int[][] row_dp = new int[miles][miles];
		int[][] col_dp = new int[miles][miles];
		for (int row = 0; row < miles; row++) {
			String rowInfo = f.readLine();
			for (int col = 0; col < miles; col++) {
				field[row][col] = rowInfo.charAt(col) == '1';
				if (field[row][col]) {
					row_dp[row][col] = (col == 0) ? 1 : (row_dp[row][col-1] + 1);
					col_dp[row][col] = (row == 0) ? 1 : (col_dp[row-1][col] + 1);
				} else {
					row_dp[row][col] = 0;
					col_dp[row][col] = 0;
				}
			}
		}
		f.close();

		int[] answer = new int[251];
		int[][] field_dp = new int[miles][miles];
		for (int row = 0; row < miles; row++) {
			field_dp[row][0] = field[row][0] ? 1 : 0;
		}
		for (int col = 0; col < miles; col++) {
			field_dp[0][col] = field[0][col] ? 1 : 0;
		}
		for (int row = 1; row < miles; row++) {
			for (int col = 1; col < miles; col++) {
				if (field[row][col]) {
					int topLeft = field_dp[row-1][col-1];
					int left = row_dp[row][col];
					int top = col_dp[row][col];
					int minSize = Math.min(topLeft+1, Math.min(left, top));
					field_dp[row][col] = minSize;
					for (int size = 2; size <= minSize; size++) {
						answer[size] ++;						
					}
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("range.out")));
		for (int i = 2; i <= miles; i++) {
			if (answer[i] != 0) {
				out.println(i + " " + answer[i]);	
			}
				
		}
		out.close();
	}
}
