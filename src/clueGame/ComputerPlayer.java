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
}
	

