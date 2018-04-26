package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Joseph Graff and Lewis Setter
 *
 */

public abstract class Player {
	private String playerName;
	protected int row;
	protected int column;
	protected BoardCell lastRoom;
	private Color color;
	protected ArrayList<Card> hand;
	protected Board board;
	private static final int CELL_SIZE = 30;
	

	public Player(String playerName, int row, int column, Color color, Board board) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		hand  = new ArrayList<Card>();
		this.board = board;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disproveList = new ArrayList<Card>();
		for(Card c: hand) {
			if(c == suggestion.getPerson() || c == suggestion.getWeapon() || c == suggestion.getRoom())
				disproveList.add(c);
		}
		if(disproveList.size() == 0)
			return null;
		else{
			return disproveList.get((int)(Math.random() * disproveList.size()));
		}
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public Color getColor() {
		return color;
	}
	
	public void makeMove(BoardCell b) { // moves human player
		if(Board.getInstance().targetCells.contains(b)) {
			if(b.isDoorway()) {
				JFrame humanGuess = new JFrame();
				humanGuess.setLayout(new GridLayout(4,0));
				humanGuess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				humanGuess.setTitle("Make a Guess");
				humanGuess.setSize(250,250);
				JPanel rowOne = new JPanel();
				JLabel room = new JLabel("Your room");
				rowOne.add(room);
				//System.out.println(Board.getInstance().getLegend().get(b.getInitial()));
				JTextField currentRoom = new JTextField(Board.getInstance().getLegend().get(b.getInitial()));
				currentRoom.setEditable(false);
				rowOne.add(currentRoom);
				JPanel rowTwo = new JPanel();
				JLabel person = new JLabel("Person");
				rowTwo.add(person);
				ArrayList<String> peeps = new ArrayList<String>();
				for(Card c: Board.getInstance().getPeople())
					peeps.add(c.getCardName());
				JComboBox personChoice = new JComboBox();
				personChoice.setModel(new DefaultComboBoxModel(peeps.toArray()));
				rowTwo.add(personChoice);
				JPanel rowThree = new JPanel();
				JLabel weapon = new JLabel("Weapon");
				ArrayList<String> weapons = new ArrayList<String>();
				for(Card c: Board.getInstance().getWeapons())
					weapons.add(c.getCardName());
				JComboBox weaponChoice = new JComboBox();
				weaponChoice.setModel(new DefaultComboBoxModel(weapons.toArray()));
				rowThree.add(weapon);
				rowThree.add(weaponChoice);
				JPanel rowFour = new JPanel();
				JButton submit = new JButton("Submit");
				submit.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						Card tempPerson = null;
						Card tempWeapon = null;
						for(Card c: Board.getInstance().getPeople()) {
							if(personChoice.getSelectedItem().equals(c.getCardName()))
								tempPerson = c;
						}
						for(Card c: Board.getInstance().getWeapons()) {
							if(weaponChoice.getSelectedItem().equals(c.getCardName()))
								tempWeapon = c;
						}
						Solution suggestion = new Solution(tempPerson, tempWeapon, Board.getInstance().getRoom(b.getRow(), b.getColumn()));
						Board.getInstance().gameControl.updateGuess(tempPerson.getCardName(), tempWeapon.getCardName(), Board.getInstance().getRoom(b.getRow(), b.getColumn()).getCardName());
						Board.getInstance().gameControl.updateResult(Board.getInstance().handleSuggestion(0, suggestion).getCardName());
						humanGuess.dispose();
					}
				});
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						humanGuess.dispose();
					}
				});
				rowFour.add(submit);
				rowFour.add(cancel);
				humanGuess.add(rowOne);
				humanGuess.add(rowTwo);
				humanGuess.add(rowThree);
				humanGuess.add(rowFour);
				humanGuess.setVisible(true);
			}
			row = b.getRow();
			column = b.getColumn();
		} else {
			JOptionPane errorMessage = new JOptionPane();
			errorMessage.showMessageDialog(Board.getInstance(), "This is not a valid selection", "Invalid Selection", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void movePlayer(int pathlength) { // moves computer player
		board.calcTargets(row, column, pathlength);
		for(BoardCell location : board.getTargets()) {
			if(location.isDoorway()) {
				if(lastRoom == null || (lastRoom.getInitial() != location.getInitial() && location != lastRoom)) {
					row = location.getRow();
					column = location.getColumn();
					Solution computerSuggestion = createSuggestion();
					Board.getInstance().gameControl.updateGuess(computerSuggestion.getPerson().getCardName(), computerSuggestion.getWeapon().getCardName(), computerSuggestion.getRoom().getCardName());
					if(Board.getInstance().handleSuggestion(Board.getInstance().getPlayerPosition(), computerSuggestion) == null) {
						Board.getInstance().gameControl.updateResult("No Cards to match Suggestion");
					} else {
						Board.getInstance().gameControl.updateResult(Board.getInstance().handleSuggestion(Board.getInstance().getPlayerPosition(), computerSuggestion).getCardName());
					}
					lastRoom = location;
					return;
				}
			}
		}
		ArrayList<BoardCell> randomTargetCells = new ArrayList<BoardCell>();
		for(BoardCell location : board.getTargets()) {
			randomTargetCells.add(location);
		}
		Collections.shuffle(randomTargetCells);
		row = randomTargetCells.get(0).getRow();
		column = randomTargetCells.get(0).getColumn();
	}
	
	public abstract Solution createSuggestion();

	public Card getRoom() {
		return board.getRoom(row, column);
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
		g.drawOval(column*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}
	
}
