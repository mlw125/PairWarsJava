package pairWars.model;

import java.util.ArrayList;

public class Player extends AbstractModel {
	int id;
	ArrayList<Card> hand = new ArrayList<Card>();
	// make list for pairs
	
	public Player(int id) {
		this.id = id;
	}
	
	public void getCard(Card card) {
		hand.add(card);
	}
	
	// check if there is a pair in the players hand
	public boolean isPair() {
		for(int x = 0; x < hand.size()-1; x++) {
			for(int y = x+1; y < hand.size(); y++) {
				if(hand.get(x).getFace() == hand.get(y).getFace()) {
					
					// Send the pair to be accessed by the PlayerView
					//ModelEvent me = new ModelEvent(this, id, "Pair", hand.get(x).getFace(), hand.get(x).getSuit(), 
					//								hand.get(y).getFace(), hand.get(x).getSuit());
					//notifyChanged(me);
					return true;
				}
			}
		}
		return false;
	}	
}
