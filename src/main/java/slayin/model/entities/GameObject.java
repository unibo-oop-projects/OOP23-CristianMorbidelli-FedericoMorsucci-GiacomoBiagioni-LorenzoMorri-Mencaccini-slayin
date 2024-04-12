package slayin.model.entities;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public abstract class GameObject {
    private P2d pos;
    private Vector2d vectorMovement;
    private BoundingBox boundingBox;
    private World world;

    public GameObject(P2d pos,Vector2d vectorMovement, BoundingBox boundingBox,World world){
        this.pos=pos;
        this.vectorMovement=vectorMovement;
        this.boundingBox=boundingBox;
        this.world=world;
    }

    public P2d getPos(){
        return this.pos;
    }

    public World getWorld(){
        return this.world;
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
        return boundingBox;
    }


    public abstract void updateVel(MovementController input);   
    
    public abstract void updatePos(int dt);


}
