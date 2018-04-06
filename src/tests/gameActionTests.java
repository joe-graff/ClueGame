package tests;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Solution;


class gameActionTests {
	public static Board board;
	
	@BeforeClass
	public static void setup()  throws  BadConfigFormatException{
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
	}
	
	@Test
	public void testAccusations() {
		//assertTrue(board.checkAccusation(Solution.person, Solution.weapon, Solution.room));
	}
}
