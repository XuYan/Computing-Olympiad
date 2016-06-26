/*
ID: Xu Yan
LANG: JAVA
TASK: ride
*/
import java.io.*;
import java.util.*;

class ride {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("ride.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
    
    StringTokenizer st = new StringTokenizer(f.readLine());
    String cometName = st.nextToken();
    
    st = new StringTokenizer(f.readLine());
    String groupName = st.nextToken();
    
    ride r = new ride();
    out.println(r.convertToNumber(cometName) == r.convertToNumber(groupName) ? "GO" : "STAY");
    
    f.close();
    out.close();
  }
  
  private int convertToNumber(String name) {
      int number = 1;
      for (int i = 0; i < name.length(); i++) {
          number *= (name.charAt(i) - 'A' + 1);
      }
      return number % 47;
  }
}