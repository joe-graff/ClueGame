package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {
	
	public Board() {
		
	}
	private static int NUM_ROWS;
	private static int NUM_COLUMNS;
	private static int MAX_BOARD_SIZE;
	private BoardCell board[][];
	private Map<Character, String> legend;
	private String boardConfigFile;
	private String legendConfigFile;
	
	public static Board getInstance() {return null;}
	public void initialize() {}
	public void loadRoomConfig() {}
	public void loadBoardConfig() {}
	public void calcAdjacencies() {}
	public void calcTargets(BoardCell cell, int pathLength) {}
	
	public String getBoardConfigFile() {
		return boardConfigFile;
	}
	
	public void setBoardConfigFile(String boardConfigFile) {
		this.boardConfigFile = boardConfigFile;
	}
	
	public String getRoomConfigFile() {
		return legendConfigFile;
	}
	
	public void setLegendConfigFile(String legendConfigFile) {
		this.legendConfigFile = legendConfigFile;
	}
	
	public Map<Character, String> getLegend(){
		return legend;
	}
	
	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}
	
	public BoardCell getCellAt(int row, int col) {
		BoardCell b = board[row][col];
		return b;
	}
	
	public int getRows() {
		return NUM_ROWS;
	}
	
	public int getColumns() {
		return NUM_COLUMNS;
	}
	
}
