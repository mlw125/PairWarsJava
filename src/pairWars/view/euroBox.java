package pairWars.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pairWars.controller.AccountController;
import pairWars.controller.Controller;
import pairWars.model.Model;
import pairWars.model.ModelEvent;

public class euroBox extends JFrameView {
	public static final String DEPOSIT = "Deposit"; 
	public static final String WITHDRAW = "Withdraw";
	public static final String DISMISS = "Dismiss";
	public JTextField dataFieldEuro;
	private JTextField amountFieldEuro;
	private String title;

	public euroBox(Model model, Controller controller, String title) {
		super(model, controller);
		
		new JFrame();
		this.setTitle(title);
		this.setVisible(false);
		this.title = title;
				
		dataFieldEuro = new JTextField();
		dataFieldEuro.setText("0.0");
		amountFieldEuro = new JTextField();
		amountFieldEuro.setText("Amount: 0.0€");
		amountFieldEuro.setEditable(false);
				         
		Handler handler = new Handler();
		JButton jButtonDeposit = new JButton(DEPOSIT); 
		jButtonDeposit.addActionListener(handler);
		JButton jButtonWithdraw = new JButton(WITHDRAW); 
		jButtonWithdraw.addActionListener(handler); 
		JButton jButtonDismiss = new JButton(DISMISS); 
		jButtonDismiss.addActionListener(handler);
		
		JPanel buttonPane = new JPanel();
		buttonPane.add(jButtonDeposit);
		buttonPane.add(jButtonWithdraw);
		buttonPane.add(jButtonDismiss);
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(amountFieldEuro, null);
		this.add(dataFieldEuro, null);
		this.add(buttonPane, null);
		this.pack();		
	} // end constructor
	
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			//((AccountController)getController()).operationEuro(e.getActionCommand(), title, dataFieldEuro.getText()); 
	    } 
	}

	@Override
	public void modelChanged(ModelEvent event) {		
		if(event.getTitle() == this.title) {
			// open Euro window
			if(event.getMessage() == "OE") {
				this.setVisible(true);
			// close Euro window
			} else if(event.getMessage() == "CE") {
				this.setVisible(false);
			// change amount
			} else if(event.getMessage() == "C" || event.getMessage() == "I") {
				amountFieldEuro.setText("Amount: " + event.getEuro() + "€");
				dataFieldEuro.setText("0.0");
			}
		} // end if
	}
}
