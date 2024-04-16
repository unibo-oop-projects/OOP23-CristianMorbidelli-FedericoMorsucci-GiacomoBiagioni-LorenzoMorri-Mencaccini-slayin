package slayin.model.entities;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public abstract class GameObject {
    public static enum Direction { LEFT, RIGHT }
    private P2d pos;
    private Direction dir;
    private Vector2d vectorMovement;
    private BoundingBox boundingBox;
    private World world;

    public GameObject(P2d pos,Vector2d vectorMovement, BoundingBox boundingBox,World world){
        this.pos=pos;
        this.vectorMovement=vectorMovement;
        this.boundingBox=boundingBox;
        this.world=world;
        //For now I'll default to LEFT, I'll probably change later
        this.dir=Direction.LEFT;
    }

    public P2d getPos(){
        return this.pos;
    }

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


    public void setPos(P2d pos) {
        this.pos = pos;
    }

    public Vector2d getVectorMovement() {
        return vectorMovement;
    }

    public void setVectorMovement(Vector2d vectorMouvement) {
        this.vectorMovement = vectorMouvement;
    }

    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentBoundigBox(boundingBox);
    }  
    
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
