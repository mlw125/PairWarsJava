package pairWars.model;

import java.util.ArrayList;

public class Player {
	ArrayList<Card> hand = new ArrayList<Card>();
	// make list for pairs
	
	public Player() {		
	}
	
	public void getCard(Card card) {
		hand.add(card);
	}
	
	public boolean isPair() {
		return false;		
	}	
}
