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
	@Override
	public Solution createSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}

}
