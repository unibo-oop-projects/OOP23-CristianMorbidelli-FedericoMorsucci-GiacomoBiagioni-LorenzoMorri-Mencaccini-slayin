package slayin.model.events.collisions;

import slayin.model.entities.shots.ShotObject;
import slayin.model.events.GameEvent;

public class SpawnShotsEvent implements GameEvent{
    private ShotObject shot;

    /**
     * @param shotObject - save the shot to add in the world 
     */
    public SpawnShotsEvent(ShotObject shotObject){
        this.shot=shotObject;
    }

    /**
     * @return ShotObject to add in the world
     */
    public ShotObject getShot() {
        return shot;
    }
}
