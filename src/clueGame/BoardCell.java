package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */
public class BoardCell{
	private int row;
	private int column;
	private char initial;
	private Boolean isDoorway;
	private DoorDirection doorDirection;
	public static final int CELL_SIZE = 30;
	
	/**
	 * constructor: creates a cell on the board given a row and a column
	 * @param row: a row on the board
	 * @param column: a column on the board
	 */
	public BoardCell(int row, int column, char initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		isDoorway = false;
	}
	// Tests if a cell is a walkway
	public boolean isWalkway() {
		if(getInitial() == 'W')
			return true;
		return false;
	} 
	// Tests if a cell is a room
	public boolean isRoom() {
			if(getInitial() != 'W')
				return true;
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
	
	//getter for row
		public int getRow() {
			return row;
		} 
		
	//getter for row
		public int getColumn() {
			return column;
		} 
		
		public void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(1));
			if (initial == 'W') {
				g2.setColor(Color.white);
				g2.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				g2.setColor(Color.BLACK);
				g2.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			}
			else if (initial == 'K') {
				g2.setColor(Color.red);
				g2.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				g2.setColor(Color.red);
				g2.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			}
			else {
				g2.setColor(Color.gray);
				g2.fillRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
				g2.setColor(Color.gray);
				g2.drawRect(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
			}
			
			if (isDoorway) {
				g2.setColor(Color.blue);
				g2.setStroke(new BasicStroke(10));
				switch (doorDirection) {
				case DOWN:
					g2.drawLine(column*CELL_SIZE, row*CELL_SIZE + CELL_SIZE,  column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
					break;
				case UP:
					g2.drawLine(column*CELL_SIZE, row*CELL_SIZE,column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE);
					break;
				case RIGHT:
					g2.drawLine(column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE, column*CELL_SIZE + CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
					break;
				case LEFT:
					g2.drawLine(column*CELL_SIZE, row*CELL_SIZE, column*CELL_SIZE, row*CELL_SIZE + CELL_SIZE);
					break;
				}
			}
			
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			g.drawString("Brown", 1 * CELL_SIZE, 3 * CELL_SIZE);
			g.drawString("Alderson", 2 * CELL_SIZE, 12 * CELL_SIZE);
			g.drawString("Hill", 1 * CELL_SIZE, 22 * CELL_SIZE);
			g.drawString("Marquez", 10 * CELL_SIZE, 3 * CELL_SIZE);
			g.drawString("CTLM", 5 * CELL_SIZE, 22 * CELL_SIZE);
			g.drawString("Stratton", 9 * CELL_SIZE, 22 * CELL_SIZE);
			g.drawString("Coors Tek", 18* CELL_SIZE, 4 * CELL_SIZE);
			g.drawString("Green", 13 * CELL_SIZE, 22 * CELL_SIZE);
			g.drawString("Center", 13 * CELL_SIZE, 23 * CELL_SIZE);
			g.drawString("Berthoud", 18 * CELL_SIZE, 17 * CELL_SIZE);
		}
		
		public void paintTargets(Graphics g) {
			g.setColor(Color.cyan);
			g.fillRect(this.column*CELL_SIZE, this.row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}
}
