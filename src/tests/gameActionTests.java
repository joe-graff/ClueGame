package tests;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;


public class gameActionTests {

	public static Player testComputerPlayer;
	public static Board board;
	
	
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
		testComputerPlayer = board.getPlayer(3);
	}
	
	/*
	 * This tests the ability of a computer player to move considering the rules for movement that appear
	 * on canvas
	 */
	@Test 
	public void TestACompMovement() {
		testComputerPlayer.movePlayer(4);
		assertEquals(testComputerPlayer.getRow(), 22); // tests if the computer player entered the room
		assertEquals(testComputerPlayer.getColumn(), 9);
		testComputerPlayer.movePlayer(8);
		assertEquals(testComputerPlayer.getRow(), 20); // tests if the computer player entered another room
		assertEquals(testComputerPlayer.getColumn(), 5);
		testComputerPlayer.movePlayer(1);
		assertTrue(board.getCellAt(testComputerPlayer.getRow(), testComputerPlayer.getColumn()).isWalkway()); // tests if player left room
	}
}
