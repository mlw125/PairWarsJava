package pairWars.view;
import pairWars.model.Model;
import pairWars.controller.Controller;

public interface View {
	Controller getController();
	void setController(Controller controller);
	Model getModel();
	void setModel(Model model);
}
