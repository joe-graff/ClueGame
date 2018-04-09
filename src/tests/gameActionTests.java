package tests;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;
import clueGame.Player;


public class gameActionTests {

	public static Player testComputerPlayer;
	public static Board board;
	
	@BeforeClass
	public static void before()  throws  BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
		board.dealDeck();
		testComputerPlayer = board.getPlayer(3);
	}
	
	/**
	 * This tests whether an accusation is correct or incorrect
	 */
	@Test
	public void testAccusations() {
		assertTrue(board.checkAccusation(board.getSolution().getPerson(), board.getSolution().getWeapon(), board.getSolution().getRoom()));
		assertFalse(board.checkAccusation(board.getSolution().getWeapon(), board.getSolution().getWeapon(), board.getSolution().getRoom()));
		assertFalse(board.checkAccusation(board.getSolution().getPerson(), board.getSolution().getPerson(), board.getSolution().getRoom()));
		assertFalse(board.checkAccusation(board.getSolution().getPerson(), board.getSolution().getWeapon(), board.getSolution().getWeapon()));
	}
	
	/**
	 * This tests when a suggestion is made, if the player has only one of these cards, the player should return that card,
	 * if the player has multiple of these cards, then the player should return one of the cards at random,
	 * if the  player has none of the cards the player should return null.
	 */
	@Test
	public void testdisproveSuggestion() {
		Card tempPlayer = null;
		Card tempWeapon = null;
		Card tempRoom = null;
		Solution suggestion = new Solution();
		for(Card c: testComputerPlayer.getHand()){
			if(c.cardType == CardType.PERSON) {
				tempPlayer = c;
			} else if(c.cardType == CardType.WEAPON) {
				tempWeapon = c;
			} else {
				tempRoom = c;
			}
		}
		// tests if the player has none of the cards from the suggestion
		if(tempPlayer != null && tempWeapon != null && tempRoom != null) {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), null);
		}			
		// tests if the player has multiple of the cards from the suggestion
		if(tempPlayer != null && tempWeapon != null) {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM);
			assertTrue(testComputerPlayer.disproveSuggestion(suggestion) == tempPlayer || 
					   testComputerPlayer.disproveSuggestion(suggestion) == tempWeapon);
		}
		if(tempPlayer != null && tempRoom != null) {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(board.getSolution().getWeapon(), CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			assertTrue(testComputerPlayer.disproveSuggestion(suggestion) == tempPlayer || 
					   testComputerPlayer.disproveSuggestion(suggestion) == tempRoom);
		}
		if(tempWeapon != null && tempRoom != null) {
			suggestion.setSolutionCard(board.getSolution().getPerson(), CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			assertTrue(testComputerPlayer.disproveSuggestion(suggestion) == tempWeapon || 
					   testComputerPlayer.disproveSuggestion(suggestion) == tempRoom);
		}
		//tests if the player only has one of the cards from the suggestion
		if(tempPlayer != null)
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(board.getSolution().getWeapon(), CardType.WEAPON);
			suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), tempPlayer);
		if(tempWeapon != null)
			suggestion.setSolutionCard(board.getSolution().getPerson(), CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), tempWeapon);
		if(tempRoom != null)
			suggestion.setSolutionCard(board.getSolution().getPerson(), CardType.PERSON);
			suggestion.setSolutionCard(board.getSolution().getWeapon(), CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), tempRoom);
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
	/*
	@Test
	public void TestSuggestionHandling() {
		
	}*/
}
