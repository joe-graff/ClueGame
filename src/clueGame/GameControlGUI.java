/**
 * Authors: Joseph Graff & Lewis Setter
 */


package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * GameControl GUI creates the game control menu for the interface with the user
 *
 */
public class GameControlGUI extends JPanel{
	public ClueGame game;
	private JTextField turnField;
	private JTextField dieField;
	private JTextField resultField;
	private JTextField guessField;

	public GameControlGUI(){
		setLayout(new GridLayout(2,0));
		JPanel panel = new JPanel();
		panel = createFirstPanel();
		add(panel);
		panel = createSecondPanel();
		add(panel);
	}
	/**
	 * creates the top row of fields and buttons
	 * @return
	 */
	private JPanel createFirstPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,0));
		turnField = new JTextField(10);
		turnField.setEditable(false);
		JLabel turnLabel = new JLabel("Who's Turn?");
		turnPanel.add(turnLabel);
		turnPanel.add(turnField);
		panel.add(turnPanel);
		JButton next = new JButton("NextPlayer");
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Board.getInstance().nextPlayer();
			}
		});
		JButton accusation = new JButton("Make an accusation");
		accusation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame humanAccusation = new JFrame();
				humanAccusation.setLayout(new GridLayout(4,0));
				humanAccusation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				humanAccusation.setTitle("Make a Guess");
				humanAccusation.setSize(250,250);
				JPanel rowOne = new JPanel();
				JLabel room = new JLabel("Room");
				rowOne.add(room);
				JComboBox roomChoice = new JComboBox();
				ArrayList<String> rooms = new ArrayList<String>();
				for(Card c: Board.getInstance().getRooms())
					rooms.add(c.getCardName());
				roomChoice.setModel(new DefaultComboBoxModel(rooms.toArray()));
				rowOne.add(roomChoice);
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
						Solution suggestion = new Solution(tempPerson, tempWeapon, Board.getInstance().getRoom(Board.getInstance().getPlayer(Board.getInstance().getPlayerPosition()).getRow(), (Board.getInstance().getPlayer(Board.getInstance().getPlayerPosition()).getColumn())));
						if(suggestion == Board.getInstance().getSolution()) {
							JOptionPane winningMessage = new JOptionPane();
							winningMessage.showMessageDialog(Board.getInstance(), "You Won Clue!", "You Win!", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				JButton cancel = new JButton("Cancel");
				rowFour.add(submit);
				rowFour.add(cancel);
				humanAccusation.add(rowOne);
				humanAccusation.add(rowTwo);
				humanAccusation.add(rowThree);
				humanAccusation.add(rowFour);
				humanAccusation.setVisible(true);
			}
		});
		panel.add(next);
		panel.add(accusation);

		return panel;
	}

	/**
	 * creates the second row of fields and buttons
	 * @return
	 */
	private JPanel createSecondPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));

		JPanel dieRoll = new JPanel();
		dieRoll.setLayout(new GridLayout(2,0));
		dieField = new JTextField(5);
		dieField.setEditable(false);
		JLabel dieLable = new JLabel("Die Roll");
		dieRoll.add(dieLable);
		dieRoll.add(dieField);

		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2,0));
		guessField = new JTextField(20);
		guessField.setEditable(false);
		JLabel guessLable = new JLabel("Guess");
		guessPanel.add(guessLable);
		guessPanel.add(guessField);

		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(2,0));
		resultField = new JTextField(10);
		resultField.setEditable(false);
		JLabel resultLable = new JLabel("Guess Result Response");
		guessResultPanel.add(resultLable);
		guessResultPanel.add(resultField);

		panel.add(dieRoll);
		panel.add(guessPanel);
		panel.add(guessResultPanel);
		return panel;
	}

	public void updateTurn(String player, int roll) {
		turnField.setText(player);
		dieField.setText(String.valueOf(roll));
	}

	public void updateResult(String result) {
		resultField.setText(result);
	}

	public void updateGuess(String person, String weapon, String room) {
		String guess = person + ", " + weapon + ", " + room;
		guessField.setText(guess);
	}

	public static void main(String[] args) {
		JFrame game = new JFrame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setTitle("GameControlGUI");
		game.setSize(500,250);
		GameControlGUI gui = new GameControlGUI();
		Board.getInstance().setGameControlGUI(gui);
		game.add(gui, BorderLayout.CENTER);
		game.setVisible(true);
	}
}
