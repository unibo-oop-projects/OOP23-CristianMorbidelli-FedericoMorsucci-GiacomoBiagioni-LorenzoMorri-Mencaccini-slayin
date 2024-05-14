package slayin.model.events.collisions;

import slayin.model.entities.GameObject;
import slayin.model.events.GameEvent;

/**
 * An event that is generated whenever a shot collides
 * with the world.
 */
public class ShotCollisionWithWorldEvent implements GameEvent {
    /**
    * A reference to the slamming shot.
    */
    private GameObject shot;

    /**
    *  The constructor of the ShotCollisionWithWorldEvent class
    *  @param shot - the bullet that touched the edges of the world
    */
    public ShotCollisionWithWorldEvent(GameObject shot) {
        this.shot = shot;
    }

    /**
    * A getter for the shot attribute
    * @return shot that touched the world
    */
    public GameObject getShot() {
        return shot;
    }
    

}
