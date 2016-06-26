/*
ID: Xu Yan
LANG: JAVA
TASK: milk2
*/
import java.io.*;
import java.util.*;

class milk2 {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
    
    int farmers = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
    int[][] workingPeriods = new int[farmers][2];
    for (int i = 0; i < farmers; i++) {
        StringTokenizer line = new StringTokenizer(f.readLine());
        int from = Integer.parseInt(line.nextToken());
        int to = Integer.parseInt(line.nextToken());
        workingPeriods[i] = new int[]{from, to};
    }
    
    Arrays.sort(workingPeriods, new Comparator<int[]>() {
        @Override
        public int compare(int[] wp1, int[] wp2) {
            return (wp1[0] != wp2[0]) ? (wp1[0] - wp2[0]) : (wp1[1] - wp2[1]);  
        }
    });
    
    milk2 m = new milk2();
    int[] ans = m.calc(workingPeriods);
    out.println(ans[0] + " " + ans[1]);
    
    f.close();
    out.close();
  }
  
  private int[] calc(int[][] workingPeriods) {
      int atLeastOneWorkingStart = workingPeriods[0][0];
      int atLeastOneWorkingUntil = workingPeriods[0][1];
      int ans1 = atLeastOneWorkingUntil - atLeastOneWorkingStart, ans2 = 0; // This line must be under the above two lines to handle case "1<br>100 200"
      for (int i = 1; i < workingPeriods.length; i++) {
          int[] wp = workingPeriods[i];
          if (wp[0] <= atLeastOneWorkingUntil) {
              atLeastOneWorkingUntil = Math.max(atLeastOneWorkingUntil, wp[1]);
              ans1 = Math.max(ans1, atLeastOneWorkingUntil - atLeastOneWorkingStart);
          } else {
              ans2 = Math.max(ans2, wp[0] - atLeastOneWorkingUntil);
              atLeastOneWorkingStart = wp[0];
              atLeastOneWorkingUntil = wp[1];
          }
      }
      return new int[]{ans1, ans2};
  }
}