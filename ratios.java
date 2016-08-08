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
TASK: ratios
*/
/**
 * Thoughts: I approach this problem by applying some knowledge about vector.
 *           The goal and final mixtures are vectors in a three-dimensional space.
 *           The problem can be converted to figure out how to mix the mixtures s.t. the final mixture vector and goal vector are collinear.
 *           The vectors are collinear -> their angle is 0.
 *           According to the definition of inner product of two vectors:
 *           V(a) * V(b) = |V(a)| * |V(b)| * cosX, where X is the angle between vector a and vector b.
 *           V(a) * V(b) = a1*b1 + a2*b2 + a3*b3
 *           Let X = 0 (Collinear),
 *           We have (V(a) * V(b))^2 = |V(a)|^2 * |V(b)|^2
 *           => (a1*b1 + a2*b2 + a3*b3)^2 = (a1^2 + a2^2 + a3^2)^2 + (b1^2 + b2^2 + b3^2)^2
 *           Suppore (a1,a2,a3) is the goal vector
 *           We just need to find out the proportion s.t. the (b1,b2,b3) satisfies the above equality!
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 13th, 2016
 */
public class ratios {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("ratios.in"));
		int[] goal_ratio = new int[3];
		StringTokenizer inputs = new StringTokenizer(f.readLine());
		for (int i = 0; i < 3; i++) {
			goal_ratio[i] = Integer.parseInt(inputs.nextToken());
		}
		int[][] mixtures = new int[3][3];
		for (int i = 0; i < 3; i++) {
			inputs = new StringTokenizer(f.readLine());
			for (int j = 0; j < 3; j++) {
				mixtures[i][j] = Integer.parseInt(inputs.nextToken());
			}
		}
		f.close();
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));
		int[] answer = new int[] {200, 200, 200, 200};
		for (int i = 0; i < 100; i++) {
			int[] actual_after_mixture1 = {i * mixtures[0][0], i * mixtures[0][1], i * mixtures[0][2]};
			for (int j = 0; j < 100; j++) {
				int[] actual_after_mixture2 = {
					actual_after_mixture1[0] + j * mixtures[1][0],
					actual_after_mixture1[1] + j * mixtures[1][1],
					actual_after_mixture1[2] + j * mixtures[1][2]
				};
				for (int k = 0; k < 100; k++) {
					int[] actual_after_mixture3 = {
						actual_after_mixture2[0] + k * mixtures[2][0],
						actual_after_mixture2[1] + k * mixtures[2][1],
						actual_after_mixture2[2] + k * mixtures[2][2],
					};
					if (isCollinear(goal_ratio, actual_after_mixture3)) {
						for (int m = 0; m < 3; m++) {
							if (goal_ratio[m] * actual_after_mixture3[m] != 0) {
								int amount = (int) (actual_after_mixture3[m] / goal_ratio[m]);
								if (amount * goal_ratio[m] == actual_after_mixture3[m]) {
									answer = getMin(answer, new int[] {i, j, k, amount});
								}
							}
						}
					}
				}
			}
		}
		
		if (answer[0] == 200) {
			out.println("NONE");	
		} else {
			out.println(answer[0] + " " + answer[1] + " " + answer[2] + " " + answer[3]);
		}
		out.close();
	}
	
	private static int[] getMin(int[] a1, int[] a2) {
		int a1_sum = a1[0] + a1[1] + a1[2];
		int a2_sum = a2[0] + a2[1] + a2[2];
		return a1_sum < a2_sum ? a1 : a2;
	}
	
	private static boolean isCollinear(int[] goal_ratio, int[] actual_ratio) {
		long goal_vector_length = 
			goal_ratio[0] * goal_ratio[0] + goal_ratio[1] * goal_ratio[1] + goal_ratio[2] * goal_ratio[2];
		long actual_vector_length =
			actual_ratio[0] * actual_ratio[0] + actual_ratio[1] * actual_ratio[1] + actual_ratio[2] * actual_ratio[2];
		long l_value = goal_vector_length * actual_vector_length;
		long r_value_temp = goal_ratio[0] * actual_ratio[0] + goal_ratio[1] * actual_ratio[1] + goal_ratio[2] * actual_ratio[2];
		long r_value = (long) Math.pow(r_value_temp, 2);
		return l_value == r_value;
	}
}
