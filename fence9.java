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
TASK: fence9
*/
/**
 * Thoughts: I approach the problem with analytical geometry analysis. An alternative approach is Pick's theorem!
 * Pitfalls: 
 *           
 * Take-away tips: "Double" is not precise! Whenever you have to use it, give it a small enough error tolerance.
 * 
 * @author Xu Yan
 * @date July 31st, 2016
 */
public class fence9 {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("fence9.in"));
		StringTokenizer input_tokens = new StringTokenizer(f.readLine());
		int n = Integer.parseInt(input_tokens.nextToken());
		int m = Integer.parseInt(input_tokens.nextToken());
		int p = Integer.parseInt(input_tokens.nextToken());
		f.close();
		
		int answer = 0;
		if (n == 0) {
			for (int i = 1; i < p; i++) {
				answer += adjust((1.0 * m / (n-p)) * i + (1.0 * m * p / (p - n)), false);
			}
		} else if (n < p) {
			for (int i = 1; i <= n; i++) {
				answer += adjust((1.0 * m / n) * i, false);
			}
			for (int i = n + 1; i < p; i++) {
				answer += adjust((1.0 * m / (n-p)) * i + (1.0 * m * p / (p - n)), false);
			}
		} else if (n == p) {
			for (int i = 1; i < n; i++) {
				answer += adjust((1.0 * m / n) * i, false);
			}
		} else { /*n > p*/
			for (int i = 1; i < n; i++) { // Be careful about the boundary here! (i < n, not i <= n)
				answer += adjust((1.0 * m / n) * i, false);
			}
			for (int i = p + 1; i < n; i++) {
				answer -= adjust((1.0 * m / (n-p)) * i + (1.0 * m * p / (p - n)), true);
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence9.out")));
		out.println(answer);
		out.close();
	}
	
	private static int adjust(double y, boolean include_point_on_line) {
		double tolerance = 0.00000001;
		int ceil_y = (int) Math.ceil(y);
		int floor_y = (int) Math.floor(y);
		
		if (ceil_y - y < tolerance) {
			return include_point_on_line ? ceil_y : ceil_y - 1;
		} else if (y - floor_y < tolerance) {
			return include_point_on_line ? floor_y : floor_y - 1;
		}
		
		return (int) Math.floor(y);
	}
}
