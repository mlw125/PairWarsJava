package pairWars.model;

public class Card {
	int face;
	int suit;
	
	public Card() {
		this.face = -1;
	}	

	public Card(int type, int suit) {
		this.face = type;
		this.suit = suit;
	}

	public int getFace() {
		return this.face;
	}
	
	public int getSuit() {
		return this.suit;
	}
}
