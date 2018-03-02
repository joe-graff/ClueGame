package expreriment;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjCells;
	private Set<BoardCell> targetCells;
	private static int BOARD_WIDTH = 4;
	private static int BOARD_HEIGHT = 4;
	private BoardCell[][] board;
	private Set<BoardCell> visited;
	
public IntBoard() {
		
		board = new BoardCell[BOARD_HEIGHT][BOARD_WIDTH];
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				board[i][j] = new BoardCell(i,j);
			}
		}
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				BoardCell a = board[i][j];
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if(i != 0) {
					temp.add(board[i-1][j]);
				}
				if(i != BOARD_WIDTH -1) {
					temp.add(board[i+1][j]);
				}
				if(j != 0) {
					temp.add(board[i][j-1]);
				}
				if(j != BOARD_HEIGHT-1) {
					temp.add(board[i][j+1]);
				}
				adjCells.put(a, temp);
			}
		}
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjCells.get(cell);
	}	
	
	public void calcTargets(BoardCell startCell, int pathLength) { 
		Set<BoardCell> adj = getAdjList(startCell);
		for(BoardCell a: adj) {
			if(visited.contains(a))
				continue;
			visited.add(a);
			if(pathLength == 1)
				targetCells.add(a);
			else
				calcTargets(a, pathLength-1);
			visited.remove(a);
		}
	}
	
	public Set<BoardCell> getTargets() { // finish lter
		return targetCells;
	}

	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}
}
