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
		// opens file
		FileReader iFS;
		ArrayList<String> input = new ArrayList<String>();
		try {
			iFS = new FileReader(boardConfigFile);
			Scanner scan = new Scanner(iFS);
			// read in rows
			while (scan.hasNextLine()) {
				input.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//set rows
		NUM_ROWS = input.size();
		board = new BoardCell[input.size()][];
		//allocate columns
		for (int i = 0; i < input.size(); i++) {
			String[] split = input.get(i).split(",");
			board[i] = new BoardCell[split.length];
			if (i == 0) {
				NUM_COLUMNS = split.length;
			}
			//throws exception if columns are not consistent
			if (i > 0) {
				if (split.length != NUM_COLUMNS) {
					throw new BadConfigFormatException("Row " + i + " doesnt have the same number of columns.");
				}
			}
			// puts board together
			for (int j = 0; j < split.length; j++) {
				if (legend.containsKey(split[j].charAt(0))){
					board[i][j] = new BoardCell(i,j,split[j].charAt(0));
				}
				else {
					throw new BadConfigFormatException("The letter " + split[j].charAt(0) + " is not in the legend");
				}
				// determines if location is a door
				if (split[j].length() > 1) {
					if (split[j].charAt(1) == 'R' || split[j].charAt(1) == 'U' || split[j].charAt(1) == 'L' || split[j].charAt(1) == 'D') {
						board[i][j].setisDoorway(true);
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
	
	// loads the legend
	public void loadRoomConfig() throws BadConfigFormatException {
		//opens file
		FileReader iFS;
		Scanner scan = null;
		try {
			iFS = new FileReader(roomConfigFile);
			scan = new Scanner(iFS);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//reads in the lines of the file
		while (scan.hasNextLine()) {
			String input = scan.nextLine();
			String[] split = input.split(", ");
			//throws exception if not all contents are available
			if (split.length < 3) {
				throw new BadConfigFormatException("There are not 3 entries for this line" + split[0]);
			}
			String str = split[2];
			// throws exception if room is not of type card or other
			if (!str.equalsIgnoreCase("Card")) {
				if (!str.equalsIgnoreCase("Other")) {
					throw new BadConfigFormatException("Type set Error: " + split[1] + " " + split[2]);
				}
			}
			// puts legend together
			legend.put(split[0].charAt(0), split[1]);
		}
		scan.close();
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < NUM_ROWS - 1; i++) {
			for (int j = 0; j < NUM_COLUMNS - 1; j++) {
				BoardCell a = board[i][j];
				Set<BoardCell> temp = new HashSet<BoardCell>();
				if(i != 0) {
					temp.add(board[i-1][j]);
				}
				if(i != NUM_COLUMNS - 2) {
					temp.add(board[i+1][j]);
				}
				if(j != 0) { 
					temp.add(board[i][j-1]);
				}
				if(j != NUM_ROWS - 2) { 
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