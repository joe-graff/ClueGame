package tests;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import expreriment.BoardCell;
import expreriment.IntBoard;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */
public class IntBoardTests {
	public IntBoard testBoard;
	@Before
	public void before() {
		testBoard = new IntBoard();
	}
	
	/*
	 * Test the creation of an adjacency list at a corner
	 */
	@Test
	public void testAdjacency00() {
		BoardCell cell = testBoard.getCell(0,0);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(1,0)));
		assertTrue(testList.contains(testBoard.getCell(0,1)));
		assertEquals(2, testList.size());
	}

	/*
	 * Test the creation of an adjacency list at a corner
	 */
	@Test
	public void testAdjacency33() {
		BoardCell cell = testBoard.getCell(3,3);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(2, 3)));
		assertTrue(testList.contains(testBoard.getCell(3,2)));
		assertEquals(2, testList.size());
	}

	/*
	 * Test the creation of an adjacency list at a wall
	 */
	@Test
	public void testAdjacency13() {
		BoardCell cell = testBoard.getCell(1,3);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(0,3)));
		assertTrue(testList.contains(testBoard.getCell(1,2)));
		assertTrue(testList.contains(testBoard.getCell(2,3)));
		assertEquals(3, testList.size());
	}

	/*
	 * Test the creation of an adjacency list at a wall
	 */
	@Test
	public void testAdjacency30() {
	BoardCell cell = testBoard.getCell(3,0);
	Set<BoardCell> testList = testBoard.getAdjList(cell);
	assertTrue(testList.contains(testBoard.getCell(3,1)));
	assertTrue(testList.contains(testBoard.getCell(2,0)));
	assertEquals(2, testList.size());
	}

	/*
	 * Test the creation of an adjacency list mid board
	 */
	@Test
	public void testAdjacency11() {
		BoardCell cell = testBoard.getCell(1,1);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(2,1)));
		assertTrue(testList.contains(testBoard.getCell(2,1)));
		assertTrue(testList.contains(testBoard.getCell(0,1)));
		assertTrue(testList.contains(testBoard.getCell(1,0)));
		assertEquals(4, testList.size());
	}

	/*
	 * Test the creation of an adjacency list mid board
	 */
	@Test
	public void testAdjacency22() {
		BoardCell cell = testBoard.getCell(2,2);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		assertTrue(testList.contains(testBoard.getCell(2,1)));
		assertTrue(testList.contains(testBoard.getCell(1,2)));
		assertTrue(testList.contains(testBoard.getCell(2,3)));
		assertTrue(testList.contains(testBoard.getCell(3,2)));
		assertEquals(4, testList.size());
	}

	/*
	 * Test the creation of the target set at a corner moving 1 space
	 */
		@Test	
		public void testTargets00Move1() {
			BoardCell cell = testBoard.getCell(0,0);
			testBoard.calcTargets(cell, 1);
			Set<BoardCell> testList = testBoard.getTargets();
			assertTrue(testList.contains(testBoard.getCell(0,1)));
			assertTrue(testList.contains(testBoard.getCell(1,0)));
			assertEquals(2, testList.size());
		}

		/*
		 * Test the creation of the target set at a corner moving 2 spaces
		 */
		@Test	
		public void testTargets00Move2() {
			BoardCell cell = testBoard.getCell(0,0);
			testBoard.calcTargets(cell, 2);
			Set<BoardCell> testList = testBoard.getTargets();
			assertTrue(testList.contains(testBoard.getCell(0,2)));
			assertTrue(testList.contains(testBoard.getCell(1,1)));
			assertTrue(testList.contains(testBoard.getCell(2,0)));
			assertEquals(3, testList.size());
		}

		/*
		 * Test the creation of the target set at a wall moving 3 spaces
		 */
		@Test	
		public void testTargets01Move3() {
			BoardCell cell = testBoard.getCell(0,1);
			testBoard.calcTargets(cell, 3);
			Set<BoardCell> testList = testBoard.getTargets();
			assertTrue(testList.contains(testBoard.getCell(0,0)));
			assertTrue(testList.contains(testBoard.getCell(0,2)));
			assertTrue(testList.contains(testBoard.getCell(1,1)));
			assertTrue(testList.contains(testBoard.getCell(2,0)));
			assertTrue(testList.contains(testBoard.getCell(2,2)));
			assertTrue(testList.contains(testBoard.getCell(3,1)));
			assertTrue(testList.contains(testBoard.getCell(1,3)));
			assertEquals(7, testList.size());
		}

		/*
		 * Test the creation of the target set mid board moving 1 space
		 */
		@Test	
		public void testTargets22Move1() {
			BoardCell cell = testBoard.getCell(2,2);
			testBoard.calcTargets(cell, 1);
			Set<BoardCell> testList = testBoard.getTargets();
			assertTrue(testList.contains(testBoard.getCell(1,2)));
			assertTrue(testList.contains(testBoard.getCell(2,1)));
			assertTrue(testList.contains(testBoard.getCell(3,2)));
			assertTrue(testList.contains(testBoard.getCell(2,3)));
			assertEquals(4, testList.size());
		}

		/*
		 * Test the creation of the target set mid board moving 2 spaces
		 */
		@Test	
		public void testTargets22Move2() {
			BoardCell cell = testBoard.getCell(2,2);
			testBoard.calcTargets(cell, 2);
			Set<BoardCell> testList = testBoard.getTargets();
			assertTrue(testList.contains(testBoard.getCell(0,2)));
			assertTrue(testList.contains(testBoard.getCell(1,1)));
			assertTrue(testList.contains(testBoard.getCell(2,0)));
			assertTrue(testList.contains(testBoard.getCell(3,1)));
			assertTrue(testList.contains(testBoard.getCell(3,3)));
			assertTrue(testList.contains(testBoard.getCell(1,3)));
			assertEquals(6, testList.size());
		}

		/*
		 * Test the creation of the target set when no move is possible
		 */
		@Test	
		public void testTargets00Move16() {
			BoardCell cell = testBoard.getCell(0,0);
			testBoard.calcTargets(cell, 16);
			Set<BoardCell> testList = testBoard.getTargets();
			assertEquals(0, testList.size());
		}
		
}
