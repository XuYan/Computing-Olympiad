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
TASK: sprime
*/
/**
 * Thoughts: DFS and Pruning. Gradually generate numbers of length N in N steps. In each step, check if the current number is a prime.
 *           For a prime number, the left-most digit can be 1,2,3,5,7,9. The other digit can *only* be 1,3,7,9, otherwise it's not a prime definitely!
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 17th,2016
 */
public class sprime {
	private int sprimeDigits;
	private int[] mostSignificantDigitCandidates = new int[6];
	private int[] otherDigitCandidates = new int[4];
	public List<Integer> sprimes;
	
	public sprime(int sprimeDigits) {
		this.sprimeDigits = sprimeDigits;
		this.mostSignificantDigitCandidates = new int[] {1, 2, 3, 5, 7, 9};
		this.otherDigitCandidates = new int[] {1, 3, 7, 9};
		this.sprimes = new ArrayList<Integer>();
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("sprime.in"));
        int superPrimeDigits = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        f.close();
        
        sprime solver = new sprime(superPrimeDigits);
        solver.solve();
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
        for (int sprime : solver.sprimes) {
        	out.println(sprime);
        }
		out.close();
	}
	
	private void solve() {
		int base = 0;
		for (int candidate : this.mostSignificantDigitCandidates) {
			solve_helper(base * 10 + candidate, 1);
		}
	}
	
	/**
	 * DFS helper to generate prime number of a given length step by step
	 * @param num generated number of length N, where N <= expected length
	 * @param numLength expected length
	 */
	private void solve_helper(int num, int numLength) {
		if (!this.isPrime(num)) {
			return; // Early termination. Any number constructed based on this number will not be a super prime
		}
		if (numLength == this.sprimeDigits) {
			this.sprimes.add(num);
			return;
		}
		for (int candidate : this.otherDigitCandidates) {
			solve_helper(num * 10 + candidate, numLength + 1);
		}
	}
	
	/**
	 * Returns true if the given number is a prime
	 * @param num the testee
	 * @return true if testee is a prime number
	 */
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
}
