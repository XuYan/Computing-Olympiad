import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: pprime
*/
/**
 * Thoughts: Brute-force generation of palindromes with different length and check if they're prime numbers.
 *           
 * Pitfalls: 
 *           
 * Take-away tips: Think about how to generate the next palindrome number given any number? 
 * 
 * @author Xu Yan
 * @date May 14th,2016
 */
public class pprime {
	int lowerBound, upperBound;
	
	public pprime(int lowerBound, int upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("pprime.in"));
        StringTokenizer ab = new StringTokenizer(f.readLine());
        int a = Integer.parseInt(ab.nextToken());
        int b = Integer.parseInt(ab.nextToken());
        f.close();

        pprime solver = new pprime(a, b);
        List<Integer> ans = solver.solve();
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
		for (int i : ans) {
			out.println(i);
		}
		out.close();
	}
	
	private List<Integer> solve() {
		List<Integer> pp = new ArrayList<Integer>();
		calculateOneDigitPrimePalindrome(pp);
		calculateTwoDigitPrimePalindrome(pp);
		calculateThreeDigitPrimePalindrome(pp);
		calculateFourDigitPrimePalindrome(pp);
		calculateFiveDigitPrimePalindrome(pp);
		calculateSixDigitPrimePalindrome(pp);
		calculateSevenDigitPrimePalindrome(pp);
		calculateEightDigitPrimePalindrome(pp);
		return pp;
	}
	
	private boolean isValidPrimePalindrome(int palindrome) {
		return palindrome >= this.lowerBound && palindrome <= this.upperBound && this.isPrime(palindrome);
	}
	
	private boolean isPrime(int num) {
		if (num == 1) {
			return false;
		}
		for (int i = 2; i * i <= num; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	private void calculateOneDigitPrimePalindrome(List<Integer> pp) {
		if (lowerBound <= 5 && 5 <= upperBound) {
			pp.add(5);
		}
		if (lowerBound <= 7 && 7 <= upperBound) {
			pp.add(7);
		}
	}
	
	private void calculateTwoDigitPrimePalindrome(List<Integer> pp) {
		if (99 <= lowerBound) {
			return;
		}
		for (int i = 1; i <= 9; i+=2) {
			int palindrome = i * 10 + i;
			if (palindrome > upperBound) {
				return;
			}
			if (isValidPrimePalindrome(palindrome)) {
				pp.add(palindrome);
			}
		}
	}
	
	private void calculateThreeDigitPrimePalindrome(List<Integer> pp) {
		if (999 <= lowerBound) {
			return;
		}
		for (int i = 1; i <= 9; i+=2) {
			for (int j = 0; j <= 9; j++) {
				int palindrome = i * 100 + j * 10 + i;
				if (palindrome > upperBound) {
					return;
				}
				if (isValidPrimePalindrome(palindrome)) {
					pp.add(palindrome);
				}
			}
		}
	}
	
	private void calculateFourDigitPrimePalindrome(List<Integer> pp) {
		if (9999 <= lowerBound) {
			return;
		}
		for (int i = 1; i <= 9; i+=2) {
			for (int j = 0; j <= 9; j++) {
				int palindrome = i * 1000 + j * 100 + j * 10 + i;
				if (palindrome > upperBound) {
					return;
				}
				if (isValidPrimePalindrome(palindrome)) {
					pp.add(palindrome);
				}
			}
		}
	}
	
	private void calculateFiveDigitPrimePalindrome(List<Integer> pp) {
		if (99999 <= lowerBound) {
			return;
		}
		for (int i = 1; i <= 9; i+=2) {
			for (int j = 0; j <= 9; j++) {
				for (int k = 0; k <= 9; k++) {
					int palindrome = i * 10000 + j * 1000 + k * 100 + j * 10 + i;
					if (palindrome > upperBound) {
						return;
					}
					if (isValidPrimePalindrome(palindrome)) {
						pp.add(palindrome);
					}
				}
			}
		}
	}
	
	private void calculateSixDigitPrimePalindrome(List<Integer> pp) {
		if (999999 <= lowerBound) {
			return;
		}
		for (int i = 1; i <= 9; i+=2) {
			for (int j = 0; j <= 9; j++) {
				for (int k = 0; k <= 9; k++) {
					int palindrome = i * 100000 + j * 10000 + k * 1000 + k * 100 + j * 10 + i;
					if (palindrome > upperBound) {
						return;
					}
					if (isValidPrimePalindrome(palindrome)) {
						pp.add(palindrome);
					}
				}
			}
		}
	}
	
	private void calculateSevenDigitPrimePalindrome(List<Integer> pp) {
		if (9999999 <= lowerBound) {
			return;
		}
		for (int i = 1; i <= 9; i+=2) {
			for (int j = 0; j <= 9; j++) {
				for (int k = 0; k <= 9; k++) {
					for (int m = 0; m <= 9; m++) {
						int palindrome = i * 1000000 + j * 100000 + k * 10000 + m * 1000 + k * 100 + j * 10 + i * 1;
						if (palindrome > upperBound) {
							return;
						}
						if (isValidPrimePalindrome(palindrome)) {
							pp.add(palindrome);
						}
					}
				}
			}
		}
	}
	
	private void calculateEightDigitPrimePalindrome(List<Integer> pp) {
		if (99999999 <= lowerBound) {
			return;
		}
		for (int i = 1; i <= 9; i+=2) {
			for (int j = 0; j <= 9; j++) {
				for (int k = 0; k <= 9; k++) {
					for (int m = 0; m <= 9; m++) {
						int palindrome = i * 10000000 + j * 1000000 + k * 100000 + m * 10000 + m * 1000 + k * 100 + j * 10 + i * 1;
						if (palindrome > upperBound) {
							return;
						}
						if (isValidPrimePalindrome(palindrome)) {
							pp.add(palindrome);
						}
					}
				}
			}
		}
	}
}
