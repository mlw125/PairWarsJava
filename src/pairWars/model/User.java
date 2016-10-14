package pairWars.model;

public class User {
	private String name;
	private String id;
	private double amount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount2) {
		this.amount = amount2;
	}
	
	public synchronized void depositThread(double change) {
		this.amount += change;
		notifyAll();
	}
	
	public synchronized void withdrawThread(double change) throws InterruptedException {
		while (amount - change < 0) { wait(); }
		this.amount -= change;
		notifyAll();
	}
	
	public boolean greater(String id2) {
		String[] idArray = id.split("");
		String[] id2Array = id2.split("");
		
		for(int x = 0; x < idArray.length; x++) {
			if(Integer.parseInt(idArray[x]) > Integer.parseInt(id2Array[x])) {
				return true;
			} // end if
			else if(Integer.parseInt(idArray[x]) < Integer.parseInt(id2Array[x])) {
				return false;
			} // end else
		} // end for
		return false;		
	} // end greater(String id2)
}
