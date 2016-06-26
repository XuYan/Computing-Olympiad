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
TASK: holstein
*/
/**
 * Thoughts: 
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 28th, 2016
 */
public class holstein {
	public int[][] feedVitamins;
	int minScoops;
	List<Integer> answer;
	
	public holstein(int[][] feedVitamins) {
		this.feedVitamins = feedVitamins;
		this.minScoops = Integer.MAX_VALUE;
		this.answer = new ArrayList<Integer>();
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("holstein.in"));
        int V = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        
        StringTokenizer lineTokens = new StringTokenizer(f.readLine());
        int[] requiredVitamins = new int[V];
        for (int i = 0; i < V; i++) {
        	requiredVitamins[i] = Integer.parseInt(lineTokens.nextToken());
        }
        
        int G = Integer.parseInt(new StringTokenizer(f.readLine()).nextToken());
        int[][] feedVitamins = new int[G][V];
        for (int feedIndex = 0; feedIndex < G; feedIndex++) {
        	StringTokenizer feedVitaminTokens = new StringTokenizer(f.readLine());
        	for (int vitaminIndex = 0; vitaminIndex < V; vitaminIndex++) {
        		feedVitamins[feedIndex][vitaminIndex] = Integer.parseInt(feedVitaminTokens.nextToken());
        	}
        }
        f.close();
        
        holstein solver = new holstein(feedVitamins);
        List<Integer> feed = new ArrayList<Integer>();
        solver.solve(requiredVitamins, feed);
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));
		out.print(solver.minScoops + " ");
		for (int i = 0; i < solver.answer.size(); i++) {
			out.print(solver.answer.get(i) + ((i != solver.answer.size() - 1) ? " " : "\n"));
		}
		out.close();
	}
	
	private void solve(int[] neededVitamins, List<Integer> feed) {
		if (feed.size() >= this.minScoops) {
			return;
		}
		int thisFeed = feed.isEmpty() ? 0 : feed.get(feed.size() - 1) + 1;
		for (int i = thisFeed; i < feedVitamins.length; i++) {
			int[] nextFeed = this.feedVitamins[i];
			feed.add(i);
			boolean needMoreFeed = false;
			int[] newNeededVitamins = new int[neededVitamins.length];
			for (int v = 0; v < neededVitamins.length; v++) {
				newNeededVitamins[v] = neededVitamins[v] - nextFeed[v];
				if (newNeededVitamins[v] > 0) {
					needMoreFeed = true;
				}
			}
			if (needMoreFeed) {
				solve(newNeededVitamins, feed);
				feed.remove(feed.size() - 1);
			} else {
				if (feed.size() < this.minScoops) {
					this.minScoops = feed.size();
					this.answer.clear();
					for (int f : feed) {
						this.answer.add(f + 1);
					}
				}
				feed.remove(feed.size() - 1);
				break;
			}
		}
		
	}
}
