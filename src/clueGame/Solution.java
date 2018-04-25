package clueGame;

import java.util.ArrayList;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public void setPerson(Card person) {
		this.person = person;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}

	public ArrayList<Card> returnCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(person);
		cards.add(weapon);
		cards.add(room);
		return cards;
	}
	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
	
	public String toString() {
		return person.getCardName() + " " + room.getCardName() + " " + weapon.getCardName();
	}
}
