package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class gameSetupTests {
	public static final int DECK_SIZE = 21;
	public static Board board;
	
	@BeforeClass
	public static void setup()  throws  BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
	}
	
	@Before
	public void moreSetup(){

	
	}
	
	@Test
	public void testNumCards() {
		assertEquals(board.getDeck().size(),DECK_SIZE);
	}

}

