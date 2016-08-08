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
TASK: game1
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 23rd, 2016
 */
public class game1 {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader f = new BufferedReader(new FileReader("game1.in"));
		StringTokenizer input_tokens = new StringTokenizer(f.readLine());
		int N = Integer.parseInt(input_tokens.nextToken());

		int[] board = new int[N];
		int sum = 0;
		int i = 0;
		while (i < N) {
			input_tokens = new StringTokenizer(f.readLine());
			while (input_tokens.hasMoreTokens()) {
				board[i] = Integer.parseInt(input_tokens.nextToken());
				sum += board[i++];
			}
		}
		f.close();
		
		int[][] states = new int[N][N];
		int first_player_optimal_score = solve(states, board, sum, 0, N-1);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("game1.out")));
		out.println(first_player_optimal_score + " " + (sum - first_player_optimal_score));
		out.close();
	}
	
	private static int solve(int[][] states, int[] board, int total_score, int start_index, int end_index) {
		if (start_index >= board.length || end_index < 0 || start_index > end_index) {
			return 0;
		}
		if (start_index == end_index) {
			return board[start_index];
		}
		if (states[start_index][end_index] != 0) {
			return states[start_index][end_index];
		}
		int left_most_element = board[start_index];
		int remaining_score1 = total_score - left_most_element;
		int sub1 = left_most_element + remaining_score1 - solve(states, board, remaining_score1, start_index + 1, end_index);
//		int sub_max1 = Math.max(
//				solve(states, board, start_index + 2, end_index),
//				solve(states, board, start_index + 1, end_index - 1));
		int right_most_element = board[end_index];
		int remaining_score2 = total_score - right_most_element;
		int sub2 = right_most_element + remaining_score2 - solve(states, board, remaining_score2, start_index, end_index - 1);
//		int sub_max2 = Math.max(
//				solve(states, board, start_index, end_index - 2),
//				solve(states, board, start_index + 1, end_index - 1));
		
		states[start_index][end_index] = Math.max(sub1, sub2);
		return states[start_index][end_index];
	}
}
