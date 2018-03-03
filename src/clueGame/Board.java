package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows, numColumns;
	private static int MAX_BOARD_SIZE;
	private BoardCell board[][];
	private Map<BoardCell, Set<BoardCell>> legend;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board getInstance() {return null;}
	public void initialize() {}
	public void loadRoomConfig() {}
	public void loadBoardConfig() {}
	public void calcAdjacencies() {}
	public void calcTargets(BoardCell cell, int pathLength) {};
}
