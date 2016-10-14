package pairWars.view;

import java.awt.BorderLayout;
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
public class StartAgent extends JFrameView {
	public static final String DEPOSIT = "Deposit"; 
	public static final String WITHDRAW = "Withdraw";
	public static final String DISMISS = "Dismiss";
	public JTextField agentIdField;
	private JTextField amountField;
	private JTextField operationsField;
	private String title;
	private String type;

	// constructor
	public StartAgent(AccountModel model, AccountController controller, String title, String type) {
		super(model, controller);
		this.type = type;
		
		// create a new frame and set the title
		// make the window invisible
		new JFrame();
		
		// for a deposit window
		if(type == "D") {
			this.setTitle("Deposit agent " + title);
			this.title = title;
			this.setVisible(false);
			
			// set up the text fields to default values
			JTextField staticAgentField = new JTextField();
			staticAgentField.setText("Agent ID:");
			staticAgentField.setEditable(false);
			staticAgentField.setColumns(10);
			agentIdField = new JTextField();
			agentIdField.setText("");
			agentIdField.setColumns(10);
			
			JTextField staticAmountField = new JTextField();
			staticAmountField.setText("Amount: ");
			staticAmountField.setEditable(false);
			staticAmountField.setColumns(10);
			amountField = new JTextField();
			amountField.setText("0.0");
			amountField.setColumns(10);

			JTextField staticOPField = new JTextField();
			staticOPField.setText("Operations Per Second");
			staticOPField.setEditable(false);
			staticOPField.setColumns(10);
			operationsField = new JTextField();
			operationsField.setText("0.0");
			operationsField.setColumns(10);
			
			JPanel agentPane = new JPanel();
			agentPane.add(staticAgentField, BorderLayout.WEST);
			agentPane.add(agentIdField, BorderLayout.EAST);
			
			JPanel amountPane = new JPanel();
			amountPane.add(staticAmountField, BorderLayout.WEST);
			amountPane.add(amountField, BorderLayout.EAST);
	
			JPanel opsPane = new JPanel();
			opsPane.add(staticOPField, BorderLayout.WEST);
			opsPane.add(operationsField, BorderLayout.EAST);
			
			// create the layout for the window
			Handler handler = new Handler();
			JButton jButtonDeposit = new JButton(DEPOSIT); 
			jButtonDeposit.addActionListener(handler);
			JButton jButtonDismiss = new JButton(DISMISS); 
			jButtonDismiss.addActionListener(handler);
			
			// button pane creation
			JPanel buttonPane = new JPanel();
			buttonPane.add(jButtonDeposit);
			buttonPane.add(jButtonDismiss);
			
			// set the layout
			this.setLayout(new GridLayout(4, 4, 5, 5));
			this.add(agentPane, null);
			this.add(amountPane, null);
			this.add(opsPane, null);
			this.add(buttonPane, null);
			this.pack();
		} // end if
		// for a withdraw window
		else if(type == "W") {
			this.setTitle("Withdraw agent " + title);
			this.title = title;
			this.setVisible(false);
			
			// set up the text fields to default values
			JTextField staticAgentField = new JTextField();
			staticAgentField.setText("Agent ID:");
			staticAgentField.setEditable(false);
			staticAgentField.setColumns(10);
			agentIdField = new JTextField();
			agentIdField.setText("");
			agentIdField.setColumns(10);
			
			JTextField staticAmountField = new JTextField();
			staticAmountField.setText("Amount: ");
			staticAmountField.setEditable(false);
			staticAmountField.setColumns(10);
			amountField = new JTextField();
			amountField.setText("0.0");
			amountField.setColumns(10);

			JTextField staticOPField = new JTextField();
			staticOPField.setText("Operations Per Second");
			staticOPField.setEditable(false);
			staticOPField.setColumns(10);
			operationsField = new JTextField();
			operationsField.setText("0.0");
			operationsField.setColumns(10);
			
			JPanel agentPane = new JPanel();
			agentPane.add(staticAgentField, BorderLayout.WEST);
			agentPane.add(agentIdField, BorderLayout.EAST);
			
			JPanel amountPane = new JPanel();
			amountPane.add(staticAmountField, BorderLayout.WEST);
			amountPane.add(amountField, BorderLayout.EAST);
	
			JPanel opsPane = new JPanel();
			opsPane.add(staticOPField, BorderLayout.WEST);
			opsPane.add(operationsField, BorderLayout.EAST);
			
			// create the layout for the window
			Handler handler = new Handler();
			JButton jButtonDeposit = new JButton(WITHDRAW); 
			jButtonDeposit.addActionListener(handler);
			JButton jButtonDismiss = new JButton(DISMISS); 
			jButtonDismiss.addActionListener(handler);
			
			// button pane creation
			JPanel buttonPane = new JPanel();
			buttonPane.add(jButtonDeposit);
			buttonPane.add(jButtonDismiss);
			
			// set the layout
			this.setLayout(new GridLayout(4, 4, 5, 5));
			this.add(agentPane, null);
			this.add(amountPane, null);
			this.add(opsPane, null);
			this.add(buttonPane, null);
			this.pack();
		} // end else
	} // end constructor
	
	// Handler subclass, for things that this window does
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			// need to pass the title and the amount currently held by the data text field
			//((AccountController)getController()).operationThread(e.getActionCommand(), title, 
			//														agentIdField.getText(), amountField.getText(),
			//														operationsField.getText()); 
	    } // end actionPerformed()
	} // end class Handler
	
	// MOdelChange event specially for this window type
	@Override
	public void modelChanged(ModelEvent event) {

	} // end modelChanged()
	
	public String getId() {
		return agentIdField.getText();
	}
	
	public String getTYPE() {
		return this.type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAmount() {
		return amountField.getText();
	}
	
	public String getOPS() {
		return operationsField.getText();
	}
} // end class usBox
