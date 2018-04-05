package clueGame;
/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */
public class Card {
	public String cardName;
	public CardType cardType;
	
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (cardType != other.cardType)
			return false;
		return true;
	}
	
}
