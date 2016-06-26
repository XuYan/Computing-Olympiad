package bruteforce;

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
TASK: beads
*/
public class beads {
    public static void main(String[] args) throws IOException {
        beads program = new beads();
        
        BufferedReader f = new BufferedReader(new FileReader("beads.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
        
        int beadsLength = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        String beads = new StringTokenizer(f.readLine()).nextToken();
        int maxCollected = program.calcMaxCollectedBeads(beads);
        out.println(maxCollected);
        out.close();
        f.close();
    }
    
    private int calcMaxCollectedBeads(String beads) {
        int ans = 0;
        for (int i = 0; i < beads.length(); i++) {
            int leftLength = calcHelper(beads, i, true);
            int rightLength = calcHelper(beads, i+1 < beads.length() ? i+1 : 0, false);
            
            ans = Math.max(ans, Math.min(leftLength + rightLength, beads.length()));
        }
        return ans;
    }
    
    private int calcHelper(String beads, int startPosition, boolean searchLeft) {
        int length = 0;
        char pureColor = '?';
        int currentPosition = startPosition;
        boolean flag = true;
        while (currentPosition != startPosition || flag) {
            flag = false;
            if (beads.charAt(currentPosition) == 'w' || beads.charAt(currentPosition) == pureColor) {
                length++;
            } else if (pureColor == '?') {
                length++;
                pureColor = beads.charAt(currentPosition);
            } else {
                return length;
            }
            if (searchLeft) {
                currentPosition--;
                if (currentPosition == -1) {
                    currentPosition = beads.length() - 1;
                }
            } else {
                currentPosition++;
                if (currentPosition == beads.length()) {
                    currentPosition = 0;
                }
            }
        }
        
        return length;
    }
}
