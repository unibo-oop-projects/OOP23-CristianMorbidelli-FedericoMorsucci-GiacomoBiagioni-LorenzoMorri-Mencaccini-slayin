package slayin.model.events.collisions;

import slayin.model.entities.GameObject;
import slayin.model.events.GameEvent;

/**
 * An event that gets raised whenever a weapon (or its bullet, if its ranged) have a collision
 * with an enemy.
 */
public class WeaponCollisionEvent implements GameEvent{

    /**
     * A reference of the enemy that has got hit.
     */
    private final GameObject collided;

    /**
    *  The constructor of the WeaponCollisionEvent class
    *  @param collided - the object that has got hit
    */
    public WeaponCollisionEvent(GameObject collided) {
        this.collided = collided;
    }

    /**
     * A getter for the collided attribute
     * @return the object that has got hit to raise this event
     */
    public GameObject getCollidedObject(){
        return this.collided;
    }
    
}
