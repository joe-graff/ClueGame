package expreriment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjCells;
	private Set<BoardCell> visitedCells;
	private Set<BoardCell> moveOptions;
	private BoardCell[][] grid;
	
	public IntBoard() {}
	
	public void calcAdjacencies() {return;}
	
	public Set<BoardCell> getAdjList(){return null;}
	
	public void calcTargets(int startCell, int pathLength) {return;}
	
	public Set<BoardCell> getTargets(){return null;}
	
	public BoardCell getCell(int row, int col) {return null;}
	
	public Set<BoardCell> getAdjList(BoardCell cell){return null;}
	
}
