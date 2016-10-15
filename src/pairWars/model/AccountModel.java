package pairWars.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AccountModel extends AbstractModel {
	
	// list to hold all of the user account
	public ArrayList<User> users = new ArrayList<User>();
	// list to hold the titles, for quick checking
	private ArrayList<String> titleList = new ArrayList<String>();
	// for formatting the money into proper format, doesn't always work though.
	DecimalFormat precision = new DecimalFormat("#0.00");
	
	// slightly different from suggested prices, checked up to date exchange rate.
	private static final double EURO =  1.13;
	private static final double YUAN =  0.15;
	
	// holds the file name that we read from
	private String filename;
	
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private ArrayList<Agent> runnables = new ArrayList<Agent>();
	
	public void exit() {
		// close the program
		System.exit(0);
	} // end exit()
	
	public void startThread(String id, String title, String delayStr, String type, String amountStr) {
		// if any of the fields are empty then we cannot continue, send error message
		if(id.equals("")) {
			ModelEvent me = new ModelEvent(this, "EM", "E", 0, 0, 0);
			notifyChanged(me);
		} // end if
		else if(delayStr.equals("0.0")) {
			ModelEvent me = new ModelEvent(this, "EM", "E", 0, 0, 0);
			notifyChanged(me);
		} // end else if
		else if(amountStr.equals("0.0")) {
			ModelEvent me = new ModelEvent(this, "EM", "E", 0, 0, 0);
			notifyChanged(me);
		} // end else if
		else {
			// search if the agent id is already on the list
			int search = findThread(id);
			// if the id cannot be found then create thread
			if(search == -1) {
				try {
					// get the index of the User object
					int index = 0;//findAccount(title);
					// since the operations per second can be entered as a double we convert it to one.
					double delay = 1000 / Double.parseDouble(delayStr);
					// however the sleep function only accepts ints so we convert it to int
					int delayInt = (int) delay;
					// convert the ampunt passed to double
					double amount = Double.parseDouble(amountStr);
					
					// create runnable
					Agent temp = new Agent(title, index, delayInt, type, amount);
					temp.setName(id);
					temp.setUser(users.get(index));
					runnables.add(temp);
					
					// close the start window and create new running window
					ModelEvent me = new ModelEvent(this, id, "RT", 0, 0, 0);
					notifyChanged(me);
				} // end try
				catch (NumberFormatException e) {
					ModelEvent me = new ModelEvent(this, "NUM", "E", 0, 0, 0);
					notifyChanged(me);
				} // end catch
			} // end if
			else {
				ModelEvent me = new ModelEvent(this, "ID", "E", 0, 0, 0);
				notifyChanged(me);
			}  // end else
		} // end else
	} // end startThread
	
	// this will start the thread once the runnign window is created
	public void runThread(String id) {
		// find the runnable
		int index = findThread(id);
		
		// create thread
		Thread a = new Thread(runnables.get(index));
		a.setName(id);
		a.start();
		threads.add(a);
	} // end runThread()
	
	public void stopThread(String id) {	
		// find the thread on the list
		int index = findThread(id);
		// if found then interrupt the thread and kill it
		// the remove it from the list
		if( index > -1) {
			threads.get(index).interrupt();
			runnables.get(index).stopThread();
			threads.remove(index);
			runnables.remove(index);
		} // end if
	} // end stopThread
	
	public int findThread(String id) {
		// dont bother looking if the list is empty
		if(runnables.size() == 0) {
			return -1;
		} // end if
		// loop until the thread is found or return -1 if not found
		for(int x = 0; x < runnables.size(); x++) {
			if(runnables.get(x).getName().compareTo(id) == 0) {
				return x;
			} // end if
		} // end for
		return -1;
	} // end findThread()
	
	// inner class Agent - the monitor class
	class Agent implements Runnable {
		String title;
		String type;
		String id;
		Double amount;
		String amountStr;
		int delay;
		int index;
		String status;
		double counter = 0;
		double total = 0.0;
		User user = new User();
		
		public Agent(String title, int index, int delay, String type, double amount) {
			this.title = title;
			this.index = index;
			this.delay = delay;
			this.type = type;
			this.amount = amount;			
			this.amountStr = precision.format(amount);
		} // end Agent()

		public void setUser(User user) {
			this.user = user;		
		} // end setUser()

		synchronized public void stopThread() {
			this.status = "Stopped";
		} // end stopThread()

		public void setName(String id) {
			this.id = id;	
		} // end setName()
		
		public String getName() {
			return id;
		} // end getName()
		
		public void setAmount(Double amount) {
			this.amount = amount;
		} // end setAmount()
		
		public double getAmount() {
			return amount;
		} // end getAmount()
		
		public synchronized void depositThread() {
			// deposit the amount
			user.depositThread(amount);
			//deposit("U", this.title, this.amountStr);
			total += amount;
			
			// copied from the deposit from Account Model
			// this will notify the currency windows to update
			
			// convert amount to double
			double usd = users.get(index).getAmount();
			// back to string for 0.00 format
			String usdStr = precision.format(usd);
			// back to double
			usd = Double.parseDouble(usdStr);
			
			// convert to euro
			double euro = usd / EURO;
			String euroStr = precision.format(euro);
			euro = Double.parseDouble(euroStr);
			
			// convert to yuan
			double yuan = usd / YUAN;
			String yuanStr = precision.format(yuan);
			yuan = Double.parseDouble(yuanStr);
			
			ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
			notifyChanged(me);
		} // end depositThread()
		
		public synchronized void withdrawThread() {
			// notify that the window will be blocked
			ModelEvent me = new ModelEvent(this, id, "BL", 0, 0, 0);
			notifyChanged(me);
			
			// try to withdraw without being interrupted
			try {
				user.withdrawThread(amount);
			} catch (InterruptedException e) {
				return;
			} // end try/catch
			
			// notify that the thread is running again, usually the thread moves so fast that this never changes
			ModelEvent me2 = new ModelEvent(this, id, "RU", 0, 0, 0);
			notifyChanged(me2);
			
			// copied from the withdraw function from AccountModel
			// this will update the currency windows
			try {
				// convert the amount from string to double
				double usd = users.get(index).getAmount();
				// back to string to put into form 0.00
				String usdStr = precision.format(usd);
				// back to double
				usd = Double.parseDouble(usdStr);
					
				// convert to euro
				double euro = usd / EURO;
				String euroStr = precision.format(euro);
				euro = Double.parseDouble(euroStr);
					
				// convert to yuan
				double yuan = usd / YUAN;
				String yuanStr = precision.format(yuan);
				yuan = Double.parseDouble(yuanStr);
				
				total -= amount;
					
				// send change event
				ModelEvent me3 = new ModelEvent(this, title, "C", usd, euro, yuan);
				notifyChanged(me3);
			} // end try
			catch (NumberFormatException e) {
				// if the formatting does'nt work aka letter instead, extra symbols, etc.
				ModelEvent me3 = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me3);
			} // end catch			
		} //end withdrawThread()

		@Override
		public void run() {
			status = "Running";
			// loop until status says to stop
			while (!status.equals("Stopped")) {
				try {					
					// for a depositing agent
					if(type == "D") {
						// deposit the money
						depositThread();
						
						// increment the counter and update the operation completed and amount deposited
						counter++;
						ModelEvent me = new ModelEvent(this, id, "COUNT", counter, total, 0);
						notifyChanged(me);
					} // end if
					// for a withdrawing agent
					else {
						// withdraw the money
						withdrawThread();
						
						// change window status to open
						//ModelEvent me2 = new ModelEvent(this, id, "RU", 0, 0, 0);
						//notifyChanged(me2);
						
						// increment the counter and update the operation completed and amount deposited
						counter++;
						ModelEvent me = new ModelEvent(this, id, "COUNT", counter, total, 0);
						notifyChanged(me);
					} // end else
					// put thread to sleep
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					break;
					//e.printStackTrace();
				} // end catch
			} // end while
			
			// notify the window that the thread is stopped
			ModelEvent me = new ModelEvent(this, id, "ST", 0, 0, 0);
			notifyChanged(me);
		} // end run()
	} // end class Agent
} // end AccountModel()
