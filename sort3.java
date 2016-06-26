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
TASK: sort3
*/
/**
 * Thoughts: 
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 22nd, 2016
 */
public class sort3 {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("sort3.in"));
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        
        int[] sequence = new int[N];
        int[] numCount = {0, 0, 0};
        for (int i = 0; i < N; i++) {
        	sequence[i] = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        	numCount[sequence[i] - 1] += 1;
        }
        f.close();
        
        int[] a = {0, 0, 0};
        for (int i = 0; i < numCount[0]; i++) {
        	a[sequence[i]-1] += 1;
        }
        
        int[] b = {0, 0, 0};
        for (int i = numCount[0]; i < numCount[0] + numCount[1]; i++) {
        	b[sequence[i]-1] += 1;
        }
        
        int[] c = {0, 0, 0};
        for (int i = numCount[0] + numCount[1]; i < N; i++) {
        	c[sequence[i]-1] += 1;
        }
        
        int ans = 0;
        int swapPair = 0;
        // 1 <-> 2
        swapPair = Math.min(a[1], b[0]);
        ans += swapPair;
        a[1] -= swapPair;
        b[0] -= swapPair;
        
        // 2 <-> 3
        swapPair = Math.min(b[2], c[1]);
        ans += swapPair;
        b[2] -= swapPair;
        c[1] -= swapPair;

        // 1 <-> 3
        swapPair = Math.min(a[2], c[0]);
        ans += swapPair;
        a[2] -= swapPair;
        c[0] -= swapPair;

        ans += 2 * (a[1] + a[2]);
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
		out.println(ans);
		out.close();
	}
}
