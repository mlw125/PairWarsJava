package pairWars.model;

public class Card {
	int type;
	int suit;
	
	public Card() {
		this.type = -1;
	}	

	public Card(int type, int suit) {
		this.type = type;
		this.suit = suit;
	}
	
	public int getCard() {
		return type;
	}
}
