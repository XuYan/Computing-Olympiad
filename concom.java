import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: concom
*/
/**
 * Thoughts: Breadth-First search. Be aware of the 
 *           
 * Pitfalls: 
 *           
 * Take-away tips: Don't try to list all the cases. Find the essence of a problem. For this problem, the essence is 'control' relationship is transitive! 
 * 
 * @author Xu Yan
 * @date June 17th, 2016
 */
public class concom {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("concom.in"));
		int triple_count = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());

		Map<Integer,List<Integer>> direct_relationship = new HashMap<Integer, List<Integer>>();
		int[][] control_share = new int[101][101];
		for (int i = 0; i < triple_count; i++) {
			StringTokenizer token = new StringTokenizer(f.readLine());
			int controller = Integer.parseInt(token.nextToken());
			int controllee = Integer.parseInt(token.nextToken());
			int share = Integer.parseInt(token.nextToken());
			
			control_share[controller][controllee] += share;
			
			if (direct_relationship.containsKey(controller)) {
				direct_relationship.get(controller).add(controllee);
			} else {
				List<Integer> list = new ArrayList<Integer>();
				list.add(controllee);
				direct_relationship.put(controller, list);
			}
		}
		f.close();

		List<int[]> answers = solve(direct_relationship, control_share);
		Collections.sort(answers, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0] != o2[0] ? (o1[0] - o2[0]) : (o1[1] - o2[1]);
			}
			
		});
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("concom.out")));
		for (int[] answer : answers) {
			out.println(answer[0] + " " + answer[1]);
		}
		out.close();
	}
	
	private static List<int[]> solve(Map<Integer,List<Integer>> direct_relationship, int[][] control_share) {
		List<int[]> answers = new ArrayList<int[]>(); // Contains a list of integer array [a,b] where 'a' controls 'b'
		for (int controller : direct_relationship.keySet()) {
			int[] shares = new int[101]; // The shares owned by 'controller' for each company
			boolean[] in_processing = new boolean[101]; // True if a company is waiting to be processed in 'control-queue'
			Queue<Integer> control_queue = new LinkedList<Integer>(); // Contains the companies controlled by 'controller'
			control_queue.add(controller);
			in_processing[controller] = true;
			while (!control_queue.isEmpty()) {
				int controlled_company = control_queue.poll(); // pop up a company, which is controlled by 'controller'
				if (direct_relationship.containsKey(controlled_company)) {
					for (int shareholder_company : direct_relationship.get(controlled_company)) { // traverse each company in which 'controlled company' has shares
						shares[shareholder_company] += control_share[controlled_company][shareholder_company];
						if (shares[shareholder_company] > 50 && !in_processing[shareholder_company]) {
							in_processing[shareholder_company] = true;
							answers.add(new int[] {controller, shareholder_company});
							control_queue.add(shareholder_company);
						}
					}
				}
			}
		}
		return answers;
	}
}