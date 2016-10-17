package pairWars.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pairWars.controller.AccountController;
import pairWars.controller.Controller;
import pairWars.controller.GameController;
import pairWars.model.GameModel;
import pairWars.model.Model;
import pairWars.model.ModelEvent;
import pairWars.view.AccountView.Handler;

public class GameView extends JFrameView {
	public static final String SAVE = "Save";
	public static final String EXIT = "Exit";
	public static final String RUN = "Run"; 
	public static final String STOP = "Stop";
	
	// the dialog box
	public JDialog error;
	// for checking for severe errors
	private boolean severeError = false;
	
	int playerCount = 0;
	ArrayList<PlayerView> players; 

	public GameView(GameModel model, GameController controller) {
		super(model, controller);
		
		this.setTitle("Pair Wars");		
		
		JPanel buttonPanel = new JPanel();
		//JPanel agentPanel = new JPanel(); 
		Handler handler = new Handler();
		JButton jButtonSave = new JButton(SAVE); 
		jButtonSave.addActionListener(handler);
		JButton jButtonExit = new JButton(EXIT); 
		jButtonExit.addActionListener(handler); 
		JButton jButtonRun = new JButton(RUN); 
		jButtonRun.addActionListener(handler); 
		JButton jButtonStop = new JButton(STOP); 
		jButtonStop.addActionListener(handler); 
		
		buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
		buttonPanel.add(jButtonSave);
		buttonPanel.add(jButtonExit);
		buttonPanel.add(jButtonRun);
		buttonPanel.add(jButtonStop);
		
		JTextField players = new JTextField();
		players.setText("vbsbsbs");
		
		//this.setLayout(new GridLayout(4, 4, 5, 5));
		//this.getContentPane().add(players, null);
		this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		//this.add(players, null);
		pack();
	}

	@Override
	public void modelChanged(ModelEvent event) {
		// if the message contains E, then that means error and this view will
		// handle the event
		if(event.getMessage().equals("E")) {
			/*
			if(event.getTitle().equals("NUM")) { // number formatting error
				errorBox("NUM");
			} else if(event.getTitle().equals("NEG")) { // negative number for amount
				errorBox("NEG");
			} else if(event.getTitle().equals("LOAD")) { // loading file error, is severe
				errorBox("LOAD");
			} else if(event.getTitle().equals("SAVE")) { // saving file error, is severe
				errorBox("SAVE");
			} else if(event.getTitle().equals("ID")) { // for not having a unique thread id
				errorBox("ID");
			} else if(event.getTitle().equals("EM")) { // for having empty fields in a StartAgent window
				errorBox("EM");
			}// end if/else
			*/
		} else if (event.getMessage().equals("IC")) { // if the message is for initializing the view
			//initComboBox();
		} else if (event.getMessage().equals("RT")) { // if the message says to create a RunAgent window			
		} // end if/else
	}
	
	// Inner classes for Event Handling 
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			// send textfield.getText for number of players
			((GameController)getController()).operation(e.getActionCommand(), 3); 
		   } // end actionPerformed
	} // end class Handler
	
	// main function
	public static void main(String [] args) {
		new GameController();
	} // end main
	
	// for creating the error dialog box
	public void errorBox(String errorType) {
		// string hold the message
		String message = "Test";
		// if the error is number formatting
		if(errorType == "NUM") {
		    message = "Only Numbers are allowed";
		   } // end if
		// if the error is a negative number
		else if(errorType == "NEG") {
		    message = "Amount Cannot Not Be Negative";
		} // end else if
		// if the error is from file saving
		else if(errorType == "SAVE") {
		    message = "Error on saving File";
		    severeError = true;
		} // end else if
		// if the error is not having a unique thread id
		else if(errorType == "ID") {
		    message = "Agent ID must be Unique";
		} // end else if
		// if the error is having empty fields for a StartAgent window
		else if(errorType == "EM") {
		    message = "A field cannot be left empty";
		} // end else if
			
		// create new dialog box()
		error = new JDialog();
			
		// set up the box
		JTextField messagePanel = new JTextField();
		messagePanel.setText(message);
		messagePanel.setEditable(false);
		messagePanel.setOpaque(false);
		
		Handler handler = new Handler();
		JPanel buttonPane = new JPanel();
		JButton button = new JButton("OK");
		buttonPane.add(button);
		button.addActionListener(handler);
		error.setLayout(new GridLayout(4, 4));
		error.add(messagePanel, null);
		error.add(buttonPane, null);
		error.pack();
		    
		error.setVisible(true);
		error.setAlwaysOnTop(true);
	} // end errorBox()
		
	// controller will call this function, based on severeError status 
	// the view will close the box or end the program
	public void hideError() {
		// end the program
		if (severeError == true) {
			System.exit(0);
		} // end if
		// hide the dialog box
		else {
			error.setVisible(false);
		} // end else
	} // end hideError()

	public void Initialize(int playerCount) {
		this.playerCount = playerCount;
		players = new ArrayList<PlayerView>();
		for(int x = 0; x < playerCount; x++) {
			PlayerView newPlayer = new PlayerView(((GameModel)getModel()), ((GameController)getController()), x);
			players.add(newPlayer);
		}
	} 
} // end class GameView
