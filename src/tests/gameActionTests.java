package tests;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;


class gameActionTests {

	Player testComputerPlayer;
	Board board;
	
	
	@BeforeClass
	public void setup() {
		board = Board.getInstance();
		testComputerPlayer = board.getPlayer(3);
	}
	
	/*
	 * This tests the ability of a computer player to move considering the rules for movement that appear
	 * on canvas
	 */
	@Test 
	public void TestACompMovement() {
		testComputerPlayer.movePlayer(4);
		assertEquals(testComputerPlayer.getRow(), 24); // tests if the computer player entered the room
		assertEquals(testComputerPlayer.getColumn(), 9);
		testComputerPlayer.movePlayer(8);
		assertEquals(testComputerPlayer.getRow(), 20); // tests if the computer player entered another room
		assertEquals(testComputerPlayer.getColumn(), 5);
		testComputerPlayer.movePlayer(1);
		assertTrue(board.getCellAt(testComputerPlayer.getRow(), testComputerPlayer.getColumn()).isWalkway()); // tests if player left room
	}
}
