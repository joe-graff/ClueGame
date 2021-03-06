package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.BoardCell;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */

public class Board extends JPanel {
	
	private static int NUM_ROWS;
	private static int NUM_COLUMNS;
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
	public Set<BoardCell> targetCells; // list of all cells one can move to give a location and a roll of the die.
	private ArrayList<Card> deck, originalDeck;
	private ArrayList<Card> people;
	private ArrayList<Card> weapons;
	private ArrayList<Card> rooms;
	private Solution solution;
	public Player[] players;
	private int playerPosition;
	public int diceRoll;
	public GameControlGUI gameControl;
	public BoardCell selectedCell;
	public Boolean accusationFlag;
	public Solution previousSolution;
	
	public int getDiceRoll() {
		return diceRoll;
	}
	
	/**
	 * constructor returns the single board
	 */
	private Board() { 
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targetCells = new HashSet<BoardCell>();
		legend = new HashMap<Character, String>();
		deck = new ArrayList<Card>();
		people = new ArrayList<Card>();
		weapons = new ArrayList<Card>();
		rooms = new ArrayList<Card>();
		playerPosition = -1;
		accusationFlag = false;
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
		calcAdjacencies();
		addMouseListener(new PanelListener());
	}
	
	private class PanelListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int cellX = y/BoardCell.CELL_SIZE;
			int cellY = x/BoardCell.CELL_SIZE;
			selectedCell = getCellAt(cellX, cellY);
			players[playerPosition % 6].makeMove(selectedCell);
			nextPlayer();
		}
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
				rooms.add(room);
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
			people.add(player);	
			Color color = convertColor(split[1]);
			int r = Integer.parseInt(split[3]);
			int c = Integer.parseInt(split[4]);
			String type = split[2];
			if(type.equals("Player")) {
				players[i] = new HumanPlayer(split[0], r, c, color, getInstance());
			} else if(type.equals("Computer")){
				players[i] = new ComputerPlayer(split[0], r, c, color, getInstance());
			}
		}
	}
	
	/**
	 * helper function to find what room player is in
	 * @param row
	 * @param col
	 * @return
	 */
	public Card getRoom(int row, int col) {
		BoardCell  b = board[row][col];
		String roomName = legend.get(b.getInitial());
		for(Card c: originalDeck) {
			if(c.getCardName().equals(roomName))
				return c;
		}
		return null;
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
				weapons.add(weapon);
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

	/**
	 * removes cards from the deck and adds them to the solution. also distributes
	 * the rest of the cards to the players
	 * @return
	 */
	public void dealDeck() {
		originalDeck = new ArrayList<Card>();
		for(Card c : deck) {originalDeck.add(c);}
		Card person = null;
		Card weapon = null;
		Card room = null;
		Collections.shuffle(deck);
		for(CardType cardType : CardType.values()) {
			for(Card card : deck) {
				if(card.getCardType() == cardType) {
					if(cardType == CardType.PERSON) {person = card;}
					else if(cardType == CardType.WEAPON) {weapon = card;}
					else {room = card;}
					break;
				}
			}
		}
		solution = new Solution(person, weapon, room);
		for(Card card : solution.returnCards()) {
			deck.remove(card);
		}
		int playerNumber = 0;
		for(Card card : deck) {
			players[playerNumber].getHand().add(card);
			playerNumber = ++playerNumber % numPlayers;
		}
		for(Player player : players) {
			for(Card card : player.getHand()) {
				deck.remove(card);
			}
		}
	}
	
	// only for testing purposes
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}

	public Player getPlayer(int playerNum) {
		return players[playerNum];
	}
	
	public Player getPlayer() {
		return players[playerPosition%6];
	}
	
	public int getPlayerPosition() {
		return playerPosition % 6;
	}
	
	public Boolean checkAccusation(Solution accusation) {
		if(solution.getPerson().equals(accusation.getPerson()) &&
		   solution.getWeapon().equals(accusation.getWeapon())&&
		   solution.getRoom().equals(accusation.getRoom())) {
			return true;
		} else {
			return false;
		}
	}
	
	public Card handleSuggestion(int playerID, Solution suggestion) {
		previousSolution = suggestion;
		selectedCell = null;
		int nextPlayerID = (playerID + 1) % numPlayers;
		while(nextPlayerID != playerID) {
			Card revealThisCard = players[nextPlayerID].disproveSuggestion(suggestion);
			if(revealThisCard != null) {
				addCardToSeen(revealThisCard);
				return revealThisCard;
			}
			nextPlayerID = (nextPlayerID + 1) % numPlayers;
		}
		accusationFlag = true;
		return null;
	}
	
	public void addCardToSeen(Card c) {
		for(int i = 0; i < numPlayers; i++)
			if(players[i] instanceof ComputerPlayer)
				((ComputerPlayer) players[i]).addToHand(c);
	}
	
	public Solution getSolution() {
		return solution;
	}

	public int getNumWeapons() {
		return weapons.size();
	}

	public ArrayList<Card> getWeapons() {
		return weapons;
	}

	public int getNumPeople() {
		return people.size();
	}
	
	public ArrayList<Card> getPeople() {
		return people;
	}
	
	public ArrayList<Card> getRooms() {
		return rooms;
	}
	
	public void paintComponent(Graphics g) {
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				board[i][j].draw(g);
			}
		}
		for(int player = 0; player < numPlayers; player++) {
			players[player].draw(g);
		}
		if(playerPosition != -1 && players[playerPosition % 6] instanceof HumanPlayer) { 
			calcTargets(players[playerPosition % 6].row, players[playerPosition % 6].column, diceRoll);
			for(BoardCell b: targetCells) {
				b.paintTargets(g);
			}
		}
	}
	
	public void nextPlayer() {
		if(playerPosition == -1 || (selectedCell != null && players[playerPosition % 6] instanceof HumanPlayer) || (selectedCell == null && players[playerPosition % 6] instanceof ComputerPlayer)){
			playerPosition++;
			if(players[playerPosition % 6] instanceof ComputerPlayer) {
				((ComputerPlayer) players[playerPosition % 6]).movePlayer(diceRoll);
			}
			diceRoll = (int)(Math.random() * 6) + 1;
			gameControl.updateTurn(players[playerPosition % 6].getPlayerName(), diceRoll);
		}
		repaint();
		selectedCell = null;
	}
	
	public int getNextPlayer() {
		return playerPosition % 6;
	}
	
	public void setGameControlGUI(GameControlGUI gui) {
		gameControl = gui;
	}
	
	private class MouseControl implements MouseListener {

		// unused function from MouseListener
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mouseClicked(MouseEvent e) {}

		// unused function from MouseListener
		@Override
		public void mouseEntered(MouseEvent e) {}

		// unused function from MouseListener
		@Override
		public void mouseExited(MouseEvent e) {}
		
		// TBD
		@Override
		public void mousePressed(MouseEvent e) {}
		
	}
}


