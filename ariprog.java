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
TASK: ariprog
*/
/**
 * Thoughts: 
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 10th,2016
 */
public class ariprog {
	private static class Result {
		int base;
		int diff;
		
		public Result(int base, int diff) {
			this.base = base;
			this.diff = diff;
		}
	}
	
	public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("ariprog.in"));
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken()); // The length of required progressions
        int M = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken()); // The search upper bound
        f.close();

        ariprog solver = new ariprog();
        boolean[] isBiSquare = new boolean[2 * M * M + 1];
		for (int i = 0; i <= M; i++) {
			for (int j = i; j <= M; j++) {
				isBiSquare[i * i + j * j] = true;
			}
		}
		List<Result> results = solver.solve(isBiSquare, M, N);
		Collections.sort(results, new Comparator<Result>() {
			@Override
			public int compare(Result o1, Result o2) {
				return o1.diff != o2.diff ? (o1.diff - o2.diff) : (o1.base - o2.base);
			}
		});
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
		if (results.isEmpty()) {
			out.println("NONE");
		} else {
			for (Result r : results) {
				out.println(r.base + " " + r.diff);
			}			
		}
		
		out.close();
    }
	
	private List<Result> solve(boolean[] isBiSquare, int M, int N) {
		List<Result> results = new ArrayList<Result>();
		for (int i = 0; i < isBiSquare.length; i++) {
			if (isBiSquare[i]) {
				for (int j = i + 1; i + (N-1) * (j-i) <= 2 * M * M; j++) {
					if (isBiSquare[j]) {
						if (search(isBiSquare, i, j - i, N)) {
							results.add(new Result(i, j - i));
						}
					}
				}
			}
		}
		return results;
	}
	
	private boolean search(boolean[] isBiSquare, int i, int diff, int N) {
		for (int k = 1; k < N; k++) {
			if (!isBiSquare[i + k * diff]) {
				return false;
			}
		}
		return true;
	}
}
