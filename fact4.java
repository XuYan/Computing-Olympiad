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
TASK: fact4
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 14th, 2016
 */
public class fact4 {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("fact4.in"));
		int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
		f.close();
		
		int five_factor_count = 0;
		for (int i = N; i >= 1; i--) {
			int n = i;
			while (5 * (n/5) == n) {
				n /= 5;
				five_factor_count++;
			}
		}
		
		int answer = 1;
		int twos = five_factor_count;
		int fives = five_factor_count;
		for (int i = N; i >= 1; i--) {
			int n = i;
			while (5 * (n/5) == n && fives > 0) {
				n /= 5;
				fives --;
			}
			while (2 * (n/2) == n && twos > 0) {
				n /= 2;
				twos --;
			}
			answer = (answer * n) % 10;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fact4.out")));
		out.println(answer);
		out.close();
	}
}
