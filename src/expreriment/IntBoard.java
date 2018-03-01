package expreriment;
import java.util.Set;
import java.util.Map;

public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjCells;
	private Set<BoardCell> targetCells;
	private static int boardWidth = 4;
	private static int boardHeight = 4;
	
	public void setCurrentCell(BoardCell cell) {
		
	}
	
	public IntBoard() {
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		return;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return null;
	}	
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		return;
	}
	
	public Set<BoardCell> getTargets() {
		return null;
	}

	public BoardCell getCell(int row, int col) {
		return null;
	}
}
