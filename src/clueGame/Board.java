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
		Map<Character,String> temp = null;
		return temp;
	}
	/**
	 * sets the legend
	 * @param legend
	 */
	public void setLegend(Map<Character, String> legend) {}
	/**
	 * returns a BoardCell based of the given row and column
	 * @param row
	 * @param col
	 * @return BoardCell
	 */
	public BoardCell getCellAt(int row, int col) {
		BoardCell b = null;
		return b;
	}
	/**
	 * gets the Row
	 * @return
	 */
	public int getRows() {
		return (Integer) null;
	}
	/**
	 * gets the Column
	 * @return
	 */
	public int getColumns() {
		return (Integer) null;
	}
}
