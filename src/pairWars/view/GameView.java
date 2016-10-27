package pairWars.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pairWars.controller.Controller;
import pairWars.controller.GameController;
import pairWars.model.GameModel;
import pairWars.model.Model;
import pairWars.model.ModelEvent;

public class GameView extends JFrameView {
	public static final String SAVE = "Save";
	public static final String EXIT = "Exit";
	public static final String RUN = "Run"; 
	
	// the dialog box
	public JDialog error;
	// for checking for severe errors
	private boolean severeError = false;
	
	int playerCount = 0;
	ArrayList<PlayerView> players = new ArrayList<PlayerView>();
	JTextField player;
	
	LogView gameLog;

	public GameView(GameModel model, GameController controller) {
		super(model, controller);
		
		this.setTitle("Pair Wars");
		
		gameLog = new LogView(model, controller);
		
		JPanel buttonPanel = new JPanel();
		//JPanel agentPanel = new JPanel(); 
		Handler handler = new Handler();
		JButton jButtonSave = new JButton(SAVE); 
		jButtonSave.addActionListener(handler);
		JButton jButtonExit = new JButton(EXIT); 
		jButtonExit.addActionListener(handler); 
		JButton jButtonRun = new JButton(RUN); 
		jButtonRun.addActionListener(handler); 
		
		//buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
		buttonPanel.add(jButtonSave);
		buttonPanel.add(jButtonExit);
		buttonPanel.add(jButtonRun);
		
		JLabel instructions = new JLabel();
		instructions.setText("Players must be between 1-7 players.");
		
		player = new JTextField();
		player.setText("Enter Number of Players Here");
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.getContentPane().add(instructions, null);
		this.getContentPane().add(player, null);
		this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		pack();
	}

	@Override
	public void modelChanged(ModelEvent event) {
		// if the message contains E, then that means error and this view will
		// handle the event
		if(event.getMessage().equals("NUM")) {
			errorBox(event.getMessage2());
		} else if (event.getMessage().equals("Ini")) { // if the message is for initializing the view
			int playerCount = Integer.parseInt(event.getMessage2());
			Initialize(playerCount);
		} else if (event.getMessage().equals("RT")) { // if the message says to create a RunAgent window			
		} // end if/else
	}
	
	// Inner classes for Event Handling 
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			String number = player.getText();
			// send textfield.getText for number of players
			((GameController)getController()).operation(e.getActionCommand(), number); 
		} // end actionPerformed
	} // end class Handler
	
	// main function
	public static void main(String [] args) {
		new GameController();
	} // end main
	
	// for creating the error dialog box
	public void errorBox(String errorType) {
		// string hold the message
		String message = errorType;
			
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
		// get the player count and store it
		this.playerCount = playerCount;
		// loop through the players and create a player view for each
		for(int x = 0; x < playerCount; x++) {
			// create a player
			PlayerView newPlayer = new PlayerView(((GameModel)getModel()), ((GameController)getController()), x);
			newPlayer.setSize(500, 200);
			players.add(newPlayer);
		} // end for
		
		// set the game log to visible so the user can see it
		gameLog.setVisible(true);
		gameLog.setSize(500, 200);
		
		// tell the controller to start the game
		((GameController)getController()).operation("Start", Integer.toString(playerCount)); 
	} // end Initialize()

	public void clearPlayers() {
		// this will dispose of all players, assuming there are any
		if(players.size() > 0) {
			for(int x = 0; x < players.size(); x++) {
				players.get(x).dispose();
			} // end for
			players.clear();
		} // end if
		
		gameLog.dispose();
	} // end clearPlayers();
} // end class GameView
