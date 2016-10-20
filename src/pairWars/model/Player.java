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
	
	// returns the number of pairs
	public int returnPairs() {
		return pairs.size() / 2;
	} // end returnPairs()
	
	public ArrayList<Integer> checkForPairs() {		
		// list to hold the card values, every two elements are parts of a card
		ArrayList<Integer> temp = new ArrayList<Integer>(); 
		// loop thorugh the entire hand
		for(int x = 0; x < hand.size()-1; x++) {
			// loop through the hand again
			for(int y = x+1; y < hand.size(); y++) {
				// if the face values are the same then we have a pair
				if(hand.get(x).getFace() == hand.get(y).getFace()) {
					// get the cards from the hand
					Card tempCard1 = hand.get(x);
					Card tempCard2 = hand.get(y);
					// add the cards to the pair list
					pairs.add(tempCard1);
					pairs.add(tempCard2);
					// remove the cards from the hand
					hand.remove(tempCard1);
					hand.remove(tempCard2);
					
					// add the parts of the card onto a list
					temp.add(tempCard1.getFace());
					temp.add(tempCard1.getSuit());
					temp.add(tempCard2.getFace());
					temp.add(tempCard2.getSuit());
					return temp;					
				} // end if
			} // end for
		} // end for
		
		// return a blank card if a pair isn't found
		temp.add(-1);
		return temp;
	} // end checkForPairs()
	
	public Card returnLastCard() {
		int temp = hand.size()-1;
		return hand.get(temp);
	} // end returnLastCard()
	
	public int getID() {
		return id;
	} // end class getID()
} // end class Player
