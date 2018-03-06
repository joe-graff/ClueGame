package clueGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */

public class Board {
	

	
	private static int NUM_ROWS;
	private static int NUM_COLUMNS;
	private static int MAX_BOARD_SIZE;
	private BoardCell board[][];
	private Map<Character, String> legend;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board instance = new Board();
	private Map<BoardCell, Set<BoardCell>> adjCells; // list of all cells adjacent cells for each cell on the board.
	private Set<BoardCell> visited; // used for the calculation of target cells.
	private Set<BoardCell> targetCells; // list of all cells one can move to give a location and a roll of the die.
	
	// constructor returns the only board
	private Board() { 
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targetCells = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
	}
	
	//gets an instance of the board
	public static Board getInstance() {
		return instance;
	} 
	
	//initializes the board
	public void initialize() {
		try {
			loadRoomConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		try {
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		calcAdjacencies();
	} 
	
	// loads the board
	public void loadBoardConfig() throws BadConfigFormatException {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			FileReader input = new FileReader(boardConfigFile);
			Scanner reader = new Scanner(input);
			while(reader.hasNextLine()) {
				lines.add(reader.nextLine());
			}
			reader.close();
		} 
		catch (FileNotFoundException e) {
				System.out.println(boardConfigFile + " not found");
		}
		board = new BoardCell[lines.size()][];
		for (int i = 0; i < lines.size(); i++) {
			String[] split = lines.get(i).split(",");
			board[i] = new BoardCell[split.length];
			if (i == 0) {
				NUM_COLUMNS = split.length;
			}
			else {
				if (split.length != NUM_COLUMNS) {
					throw new BadConfigFormatException("Row " + i + " is a different length than the previous row");
				}
				for (int j = 0; j < split.length; j++) {
					if (legend.containsKey(split[j].charAt(0))){
						board[i][j] = new BoardCell(i,j,split[j].charAt(0));
					}
					else {
						throw new BadConfigFormatException(split[j].charAt(0) + " is not in the legend");
					}
					if (split[j].length() > 1) {
						if (split[j].charAt(1) == 'R' || split[j].charAt(1) == 'U' || split[j].charAt(1) == 'L' || split[j].charAt(1) == 'D') {
							board[i][j].setIsDoor(true);
							switch(split[j].charAt(1)) {
							case 'D':
								board[i][j].setDoorDirection(DoorDirection.DOWN);
								break;
							case 'U':
								board[i][j].setDoorDirection(DoorDirection.UP);
								break;
							case 'L':
								board[i][j].setDoorDirection(DoorDirection.LEFT);
								break;
							case 'R':
								board[i][j].setDoorDirection(DoorDirection.RIGHT);
								break;
							}

						}
					}
				}
			}

		}
	}
	
	// loads the legend
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(roomConfigFile);
			Scanner legendInfo = new Scanner(reader);
			while (legendInfo.hasNextLine()) {
				String input = legendInfo.nextLine();
				String[] split = input.split(", ");
				if (split.length != 2) {
					throw new BadConfigFormatException("There are not 2 entries for one of the lines in the file");
				}
				if (split[0].length() > 2) {
					throw new BadConfigFormatException("The symbol " + split[0] + " is too long");
				}
				String str = split[2];
				if (!str.equalsIgnoreCase("Card")) {
					if (!str.equalsIgnoreCase("Other")) {
						throw new BadConfigFormatException("One of the cell types in the file is not legal");
					}
				}
				legend.put(split[0].charAt(0), split[1]);
				legendInfo.close(); 
			}
		} catch (FileNotFoundException e) {
			System.out.println(roomConfigFile + " not found");
		}
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				BoardCell a = board[i][j];
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if(i != 0) {
					temp.add(board[i-1][j]);
				}
				if(i != NUM_COLUMNS -1) {
					temp.add(board[i+1][j]);
				}
				if(j != 0) { 
					temp.add(board[i][j-1]);
				}
				if(j != NUM_ROWS-1) { 
					temp.add(board[i][j+1]);
				}
				adjCells.put(a, temp);
			}
		}
	}
	
	//calculates the possible moves
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		for(BoardCell a : adjCells.get(startCell)) {
			if(!visited.contains(a)) {
				visited.add(a);
				if(pathLength == 1)
					targetCells.add(a);
				else
					calcTargets(a, pathLength - 1);
				visited.remove(a);
			}
		}
	}
	
	/**
	 * gets the legend
	 * @return Map<Character, String>
	 */
	public Map<Character, String> getLegend(){
		return legend;
		
	}
	/**
	 * sets the legend
	 * @param legend
	 */
	
	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}
	
	/**
	 * returns a BoardCell based of the given row and column
	 * @param row
	 * @param col
	 * @return BoardCell
	 */
	public BoardCell getCellAt(int row, int col) {
		BoardCell b = board[row][col];
		return b;
	}
	
	/**
	 * gets the Row
	 * @return
	 */
	public int getNumRows() {
		return NUM_ROWS;
	}
	
	/**
	 * gets the Column
	 * @return
	 */
	public int getNumColumns() {
		return NUM_COLUMNS;
	}
	public void setConfigFiles(String boardConfig, String roomConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;
		
	}
}
