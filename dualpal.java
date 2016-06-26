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
TASK: dualpal
*/
/**
 * Thoughts: If (num)2 is palindrome, num(4) is still undeterministic. For example, (num)2 is 10101, (num)4 is 010101 => 111, which is a palindrome.
 *                                                                                  (num)2 is 11011, (num)4 is 011011 => 123, which is not a palindrome.
 *           Similarly, (num)3 is a palindrome doesn't guarantee num(9) is a palindrome.     
 * Pitfalls: 
 * Take-away tips: 1. A number is palindrome if and only if reading from MSD to LSD is the same as reading from LSD to MSD.
 *                    So we can generate a new number with LSD of the original number each time until the original number becomes zero.
 *                    And then check if the new generated number is the same as the original number.   
 *                 2. Use Integer.toString(n,base) to get the string representation of a number under given base.
 * @author Xu Yan
 * @date April 23,2016
 */
public class dualpal {
    public static void main (String [] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("dualpal.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
        
        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        
        dualpal program = new dualpal();
        for (int i = S + 1, dualpalCount = 0; dualpalCount < N; i++) {
            // palCount counts how many times we have found the number i under certain base is a palindrome. When it's equal to 2, terminate inner loop since a dualpal is found.
            for (int base = 2, palCount = 0; base <= 10; base++) {
                if (program.isPalindromeUnderBase(i, base)) {
                    palCount++;
                }
                if (palCount >= 2) {
                    dualpalCount++;
                    out.println(i);
                    break;
                }
            }
        }
        
        in.close();
        out.close();
    }
    
    /**
     * Checks if n is a palindrome under given base
     * @param n number in base 10
     * @param base the base n will be converted to.
     * @return true if n is palindromic under base b 
     */
    private boolean isPalindromeUnderBase(int n, int base) {
        int before = n, after = 0; // 'before' and 'after' are the numbers before and after conversion respectively
        for ( ; n > 0; n /= base) {
            after = after * base + (n % base);
        }
        return before == after;
    }
}
