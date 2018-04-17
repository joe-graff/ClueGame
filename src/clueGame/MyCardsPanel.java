package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCardsPanel extends JPanel{
	private JTextField people, weapons, rooms;
	private static Board board;
	
	public MyCardsPanel() {
		board = Board.getInstance();
		setLayout(new GridLayout(3,0));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards:"));
		JPanel panel = createPeople();
		add(panel);
		panel = createWeapons();
		add(panel);
	}
	
	private JPanel createPeople() {
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(1,1));
		JLabel lable = new JLabel("People:");
		people = new JTextField(15);
		String card = "";
		for(Card c : board.getPlayer(0).getHand()) {
			if(c.cardType == CardType.PERSON)
				card += c.getCardName();
		}
		people.setEditable(false);
		people.setText(card);
		tempPanel.add(lable);
		tempPanel.add(people);
		return tempPanel;
	}
	
	private JPanel createWeapons() {
		JPanel tempPanel = new JPanel();
		JLabel lable = new JLabel("Weapons:");
		weapons = new JTextField(15);
		String card = "";
		for(Card c:board.getPlayer(0).getHand()) {
			if(c.cardType == CardType.WEAPON)
				card += c.getCardName();
		}
		weapons.setEditable(false);
		weapons.setText(card);
		tempPanel.add(lable);
		tempPanel.add(weapons);
		return tempPanel;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,150);
		MyCardsPanel panel = new MyCardsPanel();
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
