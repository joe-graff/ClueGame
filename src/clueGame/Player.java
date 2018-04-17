package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * 
 * @author Joseph Graff and Lewis Setter
 *
 */

public abstract class Player {
	private String playerName;
	protected int row;
	protected int column;
	protected BoardCell lastRoom;
	private Color color;
	protected ArrayList<Card> hand;
	protected Board board;
	private static final int CELL_SIZE = 30;
	

	public Player(String playerName, int row, int column, Color color, Board board) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		hand  = new ArrayList<Card>();
		this.board = board;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disproveList = new ArrayList<Card>();
		for(Card c: hand) {
			if(c == suggestion.getPerson() || c == suggestion.getWeapon() || c == suggestion.getRoom())
				disproveList.add(c);
		}
		if(disproveList.size() == 0)
			return null;
		else{
			return disproveList.get((int)(Math.random() * disproveList.size()));
		}
	}
	
	public abstract void movePlayer(int pathLength);
	
	public String getPlayerName() {
		return playerName;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public Color getColor() {
		return color;
	}

	public abstract Solution createSuggestion();

	public Card getRoom() {
		return board.getRoom(row, column);
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		g.drawOval(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}
}
