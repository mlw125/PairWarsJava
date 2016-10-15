package pairWars.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import pairWars.controller.Controller;
import pairWars.model.Model;
import pairWars.model.ModelEvent;
import pairWars.view.euroBox.Handler;

public class PlayerView extends JFrameView {
	
	String title;
	public JTextField cards;

	public PlayerView(Model model, Controller controller, String title) {
		super(model, controller);
		
		new JFrame();
		this.setTitle("Player " + title);
		this.setVisible(true);
		this.title = title;
		
		cards = new JTextField();
		cards.setText("0.0");
		cards.setEditable(false);
		
		Handler handler = new Handler();
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(cards, null);
		this.pack();
	}
	
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			//((AccountController)getController()).operationEuro(e.getActionCommand(), title, dataFieldEuro.getText()); 
	    } 
	}

	@Override
	public void modelChanged(ModelEvent event) {
		// TODO Auto-generated method stub

	}

}
