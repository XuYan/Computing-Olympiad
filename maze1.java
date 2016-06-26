import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
ID: Xu Yan
LANG: JAVA
TASK: maze1
*/
/**
 * Thoughts: 
 * Pitfalls: 
 *           
 * Take-away tips: 
 * 
 * @author Xu Yan
 * @date June 23rd, 2016
 */
public class maze1 {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("maze1.in"));
		StringTokenizer input = new StringTokenizer(f.readLine());
		int W = Integer.parseInt(input.nextToken());
        int H = Integer.parseInt(input.nextToken());
        char[][] maze = new char[2*H+1][2*W+1];
        int[] exit1 = null;
		int[] exit2 = null;
        for (int h = 0; h < maze.length; h++) {
        	String row = f.readLine();
        	for (int w = 0; w < maze[0].length; w++) {
        		maze[h][w] = row.charAt(w);
        		if (maze[h][w] == ' ' && (h == 0 || h == 2*H || w == 0 || w == 2*W)) {
        			if (exit1 == null) {
        				exit1 = new int[] {adjust(h, 2*H), adjust(w, 2*W)};
        			} else {
        				exit2 = new int[] {adjust(h, 2*H), adjust(w, 2*W)};
        			}
        		}
        	}
        }
        f.close();
        
        int answer = solve(maze, exit1, exit2);
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maze1.out")));
		out.println(answer);
		out.close();
	}
	
	/**
	 * When we found an exit, we actually found an exit's boundary coordinate. We'll adjust the boundary coordinate to cell coordinate.
	 */
	private static int adjust(int x, int max_allowed_x) {
		return x == 0 ? 1 : (x == max_allowed_x ? max_allowed_x - 1 : x);
	}
	
	private static int solve(char[][] maze, int[] exit1, int[] exit2) {
		int[][] distanceToExit =  new int[maze.length/2][maze[0].length/2];
		for (int h = 0; h < distanceToExit.length; h++) {
			for (int w = 0; w < distanceToExit[0].length; w++) {
				distanceToExit[h][w] = Integer.MAX_VALUE;
			}
		}
		
		BFS(maze, distanceToExit, exit1);
		BFS(maze, distanceToExit, exit2);
		
		// find the max distance to any exit in distanceToExit
		int answer = 0;
		for (int h = 0; h < distanceToExit.length; h++) {
			for (int w = 0; w < distanceToExit[0].length; w++) {
				if (distanceToExit[h][w] > answer) {
					answer = distanceToExit[h][w];
				}
			}
		}
		return answer;
	}
	
	private static void BFS(char[][] maze, int[][] distanceToExit, int[] exit) {
		int[][] direction = new int[][] {{0,-1}, {1,0}, {0,1}, {-1,0}};
		int[][] move = new int[][] {{0,-2}, {2,0}, {0,2}, {-2,0}}; // Add to get to next_cell_position from current_cell_position
		boolean[][] visited = new boolean[distanceToExit.length][distanceToExit[0].length]; // true if we have calculated or is going to calculate a cell's distance to exit
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.add(exit);
		visited[exit[0]/2][exit[1]/2] = true;
		int distance = 1;
		while (!queue.isEmpty()) {
			int iteration_length = queue.size();
			for (int i = 0; i < iteration_length; i++) {
				int[] current_cell_position = queue.poll();
				int current_cell_h = current_cell_position[0]/2;
				int current_cell_w = current_cell_position[1]/2;
				distanceToExit[current_cell_h][current_cell_w] = distance;
				for (int direction_index = 0; direction_index < direction.length; direction_index++) {
					int[] next_boundary_position = new int[] {current_cell_position[0] + direction[direction_index][0], current_cell_position[1] + direction[direction_index][1]}; 
					if (canMove(maze, next_boundary_position)) {
						int[] next_cell_position = new int[] {current_cell_position[0] + move[direction_index][0], current_cell_position[1] + move[direction_index][1]};
						int next_cell_h = next_cell_position[0]/2;
						int next_cell_w = next_cell_position[1]/2;
						if (isCloserToExit(distanceToExit, next_cell_h, next_cell_w, distance + 1) && !visited[next_cell_h][next_cell_w]) {
							queue.add(next_cell_position);
							visited[next_cell_h][next_cell_w] = true;
						}
					}
				}
			}
			distance ++;
		}
	}
	
	/**
	 * Check the state of next boundary position
	 * @param maze
	 * @param next_boundary_position one character away from current_cell_position
	 * @return true if the state of next boundary position is neither a maze inner wall nor maze boundary. Be careful that maze boundary can be ' ' to represent exit!!!
	 */
	private static boolean canMove(char[][] maze, int[] next_boundary_position) {
		return (next_boundary_position[0] > 0 && next_boundary_position[0] < maze.length - 1)	// Be careful that the next character in a direction CANNOT be maze boundary!
				&& (next_boundary_position[1] > 0 && next_boundary_position[1] < maze[0].length - 1)
				&& maze[next_boundary_position[0]][next_boundary_position[1]] == ' ';	// If not a space character, it will be a wall ('|' or '-')
	}
	
	/**
	 * Check if we found a closer path to exit
	 * @param distanceToExit the current minimum distance of each cell to its optimal exit  
	 * @param next_cell_h the row index of a cell in maze
	 * @param next_cell_w the column index of a cell in maze
	 * @param distance the expected distance from cell position to 
	 * @return true if the expected distance is smaller than what we have calculated before
	 */
	private static boolean isCloserToExit(int[][] distanceToExit, int next_cell_h, int next_cell_w, int distance) {
		return distanceToExit[next_cell_h][next_cell_w] > distance; 
	}
}