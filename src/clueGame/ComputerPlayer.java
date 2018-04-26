package clueGame;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player{
	private ArrayList<Card> peopleSeen;
	private ArrayList<Card> weaponsSeen;
	private ArrayList<Card> roomsSeen;
	
	public ComputerPlayer(String playerName, int row, int column, Color color, Board board) {
		super(playerName, row, column, color, board);
		peopleSeen = new ArrayList<Card>();
		weaponsSeen = new ArrayList<Card>();
		roomsSeen = new ArrayList<Card>();
		for(Card c: hand) {
			if(c.getCardType() == CardType.PERSON) {
				peopleSeen.add(c);
			} else if(c.getCardType() == CardType.WEAPON) {
				weaponsSeen.add(c);
			} else {
				roomsSeen.add(c);
			}
		}
	}
	
	public void addToHand(Card c) {
		if(c.cardType == CardType.PERSON)
			peopleSeen.add(c);
		else if(c.cardType == CardType.WEAPON)
			weaponsSeen.add(c);
		else if(c.cardType == CardType.ROOM)
			roomsSeen.add(c);
	}
	
	/**
	 * creates a suggestion with the room the player is in currently and a random person and random weapon that the player has not yet seen. 
	 * @return
	 */
	public Solution createSuggestion() {
		ArrayList<Card> tempPeople = new ArrayList<Card>();
		ArrayList<Card> tempWeapons = new ArrayList<Card>();
		for(Card person : board.getPeople()) {
			if(!peopleSeen.contains(person)) {
				tempPeople.add(person);
			}
		}
		for(Card weapon : board.getWeapons()) {
			if(!weaponsSeen.contains(weapon)) {
				tempWeapons.add(weapon);
			}
		}
		if(Board.getInstance().accusationFlag && !hand.contains(Board.getInstance().previousSolution.getRoom())){
			if(Board.getInstance().checkAccusation(Board.getInstance().previousSolution)) {
				JOptionPane losingMessage = new JOptionPane();
				losingMessage.showMessageDialog(Board.getInstance(),  Board.getInstance().getPlayer().getPlayerName() + " Won Clue", "You Lose", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			} else {
				JOptionPane message = new JOptionPane();
				message.showMessageDialog(Board.getInstance(),  Board.getInstance().getPlayer().getPlayerName() + " made an incorrect accusation", "Incorrect Accusation", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		Solution solution = new Solution(tempPeople.get((int)(Math.random() * tempPeople.size())), tempWeapons.get((int)(Math.random() * tempWeapons.size())), board.getRoom(row, column));
		return solution;
	}

	public ArrayList<Card> weaponsSeen() {
		return weaponsSeen;
	}

	public ArrayList<Card> peopleSeen() {
		return peopleSeen;
	}
	
	public ArrayList<Card> roomsSeen() {
		return roomsSeen;
	}
}
	

