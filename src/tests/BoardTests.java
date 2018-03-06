package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */

class BoardTests {
	public static final int LEGEND_LENGTH = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 22;
	public static Board board;
	
	Map<Character, String> legend = new HashMap<Character, String>();
	/**
	 * loads in the files to begin testing
	 */
	@Before	
	public static void Start() {
		board = Board.getInstance();
		board.loadBoardConfig("ClueRooms.csv");	
		board.loadLegendConfig("ClueRooms.txt");
		board.initialize();
	}
	
	/**
	 * tests the legend length and a few of the characters with their corresponding name 
	 */
	@Test
	public void testRooms() {
		legend = board.getLegend();
		assertEquals(LEGEND_LENGTH, legend.size());
		assertEquals("M", legend.get("Marquez"));
		assertEquals("T", legend.get("Coors Tek"));
		assertEquals("A", legend.get("Alderson"));
		assertEquals("C", legend.get("CTLM"));
		assertEquals("W", legend.get("Walkway"));
	}
	
	/**
	 * tests the dimensions of the board
	 */
	@Test
	public void testDimensions() {
		assertEquals(NUM_ROWS, board.getRows());
		assertEquals(NUM_COLUMNS, board.getColumns());
	}
	
	/**
	 * testDoorDirection tests a doorway for each way you can enter one based off of the enum
	 * It also, tests a hallway and 'closet' cell to verify it does not count any cell as a door
	 */
	@Test
	public void testDoorDirection() {
		BoardCell room = board.getCellAt(22,9);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(22,15);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(10,20);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(20,5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(13,9);
		assertFalse(room.isDoorway());
		room = board.getCellAt(12,12);
		assertFalse(room.isDoorway());
	}
	
	/**
	 * verifies number of doors is correct
	 */
	@Test
	public void testNumberOfDoors() {
		int numDoors = 0;
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLUMNS; j++) {
				BoardCell temp = board.getCellAt(i,j);
				if(temp.isDoorway()) 
					numDoors++;
			}
		}
		assertEquals(12, numDoors);
	}
	
	/**
	 * test initials of cells with the board
	 */
	@Test
	public void testRoomInitials() {
		assertEquals('H', board.getCellAt(24,0).getInitial());
		assertEquals('C', board.getCellAt(23,4).getInitial());
		assertEquals('W', board.getCellAt(13,10).getInitial());
		assertEquals('K', board.getCellAt(13,13).getInitial());
	}

}
