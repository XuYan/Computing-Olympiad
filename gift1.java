/*
ID: Xu Yan
LANG: JAVA
TASK: gift1
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class gift1 {
  public static void main (String [] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("gift1.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
    
    gift1 g = new gift1();
    
    Object[] initAns = g.init(f);
    Map<String,Integer> person_money = (Map<String,Integer>) initAns[0];
    String[] people = (String[]) initAns[1];

    int i = 0;
    while (i < people.length) {
        g.transfer(f, person_money);
        i++;
    }
    
    g.save(out, person_money, people);
    
    f.close();
    out.close();
  }
  
  private Object[] init(BufferedReader f) throws IOException {
      Object[] ans = new Object[2];
      int NP = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());

      Map<String,Integer> map = new HashMap<String,Integer>(NP);
      String[] people = new String[NP]; 
      for (int i = 0; i < NP; i++) {
          String person = new StringTokenizer(f.readLine()).nextToken(); 
          map.put(person, 0);
          people[i] = person;
      }
      
      ans[0] = map;
      ans[1] = people;
      return ans;
  }
  
  private void transfer(BufferedReader f, Map<String,Integer> person_money) throws IOException {
      String sender = new StringTokenizer(f.readLine()).nextToken();
      StringTokenizer balance_receiverCount = new StringTokenizer(f.readLine());
      int balance = Integer.parseInt(balance_receiverCount.nextToken());
      int receiverCount = Integer.parseInt(balance_receiverCount.nextToken());
      
      if (receiverCount == 0) { // Don't forget divide by zero case
          return;
      }
      
      int sent = balance / receiverCount;
      person_money.put(sender, person_money.get(sender) - balance + balance - sent * receiverCount);
      for (int i = 0; i < receiverCount; i++) {
          String receiver = new StringTokenizer(f.readLine()).nextToken(); 
          int existingBalance = person_money.get(receiver);
          person_money.put(receiver, existingBalance + sent);
      }
  }
  
  private void save(PrintWriter out, Map<String,Integer> person_balance, String[] people) {
      for (int i = 0; i < people.length; i++) {
          out.println(people[i] + " " + person_balance.get(people[i]));
      }
  }
}