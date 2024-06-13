package slayin.model.events.collisions;

import slayin.model.entities.GameObject;
import slayin.model.entities.character.MeleeWeapon;
import slayin.model.entities.shots.ShotObject;
import slayin.model.events.GameEvent;

import java.util.Optional;

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
    * A reference of the shot.
    */
    private final Optional<ShotObject> shot;
    /**
    * A reference of the weapon.
    */
    private final Optional<MeleeWeapon> weapon;

    /**
    *  The constructor of the WeaponCollisionEvent class
    *  @param collided - the object that has got hit
    *  @param weapon - the weapon that is has generate this event
    */
    public WeaponCollisionEvent(GameObject collided,ShotObject shot) {
        this.collided = collided;
        this.shot=Optional.of(shot);
        this.weapon=Optional.empty();
    }

    /**
    *  The constructor of the WeaponCollisionEvent class
    *  @param collided - the object that has got hit
    *  @param weapon - the weapon that is has generate this event
    */
    public WeaponCollisionEvent(GameObject collided,MeleeWeapon weapon) {
        this.collided = collided;
        this.shot=Optional.empty();
        this.weapon=Optional.of(weapon);
    }

    /**
    * A getter for the collided attribute
    * @return the object that has got hit to raise this event
    */
    public GameObject getCollidedObject(){
        return this.collided;
    }

    /**
    * A getter for the shot attribute
    * @return the shot that is has generate this event
    */
    public Optional<ShotObject> getShot(){
        return this.shot;
    }

    /**
    * A getter for the weapon attribute
    * @return the weapon that is has generate this event
    */
    public Optional<MeleeWeapon> getWeapon(){
        return this.weapon;
    }
    
}
