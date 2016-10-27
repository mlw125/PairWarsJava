package pairWars.controller;

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
	public void operation(String option, String players){
		if(option.equals(GameView.RUN)){ // pressing Run
			// kill the old players
			((GameView)getView()).clearPlayers();
			// have this run multiple games
			((GameModel)getModel()).createPlayers(players);
			//runGame(players);
		} else if(option.equals(GameView.EXIT)){ // pressing exit
			((GameModel)getModel()).exit();
		} else if(option.equals("Start")){ // pressing exit
				runGame(players);
		} else if(option.equals(GameView.SAVE)){ // opening euro window
				//((AccountModel)getModel()).openWindow(title, "OE");
		} else if(option.equals("OK")){ // for closing dialog box
			((GameView)getView()).hideError();
		} // end if/else			
	} // end operation()
	
	public void runGame(String players) {
		// the starting player
		int playerTurn = 0;
		// create the dealer
		((GameModel)getModel()).createDealer();
		// create the players
		//((GameModel)getModel()).createPlayers(players);
		int playersInt = Integer.parseInt(players);
		// deal out the cards to everyone
		((GameModel)getModel()).deal();
		
		// loop until someone has a pair
		while(((GameModel)getModel()).isEmpty() != true) {
			// deal with dealer having no cards left in the deck
			// if the deck is not empty then deal out another card
			if(((GameModel)getModel()).isEmpty() != true) {
				((GameModel)getModel()).dealCard(playerTurn);
			} // end if
			
			// check for any pairs in the player's hand
			((GameModel)getModel()).pairCheck(playerTurn);			
				
			// make sure if we reach the last player created then we will start with the first player,
			// otherwise go to next player
			if(playerTurn+1 == playersInt)
				playerTurn = 0; // end if
			else
				playerTurn++; // end else
		} // end while

		// finds the winner of the game
		((GameModel)getModel()).numsPair();	
		
		// say game is over
		System.out.println("Game over");
	} // end runGame()
} // end class GameController