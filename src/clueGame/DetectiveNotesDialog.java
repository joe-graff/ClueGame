package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
	
public class DetectiveNotesDialog extends JDialog{
	private JComboBox<String> peopleBox, weaponsBox, roomsBox;
	private JPanel roomsPanel, weaponsPanel, peoplePanel;
	private Board board;
	public DetectiveNotesDialog() {
		setTitle("Detective Notes");
		setSize(1000, 1000);
		setLayout(new GridLayout(3, 2));
		board = Board.getInstance();
		peopleBox = new JComboBox<String>();
		weaponsBox = new JComboBox<String>();
		roomsBox = new JComboBox<String>();
		roomsPanel = new JPanel();
		weaponsPanel = new JPanel();
		peoplePanel = new JPanel();
		CreateComboBox();
		add(roomsPanel);
		add(roomsBox);
		add(weaponsPanel);		
		add(weaponsBox);
		add(peoplePanel);
		add(peopleBox);
		roomsBox.setBorder(new TitledBorder (new EtchedBorder(), "Rooms Guess:"));
		weaponsBox.setBorder(new TitledBorder (new EtchedBorder(), "Weapons Guess:"));
		peopleBox.setBorder(new TitledBorder (new EtchedBorder(), "Persons Guess:"));
		roomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms:"));
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons:"));
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People:"));
	}
	public void CreateComboBox() {
		ArrayList<Card> rooms = board.getRooms();
		ArrayList<Card> weapons = board.getWeapons();
		Player[] players = board.players;
		int num = board.getNumPlayers();
		for (Card card: rooms) {
			roomsBox.addItem(card.getCardName());
			JCheckBox checkBox = new JCheckBox(card.getCardName());
			roomsPanel.add(checkBox);
		}
		for (Card card: weapons) {
			weaponsBox.addItem(card.getCardName());
			JCheckBox checkBox = new JCheckBox(card.getCardName());
			weaponsPanel.add(checkBox);
		}
		for (int i = 0; i < num; i++) {
			peopleBox.addItem(players[i].getPlayerName());
			JCheckBox checkBox = new JCheckBox(players[i].getPlayerName());
			peoplePanel.add(checkBox);
		}
	}
	
}
