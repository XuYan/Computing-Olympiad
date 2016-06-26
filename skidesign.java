import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: skidesign
*/
/**
 * Thoughts: 枚举。维护一个长度为17的区间，从[lowest_hill_height, lowest_hill_height + 17    ] -> [highest_hill_height - 17, highest_hill_height], 每次区间右移1单位距离
 * Pitfalls: Don't forget to consider the corner case when all hills are within a range of 17 at first.
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 5th,2016
 */
public class skidesign {
	private static int DIFF = 17;
	
	public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("skidesign.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
        
        int hillCount = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        int[] hillHeights = new int[hillCount];
        for (int i = 0; i < hillCount; i++) {
        	hillHeights[i] = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        }
        Arrays.sort(hillHeights);
        
        
        int lowestHeight = hillHeights[0], highestHeight = hillHeights[hillCount-1];
        if (highestHeight - lowestHeight <= skidesign.DIFF) {
        	out.println(0);
        } else {
        	int minCost = Integer.MAX_VALUE;
            for (int intervalStart = lowestHeight; intervalStart < highestHeight - skidesign.DIFF; intervalStart++) {
            	int cost = 0;
            	int intervalEnd = intervalStart + skidesign.DIFF;
            	for (int hillIndex = 0; hillIndex < hillCount; hillIndex++) {
            		if (hillHeights[hillIndex] < intervalStart) {
            			cost += Math.pow(intervalStart - hillHeights[hillIndex], 2);
            		} else if (hillHeights[hillIndex] > intervalEnd) {
            			cost += Math.pow(hillHeights[hillIndex] - intervalEnd, 2);
            		}
            	}
            	minCost = Math.min(minCost, cost);
            }
            
            out.println(minCost);
        }
        
        f.close();
        out.close();
    }
}
