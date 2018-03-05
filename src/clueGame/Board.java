package clueGame;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */

public class Board {
	
	public Board() {
		
	}
	private static int NUM_ROWS;
	private static int NUM_COLUMNS;
	private static int MAX_BOARD_SIZE;
	private BoardCell board[][];
	private Map<Character, String> legend;
	private String boardConfigFile;
	private String legendConfigFile;
	
	public static Board getInstance() {return null;} //gets an instance of the board
	public void initialize() {} //initializes the board
	public void loadLegendConfig(String legend) {} // loads the legend
	public void loadBoardConfig(String board) {} // loads the board
	public void calcAdjacencies() {} // calculates adjacent cells
	public void calcTargets(BoardCell cell, int pathLength) {} //calculates the possible moves
	/**
	 * gets the legend
	 * @return Map<Character, String>
	 */
	public Map<Character, String> getLegend(){
		return legend;
	}
	/**
	 * sets the legend
	 * @param legend
	 */
	public void setLegend(Map<Character, String> legend) {
		this.legend=legend;
	}
	/**
	 * returns a BoardCell based of the given row and column
	 * @param row
	 * @param col
	 * @return BoardCell
	 */
	public BoardCell getCellAt(int row, int col) {
		BoardCell b = board[row][col];
		return b;
	}
	/**
	 * gets the Row
	 * @return
	 */
	public int getRows() {
		return NUM_ROWS;
	}
	/**
	 * gets the Column
	 * @return
	 */
	public int getColumns() {
		return NUM_COLUMNS;
	}
}
