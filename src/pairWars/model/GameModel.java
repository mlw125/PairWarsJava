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
				Card card = dealer.dealCard();
				players.get(y).getCard(card);
				ModelEvent me = new ModelEvent(this, players.get(y).getID(), "NewCard", card.getFace(), card.getSuit(), -1, -1);
				notifyChanged(me);
			} // end for
			// if the dealer has no more cards then break out of the loop and end dealing
			if(dealer.isEmpty() == true) 
				break; // end if
		} // end for
	} // end deal()
	
	public void dealCard(int playerID) {
		System.out.println("Dealing out a card");
		players.get(playerID).getCard(dealer.dealCard());
		Card temp = players.get(playerID).returnLastCard();
		ModelEvent me = new ModelEvent(this, playerID, "NewCard", temp.getFace(), temp.getSuit(), -1, -1);
		notifyChanged(me);
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
	
	// Agent performs the operations for their own player and then gets the model to do it too
	// maybe have the functions in the model get a card an then pass it along to the Agent?
	
	/*	private ArrayList<Thread> threads = new ArrayList<Thread>();
		private ArrayList<Agent> runnables = new ArrayList<Agent>();

	// this will start the thread once the running window is created
	public void runThread(String id) {
		// find the runnable
		int index = findThread(id);
			
		// create thread
		Thread a = new Thread(runnables.get(index));
		a.setName(id);
		a.start();
		threads.add(a);
	} // end runThread()
		
	public void stopThread(String id) {	
		// find the thread on the list
		int index = findThread(id);
		// if found then interrupt the thread and kill it
		// the remove it from the list
		if( index > -1) {
			threads.get(index).interrupt();
			runnables.get(index).stopThread();
			threads.remove(index);
			runnables.remove(index);
		} // end if
	} // end stopThread
		
	public int findThread(String id) {
		// dont bother looking if the list is empty
		if(runnables.size() == 0) {
			return -1;
		} // end if
		// loop until the thread is found or return -1 if not found
		for(int x = 0; x < runnables.size(); x++) {
			if(runnables.get(x).getName().compareTo(id) == 0) {
				return x;
			} // end if
		} // end for
		return -1;
	} // end findThread()
		
	// inner class Agent - the monitor class
	class Agent implements Runnable {
		int id;
		String status;
		Player user = new Player();
			
		public Agent(String id, int index, int delay, String type, double amount) {
			this.title = title;
			this.index = index;
			this.delay = delay;
			this.type = type;
			this.amount = amount;			
			this.amountStr = precision.format(amount);
		} // end Agent()

		public void setPlayer(Player player) {
			this.player = player;		
		} // end setPlayer()

		synchronized public void stopThread() {
			this.status = "Stopped";
		} // end stopThread()
			
		public synchronized void addCardThread() {
			// deposit the amount
			user.depositThread(amount);
			//deposit("U", this.title, this.amountStr);
				
			ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
			notifyChanged(me);
		} // end depositThread()
			
		public synchronized void withdrawThread() {
			// notify that the window will be blocked
			ModelEvent me = new ModelEvent(this, id, "BL", 0, 0, 0);
			notifyChanged(me);
				
			// try to withdraw without being interrupted
			try {
				user.withdrawThread(amount);
			} catch (InterruptedException e) {
				return;
			} // end try/catch
				
			// notify that the thread is running again, usually the thread moves so fast that this never changes
			ModelEvent me2 = new ModelEvent(this, id, "RU", 0, 0, 0);
			notifyChanged(me2);
				
			// copied from the withdraw function from AccountModel
			// this will update the currency windows
			try {
				// convert the amount from string to double
				double usd = users.get(index).getAmount();
				// back to string to put into form 0.00
				String usdStr = precision.format(usd);
				// back to double
				usd = Double.parseDouble(usdStr);
						
				// convert to euro
				double euro = usd / EURO;
				String euroStr = precision.format(euro);
				euro = Double.parseDouble(euroStr);
						
				// convert to yuan
				double yuan = usd / YUAN;
				String yuanStr = precision.format(yuan);
				yuan = Double.parseDouble(yuanStr);
					
				total -= amount;
						
				// send change event
				ModelEvent me3 = new ModelEvent(this, title, "C", usd, euro, yuan);
				notifyChanged(me3);
			} // end try
			catch (NumberFormatException e) {
				// if the formatting does'nt work aka letter instead, extra symbols, etc.
				ModelEvent me3 = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me3);
			} // end catch			
		} //end withdrawThread()

		@Override
		public void run() {
			status = "Running";
			// loop until status says to stop
			while (!status.equals("Stopped")) {
				try {					
					// for a depositing agent
					if(type == "D") {
						// deposit the money
						depositThread();
							
						// increment the counter and update the operation completed and amount deposited
						counter++;
						ModelEvent me = new ModelEvent(this, id, "COUNT", counter, total, 0);
						notifyChanged(me);
					} // end if
					// for a withdrawing agent
					else {
						// withdraw the money
						withdrawThread();
							
						// change window status to open
						//ModelEvent me2 = new ModelEvent(this, id, "RU", 0, 0, 0);
						//notifyChanged(me2);
							
						// increment the counter and update the operation completed and amount deposited
						counter++;
						ModelEvent me = new ModelEvent(this, id, "COUNT", counter, total, 0);
						notifyChanged(me);
					} // end else
					// put thread to sleep
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					break;
					//e.printStackTrace();
				} // end catch
			} // end while
				
			// notify the window that the thread is stopped
			ModelEvent me = new ModelEvent(this, id, "ST", 0, 0, 0);
			notifyChanged(me);
		} // end run()
	} // end class Agent
	*/
} // end class GameModel
