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
	private char initial;
	
	/**
	 * constructor: creates a cell on the board given a row and a column
	 * @param row: a row on the board
	 * @param column: a column on the board
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	// Tests if a cell is a walkway
	public boolean isWalkway() {
		if(getInitial() == 'W')
			return true;
		return false;
	} 
	// Tests if a cell is a room
	public boolean isRoom() {
		return false;
	}
	//Tests if a cell is a doorway
	public boolean isDoorway() {
		return false;
	} 
	// Gets the direction of the door (enum)
	public DoorDirection getDoorDirection() {
		return getDoorDirection();
	} 
	//Gets the initial of the cell
	public char getInitial() {
		return initial;
	}
}
