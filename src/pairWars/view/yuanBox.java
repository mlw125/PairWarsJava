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

public class yuanBox extends JFrameView {
	public static final String DEPOSITYUAN = "Deposit"; 
	public static final String WITHDRAWYUAN = "Withdraw";
	public static final String DISMISSYUAN = "Dismiss";
	public JTextField dataFieldYuan;
	private JTextField amountFieldYuan;
	private String title;

	public yuanBox(Model model, Controller controller, String title) {
		super(model, controller);
		
		new JFrame();
		this.setTitle(title);
		this.title = title;
		this.setVisible(false);
		
		dataFieldYuan = new JTextField();
		dataFieldYuan.setText("0.0");
		amountFieldYuan = new JTextField();
		amountFieldYuan.setText("Amount: ¥0.0");
		amountFieldYuan.setEditable(false);
		         
		Handler handler = new Handler();
		JButton jButtonDeposit = new JButton(DEPOSITYUAN); 
		jButtonDeposit.addActionListener(handler);
		JButton jButtonWithdraw = new JButton(WITHDRAWYUAN); 
		jButtonWithdraw.addActionListener(handler); 
		JButton jButtonDismiss = new JButton(DISMISSYUAN); 
		jButtonDismiss.addActionListener(handler);
		
		JPanel buttonPane = new JPanel();
		buttonPane.add(jButtonDeposit);
		buttonPane.add(jButtonWithdraw);
		buttonPane.add(jButtonDismiss);
		
		this.setLayout(new GridLayout(4, 4, 5, 5));
		this.add(amountFieldYuan, null);
		this.add(dataFieldYuan, null);
		this.add(buttonPane, null);
		this.pack();
	} // end constructor
	
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			//((AccountController)getController()).operationYuan(e.getActionCommand(), title, dataFieldYuan.getText()); 
	    } 
	} // end class Handler

	@Override
	public void modelChanged(ModelEvent event) {
		if(event.getTitle() == this.title) {
			// open Yuan window
			if(event.getMessage() == "OY") {
				this.setVisible(true);
			// close Yuan window
			} else if(event.getMessage() == "CY") {
				this.setVisible(false);
				// change amount
			} else if(event.getMessage() == "C" || event.getMessage() == "I") {
				amountFieldYuan.setText("Amount: ¥" + event.getYuan());
				dataFieldYuan.setText("0.0");
			} // if/else
		} // end if
	} // end modelChanged

}
