package pairWars.view;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

import pairWars.controller.Controller;
import pairWars.model.Model;
import pairWars.model.ModelEvent;

public class PlayerView extends JFrameView {
	
	int id; // id of the player to match with in the model
	// text field to display the cards
	public JTextField cards;
	public JTextField pairs;
	// Lists to hold the cards in the player's hand, as a string
	ArrayList<String> cardsList = new ArrayList<String>();
	// Lists to hold the pairs in the player's hand, as a string
	ArrayList<String> pairsList = new ArrayList<String>();

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

		this.setLayout(new GridLayout(2, 1));
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
				// get the card components 
				int suit = event.getSuit1();
				int face = event.getFace1();
				// update the hand
				updateCards(suit, face);
			} // end if
			else if(event.getMessage().equals("Pairs")) {
				// create temporary cards
				CardView card1 = new CardView();
				CardView card2 = new CardView();
				// get the string representation of the card
				String cardStr1 = card1.getCard(event.getSuit1(), event.getFace1());
				String cardStr2 = card2.getCard(event.getSuit2(), event.getFace2());
				// add the pairs to the list
				pairsList.add(cardStr1);
				pairsList.add(cardStr2);
				updatePairs();
			} // end else if			
		} // end if
	} // end modelChanged()	
	
	// update the cards that are displayed
	public void updateCards(int suit, int face) {
		// add the new card to the list
		CardView cardStr = new CardView();
		String cardValue = cardStr.getCard(suit, face);
		cardsList.add(cardValue);
		
		// update the window
		updateText();
	} // end updateCards()
	
	public void updateText() {
		// reset the text for displaying the cards
		cards.setText("");
		// loop through and display every card in the player's hand
		for(int x = 0; x < cardsList.size(); x++) {
			String text = cards.getText();
			cards.setText(text + " | " + cardsList.get(x) + "\n");
		} // end for
		
		// if the player has no pairs then we will not update the text
		if(pairsList.size() > 0 ) {
			// reset the text for the pairs field
			pairs.setText("");
			// loop through the pairs, two at a time
			for(int x = 0; x < pairsList.size(); x += 2) {
				String text = pairs.getText();
				pairs.setText(text + " | " + pairsList.get(x) + " " + pairsList.get(x+1) + "\n");
			} // end for
		} // end if
	} // end updateText()
	
	public void updatePairs() {
		// get the last two pairs added and remove them from the card list
		String temp = pairsList.get(pairsList.size()-2);
		String temp2 = pairsList.get(pairsList.size()-1);
		cardsList.remove(temp);
		cardsList.remove(temp2);
		// update the window
		updateText();
	} // end updatePairs()
} // end class PlayerView
