package pairWars.model;

import java.util.ArrayList;

public class GameModel extends AbstractModel {

	// List of players
	ArrayList<Player> players;
	// the dealer
	Dealer dealer;
	
	// create the number of players specified by either the program or the user
	public void createPlayers(int n) {
		players = new ArrayList<Player>();
		for(int x = 0; x < n; x++) {
			Player newPlayer = new Player(x);
			players.add(newPlayer);
		} // end for
	} // createPlayers()
	
	// create the dealer and they will immediately get a deck and shuffle it
	public void createDealer() {
		dealer = new Dealer();
	} // end createDealer()
	
	// deal out one card
	public void deal() {
		// loop through each of the players in the deck
		for(int x = 0; x < 3; x++) {
			// give each player 5 cards, might change later
			for(int y = 0; y < players.size(); y++) {
				// if the dealer is out of cards, then break the loop
				if(dealer.isEmpty() == true) 
					break; // end if
				// otherwise deal out a card
				Card card = dealer.dealCard();
				players.get(y).getCard(card);
				ModelEvent me = new ModelEvent(this, players.get(y).getID(), "NewCard", card.getFace(), card.getSuit(), -1, -1);
				notifyChanged(me);
				
				ModelEvent me2 = new ModelEvent(this, -1, "Log", "Player " + players.get(y).getID() + " gained a card." );
				notifyChanged(me2);
			} // end for
			// if the dealer has no more cards then break out of the loop and end dealing
			if(dealer.isEmpty() == true) 
				break; // end if
		} // end for
	} // end deal()
	
	public void dealCard(int playerID) {
		players.get(playerID).getCard(dealer.dealCard());
		Card temp = players.get(playerID).returnLastCard();
		ModelEvent me = new ModelEvent(this, playerID, "NewCard", temp.getFace(), temp.getSuit(), -1, -1);
		notifyChanged(me);
		
		ModelEvent me2 = new ModelEvent(this, -1, "Log", "Player " + playerID + " was dealt a card." );
		notifyChanged(me2);
	} // end dealCard()
	
	public Boolean winner(int playerID) {
		return players.get(playerID).isPair();
	} // end winner()
	
	public Boolean isEmpty() {
		return dealer.isEmpty();
	} // end isEmpty()
	
	// check if there is a pair in the player's hand
	public void pairCheck(int playerID) {
		boolean stop = false;
		while(stop != true) {
			ArrayList<Integer> temp = players.get(playerID).checkForPairs();
			if(temp.get(0) != -1) {
				ModelEvent me = new ModelEvent(this, playerID, "Pairs", temp.get(0), temp.get(1), temp.get(2), temp.get(3));
				notifyChanged(me);
				
				ModelEvent me2 = new ModelEvent(this, -1, "Log", "Player " + playerID + " has a pair." );
				notifyChanged(me2);
			} // end if
			else
				stop = true;
		} // end while
	} // end pairCheck()
	
	public void numsPair() {

		ArrayList<Integer> pairSizes = new ArrayList<Integer>();
		for(int x = 0; x < players.size(); x++) {
			pairSizes.add(players.get(x).returnPairs());
		}
		
		int count = 1;
		int biggest = -1;
		int index = -1;
		for(int y = 0; y < pairSizes.size(); y++) {
			if(pairSizes.get(y) > biggest) {
				biggest = pairSizes.get(y);
				index = y;
				count = 1;
			} // end if
			else if(pairSizes.get(y) == biggest) {
				count++;
			} // end else if
		} // end for
		
		if(count == players.size()) {
			ModelEvent me2 = new ModelEvent(this, -1, "Log", "Everybody ties" );
			notifyChanged(me2);
		} // end if
		else if(count < players.size() && count > 1) {
			ModelEvent me2 = new ModelEvent(this, -1, "Log", count + " players have tied" );
			notifyChanged(me2);
		} // end else if
		else if(count == 1) {
			ModelEvent me2 = new ModelEvent(this, -1, "Log", "Player " + (players.get(index).getID()+1) + " has won with " + biggest + " pairs.");
			notifyChanged(me2);
		} // end else if
	} // end numsPair()	

	public void exit() {
		// close the program
		System.exit(0);		
	} // end exit()
} // end class GameModel
