package clueGame;

import java.util.ArrayList;

public class Solution {
	private Card person;
	private Card room;
	private Card weapon;
	
	public Solution() {
	}
	
	public void setSolutionCard(Card card, CardType type) {
		if(type == CardType.PERSON) {
			person = card;
		} else if(type == CardType.ROOM) {
			room = card;
		} else if(type == CardType.WEAPON) {
			weapon = card;
		}
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
}
