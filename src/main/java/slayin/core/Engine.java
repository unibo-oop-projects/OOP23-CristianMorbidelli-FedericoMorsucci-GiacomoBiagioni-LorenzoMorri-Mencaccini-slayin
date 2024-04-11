package slayin.core;

import slayin.model.GameObject;
import slayin.model.GameStatus;

public class Engine {

	GameStatus status;

	public void initGame(){
		status = new GameStatus();
	}

    public boolean stopLoop(){
		/* TODO: check delle condizioni per terminare la partita */
		return true;
	}

	public void updateGameStatus(int startTime) {

		// Update the logical position of the main character and the enemies on the scene
		for(GameObject object : status.getObjects()){
			object.updatePos(startTime, status.getWorld());
		}

		/* TODO: check for collisions */
	}
    
}
