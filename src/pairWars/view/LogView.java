package pairWars.view;

import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JTextField;

import pairWars.controller.Controller;
import pairWars.model.Model;
import pairWars.model.ModelEvent;

public class LogView extends JFrameView {
	
	// Just a text area to hold the game log
	TextArea log;

	public LogView(Model model, Controller controller) {
		super(model, controller);
		
		// make sure the window isn't displayed before it needs to be
		this.setVisible(false);
		
		// set up the log area
		log = new TextArea();
		log.setText("Game starting");
		log.setEditable(false);
		
		this.add(log, null);
		this.pack();
	} // end LogView()

	@Override
	public void modelChanged(ModelEvent event) {
		if(event.getMessage().equals("Log")) {
			String text = log.getText() + "\n";
			log.setText(text + event.getMessage2());
		} // end if
	} // end modelChanged()
} // end class LogView
