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
TASK: milk3
*/
/**
 * Thoughts: Depth-First Search.
 *           Each time, we found a bucket to pour milk from and a bucket to pour milk to, 
 *           calculate the new state(The amount of milk in each bucket) and recursively find a new 'from' bucket and 'to' bucket from the new state.
 *           To remove duplicates, we define state as the amount of milk in bucket X, Y, Z. Since the total amount of milk is fixed, to track state, we only need to track the amount of milk in X, Y.
 *           This can be maintained in a 2 dimensional boolean array f, where f[i][j] records if we have already been to the state when bucket X has i units of milk and bucket Y has j units of milk
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 13th,2016
 */
public class milk3 {
	int[] bucket_capacities;
	public milk3(int[] bucket_capacities) {
		this.bucket_capacities = bucket_capacities;
	}
	
	public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("milk3.in"));
        StringTokenizer ABC = new StringTokenizer(f.readLine());
        int A_capacity = Integer.parseInt(ABC.nextToken());
        int B_capacity = Integer.parseInt(ABC.nextToken());
        int C_capacity = Integer.parseInt(ABC.nextToken());
        f.close();

        milk3 solver = new milk3(new int[] {A_capacity, B_capacity, C_capacity});
        
        boolean[][] state = new boolean[A_capacity+1][B_capacity+1];
        boolean[] ans = new boolean[C_capacity+1];
        solver.solve(new int[] {0, 0, C_capacity}, state, ans);
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));
		String answer = "";
		for (int i = 0; i < ans.length; i++) {
			if (ans[i]) {
				answer += i + " ";
			}
		}
		out.println(answer.substring(0, answer.length()-1));
		out.close();
    }
	
	/**
	 * Depth first search solver
	 * @param milk The amount of milk in each bucket
	 * @param state Keep track of the state we've already been to. Since the total amount of milk is fixed, when the amount of milk in two buckets are known, 
	 *              the amount in the third bucket is known as well. So here, we use the amount of milk in first and second bucket as a state
	 * @param ans If ans[i] is true, we found a way to pour milks among buckets such that when bucket A is empty, bucket C has i amount of milk
	 */
	private void solve(int[] milk, boolean[][] state, boolean[] ans) {
		if (milk[0] == 0) {
			ans[milk[2]] = true; // Found an answer!
		}
		if (state[milk[0]][milk[1]]) {
			return; // We've been to this state before!
		}
		state[milk[0]][milk[1]] = true; // Record the state
		for (int from = 0; from < 3; from++) {
			for (int to = 0; to < 3; to++) {
				if (from == to) {
					continue; // It doesn't make sense to pour milk from bucket i back to bucket i
				}
				if (milk[from] == 0 || milk[to] == this.bucket_capacities[to]) {
					continue; // If bucket 'from' is empty or bucket 'to' is full, don't waste time since nothing will happen...
				}
				// Either 'from' bucket empty or 'to' bucket full!
				int pouredMilkAmount = Math.min(milk[from], this.bucket_capacities[to] - milk[to]); 
				int[] milk_after_pour = new int[3];
				for (int i = 0; i < 3; i++) {
					if (i == from) {
						milk_after_pour[i] = milk[i] - pouredMilkAmount; // Amount decreases
					} else if (i == to) {
						milk_after_pour[i] = milk[i] + pouredMilkAmount; // Amount increases
					} else {
						milk_after_pour[i] = milk[i]; // No change
					}
				}
				this.solve(milk_after_pour, state, ans);
			}
		}
	}
}
