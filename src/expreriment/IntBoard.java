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
	
	public IntBoard() {
		
		board = new BoardCell[BOARD_HEIGHT][BOARD_WIDTH];

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
				
				if (i - 1 >= 0) {
					Set<BoardCell> newAdjCells = new HashSet<BoardCell>();
					if (adjCells.get(board[i][j]) != null) {
						newAdjCells = adjCells.get(board[i][j]);
					}
					newAdjCells.add(board[i - 1][j]);
					adjCells.put(board[i][j], newAdjCells);
				}
				
				if (i + 1 < BOARD_HEIGHT) {
					Set<BoardCell> newAdjCells = new HashSet<BoardCell>();
					if (adjCells.get(board[i][j]) != null) {
						newAdjCells = adjCells.get(board[i][j]);
					}
					newAdjCells.add(board[i + 1][j]);
					adjCells.put(board[i][j], newAdjCells);
				}
				
				if (j - 1 >= 0) {
					Set<BoardCell> newAdjCells = new HashSet<BoardCell>();
					if (adjCells.get(board[i][j]) != null) {
						newAdjCells = adjCells.get(board[i][j]);
					}
					newAdjCells.add(board[i][j - 1]);
					adjCells.put(board[i][j], newAdjCells);
				}
				
				if (j + 1 < BOARD_WIDTH) {
					Set<BoardCell> newAdjCells = new HashSet<BoardCell>();
					if (adjCells.get(board[i][j]) != null) {
						newAdjCells = adjCells.get(board[i][j]);
					}
					newAdjCells.add(board[i][j + 1]);
					adjCells.put(board[i][j], newAdjCells);
				}
			}
		}
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjCells.get(cell);
	}	
	
	public void calcTargets(BoardCell startCell, int pathLength) { // finish later
		return;
	}
	
	public Set<BoardCell> getTargets() { // finish lter
		return targetCells;
	}

	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}
}
