package pairWars.model;
import java.awt.event.ActionEvent;

public class ModelEvent extends ActionEvent {
	private int id;
	private int face1;
	private int face2;
	private int suit1;
	private int suit2;
	private String message;
	public ModelEvent(Object obj, int id, String message, int face1, int suit1, int face2, int suit2){
		super(obj, 1, message);
		this.face1 = face1;
		this.suit1 = suit1;
		this.face2 = face2;
		this.suit2 = suit2;
		this.id = id;
		this.message = message;
	}
}