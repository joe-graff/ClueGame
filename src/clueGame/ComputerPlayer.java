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
	
	
	public void movePlayer(int pathlength) {
		board.calcTargets(row, column, pathlength);
		for(BoardCell location : board.getTargets()) {
			if(location.isDoorway()) {
				if(lastRoom == null || (lastRoom.getInitial() != location.getInitial() && location != lastRoom)) {
					row = location.getRow();
					column = location.getColumn();
					Solution computerSuggestion = createSuggestion();
					System.out.println(computerSuggestion.toString());
					Board.getInstance().gameControl.updateGuess(computerSuggestion.getPerson().getCardName(), computerSuggestion.getWeapon().getCardName(), computerSuggestion.getRoom().getCardName());
					Board.getInstance().handleSuggestion(Board.getInstance().getPlayerPosition(), computerSuggestion);
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
		solution.setSolutionCard(board.getRoom(row, column), CardType.ROOM);
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
		solution.setSolutionCard(tempPeople.get((int)(Math.random() * tempPeople.size())), CardType.PERSON);
		solution.setSolutionCard(tempWeapons.get((int)(Math.random() * tempWeapons.size())), CardType.WEAPON);
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
	

