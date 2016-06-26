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
TASK: preface
*/
/**
 * Thoughts: 
 *           
 * Pitfalls: 
 *           
 * Take-away tips: Cannot define dimension expressions when an array initializer is provided!
 * 
 * @author Xu Yan
 * @date May 29th, 2016
 */
public class preface {
	public int[] solve(int N) {
		int[] ans = new int[7];
		for (int num = 1; num <= N; num++) {
			int i = num;
			while (i > 0) {
				if (i >= 1000 /* M */) {
					ans[6] += 1;
					i -= 1000;
				} else if (i >= 900 /* CM */) {
					ans[4] += 1;
					ans[6] += 1;
					i -= 900;
				} else if (i >= 500 /* D */) {
					ans[5] += 1;
					i -= 500;
				} else if (i >= 400 /* CD */) {
					ans[4] += 1;
					ans[5] += 1;
					i -= 400;
				} else if (i >= 100 /* C */) {
					ans[4] += 1;
					i -= 100;
				} else if (i >= 90 /* XC */) {
					ans[2] += 1;
					ans[4] += 1;
					i -= 90;
				} else if (i >= 50 /* L */) {
					ans[3] += 1;
					i -= 50;
				} else if (i >= 40 /* XL */) {
					ans[2] += 1;
					ans[3] += 1;
					i -= 40;
				} else if (i >= 10 /* X */) {
					ans[2] += 1;
					i -= 10;
				} else if (i >= 9 /* IX */) {
					ans[2] += 1;
					ans[0] += 1;
					i -= 9;
				} else if (i >= 5 /* V */) {
					ans[1] += 1;
					i -= 5;
				} else if (i >= 4 /* IV */) {
					ans[0] += 1;
					ans[1] += 1;
					i -= 4;
				} else if (i >= 1 /* I */) {
					ans[0] += 1;
					i -=  1;
				}
			}
		}
		return ans;
//		int[] accumulated_I = new int[] {0,1,3,6,7,7,8,10,13,14};
//		int[] accumulated_V = new int[] {0,0,0,0,1,2,3,4,5,5};
//		
//		int[] ans = new int[7];
//		int quotient = N / 10;
//		ans[0] = quotient * accumulated_I[9];
//		ans[1] = quotient * accumulated_V[9];
//
//		int reminder = N % 10;
//		if (reminder != 0) {
//			ans[0] += accumulated_I[reminder];
//			ans[1] += accumulated_V[reminder];
//		}
//		return ans;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("preface.in"));
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        f.close();
        
        preface solver = new preface();
        int[] ans = solver.solve(N);
        
        char[] romanNumerals = new char[] {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("preface.out")));
		for (int i = 0; i < romanNumerals.length; i++) {
			if (ans[i] != 0) {
				out.println(romanNumerals[i] + " " + ans[i]);
			}
		}
		out.close();
	}
}