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
TASK: combo
*/
/**
 * Thoughts: Consider the intersection of two collections A and B. If A and B are partially overlapped, then A U B = A + B - A^B
 *           But for circular lock, the scenario is more complicated.
 *           E.g.      1
 *               0           2
 *
 *               2           0
 *                     1
 *               If John's lock is at 2, then its tolerate error interval is [0, 1, 2, 0, 1]
 *               If Master's lock is at 1, then its tolerate error interval is [2, 0, 1, 2, 0]
 *               So the two intervals intersect at two sub intervals: [0] and [2, 0, 1].
 *           Since the three dials are independent, I think we can solve the problem by finding out the intersection interval of each dial. Multiply the length of the three 
 *           intersected interval length and subtract the result from the total maximum possible solutions 5*5*5 + 5*5*5 = 250 (No intersection at all)
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 7th,2016
 */
public class combo {
	public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("combo.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));
        
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        StringTokenizer johnCombination = new StringTokenizer(f.readLine());
        int[] johnCombo = {Integer.parseInt(johnCombination.nextToken()), Integer.parseInt(johnCombination.nextToken()), Integer.parseInt(johnCombination.nextToken())};
        StringTokenizer masterCombination = new StringTokenizer(f.readLine());
        int[] masterCombo = {Integer.parseInt(masterCombination.nextToken()), Integer.parseInt(masterCombination.nextToken()), Integer.parseInt(masterCombination.nextToken())};
        
        combo solver = new combo();
        int ans = solver.solve(johnCombo, masterCombo, N);
        out.println(ans);
        
        f.close();
        out.close();
    }
	
	/**
	 * 
	 * @param johnCombo
	 * @param masterCombo
	 * @return
	 */
	private int solve(int[] johnCombo, int[] masterCombo, int N) {
		if ( N <= 5) {
			return N * N * N;
		}
		int maximumSolutions = 250;
		int intersection = 1;
		for (int i = 0; i <= 2; i++) {
			intersection *= calcIntersectionLength(N, calcInterval(johnCombo[i], N), calcInterval(masterCombo[i], N));
		}
		return maximumSolutions - intersection;
	}
	
	private int[] calcInterval(int middlePoint, int N) {
		int start = middlePoint - 2;
		int adjustedStart = start > 0 ? start : N + start; // N + start, not N - start!!!
		
		int end = middlePoint + 2;
		int adjustedEnd = end <= N ? end : (end % N);
		
		return new int[] {adjustedStart, adjustedEnd};
	}
	
	private int calcIntersectionLength(int N, int[] i, int[] j) {
		if (i[0] <= i[1] && j[0] <= j[1]) {
			int[] intersection = {Math.max(i[0], j[0]), Math.min(i[1], j[1])}; // Be careful about invalid intervals where end < start
			return (intersection[1] >= intersection[0]) ? (intersection[1] - intersection[0] + 1) : 0;
		} else if (i[0] >= i[1] && j[0] >= j[1]) {
			return (N - Math.max(i[0], j[0]) + 1) + Math.min(i[1], j[1]);
		} else {
			if (i[0] > i[1]) {
				return calcIntersectionLength(N, new int[] {i[0], N},  j) + calcIntersectionLength(N, new int[] {1, i[1]}, j);
			} else {
				return calcIntersectionLength(N, i, new int[] {j[0], N}) + calcIntersectionLength(N, i, new int[] {1, j[1]});
			}
		}
	}
}
