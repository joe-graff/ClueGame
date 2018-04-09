/**
 * Authors: Joseph Graff & Lewis Setter
 */

package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;

public class gameSetupTests {
	public static final int DECK_SIZE = 21;
	public static Board board;

	/**
	 * creates an instance of the board and initializes everything before any of the tests are called
	 * @throws BadConfigFormatException
	 */
	@BeforeClass
	public static void setup()  throws  BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
	}
	
	/**
	 * This set of tests tests the loading of the players, make sure they have the correct name, color, 
	 * starting position and whether they are human or computer players.
	 */
	@Test
	public void testLoadingPeople() {
		assertEquals(board.players[0].getPlayerName(), "Joe Student");
		assertEquals(board.players[0].getColor(), Color.pink);
		assertEquals(board.players[0].getColumn(), 16);
		assertEquals(board.players[0].getRow(), 0);
		assertTrue(board.players[0] instanceof HumanPlayer);
		
		assertEquals(board.players[3].getPlayerName(), "CPW");
		assertEquals(board.players[3].getColor(), Color.red);
		assertEquals(board.players[3].getColumn(), 7);
		assertEquals(board.players[3].getRow(), 24);
		assertTrue(board.players[3] instanceof ComputerPlayer);
		
		
		assertEquals(board.players[5].getPlayerName(), "Jeff Paone");
		assertEquals(board.players[5].getColor(), Color.orange);
		assertEquals(board.players[5].getColumn(), 3);
		assertEquals(board.players[5].getRow(), 24);
		assertTrue(board.players[5] instanceof ComputerPlayer);
	}
	
	/**
	 * This set of tests tests the cards to make sure there is the correct amount of each cards,
	 *  the correct number of cards total and checks one of each type of card to ensure the names are correct.
	 */
	@Test
	public void testCards() {
		assertEquals(board.getDeck().size(), DECK_SIZE);
		int room = 0, person = 0, weapon = 0;
		for(int i = 0; i < board.getDeck().size(); i++) {
			if(board.deck.get(i).cardType == CardType.PERSON) person++;
			else if(board.deck.get(i).cardType == CardType.WEAPON) weapon++;
			else if(board.deck.get(i).cardType == CardType.ROOM) room++;
		}
		assertEquals(person, 6);
		assertEquals(weapon, 6);
		assertEquals(room, 9);
		assertTrue(board.deck.contains(new Card("Joe Student", CardType.PERSON)));
		assertTrue(board.deck.contains(new Card("Rope", CardType.WEAPON)));
		assertTrue(board.deck.contains(new Card("Brown", CardType.ROOM)));
	}

	/**
	 * This set of tests makes sure that 3 cards (a weapon, a person, and a room) are removed from the
	 * deck and set aside as the getSolution(). It also makes sure that the remaining cards are dealt to the
	 * players.
	 */
	@Test
	public void testDealCards() {
		board.dealDeck();
		assertEquals(board.getDeck().size(), 0);
		assertEquals(board.getSolution().returnCards().size(), 3);
		int numCard = 0;
		for(CardType cardType : CardType.values()) {
			assertEquals(board.getSolution().returnCards().get(numCard).getCardType(), cardType);
			numCard++;
		}
		ArrayList<Card> remainingCards = new ArrayList<Card>();
		for(int player = 0; player < board.getNumPlayers(); player++) {
			for(Card card : board.getPlayer(player).getHand()) {
				remainingCards.add(card);
			}
		}
		assertTrue(!remainingCards.contains(board.getSolution().returnCards().get(0)));
		assertTrue(!remainingCards.contains(board.getSolution().returnCards().get(1)));
		assertTrue(!remainingCards.contains(board.getSolution().returnCards().get(2)));	
		}
}

