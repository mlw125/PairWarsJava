package pairWars.model;
import java.awt.event.ActionEvent;

public class ModelEvent extends ActionEvent {
	private String title;
	private double usd;
	private double euro;
	private double yuan;
	private String message;
	public ModelEvent(Object obj, String title, String message, double usd, double euro, double yuan){
		super(obj, 1, message);
		this.usd = usd;
		this.euro = euro;
		this.yuan = yuan;
		this.title = title;
		this.message = message;
	}
}