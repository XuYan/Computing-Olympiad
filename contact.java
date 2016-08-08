import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: contact
*/
/**
 * Thoughts: 
 *           
 * Pitfalls:  
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date July 2nd, 2016
 */
public class contact {
	private static class Token {
		String str;
		int frequency;
		
		public Token(String str) {
			this.str = str;
			this.frequency = 1; // When we add a new token, it appears at least once
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("contact.in"));
		StringTokenizer inputs = new StringTokenizer(f.readLine());
		int A = Integer.parseInt(inputs.nextToken());
		int B = Integer.parseInt(inputs.nextToken());
		int N = Integer.parseInt(inputs.nextToken());
		StringBuilder binaryStringBuilder = new StringBuilder();
		String line = f.readLine();
		do {
			binaryStringBuilder.append(line);
			line = f.readLine();
		}
		while (line != null);
		f.close();

		String binaryString = binaryStringBuilder.toString();
		
		Map<String,Token> tokens = new HashMap<String,Token>(); //Don't forget tail processing!!!
		for (int i = A; i <= binaryString.length(); i++) {
			for (int len = A; len <= B && i - len >= 0; len++) {
				String str = binaryString.substring(i-len, i);
				if (tokens.containsKey(str)) {
					tokens.get(str).frequency++;
				} else {
					Token t = new Token(str);
					tokens.put(str, t);
				}
			}
		}
		List<Token> token_list = new ArrayList<Token>();
		for (String key : tokens.keySet()) {
			token_list.add(tokens.get(key));
		}
		Collections.sort(token_list, new Comparator<Token>() {
			@Override
			public int compare(Token t1, Token t2) {
				if (t1.frequency != t2.frequency) {
					return t2.frequency - t1.frequency;
				} else if (t1.str.length() != t2.str.length()) {
					return t1.str.length() - t2.str.length();
				} else {
					return Integer.parseInt(t1.str, 2) - Integer.parseInt(t2.str, 2);
				}
			}
		});
				
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("contact.out")));
		int i = 0;
		int freq_count = 0;
		while (i < token_list.size() && freq_count < N) {
			int token_freq = token_list.get(i).frequency; 
			out.println(token_freq);
			freq_count ++;
			int freq_start = i;
			int freq_end = i + 1;
			while (freq_end < token_list.size() && token_list.get(freq_end).frequency == token_freq) {
				freq_end ++;
			}
			for (int j = freq_start; j < freq_end; j++) { // Tokens in the range [freq_start, freq_end) appears token_freq times
				if (j != freq_start && ((j - freq_start) % 6) == 0) {
					out.print("\n");
				}
				out.print(token_list.get(j).str);
				if (j + 1 < freq_end && (j + 1 - freq_start) % 6 != 0) {
					out.print(" ");
				}
			}
			i = freq_end;
			out.print("\n");
		}
		out.close();
	}
}
