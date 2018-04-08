package clueGame;

import java.awt.Color;
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
	private ArrayList<Card> hand;
	public Board board;
	
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
	
	public Card DisproveSuggestion(Card person, Card weapon, Card room) {
		ArrayList<Card> disproveList = new ArrayList();
		for(Card c: hand) {
			if(c == person || c == weapon || c == room)
				disproveList.add(c);
		}
		if(disproveList.size() == 0)
			return null;
		else{
			return disproveList.get((int)(Math.random() * disproveList.size()));
		}
	}
	
	public abstract void movePlayer(int pathLength);
	
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
