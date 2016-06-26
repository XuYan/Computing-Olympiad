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
TASK: castle
*/
/**
 * Thoughts: For each direction of a cell, there could be a wall or not a wall (2 states). So there are 16 states in total. 
 *           If there is a wall in certain direction, the bit is set. Otherwise, the bit is not set.
 *           Therefore, we have 1, 2, 4, 8 to represent if we have wall in W, N, E, S direction.
 *           
 *           We can use DFS to give each cell an ID, and then traverse from left-bottom corner to top-right corner.
 *           For each cell, we firstly check if we can break the north wall to connect two different rooms together,
 *           then check if we can break the east wall to do the same thing.
 *           With this traversal approach, when meeting an equally optimal solution, we don't need to record it anymore.
 *           The traversal approach is like this:
 *               1   2   3   4
 *              --------------
 *           1|  4   8
 *           2|  3   7   ...
 *           3|  2   6   10
 *           4|  1   5   9
 *           
 * Pitfalls: 
 *           
 * Take-away tips: This is not the most space-efficient way to represent castle layout.
 *                 For each cell, we can use at most 2 bits to represent if it has wall in the south and east direction.
 *                 For the boundary cell, we can use 1 bit to do the same thing.
 * 
 * @author Xu Yan
 * @date May 28th, 2016
 */
public class castle {
	private static class Cell {
		int walls;
		int roomId;
		int x;
		int y;
		char removedWall;
		
		public Cell(int walls, int roomId, int rowIndex, int colIndex) {
			this.walls = walls;
			this.roomId = roomId;
			this.x = rowIndex + 1;
			this.y = colIndex + 1;
		}
	}
	
	public Cell[][] cells;
	public int roomCount;
	public List<Integer> roomSize = new ArrayList<Integer>(); // The number of cells in each room
	public int largestRoomSizeAfterRemovingWall;
	public Cell removedWallCell;
	
	public castle(Cell[][] cells) {
		this.cells = cells;
		this.roomCount = 0;
		this.roomSize.add(0);
		this.largestRoomSizeAfterRemovingWall = 0;
	}
	
	public int getLargestRoomSize() {
		int largestRoomSize = 0;
		for (int size : roomSize) {
			if (size > largestRoomSize) {
				largestRoomSize = size;
			}
		}
		return largestRoomSize;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("castle.in"));
        StringTokenizer dimension = new StringTokenizer(f.readLine());
        int M = Integer.parseInt(dimension.nextToken());
        int N = Integer.parseInt(dimension.nextToken());
        
        Cell[][] cells = new Cell[N][M];
        for (int row = 0; row < N; row++) {
        	StringTokenizer rowTokens = new StringTokenizer(f.readLine()); 
        	for (int col = 0; col < M; col++) {
        		cells[row][col] = new Cell(Integer.parseInt(rowTokens.nextToken()), -1, row, col);
        	}
        }
        f.close();
        
        castle solver = new castle(cells);
        solver.solve();
        
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
		out.println(solver.roomCount);
		out.println(solver.getLargestRoomSize());
		out.println(solver.largestRoomSizeAfterRemovingWall);
		out.println(solver.removedWallCell.x + " " + solver.removedWallCell.y + " " + solver.removedWallCell.removedWall);
		out.close();
	}
	
	private void solve() {
		int rowCount = this.cells.length;
		int colCount = this.cells[0].length;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				if (this.cells[row][col].roomId == -1) {
					this.roomCount++;
					this.roomSize.add(0);
					this.nameRoom(row, col);
				}
			}
		}
		
		for (int col = 0; col < colCount; col++) {
			for (int row = rowCount - 1; row >= 0; row--) {
				Cell currentCell = this.cells[row][col];
				// If there is a north wall and the north wall is not castle boundary, we can break it to connect two rooms
				if ((currentCell.walls & 2) != 0 && row - 1 >= 0) {
					Cell northNeighbor = this.cells[row-1][col];
					if (currentCell.roomId != northNeighbor.roomId) {
						int mergedRoomSize = this.roomSize.get(currentCell.roomId) + this.roomSize.get(northNeighbor.roomId);
						if (mergedRoomSize > this.largestRoomSizeAfterRemovingWall) {
							this.largestRoomSizeAfterRemovingWall = mergedRoomSize;
							this.removedWallCell = this.cells[row][col];
							this.removedWallCell.removedWall = 'N';
						}
					}
				}
				
				// If there is an east wall and the east wall is not castle boundary, we can break it to connect two rooms
				if ((currentCell.walls & 4) != 0 && col + 1 < colCount) {
					Cell eastNeighbor = this.cells[row][col+1];
					if (currentCell.roomId != eastNeighbor.roomId) {
						int mergedRoomSize = this.roomSize.get(currentCell.roomId) + this.roomSize.get(eastNeighbor.roomId);
						if (mergedRoomSize > this.largestRoomSizeAfterRemovingWall) {
							this.largestRoomSizeAfterRemovingWall = mergedRoomSize;
							this.removedWallCell = this.cells[row][col];
							this.removedWallCell.removedWall = 'E';
						}	
					}
				}
			}
		}
	}
	
	private void nameRoom(int row, int col) {
		Cell currentCell = this.cells[row][col];
		currentCell.roomId = this.roomCount;
		this.roomSize.set(this.roomCount, this.roomSize.get(this.roomCount) + 1);
		if ((currentCell.walls & 1) == 0  && this.cells[row][col-1].roomId == -1/* go west */) {
			nameRoom(row, col-1);
		}
		if ((currentCell.walls & 2) == 0 && this.cells[row-1][col].roomId == -1/* go north */) {
			nameRoom(row-1, col);
		}
		if ((currentCell.walls & 4) == 0 && this.cells[row][col+1].roomId == -1/* go east */) {
			nameRoom(row, col+1);
		}
		if ((currentCell.walls & 8) == 0 && this.cells[row+1][col].roomId == -1/* go south */) {
			nameRoom(row+1, col);
		}
	}
}
