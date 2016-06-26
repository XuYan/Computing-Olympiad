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
TASK: zerosum
*/
/**
 * Thoughts: 
 *           
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date June 15th, 2016
 */
public class zerosum {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("zerosum.in"));
		int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
		f.close();

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("zerosum.out")));
		solve(0, 1, 2, N, "1", out);
		out.close();
	}
	
	private static void solve(int currentSum, int currentNum, int nextDigit, int N, String operations, PrintWriter out) {
		if (nextDigit > N) {
			if (currentSum + currentNum == 0) {
				out.println(operations);
			}
			return;
		}
		
		// Next digit will be concatenated with the current number
		if (currentNum > 0) {
			solve(currentSum, currentNum * 10 + nextDigit, nextDigit + 1, N, operations + " " + nextDigit, out);	
		} else {
			solve(currentSum, currentNum * 10 - nextDigit, nextDigit + 1, N, operations + " " + nextDigit, out);
		}
		
		
		// Next digit will be digit of a new number
		solve(currentSum + currentNum, nextDigit, nextDigit + 1, N, operations + "+" + nextDigit, out);
		solve(currentSum + currentNum, -nextDigit, nextDigit + 1, N, operations + "-" + nextDigit, out);
	}
}
