import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: fracdec
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips:  
 * 
 * @author Xu Yan
 * @date June 19th, 2016
 */
public class fracdec {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("fracdec.in"));
		StringTokenizer ND = new StringTokenizer(f.readLine());
		int numerator = Integer.parseInt(ND.nextToken());
		int denominator = Integer.parseInt(ND.nextToken());
		f.close();
		
		String integer_part = (numerator / denominator) + "";
		String decimal_part = calculateDecimal(numerator, denominator);
		String answer = integer_part + "." + decimal_part;
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fracdec.out")));
		int i = 0;
		while (i < answer.length()) {
			out.print(answer.charAt(i++));
			if (i % 76 == 0) {
				out.print('\n');
			}
		}
		if (i % 76 != 0) { // Correct error that output did not end in a newline!
			out.print('\n');
		}
		
		out.close();
	}
	
	private static String calculateDecimal(int numerator, int denominator) {
		Map<Integer,Integer> digit_index = new HashMap<Integer,Integer>(); // The index of each digit in decimal part
		
		int reminder = numerator % denominator;
		if (reminder == 0) {
			return "0";
		}
		StringBuilder decimal_builder = new StringBuilder();
		digit_index.put(reminder, decimal_builder.length());
		
		while (reminder != 0) {
			numerator = reminder * 10;
			decimal_builder.append(numerator / denominator);
			reminder = numerator % denominator;
			if (reminder != 0) {
				if (digit_index.containsKey(reminder)) {
					decimal_builder.insert((int) digit_index.get(reminder), '(');
					decimal_builder.append(')');
					break;
				} else {
					digit_index.put(reminder, decimal_builder.length());
				}
			}
		}
		
		return decimal_builder.toString();
	}
}
