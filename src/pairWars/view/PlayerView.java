package pairWars.view;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

import pairWars.controller.Controller;
import pairWars.controller.GameController;
import pairWars.model.Model;
import pairWars.model.ModelEvent;

public class PlayerView extends JFrameView {
	
	int id; // id of the player to match with in the model
	// text field to display the cards
	public JTextField cards;
	public JTextField pairs;
	// Lists to hold the cards in the player's hand, as a string
	ArrayList<String> cardsList = new ArrayList<String>();

	// create the window parts
	public PlayerView(Model model, Controller controller, int id) {
		super(model, controller);
		
		new JFrame();
		this.setTitle("Player " + (id+1));
		this.setVisible(true);
		this.id = id;
		
		cards = new JTextField();
		cards.setText("No cards");
		cards.setEditable(false);
		
		pairs = new JTextField();
		pairs.setText("No cards");
		pairs.setEditable(false);
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(cards, null);
		this.add(pairs, null);
		this.pack();
	} // end playerView

	@Override
	public void modelChanged(ModelEvent event) {
		// check to make sure the message is for this player
		if(event.getPlayerID() == id) {
			// if the message is for a new card being added then add the card to the lists and update the text field.
			if(event.getMessage().equals("NewCard")) {
				int suit = event.getSuit1();
				int face = event.getFace1();
				updateCards(suit, face);
			} // end if
			else if(event.getMessage().equals("Pair")) {
				
			}
			
		} // end if
	} // end modelChanged()	
	
	// update the cards that are displayed
	public void updateCards(int suit, int face) {
		// reset the text for displaying the cards
		cards.setText("");
		// add the new card to the list
		CardView cardStr = new CardView();
		String cardValue = cardStr.getCard(suit, face);
		cardsList.add(cardValue);		
		// loop through and display every card in the player's hand
		for(int x = 0; x < cardsList.size(); x++) {
			String text = cards.getText();
			cards.setText(text + " | " + cardsList.get(x) + "\n");
		} // end for
	} // end updateCards()
} // end class PlayerView
