package pairWars.controller;

import pairWars.model.GameModel;
import pairWars.view.GameView;
import pairWars.view.JFrameView;

public class GameController extends AbstractController {
	public GameController(){
		setModel(new GameModel());
		setView(new GameView((GameModel)getModel(), this));
		((JFrameView)getView()).setSize(500, 200);
		((JFrameView)getView()).setVisible(true);
	}
}