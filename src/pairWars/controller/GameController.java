package pairWars.controller;

import pairWars.model.GameModel;
import pairWars.view.AccountView;
import pairWars.view.GameView;
import pairWars.view.JFrameView;

public class GameController extends AbstractController {
	public GameController(){
		setModel(new GameModel());
		setView(new GameView((GameModel)getModel(), this));
		((JFrameView)getView()).setSize(500, 200);
		((JFrameView)getView()).setVisible(true);
	} // end GameController()
	
	// for GameView operations
	public void operation(String option, int players){
		if(option.equals(AccountView.RUN)){ // pressing save
			((GameView)getView()).Initialize(players);
			runGame(players);
		} else if(option.equals(AccountView.EXIT)){ // pressing exit
			((GameModel)getModel()).exit();
		} else if(option.equals(AccountView.STOP)){ // opening us window
						
		} else if(option.equals(AccountView.SAVE)){ // opening euro window
				//((AccountModel)getModel()).openWindow(title, "OE");
		} else if(option.equals("OK")){ // for closing dialog box
			//((GameView)getView()).hideError();
		} // end if/else			
	} // end operation()
	
	public void runGame(int players) {
		// the rounds that will be played
		int rounds = 0;
		// the starting player
		int playerTurn = 0;
		// the main game area
		while(rounds < 3) {
			// to see if there was a winner
			Boolean winner = false;
			// create the dealer
			((GameModel)getModel()).createDealer();
			// create the players
			((GameModel)getModel()).createPlayers(players);
			// deal out the cards to everyone
			((GameModel)getModel()).deal();
			
			System.out.println("Round: " + rounds + " starting");
			// loop until someone has a pair
			while(winner != true) {
				// deal with dealer having no cards left in the deck
				System.out.println("Player: " + (playerTurn+1) + "'s turn\n");
				// if the deck is not empty then deal out another card
				if(((GameModel)getModel()).isEmpty() != true) {
					((GameModel)getModel()).dealCard(playerTurn);
				} // end if
				
				// check if the current player has a pair
				if(((GameModel)getModel()).winner(playerTurn) == true) {
					System.out.println("In winning loop.\n");
					// next round
					rounds++;
					// will break the current loop
					winner = true;
				} // end if
				
				/*
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
				// make sure if we reach the last player created then we will start with the first player,
				// otherwise go to next player
				if(playerTurn+1 == players)
					playerTurn = 0; // end if
				else
					playerTurn++; // end else
			} // end while
		} // end while
	} // end runGame()
} // end class GameController