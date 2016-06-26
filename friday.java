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
TASK: friday
*/
public class friday {
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("friday.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));
        
        int N = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        
        friday program = new friday();
        int[] statistics = new int[7];
        int previousDay = 0;
        for (int year = 1900; year < 1900 + N; year++) {
            for (int month = 0; month < 12; month++) {
                previousDay = (previousDay + program.getOffset(year, month)) % 7;
                statistics[previousDay] += 1;
            }
        }
        
        for (int i = 6; i < 13; i++) {
            out.print(statistics[i % 7] + (i != 12 ? " " : "\n"));    
        }
        
        
        f.close();
        out.close();
    }
    
    private int getOffset(int year, int month) {
        if (year == 1900 && month == 0) {
            return 13 % 7;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 0) {
            return 31 % 7;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30 % 7;
        }
        
        return isLeap(year) ? 29 %  7 : 28 % 7;
    }
    
    private boolean isLeap(int year) {
        return year % 100 == 0 ? (year % 400 == 0) : (year % 4 == 0);
    }
}
