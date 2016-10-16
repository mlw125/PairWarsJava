package pairWars.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
		// TODO Auto-generated method stub
		
	}
	
	// Inner classes for Event Handling 
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			// catch mainly used when there is an error reading the file, this way the program can get to the part
			// where the dialog box can be displayed
			try {
				((AccountController)getController()).operation(e.getActionCommand(), 3); 
			} // end try
			catch (NullPointerException error) {
				((AccountController)getController()).operation(e.getActionCommand(), -1); 
			} // end catch
		   } // end actionPerformed
	} // end class Handler
	
	// main function
	public static void main(String [] args) {
		new GameController();
	} // end main

}
