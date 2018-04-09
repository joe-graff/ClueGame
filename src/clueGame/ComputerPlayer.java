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

public class ComputerPlayer extends Player{
	public ComputerPlayer(String playerName, int row, int column, Color color, Board board) {
		super(playerName, row, column, color, board);
		possibleCards = new ArrayList();
		for(Card c: hand) {
			possibleCards.remove(c);
		}
	}

	@Override
	public void movePlayer(int pathlength) {
		board.calcTargets(row, column, pathlength);
		for(BoardCell location : board.getTargets()) {
			if(location.isDoorway()) {
				if(lastRoom == null || location != lastRoom) {
					row = location.getRow();
					column = location.getColumn();
					lastRoom = location;
					return;
				}
			}
		}
		ArrayList<BoardCell> randomTargetCells = new ArrayList<BoardCell>();
		for(BoardCell location : board.getTargets()) {
			randomTargetCells.add(location);
		}
		Collections.shuffle(randomTargetCells);
		row = randomTargetCells.get(0).getRow();
		column = randomTargetCells.get(0).getColumn();
	}
	
	/**
	 * creates a suggestion with the room the player is in currently and a random person and random weapon that the player has not yet seen. 
	 * @return
	 */
	public Solution createSuggestion() {
		Solution solution = new Solution();
		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> weapons = new ArrayList<Card>();
		Card room = board.getRoom(row, column);
		solution.setSolutionCard(room, CardType.ROOM);
		for(Card c: possibleCards) {
			if(c.cardType == CardType.PERSON) {
				people.add(c);
			} else if(c.cardType == CardType.WEAPON) {
				weapons.add(c);
			}
		}
		solution.setSolutionCard(people.get((int)(Math.random() * people.size())), CardType.PERSON);
		solution.setSolutionCard(weapons.get((int)(Math.random() * weapons.size())), CardType.WEAPON);
		return solution;
	}
	
	public ArrayList<Card> getPossibleCards(){
		return possibleCards;
	}
}
	

