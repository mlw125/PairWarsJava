package pairWars.model;

public class Card {
	// the face (rank) and the suit of the card
	int face;
	int suit;
	
	// default constructor
	public Card() {
		this.face = -1;
	} // end Card()

	// copy constructor, for specifying the card
	public Card(int type, int suit) {
		this.face = type;
		this.suit = suit;
	} // end Card(int, int);

	// return the face (rank) of the card
	public int getFace() {
		return this.face;
	} // end getFace()
	
	// return the suit of the card
	public int getSuit() {
		return this.suit;
	} // end getSuit()
	
	public void killCard() {
		suit = -1;
		face = -1;
	}
} // end class Card