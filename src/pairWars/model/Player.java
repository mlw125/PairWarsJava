package pairWars.model;

import java.util.ArrayList;

public class Player extends AbstractModel implements Runnable {
	int id;
	ArrayList<Card> hand = new ArrayList<Card>();
	// make list for pairs
	
	public Player(int id) {
		this.id = id;
	}
	
	public void getCard(Card card) {
		hand.add(card);
		System.out.println(id + " getting a card\n");
		/*int temp = hand.size() - 1;
		ModelEvent me = new ModelEvent(this, id, "NewCard", hand.get(temp).getFace(), hand.get(temp).getSuit(), -1, -1);
		notifyChanged(me);*/
	}
	
	// check if there is a pair in the players hand
	public boolean isPair() {
		for(int x = 0; x < hand.size()-1; x++) {
			for(int y = x+1; y < hand.size(); y++) {
				if(hand.get(x).getFace() == hand.get(y).getFace()) {
					
					// Send the pair to be accessed by the PlayerView
					ModelEvent me = new ModelEvent(this, id, "Pair", hand.get(x).getFace(), hand.get(x).getSuit(), 
													hand.get(y).getFace(), hand.get(x).getSuit());
					notifyChanged(me);
					System.out.println(id + " is the winner.\n");
					return true;
				} // end if
			} // end for
		} // end for
		return false;
	} // end isPair()
	
	public Card returnLastCard() {
		int temp = hand.size()-1;
		return hand.get(temp);
	}

	@Override
	public void run() {	
		
	}
	
	int getID() {
		return id;
	}
}
