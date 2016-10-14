package pairWars.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pairWars.controller.AccountController;
import pairWars.model.AccountModel;
import pairWars.model.ModelEvent;
import pairWars.view.StartAgent.Handler;

public class RunAgent extends JFrameView {
	public static final String STOP = "Stop Thread"; 
	public static final String DISMISS = "Dismiss";
	public JTextField agentIdField;
	private JTextField amountField;
	private JTextField operationsField;
	private JTextField stateField;
	private JTextField totalField;
	private JTextField completedField;
	private String type;
	private String id;
	private boolean ThreadStopped = false;
	private JButton jButtonDismiss;
	private JButton jButtonStop;

	public RunAgent(AccountModel model, AccountController controller, String title, String type, String amount, String ops, String id) {
		super(model, controller);
		this.type = type;
		this.id = id;
		
		new Frame();
		this.setVisible(true);
		
		// set up the window title for deposit or withdraw
		if(type == "D") {
			this.setTitle("Deposit Agent " + id + " for " + title); 
		} // end if
		else {
			this.setTitle("Withdraw Agent " + id + " for " + title); 		
		} // end else
		
		// set up the text fields to default values					
		JTextField staticAmountField = new JTextField();
		staticAmountField.setText("Amount in $: ");
		staticAmountField.setEditable(false);
		staticAmountField.setColumns(10);
		amountField = new JTextField();
		amountField.setText(amount);
		amountField.setColumns(10);
		amountField.setEditable(false);

		JTextField staticOPField = new JTextField();
		staticOPField.setText("Operations Per Second: ");
		staticOPField.setEditable(false);
		staticOPField.setColumns(15);
		operationsField = new JTextField();
		operationsField.setText(ops);
		operationsField.setColumns(10);
		operationsField.setEditable(false);
		
		JTextField staticStateField = new JTextField();
		staticStateField.setText("State: ");
		staticStateField.setEditable(false);
		staticStateField.setColumns(5);
		stateField = new JTextField();
		stateField.setText("Running");
		stateField.setColumns(10);
		stateField.setEditable(false);
		
		JTextField staticTotalField = new JTextField();
		staticTotalField.setText("Amount Transferred: ");
		staticTotalField.setEditable(false);
		staticTotalField.setColumns(15);
		totalField = new JTextField();
		totalField.setText("0");
		totalField.setColumns(10);
		totalField.setEditable(false);
		
		JTextField staticCompleteField = new JTextField();
		staticCompleteField.setText("Operations Completed: ");
		staticCompleteField.setEditable(false);
		staticCompleteField.setColumns(15);
		completedField = new JTextField();
		completedField.setText("0");
		completedField.setColumns(10);
		completedField.setEditable(false);
		
		JPanel amountPane = new JPanel();
		amountPane.add(staticAmountField, BorderLayout.WEST);
		amountPane.add(amountField, BorderLayout.EAST);

		JPanel opsPane = new JPanel();
		opsPane.add(staticOPField, BorderLayout.WEST);
		opsPane.add(operationsField, BorderLayout.EAST);
		
		JPanel statePane = new JPanel();
		statePane.add(staticStateField, BorderLayout.WEST);
		statePane.add(stateField, BorderLayout.EAST);
		
		JPanel totalPane = new JPanel();
		totalPane.add(staticTotalField, BorderLayout.WEST);
		totalPane.add(totalField, BorderLayout.EAST);
		
		JPanel completePane = new JPanel();
		completePane.add(staticCompleteField, BorderLayout.WEST);
		completePane.add(completedField, BorderLayout.EAST);
		
		Handler handler = new Handler();
		jButtonStop = new JButton(STOP); 
		jButtonStop.addActionListener(handler);
		jButtonDismiss = new JButton(DISMISS);
		jButtonDismiss.setEnabled(false);
		jButtonDismiss.addActionListener(handler);
		
		// button pane creation
		JPanel buttonPane = new JPanel();
		buttonPane.add(jButtonStop);
		buttonPane.add(jButtonDismiss);
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(amountPane, null);
		this.add(opsPane, null);
		this.add(statePane, null);
		this.add(totalPane, null);
		this.add(completePane, null);
		this.add(buttonPane, null);
		this.pack();
		
		// start the thread associated to this window, so that the thread will start after the window is created
		//((AccountController)getController()).operationRun("Start", id); 
	} // end RunAgent()
	
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			// need to pass the title and the amount currently held by the data text field
			//((AccountController)getController()).operationRun(e.getActionCommand(), getId()); 
	    } // end actionPerformed()
	} // end class Handler

	@Override
	public void modelChanged(ModelEvent event) {
		// make sure the id is the same as this window
		if(event.getTitle().equals(this.id)) {	
			if(event.getMessage().equals("RU")) { // set status to running
				stateField.setText("Running");
			} else if(event.getMessage().equals("ST")) { // set status to stopped
				stateField.setText("Stopped");
				// disable the stop button
				jButtonStop.setEnabled(false);
				// enable the dismiis button
				jButtonDismiss.setEnabled(true);
			} else if(event.getMessage().equals("BL")) { // set status to blocked
				stateField.setText("Blocked");
			} else if(event.getMessage().equals("COUNT")) { // update the operations and total amount
				// update the operations completed field
				Double counter = (Double) event.getDollar();
				String counterStr = counter.toString();
				completedField.setText(counterStr);
				
				// update total amount field
				Double total = (Double) event.getEuro();
				String totalStr = total.toString();
				totalField.setText(totalStr);
			} // end if/else
		} // end if
	} // end modelChanged
	
	public String getId() {
		return id;
	}
} // end class RunAgent
