package expreriment;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class GameControlGUI extends JPanel{
	public GameControlGUI(){
		setLayout(new GridLayout(2,0));
		JPanel panel = createFirstPanel();
		add(panel);
		panel = createSecondPanel();
		add(panel);
		
	}
	
	private JPanel createFirstPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,0));
		JTextField turnFeild = new JTextField(10);
		turnFeild.setEditable(false);
		JLabel turnLabel = new JLabel("Who's Turn?");
		turnPanel.add(turnLabel);
		turnPanel.add(turnFeild);
		panel.add(turnPanel);
		JButton next = new JButton("NextPlayer");
		JButton accusation = new JButton("Make an accusation");
		panel.add(next);
		panel.add(accusation);

		return panel;
	}
	
	private JPanel createSecondPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		
		JPanel dieRoll = new JPanel();
		dieRoll.setLayout(new GridLayout(2,0));
		JTextField dieField = new JTextField(10);
		dieField.setEditable(false);
		JLabel dieLable = new JLabel("Die Roll");
		dieRoll.add(dieLable);
		dieRoll.add(dieField);
		
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2,0));
		JTextField guessField = new JTextField(10);
		guessField.setEditable(false);
		JLabel guessLable = new JLabel("Guess");
		guessPanel.add(guessLable);
		guessPanel.add(guessField);
		
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(2,0));
		JTextField resultField = new JTextField(10);
		resultField.setEditable(false);
		JLabel resultLable = new JLabel("Guess Result Response");
		guessResultPanel.add(resultLable);
		guessResultPanel.add(resultField);
		
		panel.add(dieRoll);
		panel.add(guessPanel);
		panel.add(guessResultPanel);
		return panel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GameControlGUI");
		frame.setSize(500,250);
		GameControlGUI gui = new GameControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
