package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.DoorDirection;
import clueGame.HumanPlayer;

public class gameSetupTests {
	public static final int DECK_SIZE = 21;
	public static Board board;
	
	@BeforeClass
	public static void setup()  throws  BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
	}
	
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
	
	@Test
	public void testCards() {
		assertEquals(board.getDeck().size(),DECK_SIZE);
		int room = 0, person = 0, weapon = 0;
		for(int i = 0; i < board.getDeck().size(); i++) {
			if(board.deck.get(i).cardType == CardType.PERSON) person++;
			else if(board.deck.get(i).cardType == CardType.WEAPON) weapon++;
			else if(board.deck.get(i).cardType == CardType.ROOM) room++;
		}
		assertEquals(person, 5);
		assertEquals(weapon, 5);
		assertEquals(room, 8);
		assertTrue(board.deck.contains(new Card("Joe Studet", CardType.PERSON)));
		assertTrue(board.deck.contains(new Card("Roe", CardType.WEAPON)));
		assertTrue(board.deck.contains(new Card("Bron", CardType.ROOM)));
	}

}

