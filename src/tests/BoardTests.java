package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

class BoardTests {
	public static final int LEGEND_LENGTH = 1;
	public static final int NUM_ROWS = 1;
	public static final int NUM_COLUMNS = 1;
	public static Board board;
	
	Map<Character, String> legend = new HashMap<Character, String>();
	
	@Before	
	public static void Start() {
		board = Board.getInstance();
		board.setBoardConfigFile("CTest_ClueLayout.csv");	
		board.setLegendConfigFile("CTest_ClueLegend.txt");
		board.initialize();
	}
	
	@Test
	public void testRooms() {
		legend = board.getLegend();
		assertEquals(LEGEND_LENGTH, legend.size());
		assertEquals("", legend.get(""));
		assertEquals("", legend.get(""));
		assertEquals("", legend.get(""));
		assertEquals("", legend.get(""));
		assertEquals("", legend.get(""));
	}
	
	@Test
	public void testDimensions() {
		assertEquals(NUM_ROWS, board.getRows());
		assertEquals(NUM_COLUMNS, board.getColumns());
	}
	
	@Test
	public void testDoorDirection() {
		BoardCell room = board.getCellAt(0,0);
		assertTrue(room.isDoorway());
		//assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(0,0);
		assertTrue(room.isDoorway());
		//assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(0,0);
		assertTrue(room.isDoorway());
		//assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(0,0);
		assertTrue(room.isDoorway());
		//assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(0,0);
		assertFalse(room.isDoorway());
		room = board.getCellAt(0,0);
		assertFalse(room.isDoorway());
	}
	
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
		assertEquals(1, numDoors);
	}
	
	@Test
	public void testRoomInitials() {
		//assertEquals('A', board.getCellAt(1,1).getInital());
		//assertEquals('A', board.getCellAt(1,1).getInital());
		//assertEquals('A', board.getCellAt(1,1).getInital());
		//assertEquals('A', board.getCellAt(1,1).getInital());
	}

}
