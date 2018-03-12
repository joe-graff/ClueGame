package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */

public class BoardTests {
	public static final int LEGEND_LENGTH = 11;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 22;
	public static Board board;
	
	/**
	 * loads in the files to begin testing
	 * @throws BadConfigFormatException 
	 */
	@Before	
	public void Start() throws BadConfigFormatException {
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt");
		board.loadRoomConfig();
		board.loadBoardConfig();	
		board.initialize();
	}
	
	/**
	 * tests the legend length and a few of the characters with their corresponding name 
	 */
	@Test
	public void testRooms() {
		assertEquals(LEGEND_LENGTH, board.getLegend().size());
		assertEquals("Kafadar", board.getLegend().get('K'));
		assertEquals("Coors Tek", board.getLegend().get('T'));
		assertEquals("Alderson", board.getLegend().get('A'));
		assertEquals("CTLM", board.getLegend().get('C'));
		assertEquals("Walkway", board.getLegend().get('W'));
	}
	
	/**
	 * tests the dimensions of the board
	 */
	@Test
	public void testDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	/**
	 * testDoorDirection tests a doorway for each way you can enter one based off of the enum
	 * It also, tests a hallway and 'closet' cell to verify it does not count any cell as a door
	 */
	@Test
	public void testDoorDirection() {
		BoardCell room = board.getCellAt(22,9);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(22,15);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(10,20);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(20,5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
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
	
	/**
	 * test a cell with only walkways as adjacent cells
	 */
	@Test
	public void testOnlyWalkwaysAdjacent() {
		BoardCell walkway = board.getCellAt(17,9);
		assertTrue(walkway.isWalkway());
		Set<BoardCell> temp = board.getAdjList(17,9);
		for(BoardCell a: temp) {
			assertTrue(a.isWalkway());
		}
	}
	
	/**
	 * tests the adjacent cells for a cell on each edge of board
	 */
	@Test
	public void testEdgeOfBoard() {
		Set<BoardCell> testList = board.getAdjList(8,0);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(7,0)));
		assertTrue(testList.contains(board.getCellAt(8,1)));
		testList = board.getAdjList(0,6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(0,5)));
		assertTrue(testList.contains(board.getCellAt(0,7)));
		assertTrue(testList.contains(board.getCellAt(1,6)));
		testList = board.getAdjList(11,21);
		assertEquals(2,testList.size());
		assertTrue(testList.contains(board.getCellAt(12,21)));
		assertTrue(testList.contains(board.getCellAt(11,20)));
		testList = board.getAdjList(24,12);
		assertEquals(1,testList.size());
		assertTrue(testList.contains(board.getCellAt(23,12)));
	}
	
	/**
	 * tests cells adjacent to doorways with needed direction
	 */
	@Test
	public void testAdjToDoor() {
		Set<BoardCell> testList = board.getAdjList(12,8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(13,8)));
		assertTrue(testList.contains(board.getCellAt(11,8)));
		assertTrue(testList.contains(board.getCellAt(12,7)));
		assertTrue(testList.contains(board.getCellAt(12,9)));
		testList = board.getAdjList(19,2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(19,1)));
		assertTrue(testList.contains(board.getCellAt(19,3)));
		assertTrue(testList.contains(board.getCellAt(18,2)));
	}
	
	@Test
	public void adjTestInRoom() {
		assertEquals(0, board.getAdjList(3,20).size()); // mid room
		assertEquals(0, board.getAdjList(0,0).size()); // corner of board
		assertEquals(0, board.getAdjList(6,0).size()); // by walkway
		assertEquals(0, board.getAdjList(5,4).size()); // by walkway and door
		assertEquals(0, board.getAdjList(12,6).size()); // by door
	}
	
	/**
	 * test adjacency list of a cell next to a door
	 */
	@Test
	public void adjTestByDoor() {
		assertEquals(3, board.getAdjList(5,13).size()); // by room and door
		assertEquals(4, board.getAdjList(4,14).size()); // by door
	}
	
	/**
	 * test adjacency list of a cell that is a door
	 */
	@Test
	public void adjTestInDoor() {
		assertTrue(board.getAdjList(6,4).size() == 1);
		assertTrue(board.getAdjList(6,4).contains(board.getCellAt(6,5))); // adjacent cell to right
		assertTrue(board.getAdjList(22,9).size() == 1);
		assertTrue(board.getAdjList(22,9).contains(board.getCellAt(22,8))); // adjacent cell to left
		assertTrue(board.getAdjList(4,13).size() == 1);
		assertTrue(board.getAdjList(4,13).contains(board.getCellAt(5,13))); // adjacent cell to below
		assertTrue(board.getAdjList(20,5).size() == 1);
		assertTrue(board.getAdjList(20,5).contains(board.getCellAt(19,5))); // adjacent cell to above
	}
	
	//test target list when a player can enter a room
	@Test
	public void testRoomEntry() {
		board.calcTargets(11, 20, 3);
		assertTrue(board.getTargets().size() == 8);
		assertTrue(board.getTargets().contains(board.getCellAt(12, 20)));
		assertTrue(board.getTargets().contains(board.getCellAt(11, 21)));
		assertTrue(board.getTargets().contains(board.getCellAt(11, 19)));
		assertTrue(board.getTargets().contains(board.getCellAt(11, 17)));
		assertTrue(board.getTargets().contains(board.getCellAt(12, 18)));
		assertTrue(board.getTargets().contains(board.getCellAt(10, 18)));
		assertTrue(board.getTargets().contains(board.getCellAt(10, 20)));
		assertTrue(board.getTargets().contains(board.getCellAt(13, 20)));
	}

}
