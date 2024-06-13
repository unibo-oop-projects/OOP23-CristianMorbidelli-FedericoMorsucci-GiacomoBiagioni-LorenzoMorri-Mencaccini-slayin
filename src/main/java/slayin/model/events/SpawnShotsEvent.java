package slayin.model.events;

import slayin.model.entities.shots.ShotObject;

public class SpawnShotsEvent implements GameEvent{
    private ShotObject shot;

    public SpawnShotsEvent(ShotObject shotObject){
        this.shot=shotObject;
    }

    public ShotObject getShot() {
        return shot;
    }
}
