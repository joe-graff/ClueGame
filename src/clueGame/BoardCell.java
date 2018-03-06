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
	private Boolean isDoorway;
	private DoorDirection doorDirection;
	
	/**
	 * constructor: creates a cell on the board given a row and a column
	 * @param row: a row on the board
	 * @param column: a column on the board
	 */
	public BoardCell(int row, int column, char initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		isDoorway = new Boolean(false);
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
		return isDoorway;
	} 
	// Gets the direction of the door (enum)
	public DoorDirection getDoorDirection() {
		return doorDirection;
	} 
	//Gets the initial of the cell
	public char getInitial() {
		return initial;
	}
	
	public void setisDoorway(boolean b) {
		isDoorway = b;
	}
	
	public void setDoorDirection(DoorDirection dir) {
		doorDirection = dir;
	}
}
