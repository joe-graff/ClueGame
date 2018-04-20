package clueGame;
/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{
	private DetectiveNotesDialog notesDialog;
	private static Board board;
	private static JOptionPane splashMenu;
	
	public ClueGame() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueRooms.csv","ClueRooms.txt","PlayerFile.txt","WeaponsFile.txt");
		board.initialize();
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createFileShowNotesItem());
		return menu;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	private JMenuItem createFileShowNotesItem() {
		JMenuItem item = new JMenuItem("Show Notes");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				notesDialog = new DetectiveNotesDialog();
				notesDialog.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		ClueGame.board.dealDeck();
		splashMenu = new JOptionPane();
		String splash = "You are Joe Student, press Next Player to play";
		splashMenu.showMessageDialog(game, splash, "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(1100, 900);
		game.add(board, BorderLayout.CENTER);
		MyCardsPanel cardsPanel = new MyCardsPanel();
		game.add(cardsPanel, BorderLayout.EAST);
		GameControlGUI gameControl = new GameControlGUI();
		Board.getInstance().setGameControlGUI(gameControl);
		game.add(gameControl, BorderLayout.SOUTH);
		game.setVisible(true);
		
	}
}
