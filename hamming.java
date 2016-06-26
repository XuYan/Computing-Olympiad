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
TASK: hamming
*/
/**
 * Thoughts: 
 * Pitfalls: The output format requires ten integers per line. Don't forget the case when there is less than 10 numbers left, the last one shouldn't be followed by a space!
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 27th, 2016
 */
public class hamming {
	public int N; // [1,64] - the set of N codewords
	public int B; // [1,8] - B bits length
	public int D; // [1,7] - Hamming distance
	public int upperLimit; // The maximum allowed number in the N codewords
	
	public hamming(int N, int B, int D) {
		this.N = N;
		this.B = B;
		this.D = D;
		this.upperLimit = (int) Math.pow(2, B) - 1;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("hamming.in"));
        StringTokenizer NBD = new StringTokenizer(f.readLine());
        int N = Integer.parseInt(NBD.nextToken());
        int B = Integer.parseInt(NBD.nextToken());
        int D = Integer.parseInt(NBD.nextToken());
        f.close();

        hamming solver = new hamming(N, B, D);
        int[] ans = solver.solve();
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));
		for (int i = 0; i < solver.N; i++) {
			out.print(ans[i] + (((i + 1) % 10 != 0 && i != solver.N - 1) ? " " : "\n"));
		}
		out.close();
	}
	
	private int[] solve() {
		int[] ans = new int[this.N];
		if (this.N == 1) {
			return new int[] {0};
		}
		for (int i = 0; i <= upperLimit - (N - 1); i++) {
			ans[0] = i;
			int id = 1;
			for (int j = i + 1; j <= upperLimit; j++) {
				int k = 0;
				for ( ; k < id; k++) {
					if (this.calcHammingDistance(ans[k], j) < this.D) {
						break;
					}
				}
				if (k == id) {
					ans[id] = j;
					id++;
				}
				if (id == N) {
					return ans;
				}
			}
		}
		return ans;
	}
	
	private int calcHammingDistance(int a, int b) {
		int distance = 0;
		int c = a ^ b;
		while (c != 0) {
			distance += (c & 1);
			c >>= 1;
		}
		return distance;
	}
}
