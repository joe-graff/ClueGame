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
	public void testNumCards() {
		assertEquals(board.getDeck().size(),DECK_SIZE);
	}
	
	@Test
	public void testLoadingPeople() {
		assertEquals(board.players[0].getPlayerName(), "Jo Student");
		assertEquals(board.players[0].getColor(), Color.red);
		assertEquals(board.players[0].getColumn(), 1);
		assertEquals(board.players[0].getRow(), 10);
		assertTrue(board.players[0] instanceof ComputerPlayer);
		
		assertEquals(board.players[3].getPlayerName(), "CW");
		assertEquals(board.players[3].getColor(), Color.yellow);
		assertEquals(board.players[3].getColumn(), 6);
		assertEquals(board.players[3].getRow(), 2);
		assertTrue(board.players[3] instanceof HumanPlayer);
		
		
		assertEquals(board.players[5].getPlayerName(), "Jef Paone");
		assertEquals(board.players[5].getColor(), Color.red);
		assertEquals(board.players[5].getColumn(), 1);
		assertEquals(board.players[5].getRow(), 4);
		assertTrue(board.players[5] instanceof HumanPlayer);
		
	}

}

