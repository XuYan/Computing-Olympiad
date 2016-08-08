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
TASK: rockers
*/
/**
 * Thoughts: I want to approach the problem with dynamic programming.
 *           Some observations:
 *           For a song, we either record it or not record it.
 *           If we don't record it, the number of songs recorded so far is the same as previous state.
 *           If we record it, we either record it in current disk or record it in a new disk. 
 *           So I think the state is decided by 
 *           (1) How many disks are used so  far
 *           (2) How many minutes are used in current disk so far
 *           The two dimensions in dp[x][y] are 
 *           (1) at most x disk are used
 *           (2) at most y minutes are used in disk x+1
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 30th, 2016
 */
public class rockers {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("rockers.in"));
		StringTokenizer input_tokens = new StringTokenizer(f.readLine());
		int N = Integer.parseInt(input_tokens.nextToken()); // #songs
		int T = Integer.parseInt(input_tokens.nextToken()); // #minutes per disk
		int M = Integer.parseInt(input_tokens.nextToken()); // #disks
		input_tokens = new StringTokenizer(f.readLine());
		int[] songs = new int[N];
		for (int i = 0; i < N; i++) {
			songs[i] = Integer.parseInt(input_tokens.nextToken());
		}
		f.close();
		
		int[][] prev = new int[M+1][T+1];
		for (int i = 0; i < songs.length; i++) {
			int song_len = songs[i];
			int[][] current = new int[M+1][T+1];
			for (int d = 1; d <= M; d++) { // Uses d disks
				for (int t = 1; t <= T; t++) { // Use at most t minutes on disk d
					current[d][t] = prev[d][t]; // Case I. We don't record song i
					if (t >= song_len) {
						// Case II. We record song i on current disk
						current[d][t] = Math.max(current[d][t], prev[d][t-song_len] + 1);
						current[d][t] = Math.max(current[d][t], prev[d-1][T] + 1);
					}
				}
			}
			prev = current;
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("rockers.out")));
		out.println(prev[M][T]);
		out.close();
	}
}
