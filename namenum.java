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
TASK: namenum
*/
/**
 * Thoughts: From brand number's perspective, the time complexity is O(3^N * log5000);
 *           From dict's perspective, the time complexity is O(dict.length);
 * Pitfalls: Don't forget output "NONE" when no valid name is found
 * Take-away tips: Don't be trapped by one thinking set. Try to tackle a problem from different perspectives.
 * 
 * @author Xu Yan
 * @date April 23,2016
 */
public class namenum {
    public static void main (String [] args) throws IOException {
        BufferedReader inputReader = new BufferedReader(new FileReader("namenum.in"));
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
        
        String brandNum = new StringTokenizer(inputReader.readLine()).nextToken();
        
        namenum program = new namenum();
        program.translateDict(out, brandNum);
        
        inputReader.close();
        out.close();
    }

    /**
     * Scan through words in dictionary and translate them to numbers. When meeting brandNum, write the word to output
     * @param out the PrintWriter
     * @param brandNum target brand number
     * @throws IOException
     */
    public void translateDict(PrintWriter out, String brandNum) throws IOException {
        boolean validNameFound = false;
        BufferedReader dictReader = new BufferedReader(new FileReader("dict.txt"));
        String word = dictReader.readLine();
        while (word != null) {
            if (word.length() == brandNum.length()) {
                int i = 0;
                for (; i < word.length(); i++) {
                    if (word.charAt(i) == 'Q' || word.charAt(i) == 'Z' || this.number(word.charAt(i)) != brandNum.charAt(i)) {
                        break;
                    }
                }
                if (i == word.length()) {
                    validNameFound = true;
                    out.println(word);
                }
            }
            word = dictReader.readLine();
        }
        
        if (!validNameFound) {
            out.println("NONE");
        }
        
        dictReader.close();
    }
    
    /**
     * Convert capital letter to digit according to keypad mapping
     * @param letter the capital letter
     * @return the digit mapped from the letter
     */
    public char number(char letter) {
        int offset = letter - 'A';
        
        int digit = 2 + ((letter >= 'S') ? (offset - 1) : offset) / 3;
        assert(digit / 10 == 0); // Must be a one digit integer in the range [2,9]
        
        return ("" + digit).charAt(0);
    }
}
