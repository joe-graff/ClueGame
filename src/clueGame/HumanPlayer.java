package clueGame;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */
import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, int row, int column, Color color, Board board) {
		super(playerName, row, column, color, board);
	}

	public void makeMove(BoardCell b) {
		row = b.getRow();
		column = b.getColumn();
	}
	
	@Override
	public Solution createSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}

}
