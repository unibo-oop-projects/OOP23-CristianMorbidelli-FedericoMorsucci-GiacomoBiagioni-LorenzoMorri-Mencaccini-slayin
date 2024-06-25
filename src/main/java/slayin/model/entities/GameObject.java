package slayin.model.entities;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;


/**
 * Represents a game object in the game world.
 * 
 * <p>This abstract class provides common functionality and attributes for all game objects, 
 * such as position, direction, movement vector, bounding box, and the world they exist in.
 * Specific types of game objects should extend this class and implement the abstract methods 
 * as needed.</p>
 */
public abstract class GameObject {

    /**
     * Enumeration for the possible directions a game object can face or move.
     */
    public static enum Direction { LEFT, RIGHT, DOWN, UP}
    private P2d pos;
    private Direction dir;
    private Vector2d vectorMovement;
    private BoundingBox boundingBox;
    private World world;


    /**
     * Constructs a new GameObject with the specified position, movement vector, bounding box, and world.
     * 
     * @param pos The position of the game object.
     * @param vectorMovement The movement vector of the game object.
     * @param boundingBox The bounding box of the game object.
     * @param world The world in which the game object exists.
     */
    public GameObject(P2d pos,Vector2d vectorMovement, BoundingBox boundingBox,World world){
        this.pos=pos;
        this.vectorMovement=vectorMovement;
        this.boundingBox=boundingBox;
        this.world=world;
        //For now I'll default to LEFT, I'll probably change later
        this.dir=Direction.LEFT;
    }


    /**
     * Gets the position of the game object.
     * 
     * @return The position of the game object.
     */
    public P2d getPos(){
        return this.pos;
    }


    /**
     * Gets the world in which the game object exists.
     * 
     * @return The world of the game object.
     */
    public World getWorld(){
        return this.world;
    }
    
    /**
     * A setter for the direction attribute
     */
    public void setDir(Direction dir){
        this.dir=dir;
    }

    /**
     * A getter for the direction attribute
     * @return the player's current direction
     */
    public Direction getDir(){
        return this.dir;
    }



    /**
     * Sets the position of the game object.
     * 
     * @param pos The new position of the game object.
     */
    public void setPos(P2d pos) {
        this.pos = pos;
    }


    /**
     * Gets the movement vector of the game object.
     * 
     * @return The movement vector of the game object.
     */
    public Vector2d getVectorMovement() {
        return vectorMovement;
    }


    /**
     * Sets the movement vector of the game object.
     * 
     * @param vectorMovement The new movement vector of the game object.
     */
    public void setVectorMovement(Vector2d vectorMouvement) {
        this.vectorMovement = vectorMouvement;
    }


    /**
     * Gets the bounding box of the game object.
     * 
     * @return The bounding box of the game object.
     */
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }


    /**
     * Gets the draw component for the game object.
     * 
     * @return The draw component of the game object.
     */
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentBoundigBox(boundingBox);
    }  
    
    /**
     * Updates the position of the game object based on the time delta.
     * 
     * @param dt The time delta in milliseconds.
     */
    public abstract void updatePos(int dt);

    /**
     * This method gets called whenever the engine resolve a WeaponCollisionEvent regarding a specific GameObject.
     * Every GameObject instance can override this function to add a specific behavior when it gets hit. By 
     * default, it returns a true boolean meaning that the GameObject will "die" after resolving the event
     * @return {@code true} if the object must be removed from the scene; {@code false} otherwise
     */
    public boolean onHit(){
        return true;
    }
}
