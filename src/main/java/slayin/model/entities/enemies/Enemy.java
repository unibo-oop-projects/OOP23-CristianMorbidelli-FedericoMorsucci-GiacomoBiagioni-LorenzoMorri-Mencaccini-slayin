package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public abstract class Enemy extends GameObject {

    private final GameEventListener eventListener;

    /**
     * Constructs an Enemy with the specified position, movement vector, bounding box, world, and event listener.
     * 
     * @param pos the initial position of the enemy as a {@link P2d} object.
     * @param vectorMovement the movement vector of the enemy as a {@link Vector2d} object.
     * @param boundingBox the bounding box for collision detection as a {@link BoundingBox} object.
     * @param world the game world the enemy exists in as a {@link World} object.
     * @param eventListener the event listener for handling game events as a {@link GameEventListener} object.
     */

    public Enemy(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, vectorMovement, boundingBox, world);
        this.eventListener = eventListener;
    }
    /**
     * Returns the event listener associated with this enemy.
     * 
     * @return the {@link GameEventListener} instance.
     */
    protected GameEventListener getEventListener(){
        return this.eventListener;
    }
    /**
     * Updates the direction of the enemy.
     */
    protected abstract void updateDir();
    /**
     * Updates the position of the enemy based on the time delta.
     * 
     * @param dt the time delta in milliseconds.
     */
    public abstract void updatePos(int dt);
    /**
     * Returns the score awarded for killing this enemy.
     * 
     * @return the score per kill as an integer.
     */
    public abstract int getScorePerKill();
    /**
     * Returns the damage inflicted by this enemy on a hit.
     * 
     * @return the damage on hit as an integer.
     */
    public abstract int getDamageOnHit();    
}
