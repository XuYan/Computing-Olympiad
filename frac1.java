import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: frac1
*/
/**
 * Thoughts: 
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 28th, 2016
 */
public class frac1 {
	public int N;
	private List<Fraction> ans;
	
	private class Fraction {
		public int numerator;
		public int denominator;
		
		public Fraction(int numerator, int denominator) {
			this.numerator = numerator;
			this.denominator = denominator;
		}
	}
	
	public frac1(int N) {
		this.N = N;
		this.ans = new ArrayList<Fraction>();
	}
	
	public static void main(String[] args) throws IOException {
//		Queue<Fraction> ans = new PriorityQueue<Fraction>(new Comparator<Fraction>() {
//			@Override
//			public int compare(Fraction frac1, Fraction frac2) {
//				System.out.println(frac1.numerator * frac2.denominator - frac1.denominator * frac2.numerator);
//				return frac1.numerator * frac2.denominator - frac1.denominator * frac2.numerator;
//			}
//		});
		
//		frac1 f = new frac1(1);
//		ans.add(f.new Fraction(1, 2));
//		ans.add(f.new Fraction(1, 5));
//		ans.add(f.new Fraction(1, 4));
//		Collections.sort(ans, new Comparator<Fraction>() {
//			@Override
//			public int compare(Fraction frac1, Fraction frac2) {
//				System.out.println(frac1.numerator * frac2.denominator - frac1.denominator * frac2.numerator);
//				return frac1.numerator * frac2.denominator - frac1.denominator * frac2.numerator;
//			}
//		});

		BufferedReader f = new BufferedReader(new FileReader("frac1.in"));
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        f.close();

        frac1 solver = new frac1(N);
        solver.solve();
        Collections.sort(solver.ans, new Comparator<Fraction>() {
			@Override
			public int compare(Fraction frac1, Fraction frac2) {
				return frac1.numerator * frac2.denominator - frac1.denominator * frac2.numerator;
			}
		});
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("frac1.out")));
		for (Fraction frac : solver.ans) {
			out.println(frac.numerator + "/" + frac.denominator);
		}
		out.close();
	}
	
	/**
	 * Enumerate all the possibilities of numerator and denominator. If a reduced fraction is found, put it into result list
	 */
	private void solve() {
		this.ans.add(new Fraction(0, 1));
		this.ans.add(new Fraction(1, 1));
		
		for (int n = 1; n <= this.N - 1; n++) {
			for (int d = n + 1; d <= this.N; d++) {
				if (isCoPrime(n, d)) {
					this.ans.add(new Fraction(n, d));
				}
			}
		}
	}
	
	/**
	 * @return true if a and b are co-prime (Flounder division algorithm: Their greatest common divisor is 1)
	 */
	private boolean isCoPrime(int a, int b) {
		while (a != b && a > 1 && b > 1) {
			int larger = a > b ? a : b;
			int smaller = a < b ? a : b;
			a = smaller;
			b = larger - smaller; 
		}
		
		return a == 1 || b == 1;
	}
}
