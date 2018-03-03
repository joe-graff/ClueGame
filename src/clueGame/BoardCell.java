package clueGame;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */
public class BoardCell {
	private int row;
	private int column;
	private char intial;
	
	/**
	 * constructor: creates a cell on the board given a row and a column
	 * @param row: a row on the board
	 * @param column: a column on the board
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway() {return false;}
	public boolean isRoom() {return false;}
	public boolean isDoorway() {return false;}
}
