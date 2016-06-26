import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/*
ID: Xu Yan
LANG: JAVA
TASK: ttwo
*/
/**
 * Thoughts: I approach the problem by simulation. I think the tricky point is how to save state efficiently so that we can know if they'll never meet quickly 
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date May 25th, 2016
 */
public class ttwo {
	private enum Direction {
		North(1, new int[]{-1,0}), East(2, new int[]{0,1}), South(3, new int[]{1,0}), West(4, new int[]{0,-1});
		private final int value;
		private final int[] offset;
		
		Direction(int value, int[] offset) {
			this.value = value;
			this.offset = offset;
		}
	}
	
	private static class MovingObject {
		private char[][] graph;
		int[] coordinate;
		Direction direction;
		
		public MovingObject(char[][] graph, int[] coordinate, Direction direction) {
			this.graph = graph;
			this.coordinate = coordinate;
			this.direction = direction;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof MovingObject)) {
				return false;
			}
			MovingObject that = (MovingObject) obj;
			return this.coordinate[0] == that.coordinate[0] && this.coordinate[1] == that.coordinate[1];
		}
		
		@Override
		public int hashCode() {
			int result = 17;
			result = result * 31 + this.coordinate[0];
			result = result * 31 + this.coordinate[1];
			result = result * 31 + this.direction.value;
			return result;
		}
		
		public void move() {
			int new_row = this.coordinate[0] + this.direction.offset[0];
			int new_col = this.coordinate[1] + this.direction.offset[1];
			if (new_row >= 0 && new_row < graph.length && new_col >= 0 && new_col < graph[0].length && this.graph[new_row][new_col] != '*') {
				this.coordinate[0] = new_row;
				this.coordinate[1] = new_col;
			} else {
				changeDirection();
			}
		}
		
		private void changeDirection() {
			switch (this.direction) {
				case North:
					this.direction = Direction.East;
					break;
				case East:
					this.direction = Direction.South;
					break;
				case South:
					this.direction = Direction.West;
					break;
				case West:
					this.direction = Direction.North;
					break;
			}
		}
	}
	
	private static int getState(MovingObject[] objects) {
		Arrays.sort(objects, new Comparator<MovingObject>() {
			@Override
			public int compare(MovingObject o1, MovingObject o2) {
				// Priority: more South -> more West
				return (o1.coordinate[0] != o2.coordinate[0]) ? (o1.coordinate[0] - o2.coordinate[0]) : (o1.coordinate[1] - o2.coordinate[1]);
			}
		});
		
		String state = "" + objects[0].coordinate[0] + objects[0].coordinate[1] + objects[0].direction.value + objects[1].coordinate[0] + objects[1].coordinate[1] + objects[1].direction.value;
		return Integer.parseInt(state);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("ttwo.in"));
		MovingObject farmer = null;
		MovingObject cows = null;
		Set<Integer> states = new HashSet<Integer>(); // The states we've already been to. If we're at a state we've been to before, farmer and cows will never meet
		char[][] graph = new char[10][10];
		for (int row = 0; row < 10; row++) {
			String row_data = f.readLine();
			for (int col = 0; col < 10; col++) {
				graph[row][col] = row_data.charAt(col);
				if (graph[row][col] == 'F') {
					farmer = new MovingObject(graph, new int[]{row, col}, Direction.North);
				} else if (graph[row][col] == 'C') {
					cows = new MovingObject(graph, new int[]{row, col}, Direction.North);
				}
			}
		}
		f.close();
		
		// Save current state
		states.add(getState(new MovingObject[] {farmer, cows}));
		int steps = 0;
		while (!farmer.equals(cows)) {
			farmer.move();
			cows.move();
			int new_state = getState(new MovingObject[] {farmer, cows}); 
			if (states.contains(new_state)) {
				steps = 0;
				break;
			}
			states.add(new_state);
			steps++;
		}
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ttwo.out")));
		out.println(steps);
		out.close();
	}
}
