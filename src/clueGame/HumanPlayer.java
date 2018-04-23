package clueGame;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */
import java.awt.Color;

import javax.swing.JOptionPane;

public class HumanPlayer extends Player {
	private static JOptionPane wrongBox;
	public HumanPlayer(String playerName, int row, int column, Color color, Board board) {
		super(playerName, row, column, color, board);
	}

	public void makeMove(BoardCell b) {
		if(Board.getInstance().targetCells.contains(b)) {
			row = b.getRow();
			column = b.getColumn();
		} else {
			JOptionPane errorMessage = new JOptionPane();
			errorMessage.showMessageDialog(Board.getInstance(), "This is not a valid selection", "Invalid Selection", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@Override
	public Solution createSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}

}
