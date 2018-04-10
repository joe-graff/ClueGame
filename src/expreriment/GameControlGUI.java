package expreriment;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class GameControlGUI extends JFrame{
	public GameControlGUI(){
		setLayout(new GridLayout(2,0));
		JPanel panel = createFirstPanel();
		add(panel);
		panel = createSecondPanel();
		add(panel);
		
	}
	
	private JPanel createFirstPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		JLabel turnLabel = new JLabel("Who's Turn?");
		JLabel whosTurn = new JLabel();
		panel.add(turnLabel);
		panel.add(whosTurn);
		
		return panel;
	}
	
	private JPanel createSecondPanel() {
		JPanel panel = new JPanel();
		
		return null;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GameControlGUI");
		frame.setSize(250,150);
		GameControlGUI gui = new GameControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
