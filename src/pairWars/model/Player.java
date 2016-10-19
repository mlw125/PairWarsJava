package pairWars.model;

import java.util.ArrayList;

public class Player extends AbstractModel {
	int id;
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<Card> pairs = new ArrayList<Card>();
	
	public Player(int id) {
		this.id = id;
	} // end Player()
	
	public void getCard(Card card) {
		hand.add(card);
	} // end getCard()
	
	// check if there is a pair in the players hand
	public boolean isPair() {
		for(int x = 0; x < hand.size()-1; x++) {
			for(int y = x+1; y < hand.size(); y++) {
				if(hand.get(x).getFace() == hand.get(y).getFace()) {
					System.out.println(id + " is the winner.\n");
					return true;
				} // end if
			} // end for
		} // end for
		return false;
	} // end isPair()
	
	public int returnPairs() {
		return pairs.size() / 2;
	}
	
	public void checkForPairs() {
		ArrayList<Card> temp = hand;
		for(int x = 0; x < hand.size()-1; x++) {
			for(int y = x+1; y < hand.size(); y++) {
				if(hand.get(x).getFace() == hand.get(y).getFace() && hand.get(x).getFace() != -1) {
					Card tempCard1 = hand.get(x);
					Card tempCard2 = hand.get(y);
					pairs.add(tempCard1);
					pairs.add(tempCard2);
					hand.get(x).killCard();
					hand.get(y).killCard();
					temp.remove(x);
					temp.remove(y-1);
				}
			}
		}
		hand = temp;
	}
	
	public Card returnLastCard() {
		int temp = hand.size()-1;
		return hand.get(temp);
	} // end returnLastCard()
	
	int getID() {
		return id;
	} // end class getID()
} // end class Player
