package pairWars.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dealer {
	
	ArrayList<Integer> deck;
	
	public Dealer() {
		createDeck();
		shuffle();
	} // end Dealer()
	
	// This functions initializes the deck and fills it with cards
	public void createDeck() {
		ArrayList<Integer> deck = new ArrayList<Integer>();
		
		// creates a deck, with values 1-13. Four of each are created.
		for(int x = 1; x < 14; x++) {
			for(int y = 0; y < 4; y++) {
				deck.add(x);
			} // end for
		} // end for
	} // end createDeck()
	
	// Randomly shuffles the cards in the deck
	public void shuffle() {
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
	} // end shuffle()
	
	// Return one card from the deck
	public int dealCard() {
		if(deck.size() > 2) {
			int card = deck.get(0);
			deck.remove(0);
			return card;
		} // end if
		else {
			return -1;
		} // end else
	} // end dealCard()
}
