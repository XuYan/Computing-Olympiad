import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: spin
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 16th, 2016
 */
public class spin {
	private static class Wheel {
		int rotating_speed;
		int[][] wedges;
		public Wheel(int speed, int[][] wedges) {
			this.rotating_speed = speed;
			this.wedges = wedges;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("spin.in"));
		StringTokenizer input_tokens = null;
		Wheel[] wheels = new Wheel[5];
		for (int i = 0; i < 5; i++) {
			input_tokens = new StringTokenizer(f.readLine());
			int speed = Integer.parseInt(input_tokens.nextToken());
			int wedge_count = Integer.parseInt(input_tokens.nextToken());
			int[][] wedges = new int[wedge_count][2];
			for (int j = 0; j < wedge_count; j++) {
				int start_angle = Integer.parseInt(input_tokens.nextToken());
				int extent = Integer.parseInt(input_tokens.nextToken());
				int end_angle = (start_angle + extent) % 360;
				wedges[j] = new int[] {start_angle, end_angle};
			}
			wheels[i] = new Wheel(speed, wedges);
		}
		f.close();
		
		int answer = -1;
		for (int t = 0; t < 360; t++) {
			List<int[]> wedge_overlap = get_wedges_after_second(wheels[0], t);
			int i = 1;
			for ( ; i < 5; i++) {
				List<int[]> wedges = get_wedges_after_second(wheels[i], t);
				wedge_overlap = get_overlap(wedge_overlap, wedges);
				if (wedge_overlap.isEmpty()) {
					break;
				}
			}
			if (i == 5) {
				answer = t;
				break;
			}
		}
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));
		out.println((answer != -1) ? answer : "none");
		out.close();
	}
	
	private static List<int[]> get_wedges_after_second(Wheel wheel, int second) {
		List<int[]> wedges = new ArrayList<int[]>();
		for (int i = 0; i < wheel.wedges.length; i++) {
			int start_angle = (wheel.wedges[i][0] + (wheel.rotating_speed * second)) % 360;
			int end_angle = (wheel.wedges[i][1] + (wheel.rotating_speed * second)) % 360;
			if (start_angle > end_angle) {
				wedges.add(new int[] {start_angle, 359});
				wedges.add(new int[] {0, end_angle});
			} else {
				wedges.add(new int[] {start_angle, end_angle});
			}
		}
		return wedges;
	}
	
	private static List<int[]> get_overlap(List<int[]> wedges1, List<int[]> wedges2) {
		List<int[]> overlaps = new ArrayList<int[]>();
		for (int i = 0; i < wedges1.size(); i++) {
			int[] w1 = wedges1.get(i);
			for (int j = 0; j < wedges2.size(); j++) {
				int[] w2 = wedges2.get(j);
				int[] overlap = new int[] {Math.max(w1[0], w2[0]), Math.min(w1[1], w2[1])};
				if (overlap[0] <= overlap[1]) {
					overlaps.add(overlap);
				}
			}
		}
		return overlaps;
	}
}