package clueGame;

import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 * @author Joseph Graff and Lewis Setter
 *
 */

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> hand;
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		hand  = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
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
}
