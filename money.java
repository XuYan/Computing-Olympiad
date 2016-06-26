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
TASK: money
*/
/**
 * Thoughts: Dynamic programming backpack problem
 *           
 * Pitfalls: 1. It's easy to approach the problem in a difficult way at first. That is, for example we define f(N) as the number of ways to construct N money units,
 *              f(N) = f(N-V[0]) + f(N-V[1]) + f(N-V[x]), where V[x] is the type of coin, of which the value is smaller than N. However, this solution will introduce
 *              duplications and the duplications cannot be removed easily. For instance, we have two types of coin of value 1 and 2. We need to construct 3 money units,
 *              f(1) = 1 (put one coin of value 1);
 *              f(2) = f(1) (add one coin of value 1) + 1 (put one coin of value 2) = 2;
 *              f(3) = f(1) (add one coin of value 2) + f(2) (add one coin of value 1) = 3
 *              f(3) has duplication if we used the analogy above [(1,2),(1,1,1),(2,1)] and (1,2),(2,1) are actually the same strategy
 *           2. Be careful that the answer may be out of the range of integer!
 *              E.g. 17 500 
                     11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 
 *           
 * Take-away tips: Think about problem from another new perspective if the old one doesn't work out.
 *                 The pitfall idea aims to find out the ways to construct each money unit completely before moving to the next money unit.
 *                 Another perspective to tackle the problem is,
 *                 we firstly use coin type of value 1 to find out ways to construct (1-N) money unit with just 1-value coin,
 *                 then use coin type of value 2 to find out ways to construct (1-N) money unit with 2-value coin, and so on.
 *                 The difference between this idea and pitfall idea is we're calculating partial solution each step in this one.
 *                 Since the value of coin I'm using in each step is increasing, we won't have the duplication problem any more. 
 * 
 * @author Xu Yan
 * @date June 11th, 2016
 */
public class money {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("money.in"));
		StringTokenizer VN = new StringTokenizer(f.readLine());
		int V = Integer.parseInt(VN.nextToken());
		int N = Integer.parseInt(VN.nextToken());
		
		int[] currencies = new int[V];
		int i = 0;
		while (i < V) { // Be careful that the question says no particular number of integers per line
			StringTokenizer currency_tokens = new StringTokenizer(f.readLine());
			while (currency_tokens.hasMoreTokens() && i < V) {
				currencies[i++] = Integer.parseInt(currency_tokens.nextToken());
			}
		}
		f.close();

		long ans = solve(currencies, N); // Be careful the integer may overflow in this problem
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("money.out")));
		out.println(ans);
		out.close();
	}
	
	private static long solve(int[] currencies, int money_unit) {
		long[] answer = new long[money_unit + 1];
		answer[0] = 1;
		for (int currency : currencies) {
			for (int money = currency; money <= money_unit; money++) {
				answer[money] += answer[money - currency];
			}
		}
		
		return answer[money_unit];
	}
}