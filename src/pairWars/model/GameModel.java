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
		System.out.println("Handing out cards");
		// loop through each of the players in the deck
		for(int x = 0; x < 5; x++) {
			// give each player 5 cards, might change later
			for(int y = 0; y < players.size(); y++) {
				// if the dealer is out of cards, then break the loop
				if(dealer.isEmpty() == true) 
					break; // end if
				// otherwise deal out a card
				players.get(y).getCard(dealer.dealCard());
			} // end for
			// if the dealer has no more cards then break out of the loop and end dealing
			if(dealer.isEmpty() == true) 
				break; // end if
		} // end for
	} // end deal()
	
	public void dealCard(int playerID) {
		System.out.println("Dealing out a card");
		players.get(playerID).getCard(dealer.dealCard());		
	} // end dealCard()
	
	public Boolean winner(int playerID) {
		return players.get(playerID).isPair();
	}
	
	public Boolean isEmpty() {
		return dealer.isEmpty();
	} // end isEmpty()

	public void exit() {
		// close the program
		System.exit(0);		
	}
} // end class GameModel
