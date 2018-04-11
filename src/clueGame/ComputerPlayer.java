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
		solution.setSolutionCard(board.getRoom(row, column), CardType.ROOM);
		for(Card person : board.getPeople()) {
			if(!peopleSeen.contains(person)) {
				solution.setSolutionCard(board.getPeople().get((int)(Math.random() * board.getPeople().size())), CardType.PERSON);
			}
		}
		for(Card weapon : board.getWeapons()) {
			if(!weaponsSeen.contains(weapon)) {
				solution.setSolutionCard(board.getWeapons().get((int)(Math.random() * board.getWeapons().size())), CardType.WEAPON);
			}
		}
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
	

