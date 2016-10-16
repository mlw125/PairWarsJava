package pairWars.controller;
import pairWars.model.GameModel;
import pairWars.view.AccountView;
import pairWars.view.JFrameView;

public class AccountController extends AbstractController {
	public AccountController(){
		setModel(new AccountModel());
		setView(new AccountView((GameModel)getModel(), this));
		((JFrameView)getView()).setSize(500, 200);
		((JFrameView)getView()).setVisible(true);
	}
	
	// for AccountView operations
	public void operation(String option, int players){
		
		if(option.equals(AccountView.RUN)){ // pressing save
			//((AccountModel)getModel()).save();
		} else if(option.equals(AccountView.EXIT)){ // pressing exit
			((AccountModel)getModel()).exit();
		} else if(option.equals(AccountView.STOP)){ // opening us window
			//((AccountModel)getModel()).openWindow(title, "OU");			
		} else if(option.equals(AccountView.SAVE)){ // opening euro window
			//((AccountModel)getModel()).openWindow(title, "OE");
		}/* else if(option.equals(AccountView.EDITYUAN)){ // opening yuan window
			//((AccountModel)getModel()).openWindow(title, "OY");
		} else if(option.equals(AccountView.DEPOSIT)){ // for a agent that will start a deposit thread
			((AccountView)getView()).threadStart(title, "D");
		} else if(option.equals(AccountView.WITHDRAW)){ // for a agent that will start a withdraw thread
			((AccountView)getView()).threadStart(title, "W");
		} else if(option.equals("Initialize")){ // Initializing the main view, not a button 
			((AccountModel)getModel()).initData(title);
		}*/ else if(option.equals("OK")){ // for closing dialog box
			((AccountView)getView()).hideError();
		} // end if/else
		
	} // end operation()
	
	/*
	// get the titles of each user
	public String[] getTitles() {
		String[] comboData = [" "];// = ((AccountModel)getModel()).returnList();
		return comboData;
	} // end getTitles()
	*/
/*
	// for buttons on the us windows
	public void operationUS(String actionCommand, String title, String amount) {
		if(actionCommand.equals("Dismiss")) { // dismiss button
			((AccountModel)getModel()).closeWindow(title, "CU");
		} else if (actionCommand.equals("Deposit")) { // deposit button
			// maybe have these return true or false
			((AccountModel)getModel()).deposit("U", title, amount);
		} else if (actionCommand.equals("Withdraw")) { // withdraw button
			((AccountModel)getModel()).withdraw("U", title, amount);
		} // end if/else
	} // end operationUS

	// for euro windows, same as us function, different parameters passed
	public void operationEuro(String actionCommand, String title, String amount) {
		if(actionCommand.equals("Dismiss")) {
			((AccountModel)getModel()).closeWindow(title, "CE");
		} else if (actionCommand.equals("Deposit")) {
			// maybe have these return true or false
			((AccountModel)getModel()).deposit("E", title, amount);
		} else if (actionCommand.equals("Withdraw")) {
			((AccountModel)getModel()).withdraw("E", title, amount);
		} // end if else		
	} // end operationEuro
	
	// for yuan windows, same as us function, different parameters passed
	public void operationYuan(String actionCommand, String title, String amount) {
		if(actionCommand.equals("Dismiss")) {
			((AccountModel)getModel()).closeWindow(title, "CY");
		} else if (actionCommand.equals("Deposit")) {
			((AccountModel)getModel()).deposit("Y", title, amount);
		} else if (actionCommand.equals("Withdraw")) {
			((AccountModel)getModel()).withdraw("Y", title, amount);
		} // end if else		
	} // end operationYuan

	// for the startAgent windows
	public void operationThread(String actionCommand, String title, String id, String amount, String delay) {
		// for a depositing agent
		if(actionCommand.equals("Deposit")) {
			((AccountModel)getModel()).startThread(id, title, delay, "D", amount);
		} // end if
		// for a withdrawing agent
		else if(actionCommand.equals("Withdraw")) {
			((AccountModel)getModel()).startThread(id, title, delay, "W", amount);
		} // end else if
		// to dismiss the window
		else if(actionCommand.equals("Dismiss")) {
			((AccountModel)getModel()).closeWindow(id, "SW");
		} // end else if
	} // end operationThread

	// for a runAGent window
	public void operationRun(String actionCommand, String id) {
		// not a button, but the command when the window is done being created
		if(actionCommand.equals("Start")) {
			((AccountModel)getModel()).runThread(id);
		} else if(actionCommand.equals("Stop Thread")) { // when the stop button is pressed
			((AccountModel)getModel()).stopThread(id);
		} else if(actionCommand.equals("Dismiss")) { // when the dismiss button is pressed
			((AccountModel)getModel()).closeWindow(id, "RW");
		} // end if/else
	} // end operationRun
	*/
} // end class AccountController
