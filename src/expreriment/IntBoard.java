package expreriment;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graffs
 *
 */
public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjCells; // list of all cells adjacent cells for each cell on the board.
	private Set<BoardCell> targetCells; // list of all cells one can move to give a location and a roll of the die.
	private static int BOARD_WIDTH = 4;
	private static int BOARD_HEIGHT = 4;
	private BoardCell[][] board; // an object holding pointers to all cells on the board.
	private Set<BoardCell> visited; // used for the calculation of target cells.
	
	/**
	 * constructor:
	 * 	instantiates the game board and data structures for the adjacent cells list, visited cells
	 * 	list, and target cells list
	 */
	public IntBoard() {
		board = new BoardCell[BOARD_HEIGHT][BOARD_WIDTH];
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				board[i][j] = new BoardCell(i,j);
			}
		}
		calcAdjacencies();

		visited = new HashSet<BoardCell>();
		targetCells = new HashSet<BoardCell>();
	}
	
	/**
	 * calculates the adjacent cells to each cell on the board
	 */
	public void calcAdjacencies() {
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				BoardCell a = board[i][j];
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if(i != 0) { // top boundary case
					temp.add(board[i-1][j]);
				}
				if(i != BOARD_WIDTH -1) { // lower boundary case
					temp.add(board[i+1][j]);
				}
				if(j != 0) { // left boundary case
					temp.add(board[i][j-1]);
				}
				if(j != BOARD_HEIGHT-1) { // right boundary case
					temp.add(board[i][j+1]);
				}
				adjCells.put(a, temp);
			}
		}
	}
	
	/**
	 * returns all cells adjacent to some cell
	 * @param cell: the cell which we want to know the neighbors of
	 * @return a list of adjacent cells to the input
	 */
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjCells.get(cell);
	}	
	
	/**
	 * calculates spots the player can move to
	 * @param startCell: the cell which the player starts at
	 * @param pathLength: how many spots the player must move
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		for(BoardCell a : adjCells.get(startCell)) {
			if(!visited.contains(a)) {
				visited.add(a);
				if(pathLength == 1)
					targetCells.add(a);
				else
					calcTargets(a, pathLength - 1);
				visited.remove(a);
			}
		}
	}
	
	/**
	 * returns all cells a player can move to
	 * @return a set of all valid locations
	 */
	public Set<BoardCell> getTargets() {
		return targetCells;
	}

	/**
	 * returns a cell on the board given a row and a column
	 * @param row: a row on the board
	 * @param col: a column on the board
	 * @return a cell on the board
	 */
	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}
}
