package expreriment;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class IntBoardTests {
	public IntBoard testBoard;
	@Before
	public void before() {
		testBoard = new IntBoard();
	}
	
	@Test
	public void testAdjacency00(){
		BoardCell cell = testBoard.getCell(0,0);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		//assertTrue(testList.contains(testBoard.getCell(1, 0)));
		assertTrue(testList.contains(testBoard.getCell(0, 1)));
		assertEquals(2, testList.size());
	}

}
