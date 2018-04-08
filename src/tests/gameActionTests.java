package tests;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;
import clueGame.Board;
import clueGame.Player;


public class gameActionTests {

	public static Player testComputerPlayer;
	public static Board board;
	
	@BeforeClass
	public static void before()  throws  BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
		testComputerPlayer = board.getPlayer(3);
	}
	
	/*
	 * This tests whether an accusation is correct or incorrect
	 */
	@Test
	public void testAccusations() {
		assertTrue(board.checkAccusation(board.solution.getPerson(), board.solution.getWeapon(), board.solution.getRoom()));
		assertFalse(board.checkAccusation(board.solution.getWeapon(), board.solution.getWeapon(), board.solution.getRoom()));
		assertFalse(board.checkAccusation(board.solution.getPerson(), board.solution.getPerson(), board.solution.getRoom()));
		assertFalse(board.checkAccusation(board.solution.getPerson(), board.solution.getWeapon(), board.solution.getWeapon()));
	}
	
	@Test
	public void testDisproveSuggestion() {
		Card tempPlayer = null;
		Card tempWeapon = null;
		Card tempRoom = null;
		for(Card c: testComputerPlayer.getHand()){
			if(c.cardType == CardType.PERSON) {
				tempPlayer = c;
			} else if(c.cardType == CardType.WEAPON) {
				tempWeapon = c;
			} else {
				tempRoom = c;
			}
		}
		if(tempPlayer != null)
			assertEquals(testComputerPlayer.DisproveSuggestion(tempPlayer, board.solution.getWeapon(), board.solution.getRoom()), tempPlayer);
		if(tempWeapon != null)
			assertEquals(testComputerPlayer.DisproveSuggestion(board.solution.getPerson(), tempWeapon, board.solution.getRoom()), tempWeapon);
		if(tempRoom != null)
			assertEquals(testComputerPlayer.DisproveSuggestion(board.solution.getPerson(), board.solution.getWeapon(), tempRoom), tempRoom);
		if(tempPlayer != null && tempWeapon != null) {
			assertTrue(testComputerPlayer.DisproveSuggestion(tempPlayer, tempWeapon,  board.solution.getRoom()) == tempPlayer || 
					testComputerPlayer.DisproveSuggestion(tempPlayer, tempWeapon,  board.solution.getRoom()) == tempWeapon);
		}
		if(tempPlayer != null && tempRoom != null) {
			assertTrue(testComputerPlayer.DisproveSuggestion(tempPlayer, tempWeapon,  board.solution.getRoom()) == tempPlayer || 
					testComputerPlayer.DisproveSuggestion(tempPlayer, tempWeapon,  board.solution.getRoom()) == tempRoom);
		}
		if(tempWeapon != null && tempRoom != null) {
			assertTrue(testComputerPlayer.DisproveSuggestion(tempPlayer, tempWeapon,  board.solution.getRoom()) == tempRoom || 
					testComputerPlayer.DisproveSuggestion(tempPlayer, tempWeapon,  board.solution.getRoom()) == tempWeapon);
		}
		assertEquals(testComputerPlayer.DisproveSuggestion(board.solution.getPerson(), board.solution.getWeapon(), board.solution.getRoom()), null);
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
