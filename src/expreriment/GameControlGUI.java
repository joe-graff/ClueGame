package expreriment;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameControlGUI extends JFrame{
	public GameControlGUI(){
		setLayout(new GridLayout(2,0));
		JPanel panel = createFirstPanel();
		add(panel);
		panel = createSecondPanel();
		add(panel);
	}
	
	private JPanel createFirstPanel() {
		return null;
	}
	
	private JPanel createSecondPanel() {
		return null;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250,150);
		GameControlGUI gui = new GameControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
