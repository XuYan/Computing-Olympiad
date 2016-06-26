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
TASK: palsquare
*/
/**
 * Thoughts: For each number in the range [1,300], calculate its square and check it the square is a palindrome or not.
 * Pitfalls: if input matrix and target matrix are identical and also mutual-reflected around a horizontal line in the middle of the matrix, we should output '2' instead of '6'
 * Take-away tips: 1. A number is palindrome if and only if reading from MSD to LSD is the same as reading from LSD to MSD.
 *                    So we can generate a new number with LSD of the original number each time until the original number becomes zero.
 *                    And then check if the new generated number is the same as the original number.   
 *                 2. Use Integer.toString(n,base) to get the string representation of a number under given base.
 * @author Xu Yan
 * @date April 22,2016
 */
public class palsquare {
    private static int RANGE_LOWERBOUND = 1;
    private static int RANGE_UPPERBOUND = 300;
    
    public static void main (String [] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("palsquare.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
        
        int base = Integer.parseInt(new StringTokenizer(in.readLine()).nextToken());
        
        palsquare program = new palsquare();
        for (int i = palsquare.RANGE_LOWERBOUND; i < palsquare.RANGE_UPPERBOUND; i++) {
            if (program.isPalindromeUnderBase(i * i, base)) {
                out.println(Integer.toString(i, base).toUpperCase() + " " + Integer.toString(i * i, base).toUpperCase());
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