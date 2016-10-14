package pairWars.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import pairWars.controller.Controller;
import pairWars.model.Model;
import pairWars.model.ModelEvent;
import pairWars.view.euroBox.Handler;

public class PlayerView extends JFrameView {
	
	String title;

	public PlayerView(Model model, Controller controller, String title) {
		super(model, controller);
		
		new JFrame();
		this.setTitle("Player " + title);
		this.setVisible(false);
		this.title = title;
		
		Handler handler = new Handler();
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
