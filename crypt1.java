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
TASK: crypt1
*/
/**
 * Thoughts: Since there are at most 9^5 possible combinations. I decided to brute-force it. 
 * Pitfalls: A solution must satisfy that partial product 1 has three digits and partial product 2 has three digits as well.
 *           Product result must have exactly four digits. 
 * Take-away tips: The scale of input decides what algorithm to take.
 * 
 * @author Xu Yan
 * @date May 3rd,2016
 */
public class crypt1 {
	public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("crypt1.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
        
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        boolean[] isValidDigit = new boolean[10];
        int[] digits = new int[N];
        int index = 0;
        StringTokenizer digitTokenizer = new StringTokenizer(f.readLine());
        while (digitTokenizer.hasMoreTokens()) {
        	int digit = Integer.parseInt(digitTokenizer.nextToken());
        	isValidDigit[digit] = true;
        	digits[index++] = digit; 
        }
        
        crypt1 crypt = new crypt1();
        
        out.println(crypt.solve(digits, isValidDigit));
        
        f.close();
        out.close();
    }
	
	/**
	 * Solving the cryptarithm
	 * @param digits input array containing N input digits
	 * @param isValidDigit array of size 10 that can give us constant time answer of whether a digit is in the given input digits
	 * @return the number of solutions to the cryptarithm
	 */
	private int solve(int[] digits, boolean[] isValidDigit) {
		int ans = 0;
		for (int i = 0; i < digits.length; i++) {
			for (int j = 0; j < digits.length; j++) {
				for (int k = 0; k < digits.length; k++) {
					int multiplicand = digits[i] * 100 + digits[j] * 10 + digits[k];
					for (int m = 0; m < digits.length; m++) {
						if (canConstruct(multiplicand * digits[m], isValidDigit, 100)) {
							for (int n = 0; n < digits.length; n++) {
								if (canConstruct(multiplicand * digits[n], isValidDigit, 100)) {
									int multiplier = digits[m] * 10 + digits[n];
									if (canConstruct(multiplicand * multiplier, isValidDigit, 1000)) {
										System.out.println(digits[i] + " " + digits[j] + " " + digits[k] + " " + digits[m] + " " + digits[n]);
										ans++;
									}	
								}
							}
						}
					}
				}
			}
		}
		return ans;
	}
	
	/**
	 * Returns true if result and magnitude are of the same order of magnitude and all the digits in result are in input set
	 * @param result multiplication result
	 * @param isValidDigit contains digits from input set
	 * @param magnitude the expected magnitude of multiplication result
	 * @return true if result can be constructed with above constraints
	 */
	private boolean canConstruct(int result, boolean[] isValidDigit, int magnitude) {
		if (result / magnitude < 1 || result / magnitude > 9) {
			return false;
		}
		while (result != 0) {
			if (!isValidDigit[result % 10]) {
				return false;
			}
			result /= 10;
		}
		return true;
	}
}
