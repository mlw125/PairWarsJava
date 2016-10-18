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
	
	int id;
	public JTextField cards;
	ArrayList<Integer> suit = new ArrayList<Integer>();
	ArrayList<Integer> face = new ArrayList<Integer>();

	public PlayerView(Model model, Controller controller, int id) {
		super(model, controller);
		
		new JFrame();
		this.setTitle("Player " + (id+1));
		this.setVisible(true);
		this.id = id;
		
		cards = new JTextField();
		cards.setText("No cards");
		cards.setEditable(false);
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(cards, null);
		this.pack();
	}

	@Override
	public void modelChanged(ModelEvent event) {
		System.out.println(id);
		if(event.getID() == id) {
			System.out.println(id + " event recieved");
			if(event.getMessage().equals("NewCard")) {
				suit.add(event.getSuit1());
				face.add(event.getFace1());
				updateCards();
			}
		}
	}
	
	public void updateCards() {
		cards.setText("");
		for(int x = 0; x < suit.size(); x++) {
			CardView cardStr = new CardView();
			String cardValue = cardStr.getCard(suit.get(x), face.get(x));
			String text = cards.getText();
			cards.setText(text + " | " + cardValue);
		}
	}

}
