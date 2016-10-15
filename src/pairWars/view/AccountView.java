package pairWars.view;
import javax.swing.*; 

import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList;

import pairWars.controller.AccountController;
import pairWars.model.AccountModel;
import pairWars.model.ModelEvent;

public class AccountView extends JFrameView {
	public static final String SAVE = "Save";
	public static final String EXIT = "Exit";
	public static final String RUN = "Run"; 
	public static final String STOP = "Stop";
	
	// lists to hold all the data
	private static ArrayList<usBox> usList = new ArrayList<usBox>();
	private static ArrayList<euroBox> euroList = new ArrayList<euroBox>();
	private static ArrayList<yuanBox> yuanList = new ArrayList<yuanBox>();
	private static ArrayList<StartAgent> startList = new ArrayList<StartAgent>();
	private static ArrayList<RunAgent> runList = new ArrayList<RunAgent>();
	// private static ArrayList<RunAgent> players = new ArrayList<RunAgent>();
	// private static Dealer dealer = new Dealer();
	
	
	// the dialog box
	public JDialog error;
	// for checking for severe errors
	private boolean severeError = false;
	
	//private String operation = PLUS; 
	public AccountView(AccountModel model, AccountController controller) { 
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
	 } // end constructor
	
	public void modelChanged(ModelEvent event) {
		// if the message contains E, then that means error and this view will
		// handle the event
		if(event.getMessage().equals("E")) {
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
		} else if (event.getMessage().equals("IC")) { // if the message is for initializing the view
			//initComboBox();
		} else if (event.getMessage().equals("RT")) { // if the message says to create a RunAgent window
			// find the corresponding start agent window
			int index = findStartWindow(event.getTitle());
			// create temporary object
			StartAgent temp = startList.get(index);
			// initialize a RunAgent window
			RunAgent test = new RunAgent(((AccountModel)getModel()), ((AccountController)getController()), 
											temp.getTitle(), temp.getTYPE(), temp.getAmount(), temp.getOPS(), temp.getId());
			// add it to the list
			runList.add(test);
			// remove the StartAgent from the list
			startList.remove(temp);
			// destroy it
			temp.dispose();
		} else if (event.getMessage().equals("SW")) { // for dismissing a StartAgent window
			// find the window
			int index = findStartWindow(event.getTitle());
			// remove and destroy it
			StartAgent temp = startList.get(index);
			startList.remove(temp);
			temp.dispose();
		} else if (event.getMessage().equals("RW")) { // for dismissing a RunAgent window
			// find the window
			int index = findRunWindow(event.getTitle());
			// remove and destroy it
			RunAgent temp = runList.get(index);
			runList.remove(temp);
			temp.dispose();
		} // end if/else
	} // end modelChanged()
	
	// for finding s specific StartAgent window, based on id
	private int findStartWindow(String id) {
		for(int x = 0; x < startList.size(); x++) {
			if(startList.get(x).getId().compareTo(id) == 0) {
				return x;
			}
		}
		return -1;
	} // end findStartWindow()
	 
	 // for finding a specific RunAgent on the list, based on id
	private int findRunWindow(String id) {
		for(int x = 0; x < runList.size(); x++) {
			if(runList.get(x).getId().compareTo(id) == 0) {
				return x;
			}
		}
		return -1;
	} // end findRunWindow()

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
		new AccountController();
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
		// if the number is from file loading
		else if(errorType == "LOAD") {
	    	message = "Error on Loading File";
	    	severeError = true;
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
	
	// this will create a new StartAgent window, type determined by button press (Deposit or Withdraw)
	public void threadStart(String title, String type) {
		StartAgent test = new StartAgent(((AccountModel)getModel()), ((AccountController)getController()), title, type);
		test.setVisible(true);
		startList.add(test);
	} // end threadStart()
} // end class AccountView
