package slayin.model.entities.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;


/**
 * Abstract base class for shot objects in the game.
 * Extends GameObject and implements Shot interface.
 */
public abstract class ShotObject extends GameObject implements Shot{


    /**
     * Constructs a new ShotObject with the specified parameters.
     *
     * @param pos           The initial position of the shot object.
     * @param vectorMovement The vector representing movement direction and speed.
     * @param boundingBox   The bounding box that defines the collision area of the shot object.
     * @param world         The world in which the shot object exists.
     */
    public ShotObject(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
    }
    
}
