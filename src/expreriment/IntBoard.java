package expreriment;
import java.util.HashSet;
import java.util.Map;

public class IntBoard {
	
	private Map<BoardCell, HashSet<BoardCell>> adjCells;
	private HashSet<BoardCell> visitedCells;
	private HashSet<BoardCell> moveOptions;
	private BoardCell[][] grid;
	private static int boardWidth = 4;
	private static int boardHeight = 4;
	
	public IntBoard() {
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		return;
	}
	
	public HashSet<BoardCell> getAdjList(BoardCell cell){
		return null;
	}	
	
	public void calcTargets(int startCell, int pathLength) {
		return;
	}
	
	public HashSet<BoardCell> getTargets() {
		return null;
	}
	
	public BoardCell getCell(int row, int col) {
		return null;
	}

}
