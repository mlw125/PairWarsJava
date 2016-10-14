package pairWars.controller;
import pairWars.model.Model;
import pairWars.view.View;

public interface Controller {
	void setModel(Model model);
	Model getModel();
	View getView();
	void setView(View view);
}
