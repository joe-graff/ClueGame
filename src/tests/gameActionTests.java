package tests;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Solution;
import clueGame.Player;


public class gameActionTests {

	public static Player testComputerPlayer, tempCompPlayer1, tempHumanPlayer; //temporary players for testing purposes
	public static Board board;
	public static Card tempCTLM, tempRope, tempCPW; //temporary cards for testing purposes
	@BeforeClass
	public static void beforeClass()  throws  BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
		board.dealDeck();
	}
	
	@Before
	public void before() {
		tempHumanPlayer = board.getPlayer(0);
		tempCompPlayer1 = board.getPlayer(1);
		testComputerPlayer = board.getPlayer(3);
		Card tempRope = new Card("tempRope", CardType.WEAPON);
		Card tempCTLM = new Card("tempCTLM", CardType.ROOM);
		Card tempCPW = new Card("tempCPW", CardType.PERSON);
		tempHumanPlayer.getHand().add(tempRope);
		tempCompPlayer1.getHand().add(tempCTLM);
		testComputerPlayer.getHand().add(tempCPW);
	}
	
	/**
	 * This tests whether an accusation is correct or incorrect
	 */
	@Test
	public void testMakeAccusations() {
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
	public void testDisproveSuggestion() {
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
		// tests if the player all of the cards from the suggestion
		if((tempPlayer != null) && (tempWeapon != null) && (tempRoom != null)) {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			Card suggestedCard = testComputerPlayer.disproveSuggestion(suggestion);
			assertTrue(suggestedCard == tempPlayer || 
					   suggestedCard == tempWeapon ||
					   suggestedCard == tempRoom);	
		}
		// tests if the player has two of the cards from the suggestion
		else if((tempPlayer != null) && (tempWeapon != null)) {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM);
			Card suggestedCard = testComputerPlayer.disproveSuggestion(suggestion);
			assertTrue(suggestedCard == tempPlayer || 
					   suggestedCard == tempWeapon);	
		}
		else if((tempPlayer != null) && (tempRoom != null)) {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(board.getSolution().getWeapon(), CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			Card suggestedCard = testComputerPlayer.disproveSuggestion(suggestion);
			assertTrue(suggestedCard == tempPlayer ||
					   suggestedCard == tempRoom);	
		}
		else if((tempWeapon != null) && (tempRoom != null)) {
			suggestion.setSolutionCard(board.getSolution().getPerson(), CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			Card suggestedCard = testComputerPlayer.disproveSuggestion(suggestion);
			assertTrue(suggestedCard == tempWeapon ||
					   suggestedCard == tempRoom);	
		}
		//tests if the player only has one of the cards from the suggestion
		else if((tempPlayer != null)) {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(board.getSolution().getWeapon(), CardType.WEAPON);
			suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), tempPlayer);
		}
		else if((tempWeapon != null)) {
			suggestion.setSolutionCard(board.getSolution().getPerson(), CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), tempWeapon);
		}
		else if((tempRoom != null)) {
			suggestion.setSolutionCard(board.getSolution().getPerson(), CardType.PERSON);
			suggestion.setSolutionCard(board.getSolution().getWeapon(), CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), tempRoom);
		}
		// tests if the player has none of the cards from the suggestion
		else {
			suggestion.setSolutionCard(tempPlayer, CardType.PERSON);
			suggestion.setSolutionCard(tempWeapon, CardType.WEAPON);
			suggestion.setSolutionCard(tempRoom, CardType.ROOM);
			assertEquals(testComputerPlayer.disproveSuggestion(suggestion), null);
		}
	}
	
	/**
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
	
	/**
	 * Tests the ability of the game to go through each players hand when a suggestion is made and
	 * correctly show any return any required card which needs to be shown by the player making
	 * a suggestion
	 */
	@Test
	public void TestSuggestionHandling() {
		Solution suggestion = new Solution();
		assertEquals(null, board.handleSuggestion(0,board.getSolution())); // tests if no one can disprove the suggestion
		suggestion.setSolutionCard(board.getSolution().getPerson(), CardType.PERSON);
		suggestion.setSolutionCard(board.getSolution().getWeapon(),CardType.WEAPON);
		suggestion.setSolutionCard(tempCTLM, CardType.ROOM);
		assertEquals(null, board.handleSuggestion(1, suggestion)); // tests if only accuser can disprove suggestion
		suggestion.setSolutionCard(tempRope, CardType.WEAPON);
		suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM);
		assertEquals(null, board.handleSuggestion(0, suggestion)); // tests if only human can disprove suggestion but human is accuser
		assertEquals(tempRope, board.handleSuggestion(1,suggestion)); // tests if only human can disprove suggestion
		suggestion.setSolutionCard(tempCTLM, CardType.ROOM);
		assertEquals(tempRope, board.handleSuggestion(2, suggestion)); // tests if two players can disprove to make sure correct player is chosen
		suggestion.setSolutionCard(tempCPW, CardType.PERSON);
		suggestion.setSolutionCard(tempRope, CardType.WEAPON);
		suggestion.setSolutionCard(board.getSolution().getRoom(), CardType.ROOM); // tests if a player and a human player can both disprove but player is before human player
		assertEquals(tempCPW, board.handleSuggestion(1,suggestion));
	}
	
	/*
	 * This tests the ability of a computer player to create a suggestion based on previously seen cards
	 */
	@Test
	public void testComputerSuggestions() {
		Solution suggestion = testComputerPlayer.createSuggestion();
		assertEquals(suggestion.getRoom(), testComputerPlayer.getRoom()); // tests if the current player's room is in the suggestion
		if(((ComputerPlayer) testComputerPlayer).peopleSeen().size() == board.getNumPeople() - 1) {
			for(Card card : board.getPeople()) {
				if(!((ComputerPlayer) testComputerPlayer).peopleSeen().contains(card)) {
					assertEquals(suggestion.getPerson(), card); // if the computer has seen all put one person cards, suggest that card
					break;
				}
			}
		}
		if(((ComputerPlayer) testComputerPlayer).weaponsSeen().size() == board.getNumWeapons() - 1) {
			for(Card card : board.getWeapons()) {
				if(!((ComputerPlayer) testComputerPlayer).weaponsSeen().contains(card)) {
					assertEquals(suggestion.getWeapon(),card); // if the computer has seen all put one weapons cards, suggest that card
					break;
				}
			}
		}
		if(((ComputerPlayer) testComputerPlayer).peopleSeen().size() < board.getNumPeople() - 1) {
			ArrayList<Card> unseenPeople = new ArrayList<Card>();
			for(Card card : board.getPeople()) {
				if(!((ComputerPlayer) testComputerPlayer).peopleSeen().contains(card)) {
					unseenPeople.add(card);
				}
			}
			assertTrue(unseenPeople.contains(suggestion.getPerson())); // if the computer has not seen multiple person cards, suggest one at random
		}
		if(((ComputerPlayer) testComputerPlayer).weaponsSeen().size() < board.getNumWeapons() - 1) {
			ArrayList<Card> unseenWeapons = new ArrayList<Card>();
			for(Card card : board.getWeapons()) {
				if(!((ComputerPlayer) testComputerPlayer).weaponsSeen().contains(card)) {
					unseenWeapons.add(card);
				}
			}
			assertTrue(unseenWeapons.contains(suggestion.getPerson())); // if the computer has not seen multiple weapon cards, suggest one at random
		}
	}
}
