package pairWars.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

import pairWars.controller.Controller;
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
		cards.setText("0.0");
		cards.setEditable(false);
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(cards, null);
		this.pack();
	}

	@Override
	public void modelChanged(ModelEvent event) {
		//textField.setText(textField.getText() + newStringHere);
		if(event.getID() == id) {
			if(event.getMessage().equals("NewCard")) { // number formatting error
				suit.add(event.getSuit1());
				face.add(event.getFace1());
				updateCards();
			}
		}
	}
	
	public void updateCards() {
		
	}

}
