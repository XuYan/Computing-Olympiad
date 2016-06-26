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
TASK: numtri
*/
/**
 * Thoughts: Dynamic Programming
 * Pitfalls: Be careful in general cases, the value in (i,j) is determined by the values in (i-1,j-1) and (i-1,j).
 *           If using one array, be sure to use the state at (i-1,j-1), not (i,j-1), which is likely to override (i-1,j-1) when using one array.
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 17th,2016
 */
public class numtri {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("numtri.in"));
        StringTokenizer inputs = new StringTokenizer(f.readLine());
        
        int rowCount = Integer.parseInt(inputs.nextToken());
        int[] lastRowRecord = new int[rowCount];
        for (int row = 0; row < rowCount; row++) {
        	StringTokenizer rowNumTokenizer = new StringTokenizer(f.readLine());
        	int[] currentRowRecord = new int[rowCount];
        	for (int i = 0; i < row + 1; i++) {
        		int num = Integer.parseInt(rowNumTokenizer.nextToken());
        		if (i == 0) {
        			currentRowRecord[i] = num + lastRowRecord[i];
        		} else if (i == row) {
        			currentRowRecord[i] = num + lastRowRecord[i-1];
        		} else {
        			currentRowRecord[i] = num + Math.max(lastRowRecord[i-1], lastRowRecord[i]);
        		}
        	}
        	lastRowRecord = currentRowRecord;
        }
        
        f.close();

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
		int max = -1;
        for (int i = 0; i < rowCount; i++) {
        	max = Math.max(max, lastRowRecord[i]);
        }
        out.println(max);
		out.close();
	}
}