package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
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
	private int numPlayers;
	private BoardCell board[][];
	private Map<Character, String> legend;
	private String boardConfigFile;
	private String roomConfigFile;
	private String weaponConfigFile;
	private String playerConfigFile;
	private static Board instance = new Board(); // the only instance of the board
	private Map<BoardCell, Set<BoardCell>> adjCells; // list of all cells adjacent cells for each cell on the board.
	private Set<BoardCell> visited; // used for the calculation of target cells.
	private Set<BoardCell> targetCells; // list of all cells one can move to give a location and a roll of the die.
	public ArrayList<Card> deck;
	public Player[] players;
	
	/**
	 * constructor returns the single board
	 */
	private Board() { 
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targetCells = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		deck = new ArrayList<Card>();
	}
	
	/**
	 * gets an instance of the board
	 * @return
	 */
	public static Board getInstance() {
		return instance;
	} 
	
	/**
	 * initializes the board
	 */
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
		loadPlayerConfig();
		loadWeaponConfig();

	} 
	
	/**
	 *  loads the board
	 * @throws BadConfigFormatException
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		
		// opens and read file
		ArrayList<String> input = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(boardConfigFile);
			Scanner scan = new Scanner(reader);
			while (scan.hasNextLine()) {
				input.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(boardConfigFile + " does not exist");
		}
		
		// set rows
		NUM_ROWS = input.size();
		board = new BoardCell[input.size()][];
		
		// create columns
		for (int i = 0; i < NUM_ROWS; i++) {
			String[] split = input.get(i).split(",");
			board[i] = new BoardCell[split.length];
			if (i == 0) {
				NUM_COLUMNS = split.length;
			}
			// throws exception if columns are not consistent
			if (i > 0) {
				if (split.length != NUM_COLUMNS) {
					throw new BadConfigFormatException("column length issue in row " + i);
				}
			}
			// build the array of BoardCells
			for (int j = 0; j < split.length; j++) {
				if (legend.containsKey(split[j].charAt(0))){
					board[i][j] = new BoardCell(i,j,split[j].charAt(0));
				}
				else {
					throw new BadConfigFormatException(split[j].charAt(0) + " is not in the legend");
				}
				// determines if the cell is a door
				if (split[j].length() > 1) {
					if (split[j].charAt(1) == 'R' || split[j].charAt(1) == 'U' || split[j].charAt(1) == 'L' || split[j].charAt(1) == 'D') {
						getCellAt(i,j).setisDoorway(true);
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
	
	/**
	 *  loads the legend
	 * @throws BadConfigFormatException
	 */
	public void loadRoomConfig() throws BadConfigFormatException {
		
		//opens file	
		Scanner scan = null;
		try {
			FileReader reader = new FileReader(roomConfigFile);
			scan = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println(roomConfigFile + " does not exist");
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
					throw new BadConfigFormatException(split[2] + " is not a legal type");
				}
			}
			// puts legend together
			legend.put(split[0].charAt(0), split[1]);
			String roomName = split[1];
			if(str.equalsIgnoreCase("Card")){
				Card room = new Card(roomName, CardType.ROOM);
				deck.add(room);
			}
		}
		scan.close();
	}
	public void loadPlayerConfig() {
		FileReader iFS;
		ArrayList<String> input = new ArrayList<String>();
		try {
			iFS = new FileReader(playerConfigFile);
			Scanner scan = new Scanner(iFS);
			// read in rows
			while (scan.hasNextLine()) {
				input.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Allocate player array
		numPlayers = input.size();
		players = new Player[numPlayers];
		
		// Fill player array
		for (int i = 0; i < input.size(); i++) {
			String[] split = input.get(i).split(", ");
			Card player = new Card(split[0], CardType.PERSON);
			deck.add(player);
			Color color = convertColor(split[1]);
			int r = Integer.parseInt(split[3]);
			int c = Integer.parseInt(split[4]);
			String type = split[2];
			System.out.println(type);
			if(type.equals("Player")) {
				players[i] = new HumanPlayer(split[0], r, c, color);
			} else if(type.equals("Computer")){
				players[i] = new ComputerPlayer(split[0], r, c, color);
			}
		}
	}
	
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}
	
	public void loadWeaponConfig() {
		FileReader iFS;
		try {
			iFS = new FileReader(weaponConfigFile);
			Scanner scan = new Scanner(iFS);
			// read in rows
			while (scan.hasNextLine()) {
				String input = scan.nextLine();
				Card weapon = new Card(input, CardType.WEAPON);
				deck.add(weapon);
				//weapons.add(weapon);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * calculate all adjacencies for cells
	 */
	public void calcAdjacencies() {
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		Set<BoardCell> temp;
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				BoardCell a = board[i][j];
				temp = new HashSet<BoardCell>();
				if(a.isDoorway()) {
					DoorDirection d = a.getDoorDirection();
					switch(d) {
					case UP:		temp.add(getCellAt(i-1,j));
								break;
					case DOWN:	temp.add(getCellAt(i+1,j));
								break;
					case LEFT:	temp.add(getCellAt(i,j-1));
								break;
					case RIGHT:	temp.add(getCellAt(i,j+1));
								break;
					default:		break;
					}
				}
				else if(a.isWalkway()) {
					if(i != 0 && (getCellAt(i-1,j).isWalkway() || getCellAt(i-1,j).getDoorDirection() == DoorDirection.DOWN)) { // top boundary case
						temp.add(board[i-1][j]);
					}
					if(i != NUM_ROWS -1 && (getCellAt(i+1,j).isWalkway() || getCellAt(i+1,j).getDoorDirection() == DoorDirection.UP)) { // lower boundary case
						temp.add(board[i+1][j]);
					}
					if(j != 0 && (getCellAt(i,j-1).isWalkway() || getCellAt(i,j-1).getDoorDirection() == DoorDirection.RIGHT)) { // left boundary case
						temp.add(board[i][j-1]);
					}
					if(j != NUM_COLUMNS-1 && (getCellAt(i, j+1).isWalkway() || getCellAt(i,j+1).getDoorDirection() == DoorDirection.LEFT)) { // right boundary case
						temp.add(board[i][j+1]);
					}
				}
				adjCells.put(a,temp);
			}
		}
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
	 * returns the list of adjacent cells
	 * @return
	 */
	public Set<BoardCell> getAdjList(int row, int col){
		return adjCells.get(getCellAt(row,col));
	}
	
	/**
	 * calculate all possible moves
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(int row, int column, int pathLength) {
		BoardCell startCell = getCellAt(row,column);
		if(visited.size() == 0)
			targetCells.clear();
		visited.add(startCell);
		for(BoardCell a : adjCells.get(startCell)) {
			if(!visited.contains(a) && (a.isWalkway() || a.isDoorway())) {
				visited.add(a);
				if(pathLength == 1 || a.isDoorway())
					targetCells.add(a);
				else
					calcTargets(a.getRow(), a.getColumn(), pathLength - 1);
				visited.remove(a);
			}
		}
		visited.remove(startCell);
	}
	
	public void clearTargets(){
		targetCells.clear();
	}
	
	/**
	 * returns the list of target cells
	 */
	public Set<BoardCell> getTargets(){
		return targetCells;
	}
	
	/**
	 * returns the list of adjacent cells for one cell on the board
	 * @return
	 */
	public Set<BoardCell> getAdjacencies(BoardCell cell){
		return adjCells.get(cell);
	}
	
	/**
	 * gets the legend
	 * @return Map<Character, String>
	 */
	public Map<Character, String> getLegend(){
		return legend;
		
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
	
	public void setConfigFiles(String boardConfig, String roomConfig, String playerConfig, String weaponConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;
		playerConfigFile = playerConfig;
		weaponConfigFile = weaponConfig;
		
		
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
	
}


