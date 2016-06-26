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
TASK: wormhole
*/
/**
 * Thoughts: 
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 7th,2016
 */
public class wormhole {
	/**
	 * A worm hole class maintaining the following information
	 * (1) The coordinate of the worm hole
	 * (2) The paired worm hole (null if not paired with any worm hole)
	 * (3) The worm hole at the nearest +x direction from this worm hole (null if leaving from this worm hole will not going into any other worm hole) 
	 */
	private static class Hole
	{
		private int x, y;
		public Hole paired;
		public Hole nextEnter;
		
		public Hole(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int x() {
			return this.x;
		}
		
		public int y() {
			return this.y;
		}
	}
	
	public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("wormhole.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
        
        wormhole solver = new wormhole();
        Hole[] wormholes = solver.init(f);
        
        int ans = solver.createPairsAndRun(wormholes);
        out.println(ans);
        
        f.close();
        out.close();
    }
	
	/**
	 * Try all the pairing possibilities and try all the starting positions of cow to see if it'll get trapped
	 * @param wormholes The unpaired worm holes
	 * @return The number of distinct pairing possibilities that can get the cow trapped with current partial pairing fixed
	 */
	private int createPairsAndRun(Hole[] wormholes) {
		int N = wormholes.length;
		
		// Find the index of a to-be-paired worm hole
		int i = 0;
		for ( ; i < N; i++) {
			if (wormholes[i].paired == null) {
				break;
			}
		}
		
		int ans = 0; // Records the number of solutions with some worm holes already paired 		
		if (i == N) {
			// All worm holes have been paired
			return this.canTrap(wormholes) ? 1 : 0;
		} else {
			for (int j = i + 1; j < N; j++) {
				if (wormholes[j].paired == null) {
					this.pair(wormholes[i], wormholes[j]);
					ans += this.createPairsAndRun(wormholes);
					this.unpair(wormholes[i], wormholes[j]);
				}
			}
		}

		return ans;
	}
	
	/**
	 * Enumerate all the initial positions and simulate cow movement
	 * @param wormholes Completely paired worm holes
	 * @return true if cow can be trapped when positioned at an unlucky place in the farm
	 */
	private boolean canTrap(Hole[] wormholes) {
		int N = wormholes.length;
		for (int i = 0; i < N; i++) {
			Hole cowPosition = wormholes[i];
			for (int step = 0; step < N; step++) {
				if (cowPosition.nextEnter == null) {
					break;
				} else {
					cowPosition = cowPosition.nextEnter.paired;
				}
			}
			if (cowPosition.nextEnter != null) {
				return true; // Cow is trapped
			}
		}
		return false;
	}
	
	/**
	 * Pairing two worm holes
	 * @param w1 worm hole 1
	 * @param w2 worm hole 2
	 */
	private void pair(Hole w1, Hole w2) {
		w1.paired = w2;
		w2.paired = w1;
	}
	
	/**
	 * Unpairing two worm holes
	 * @param w1 worm hole 1
	 * @param w2 worm hole 2
	 */
	private void unpair(Hole w1, Hole w2) {
		w1.paired = null;
		w2.paired = null;
	}
	
	/**
	 * Reading input file and initialize worm hole array
	 * @param reader input file reader
	 * @return worm hole array
	 */
	private Hole[] init(BufferedReader reader) throws IOException {
		int N = Integer.parseInt(new StringTokenizer(reader.readLine()).nextToken());
		Hole[] wormholes = new Hole[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer coordinate = new StringTokenizer(reader.readLine()); 
			int x = Integer.parseInt(coordinate.nextToken());
			int y = Integer.parseInt(coordinate.nextToken());
			wormholes[i] = new Hole(x, y);
		}
		
		// Initialize the nearest worm hole at +x direction of this worm hole
		for (int i = 0; i < N - 1; i++) {
			Hole w1 = wormholes[i];
			for (int j = i + 1; j < N; j++) {
				Hole w2 = wormholes[j];
				if (w1.y != w2.y) {
					continue;
				}
				// w1 and w2 are on the same horizontal line. According to description, the x coordinate of w1 and w2 need to be different
				if (w1.x < w2.x) {
					if (w1.nextEnter == null || w1.nextEnter.x > w2.x) {
						w1.nextEnter = w2;
					}
				} else if (w2.x < w1.x) {
					if (w2.nextEnter == null || w2.nextEnter.x > w1.x) {
						w2.nextEnter = w1;
					}
				}
			}
		}
		
		return wormholes;
	}
}
