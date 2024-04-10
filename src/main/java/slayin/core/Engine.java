package slayin.core;

import slayin.model.GameObject;
import slayin.model.GameStatus;

public class Engine {

	GameStatus status;

	public void initGame(){
		GameStatus status = new GameStatus();
	}

    public boolean stopLoop(){
		/* TODO: check delle condizioni per terminare la partita */
		return true;
	}

	public void updateGameStatus(int startTime) {
		for(GameObject object : status.getObjects()){
			object.updatePos(startTime);
		}
	}
    
}
