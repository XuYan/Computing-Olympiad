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
TASK: runround
*/
/**
 * Thoughts: This algorithm has time complexity of 9!
 * Pitfalls: 
 * Take-away tips:
 * 
 * @author Xu Yan
 * @date May 31st, 2016
 */
public class runround {
	public int M;
	public runround(int M) {
		this.M = M;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("runround.in"));
        int M = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        f.close();
        
        runround solver = new runround(M);
       	int nextRunRoundNumber = solver.solve();
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("runround.out")));
		out.println(nextRunRoundNumber);
		out.close();
	}
	
	private int solve() {
		int inputDigitCount = 0;
		int input = this.M;
		while (input > 0) {
			inputDigitCount += 1;
			input /= 10;
		}
		
		int ans = -1;
		for (int numberDigitCount = inputDigitCount; numberDigitCount <= 9; numberDigitCount++) {
			ans = solve_helper(numberDigitCount, 0, 0);
			if (ans != -1) {
				break;
			}
		}
		return ans;
	}
	
	private int solve_helper(int digitCount, int usedDigit, int currentNumber) {
		if (digitCount == 0) {
			return (currentNumber > this.M && isRunRound(currentNumber)) ? currentNumber : -1;
		}
		int ans = -1;
		for (int i = 1; i <= 9; i++) {
			if ((usedDigit & (1 << i)) == 0) {
				ans = solve_helper(digitCount - 1, usedDigit | (1 << i), currentNumber * 10 + i);
				if (ans != -1) {
					break;
				}
			}
		}
		return ans;
	}
	
	private boolean isRunRound(int number) {
		String numStr = number + "";
		int visitedDigit = 0;
		int currentIndex = 0;
		int visitedDigitCount = 0;
		char startDigit = numStr.charAt(0);
		
		int offset = numStr.charAt(currentIndex)-'0';
		int mask = 1 << offset;
		while ((visitedDigit & mask) == 0) {
			visitedDigit |= mask;
			visitedDigitCount += 1;
			currentIndex = (currentIndex + offset) % numStr.length();
			offset = numStr.charAt(currentIndex)-'0';
			mask = 1 << offset;
		}
		return visitedDigitCount == numStr.length() && startDigit == numStr.charAt(currentIndex);
	}
}
