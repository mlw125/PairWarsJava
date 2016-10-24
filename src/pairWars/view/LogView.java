package pairWars.view;

import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JTextField;

import pairWars.controller.Controller;
import pairWars.model.Model;
import pairWars.model.ModelEvent;

public class LogView extends JFrameView {
	
	TextArea log;

	public LogView(Model model, Controller controller) {
		super(model, controller);
		
		this.setVisible(false);
		
		log = new TextArea();
		log.setText("Game starting");
		log.setEditable(false);
		
		this.add(log, null);
		this.pack();
	}

	@Override
	public void modelChanged(ModelEvent event) {
		if(event.getMessage().equals("Log")) {
			String text = log.getText() + "\n";
			log.setText(text + event.getMessage2());
		}

	}

}
