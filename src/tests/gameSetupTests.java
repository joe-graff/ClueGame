package tests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class gameSetupTests {
	public static final int DECK_SIZE = 15;
	public static Board board;
	
	@BeforeClass
	public void setup() {
		
	}
	
	@Before
	public void moreSetup() {
		board = Board.getInstance();
		board.initialize();
	}
	
	@Test
	public void testNumCards() {
		assertEquals(board.getDeck.size(),DECK_SIZE);
	}
}

