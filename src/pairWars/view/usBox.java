package pairWars.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pairWars.controller.AccountController;
import pairWars.model.AccountModel;
import pairWars.model.ModelEvent;

// the euroBox and yuanClass will be the same as this, just different names.
public class usBox extends JFrameView {
	public static final String DEPOSIT = "Deposit"; 
	public static final String WITHDRAW = "Withdraw";
	public static final String DISMISS = "Dismiss";
	public JTextField dataFieldUS;
	private JTextField amountFieldUS;
	private String title;

	// constructor
	public usBox(AccountModel model, AccountController controller, String title) {
		super(model, controller);
		
		// create a new frame and set the title
		// make the window invisible
		new JFrame();
		this.setTitle(title);
		this.title = title;
		this.setVisible(false);
		
		// set up the text fields to default values
		dataFieldUS = new JTextField();
		dataFieldUS.setText("0.0");
		amountFieldUS = new JTextField();
		amountFieldUS.setText("Amount: $0.0");
		amountFieldUS.setEditable(false);

		// create the layout for the window
		Handler handler = new Handler();
		JButton jButtonDeposit = new JButton(DEPOSIT); 
		jButtonDeposit.addActionListener(handler);
		JButton jButtonWithdraw = new JButton(WITHDRAW); 
		jButtonWithdraw.addActionListener(handler); 
		JButton jButtonDismiss = new JButton(DISMISS); 
		jButtonDismiss.addActionListener(handler);
		
		// button pane creation
		JPanel buttonPane = new JPanel();
		buttonPane.add(jButtonDeposit);
		buttonPane.add(jButtonWithdraw);
		buttonPane.add(jButtonDismiss);
		
		// set the layout
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(amountFieldUS, null);
		this.add(dataFieldUS, null);
		this.add(buttonPane, null);
		this.pack();
	} // end constructor
	
	// Handler subclass, for things that this window does
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			// need to pass the title and the amount currently held by the data text field
			//((AccountController)getController()).operationUS(e.getActionCommand(), title, dataFieldUS.getText()); 
	    } // end actionPerformed()
	} // end class Handler
	
	// MOdelChange event specially for this window type
	@Override
	public void modelChanged(ModelEvent event) {
		// make sure we modify the right window
		if(event.getTitle() == this.title) {
			// open US window
			if(event.getMessage() == "OU") {
				this.setVisible(true);
			// close US window
			} else if(event.getMessage() == "CU") {
				this.setVisible(false);
			// change the data held in the text fields
			} else if(event.getMessage() == "C" || event.getMessage() == "I") {
				amountFieldUS.setText("Amount: $" + event.getDollar());
				dataFieldUS.setText("0.0");
			} // end if/else
		} // end if
	} // end modelChanged()
} // end class usBox
