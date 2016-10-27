package pairWars.model;

import java.util.ArrayList;

public class GameModel extends AbstractModel {

	// List of players
	ArrayList<Player> players;
	// the dealer
	Dealer dealer;
	
	// create the number of players specified by either the program or the user
	public void createPlayers(String n) {
		// if the number was sent as a non-number then throw an exception
		try {
			// convert the String into an int
			int numPlayers = Integer.parseInt(n);
			
			// if the number of players is less 1 then send an error
			if(numPlayers < 1) {
				ModelEvent me = new ModelEvent(this, -1, "NUM", "Too few players.");
				notifyChanged(me);
			} // end if
			// if the number of players is greater than 7 then send an error
			else if(numPlayers > 7) {
				ModelEvent me = new ModelEvent(this, -1, "NUM", "Too many players.");
				notifyChanged(me);
			} // end else if
			/// otherwise set the game up like it needs to be
			else {
				// set the array list to a new list
				players = new ArrayList<Player>();
				// create the correct number of players
				for(int x = 0; x < numPlayers; x++) {
					Player newPlayer = new Player(x);
					players.add(newPlayer);
				} // end for
				ModelEvent me = new ModelEvent(this, -1, "Ini", n);
				notifyChanged(me);
			} // end else		
		} // end try
		catch (NumberFormatException e) {
			ModelEvent me = new ModelEvent(this, -1, "NUM", "Issue with number of players.");
			notifyChanged(me);
		} // end catch
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
				
				ModelEvent me2 = new ModelEvent(this, -1, "Log", "Player " + (players.get(y).getID()+1) + " gained a card." );
				notifyChanged(me2);
			} // end for
			// if the dealer has no more cards then break out of the loop and end dealing
			if(dealer.isEmpty() == true) 
				break; // end if
		} // end for
	} // end deal()
	
	public void dealCard(int playerID) {
		//hand out a card to the player with the matching id
		players.get(playerID).getCard(dealer.dealCard());
		// get the card the player just got for display purposes
		Card temp = players.get(playerID).returnLastCard();
		// send the event so that the card can be displayed on the playerview
		ModelEvent me = new ModelEvent(this, playerID, "NewCard", temp.getFace(), temp.getSuit(), -1, -1);
		notifyChanged(me);
		// send an even so that the even can be logged by logview
		ModelEvent me2 = new ModelEvent(this, -1, "Log", "Player " + (playerID+1) + " was dealt a card." );
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
				
				ModelEvent me2 = new ModelEvent(this, -1, "Log", "Player " + (playerID+1) + " has a pair." );
				notifyChanged(me2);
			} // end if
			else
				stop = true;
		} // end while
	} // end pairCheck()
	
	// this method will find the number of pairs in each players hand and then figure out who the winner is
	public void numsPair() {
		// create an array list of integers to hold the number of pairs each has
		ArrayList<Integer> pairSizes = new ArrayList<Integer>();
		for(int x = 0; x < players.size(); x++) {
			pairSizes.add(players.get(x).returnPairs());
		} // end for
		
		// this will hold the number of players with the same amount of pairs.
		int count = 1;
		// this will hold the largest number we find
		int biggest = -1;
		// this will get the index of the player with the largest pair count
		int index = -1;
		// loop through each of the players
		for(int y = 0; y < pairSizes.size(); y++) {
			// if the player has a bigger pair count than the others than it is the new biggest.
			// the index is set to the current player and we will reset the count.
			if(pairSizes.get(y) > biggest) {
				biggest = pairSizes.get(y);
				index = y;
				count = 1;
			} // end if
			// otherwise increment the count value
			else if(pairSizes.get(y) == biggest) {
				count++;
			} // end else if
		} // end for
		
		// if the count is equal to the number of players then we every ties
		if(count == players.size()) {
			ModelEvent me2 = new ModelEvent(this, -1, "Log", "Everybody ties" );
			notifyChanged(me2);
		} // end if
		// if the the count is not equal to everyone, but more than one person then a few people tied.
		else if(count < players.size() && count > 1) {
			ModelEvent me2 = new ModelEvent(this, -1, "Log", count + " players have tied" );
			notifyChanged(me2);
		} // end else if
		// if only one player has the biggest pair count then we display that player
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
