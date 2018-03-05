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
	
	public boolean isWalkway() {return false;} // Tests if a cell is a walkway
	public boolean isRoom() {return false;} // Tests if a cell is a room
	public boolean isDoorway() {return false;} //Tests if a cell is a doorway
	public DoorDirection getDoorDirection() {return null;} // Gets the direction of the door (enum)
	public char getInitial() {return (Character) null;} //Gets the initial of the cell
}
