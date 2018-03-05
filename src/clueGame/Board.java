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
	public String getBoardConfigFile() {return null;}
	public void setBoardConfigFile(String boardConfigFile) {}
	public String getRoomConfigFile() {return null;}
	public void setLegendConfigFile(String legendConfigFile) {}
	public Map<Character, String> getLegend(){
		Map<Character,String> temp = null;
		return temp;
	}
	public void setLegend(Map<Character, String> legend) {}
	public BoardCell getCellAt(int row, int col) {
		BoardCell b = null;
		return b;
	}
	public int getRows() {
		return (Integer) null;
	}
	public int getColumns() {
		return (Integer) null;
	}
	
}
