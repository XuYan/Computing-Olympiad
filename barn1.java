import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;


/*
ID: Xu Yan
LANG: JAVA
TASK: barn1
*/
/**
 * Thoughts: Define 'continuous interval' as the length of continuous neighboring stalls with cow. For example, 1,2,3,6,7,9 has three continuous intervals.  
 *           The first thought is to insert boards to block stalls with cow and then calculate the length of boards. 
 *           Since lumber supplier can support boards of any length and M >= 1, we can definitely purchase just one board with length S to block all stalls, 
 *           no matter whether a stall has cow in it. 
 *           Since the total number of boards we can get is restrained, with the first thought, consider an extreme case for the above example.
 *           If we can purchase 10 boards, the solution is the same as if we can only purchase 3 boards.
 *           But what if we can only purchase 2 boards? We'll need to decide if we should use 2 boards to cover two continuous valid interval or use 1 board to cover both.
 *           The solution is, suppose we sort the intervals between each two continuous intervals. With the maximum M intervals not covered.
 *           Thinking the problem reversely. If we have one board covering all the S stalls, we need to break it into two pieces, the break point should be the largest intervals
 *           between two continuous intervals.
 *           Suppose we sort them from larger to smaller as i1, i2, i3, ... , iN. If we're allow to purchase 2 boards, the min total board length while covering all stalls with cow
 *           is S - i1. The proof is assuming we have a ix so that S - ix < S - i1, this will deduce ix > i1, which contradicts i1 is the largest one. 
 * Pitfalls: If we're allowed to purchase x boards => we removed the largest x-1 intervals.
 *           The initial board length is max_stall_with_cow - min_stall_with_cow + 1
 * Take-away tips: Don't make any assumption! The input is not necessary sorted!
 *                 正难则反
 * 
 * @author Xu Yan
 * @date May 4th,2016
 */
public class barn1 {
	public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("barn1.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
        
        StringTokenizer MSC = new StringTokenizer(f.readLine());
        int M = Integer.parseInt(MSC.nextToken());
        int S = Integer.parseInt(MSC.nextToken());
        int C = Integer.parseInt(MSC.nextToken());
        
        int[] stalls_with_cow = new int[C];
        for (int i = 0; i < C; i++) {
        	stalls_with_cow[i] = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        }
        Arrays.sort(stalls_with_cow);
        
        List<Integer> continuous_no_cow_interval_length = new ArrayList<Integer>();
        // List<Integer> continuous_no_cow_interval_length = new ArrayList<>();
        for (int i = 1; i < C; i++) {
        	if (stalls_with_cow[i] != stalls_with_cow[i-1] + 1) {
        		continuous_no_cow_interval_length.add(stalls_with_cow[i] - 1 - stalls_with_cow[i-1]);
        	}
        }
        
        if (M > continuous_no_cow_interval_length.size()) {
        	out.println(C);
        } else {
        	Collections.sort(continuous_no_cow_interval_length, new Comparator<Integer>() {
    	    	@Override
    	    	public int compare(Integer o1, Integer o2) {
    	    		return o2 - o1;
    	    	}
    		});
            // Collections.sort(continuous_no_cow_interval_length, (Integer o1, Integer o2) -> o2 - o1);
            
            int minBlockedStalls = stalls_with_cow[C-1] - stalls_with_cow[0] + 1;
            for (int i = 0; i < M - 1; i++) {
            	// Be careful that breaking board once equals inserting 2 boards semantically. So termination condition is M - 1
            	minBlockedStalls -= continuous_no_cow_interval_length.get(i);
            }
            
            out.println(minBlockedStalls);
        }
        
        f.close();
        out.close();
    }
}
