package pairWars.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dealer {
	
	// the deck of cards
	ArrayList<Card> deck;
	
	public Dealer() {
		createDeck();
		shuffle();
	} // end Dealer()
	
	// This functions initializes the deck and fills it with cards
	public void createDeck() {
		deck = new ArrayList<Card>();
		
		// creates a deck, with values 1-13. Four of each are created.
		for(int x = 1; x < 14; x++) {
			for(int y = 0; y < 4; y++) {
				Card temp = new Card(x,y);
				deck.add(temp);
			} // end for
		} // end for
	} // end createDeck()
	
	// Randomly shuffles the cards in the deck
	public void shuffle() {
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
	} // end shuffle()
	
	// Return one card from the deck
	public Card dealCard() {
		// modify this to make sure the empty card will not be returned
		// if the deck has at least one card in it
		if(deck.size() > 0) {
			Card card = deck.get(0);
			deck.remove(0);
			return card;
		} // end if
		else {
			Card card = new Card();
			return card;
		} // end else
	} // end dealCard()
	
	public Boolean isEmpty() {
		if(deck.size() == 0) {
			return true;
		} // end if
		else {
			return false;
		} // end else
	} // end isEmpty()
} // end class GameModel
