package pairWars.model;
import java.awt.event.ActionEvent;

public class ModelEvent extends ActionEvent {
	private int playerID;
	private int face1;
	private int face2;
	private int suit1;
	private int suit2;
	private String message;
	private String message2;
	// this method will be used for displaying card from the model to the view
	public ModelEvent(Object obj, int playerID, String message, int face1, int suit1, int face2, int suit2){
		super(obj, 1, message);
		this.face1 = face1;
		this.suit1 = suit1;
		this.face2 = face2;
		this.suit2 = suit2;
		this.playerID = playerID;
		this.message = message;
	} // end ModelEvent(Object, int, String, int, int, int, int)
	
	// this one will mainly be used for displaying information to the log
	public ModelEvent(Object obj, int playerID, String message, String message2){
		super(obj, 1, message);
		this.playerID = playerID;
		this.message = message;
		this.message2 = message2;
	} // end ModelEvent(Object, int, String, String)
	
	public int getPlayerID() {
		return playerID;
	}
	
	public String getMessage() {
		return message;
	}

	public String getMessage2() {
		return message2;
	}
	
	public int getFace1() {
		return face1;
	}
	
	public int getSuit1() {
		return suit1;
	}
	
	public int getFace2() {
		return face2;
	}

	public int getSuit2() {
		return suit2;
	}
}