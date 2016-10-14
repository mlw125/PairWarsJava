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
	
	// deposit money
	// needs the currency type, title of window, and amount to change by.
	// ideally this function could be much smaller, but ran out of time.
	public void deposit(String currency, String title, String amount) {
		// holds the index of the user account
		int index = findAccount(title);

		// if we are getting dollars
		if(currency == "U") {
			// try to convert to double, will make sure we don't get alphabetical values
			try {
				// convert amount to double
				double usd = Double.parseDouble(amount);
				// back to string for 0.00 format
				String usdStr = precision.format(usd);
				// back to double
				usd = Double.parseDouble(usdStr);
				// store result
				users.get(index).depositThread(usd);
				// get the new amount to pass to the windows
				usd = users.get(index).getAmount();
				
				// convert to euro
				double euro = usd / EURO;
				String euroStr = precision.format(euro);
				euro = Double.parseDouble(euroStr);
				
				// convert to yuan
				double yuan = usd / YUAN;
				String yuanStr = precision.format(yuan);
				yuan = Double.parseDouble(yuanStr);
				
				// send event notifying change in amount
				ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
				notifyChanged(me);
				
				// maybe add notify changed for the threads
			} // end try
			catch (NumberFormatException e) {
				ModelEvent me = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me);
			} // end catch
		} // end if
		// for euro, pattern will be the same as above
		else if(currency == "E") {
			try {
				double usd = Double.parseDouble(amount) * EURO; 
				String usdStr = precision.format(usd);
				usd = Double.parseDouble(usdStr);
				users.get(index).depositThread(usd);
				usd = users.get(index).getAmount();
				
				double euro = usd / EURO;
				String euroStr = precision.format(euro);
				euro = Double.parseDouble(euroStr);
				
				double yuan = usd / YUAN;
				String yuanStr = precision.format(yuan);
				yuan = Double.parseDouble(yuanStr);
				
				ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
				notifyChanged(me);
			} // end try
			catch (NumberFormatException e) {
				ModelEvent me = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me);
			} // end catch
		} // end else if
		// for yuan
		else if(currency == "Y") {
			try {
				double usd = Double.parseDouble(amount) * YUAN;
				String usdStr = precision.format(usd);
				usd = Double.parseDouble(usdStr);
				users.get(index).depositThread(usd);
				usd = users.get(index).getAmount();
				
				double euro = usd / EURO;
				String euroStr = precision.format(euro);
				euro = Double.parseDouble(euroStr);
	
				double yuan = usd / YUAN;
				String yuanStr = precision.format(yuan);
				yuan = Double.parseDouble(yuanStr);
				
				ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
				notifyChanged(me);
			} // end try
			catch (NumberFormatException e) {
				ModelEvent me = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me);
			} // end catch
		} // end else if
	} // end deposit()
	
	public void withdraw(String currency, String title, String amount) {
		// have this send an error to the main view
		int index = findAccount(title);
		
		// ideally this massive function could be reduced, but I ran out of time
		// for US
		if(currency == "U") {			
			try {
				// convert the amount from string to double
				double usd = Double.parseDouble(amount);
				// back to string to put into form 0.00
				String usdStr = precision.format(usd);
				// back to double
				usd = Double.parseDouble(usdStr);
				// get the amount after the withdraw
				usd = users.get(index).getAmount() - usd;
				
				// if amount would be negative
				if(usd < 0) {
					// tell view to make appropriate dialog box
					ModelEvent me = new ModelEvent(this, "NEG", "E", 0, 0, 0);
					notifyChanged(me);
				} // end if
				else {
					// store new amount for the user
					users.get(index).setAmount(usd);
					
					// convert to euro
					double euro = usd / EURO;
					String euroStr = precision.format(euro);
					euro = Double.parseDouble(euroStr);
					
					// convert to yuan
					double yuan = usd / YUAN;
					String yuanStr = precision.format(yuan);
					yuan = Double.parseDouble(yuanStr);
					
					// send change event
					ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
					notifyChanged(me);
				} // end else
			} // end try
			catch (NumberFormatException e) {
				// if the formatting does'nt work aka letter instead, extra symbols, etc.
				ModelEvent me = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me);
				
				if(amount.equals("AA.0")) {	throw new NumberFormatException("Bad Format"); }
			} // end catch
		}
		// for euro, the pattern will be the same as above
		else if(currency == "E") {
			try {
				double usd = Double.parseDouble(amount) * EURO; 
				String usdStr = precision.format(usd);
				usd = Double.parseDouble(usdStr);
				usd = users.get(index).getAmount() - usd;
				
				if(usd < 0) {
					ModelEvent me = new ModelEvent(this, "NEG", "E", 0, 0, 0);
					notifyChanged(me);
				}
				else {
					users.get(index).setAmount(usd);
					
					double euro = usd / EURO;
					String euroStr = precision.format(euro);
					euro = Double.parseDouble(euroStr);
					
					double yuan = usd / YUAN;
					String yuanStr = precision.format(yuan);
					yuan = Double.parseDouble(yuanStr);
					
					ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
					notifyChanged(me);
				} // end else
			} // end try
			catch (NumberFormatException e) {
				ModelEvent me = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me);
			}	// end catch
		} // end else if
		// for yuan
		else if(currency == "Y") {
			try {
				double usd = Double.parseDouble(amount) * YUAN;
				String usdStr = precision.format(usd);
				usd = Double.parseDouble(usdStr);
				usd = users.get(index).getAmount() - usd;
				
				if(usd < 0) {
					ModelEvent me = new ModelEvent(this, "NEG", "E", 0, 0, 0);
					notifyChanged(me);
				} // end if
				else {
					users.get(index).setAmount(usd);
					
					double euro = usd / EURO;
					String euroStr = precision.format(euro);
					euro = Double.parseDouble(euroStr);
		
					double yuan = usd / YUAN;
					String yuanStr = precision.format(yuan);
					yuan = Double.parseDouble(yuanStr);
					
					ModelEvent me = new ModelEvent(this, title, "C", usd, euro, yuan);
					notifyChanged(me);
				} // end else
			} // end try
			catch (NumberFormatException e) {
				ModelEvent me = new ModelEvent(this, "NUM", "E", 0, 0, 0);
				notifyChanged(me);
			}	// end catch
		} // end else if
	} // end withdraw()
	
	public void exit() {
		// close the program
		System.exit(0);
	} // end exit()
	
	public void save() {
		try {
	    	// try to read the data inside books.txt
            FileWriter fileWriter = new FileWriter(filename); // change to filename
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            // set up the file header
            bufferedWriter.write("    name       |   id   |   amount ");
            bufferedWriter.write(System.getProperty("line.separator"));
            bufferedWriter.write("--------------------------------------");
            bufferedWriter.write(System.getProperty("line.separator"));
            
            // loop through each account that was stored
            for(int x = 0; x < users.size(); x++) {
            	// split the first and last name from each other
            	String [] name = users.get(x).getName().split(",");
            	bufferedWriter.write(" " + name[1] + " " + name[0] + " |");
            	// get the id
            	bufferedWriter.write(" " + users.get(x).getId() + " |");
            	// convert their current amount to string and save it
            	String amountStr = precision.format(users.get(x).getAmount());
            	bufferedWriter.write(" $" + amountStr);
                bufferedWriter.write(System.getProperty("line.separator"));
            } // end for      
            bufferedWriter.close();
		} // end try
		catch (FileNotFoundException e) {
			System.out.println("\n" + filename + " not found\n");
			ModelEvent me = new ModelEvent(this, "SAVE", "E", 0, 0, 0);
			notifyChanged(me);
		} // end catch
		catch (IOException e) {
			System.out.println("\n" + filename + " not found\n");
			ModelEvent me = new ModelEvent(this, "SAVE", "E", 0, 0, 0);
			notifyChanged(me);
		} // end catch
	} // end save()

	public void load(String filename) {
		try {
	    	// try to read the data inside books.txt
            FileReader fileReader = new FileReader(filename); // change to filename
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            // read the two unneeded lines
            line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            
            line = bufferedReader.readLine();
            while(line != null) {
            	User temp = new User();
            	// split the line
            	String[] data = line.split(" ");
            	
            	// set up the name as Last,First
            	String name = data[2] + "," + data[1];
            	temp.setName(name);
            	
            	// get the account id
            	String id = data[4];
            	temp.setId(id);

            	// get the account amount, remove the dollar sign, and turn it into a double
            	String money = data[6];
            	money = money.replace("$", "");
            	double amount = Double.parseDouble(money);
            	temp.setAmount(amount);
            	
            	// add the temp user to the list of users
            	users.add(temp);

                line = bufferedReader.readLine();
            } // end while
            bufferedReader.close();
            
            // get the filename for saving
            this.filename = filename;
            
            // sort the users based on id
            sortUsers();
            
            // tell the view that it can start initialize the combo box.
			ModelEvent me = new ModelEvent(this, "", "IC", 0, 0, 0);
			notifyChanged(me);
            // need to sort the list.
	    } // end try
		// each catch will send a message to the view to explain what the
		// dialog box should say. These should all be severe errors, ending the program
		catch (FileNotFoundException e)
		{
			System.out.println("\n" + filename + " not found\n");
			ModelEvent me = new ModelEvent(this, "LOAD", "E", 0, 0, 0);
			notifyChanged(me);
		} // end catch()
		catch (NumberFormatException e) 
		{
			System.out.println("\nError in reading the file.\n");
			ModelEvent me = new ModelEvent(this, "LOAD", "E", 0, 0, 0);
			notifyChanged(me);
		} // end catch
		catch (IOException e) {
			ModelEvent me = new ModelEvent(this, "LOAD", "E", 0, 0, 0);
			notifyChanged(me);
		} // end catch
		catch (ArrayIndexOutOfBoundsException e) {
			ModelEvent me = new ModelEvent(this, "LOAD", "E", 0, 0, 0);
			notifyChanged(me);
		} // end catch
	} // end load()
	
	// returns a list of all the titles for the windows and adds them to a local
	// list for easier access in the future
	public String[] returnList() {
		// get the size of the users list, for readability
		int size = users.size();
		// create an array the same size as users
		String[] strList = new String[size];
		// loop through the new array
		for(int x = 0; x < strList.length; x++) {
			// get the id and name of the account and combine them for a title
			String comboData = users.get(x).getId() + " " + users.get(x).getName();
			// add that string to the string array
			strList[x] = comboData;
			// push the string onto the titles list as well
			titleList.add(comboData);
		} // end returnList()
		return strList;
	} // end returnList()
	
	private String[] findData(String title) {
		// create an array for holding the currencies
		String[] moneyArray = null;
		// loop through each account
		for(int x = 0; x < titleList.size(); x++) {
			// if the string that was passed is equal to an account
			if(titleList.get(x).equals(title)) {
				// initialize a temp array with each currency
				String [] tempArray = {precision.format(users.get(x).getAmount()), 
						precision.format(users.get(x).getAmount() / EURO),
						precision.format(users.get(x).getAmount() / YUAN)
						};
				// set the money array to the temp one and break loop
				moneyArray = tempArray;
				break;
			} // end if
		} // end for
		// return the array
		return moneyArray;
	} // end findData()
	
	// find the title of an account and return that index
	private int findAccount(String title) {
		// integer to hold the index
		int index = -1;
		// loop through each of the account titles
		for(int x = 0; x < titleList.size(); x++) {
			// if the string that was passed is equal to an account, then set
			// index to x and break the loop
			if(titleList.get(x).equals(title)) {
				index = x;
				break;
			} // end if
		} // end for
		// return the index for use elsewhere.
		return index;
	} // end findAccount

	// this will send an event to a window to open, we don't care about the currency right now
	public void openWindow(String title, String type) {
		ModelEvent me = new ModelEvent(this, title, type, 0, 0, 0);
		notifyChanged(me);
	}

	// this will send an event to a window to close, we don't care about the currency right now
	public void closeWindow(String title, String type) {
		ModelEvent me = new ModelEvent(this, title, type, 0, 0, 0);
		notifyChanged(me);		
	}

	// initializes the windows for each currency for an account
	public void initData(String title) {
		// get the amount in each currency based on the title passed
		String [] moneyArray = findData(title);
		// parse each currency into a double 
		Double usd = Double.parseDouble(moneyArray[0]);
		Double euro = Double.parseDouble(moneyArray[1]);
		Double yuan = Double.parseDouble(moneyArray[2]);
		// send an initialize event out
		ModelEvent me = new ModelEvent(this, title, "I", usd, euro, yuan);
		notifyChanged(me);	
	} // end initData()
	
	private void sortUsers() {
		// simple bubble sort for this program, would change for bigger data.
		for (int i = 0; i < users.size()-1; i++)  {
			for (int j = 1; j < users.size()-i; j++) {
				if (users.get(j-1).greater(users.get(j).getId()) == true) {
					User temp = users.get(j-1);
					User temp2 = users.get(j);
					users.set(j, temp);
					users.set(j-1, temp2);
				} // end if
			} // end for
		} // end for
	} // end sortUsers()
	
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
					int index = findAccount(title);
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
