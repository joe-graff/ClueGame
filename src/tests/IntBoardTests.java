package tests;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import expreriment.BoardCell;
import expreriment.IntBoard;

public class IntBoardTests {
	public IntBoard testBoard;
	@Before
	public void before() {
		testBoard = new IntBoard();
	}
	
	@Test
	public void testAdjacency00() {
		BoardCell cell = new BoardCell(0,0);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		cell = new BoardCell(0,1);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(1,0);
		assertTrue(testList.contains(cell));
		assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency33() {
		BoardCell cell = new BoardCell(3,3);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		cell = new BoardCell(2,3);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(3,2);
		assertTrue(testList.contains(cell));
		assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency13() {
		BoardCell cell = new BoardCell(1,3);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		cell = new BoardCell(0,3);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(1,2);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(2,3);
		assertTrue(testList.contains(cell));
		assertEquals(3, testList.size());
	}

	@Test
	public void testAdjacency30() {
	BoardCell cell = new BoardCell(3,0);
	Set<BoardCell> testList = testBoard.getAdjList(cell);
	cell = new BoardCell(3,1);
	assertTrue(testList.contains(cell));
	cell = new BoardCell(2,0);
	assertTrue(testList.contains(cell));
	assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency11() {
		BoardCell cell = new BoardCell(1,1);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		cell = new BoardCell(2,1);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(1,2);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(0,1);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(1,0);
		assertTrue(testList.contains(cell));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacency22() {
		BoardCell cell = new BoardCell(2,2);
		Set<BoardCell> testList = testBoard.getAdjList(cell);
		cell = new BoardCell(2,1);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(1,2);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(2,3);
		assertTrue(testList.contains(cell));
		cell = new BoardCell(3,2);
		assertTrue(testList.contains(cell));
		assertEquals(4, testList.size());
	}
	
		@Test	
		public void testTargets00Move1() {
			BoardCell cell = new BoardCell(0,0);
			testBoard.calcTargets(cell, 1);
			Set<BoardCell> testList = testBoard.getTargets();
			cell = new BoardCell(0,1);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(1,0);
			assertTrue(testList.contains(cell));
			assertEquals(2, testList.size());
		}
		
		@Test	
		public void testTargets00Move2() {
			BoardCell cell = new BoardCell(0,0);
			testBoard.calcTargets(cell, 2);
			Set<BoardCell> testList = testBoard.getTargets();
			cell = new BoardCell(0,2);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(1,1);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(2,0);
			assertTrue(testList.contains(cell));
			assertEquals(3, testList.size());
		}
		
		@Test	
		public void testTargets01Move3() {
			BoardCell cell = new BoardCell(0,1);
			testBoard.calcTargets(cell, 3);
			Set<BoardCell> testList = testBoard.getTargets();
			cell = new BoardCell(0,0);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(0,2);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(1,1);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(2,0);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(2,2);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(3,1);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(1,3);
			assertTrue(testList.contains(cell));
			assertEquals(7, testList.size());
		}
		
		@Test	
		public void testTargets22Move1() {
			BoardCell cell = new BoardCell(2,2);
			testBoard.calcTargets(cell, 1);
			Set<BoardCell> testList = testBoard.getTargets();
			cell = new BoardCell(1,2);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(2,1);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(3,2);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(2,3);
			assertTrue(testList.contains(cell));
			assertEquals(4, testList.size());
		}
		
		@Test	
		public void testTargets22Move2() {
			BoardCell cell = new BoardCell(2,2);
			testBoard.calcTargets(cell, 2);
			Set<BoardCell> testList = testBoard.getTargets();
			cell = new BoardCell(0,2);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(1,1);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(2,0);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(3,1);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(3,3);
			assertTrue(testList.contains(cell));
			cell = new BoardCell(1,3);
			assertTrue(testList.contains(cell));
			assertEquals(6, testList.size());
		}
		
		/**
		 * No move possible, getTargets should return an empty set
		 */
		@Test	
		public void testTargets00Move16() {
			BoardCell cell = new BoardCell(0,0);
			testBoard.calcTargets(cell, 16);
			Set<BoardCell> testList = testBoard.getTargets();
			assertEquals(0, testList.size());
		}
}
