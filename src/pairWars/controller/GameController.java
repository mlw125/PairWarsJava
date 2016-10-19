package pairWars.controller;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import pairWars.model.GameModel;
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
		if(option.equals(GameView.RUN)){ // pressing save
			((GameView)getView()).Initialize(players);
			runGame(players);
		} else if(option.equals(GameView.EXIT)){ // pressing exit
			((GameModel)getModel()).exit();
		} else if(option.equals(GameView.STOP)){ // opening us window
				// may not be implemented	
		} else if(option.equals(GameView.SAVE)){ // opening euro window
				//((AccountModel)getModel()).openWindow(title, "OE");
		} else if(option.equals("OK")){ // for closing dialog box
			//((GameView)getView()).hideError();
		} // end if/else			
	} // end operation()
	
	boolean runGame;
	
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
				System.out.println("Player: " + (playerTurn+1) + "'s turn");
				// if the deck is not empty then deal out another card
				if(((GameModel)getModel()).isEmpty() != true) {
					((GameModel)getModel()).dealCard(playerTurn);
				} // end if
				
				// check if the current player has a pair
				if(((GameModel)getModel()).winner(playerTurn) == true) {
					System.out.println("In winning loop.");
					// next round
					rounds++;
					// will break the current loop
					winner = true;
				} // end if
				
				runGame = false;				
				
				// make sure if we reach the last player created then we will start with the first player,
				// otherwise go to next player
				if(playerTurn+1 == players)
					playerTurn = 0; // end if
				else
					playerTurn++; // end else
			} // end while
			
			// Need to find way to pause program without stopping the swing updating
			/*
		    Timer timer = new Timer(5000, listener);
		    timer.setRepeats(false);
		    timer.start();
		    */
		} // end while
		
		// say game is over
		System.out.println("Game over");
	} // end runGame()
	
	public void continueGame() {
		runGame = true;
		//((GameModel)getModel()).go();
	}
} // end class GameController