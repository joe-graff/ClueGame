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
	
<<<<<<< HEAD
	public Set<BoardCell> getAdjList(BoardCell cell){return null;}
	
=======
	public BoardCell getCell(int row, int col) {
		return null;
	}
>>>>>>> bf8565b34dfd1e908a814930fe03d3b0b541c8ce
}
