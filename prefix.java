import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: prefix
*/
/**
 * Thoughts: Trie
 * Pitfalls: 
 * Take-away tips:
 * 
 * @author Xu Yan
 * @date June 8th, 2016
 */
public class prefix {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("prefix.in"));
		List<String> primitives = new ArrayList<String>();
		String inputLine = f.readLine();
		StringTokenizer primitiveTokens;
		while (!inputLine.equals(".")) { // Cannot compare string value equality with '!=', which is used to determine whether two string objects point to the same object in memory
			primitiveTokens = new StringTokenizer(inputLine);
			while (primitiveTokens.hasMoreTokens()) {
				primitives.add(primitiveTokens.nextToken());
			}
			inputLine = f.readLine();
		}
		
		StringBuilder s_builder = new StringBuilder();
		inputLine = f.readLine();
		while (inputLine != null) {
			s_builder.append(inputLine);
			inputLine = f.readLine();
		}
		String S = s_builder.toString();
		
		boolean[] canCompose = new boolean[S.length()+1];
		canCompose[0] = true;
		Trie trie = new Trie(primitives);
		for (int i = 0; i <= S.length(); i++) {
			if (canCompose[i]) {
				Trie.Node runner = trie.root; 
				for (int j = i + 1; j <= S.length(); j++) {
					char c = S.charAt(j-1);
					if (runner.children[c-'A'] != null) {
						if (runner.children[c-'A'].isEndOfPrimitive) {
							canCompose[j] = true;
						}
						runner = runner.children[c-'A'];
					} else {
						break;
					}
				}
			}
		}
		
		int ans = 0;
		for (int i = S.length(); i >= 0; i--) {
			if (canCompose[i]) {
				ans = i;
				break;
			}
		}
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("prefix.out")));
		out.println(ans);
		out.close();
	}
	
	private static class Trie {
		Node root;
		public Trie(List<String> primitives) {
			this.root = new Node();
			for (String primitive: primitives) {
				Node runner = root;
				for (int i = 0; i < primitive.length(); i++) {
					char c = primitive.charAt(i);
					if (runner.children[c-'A'] == null) {
						runner.children[c-'A'] = new Node();
					}
					runner = runner.children[c-'A'];
				}
				runner.isEndOfPrimitive = true;
			}
		}
		
		private class Node {
			boolean isEndOfPrimitive;
			Node[] children;
			
			public Node() {
				this.children = new Node[26];
			}
		}
	}
}
