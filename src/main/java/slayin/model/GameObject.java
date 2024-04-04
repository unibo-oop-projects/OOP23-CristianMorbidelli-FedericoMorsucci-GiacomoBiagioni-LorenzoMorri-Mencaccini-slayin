package slayin.model;

import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public abstract class GameObject {
    protected P2d pos;
    protected Vector2d vectorMouvement;
    private BoundingBox boundingBox;

    public GameObject(P2d pos,Vector2d vectorMouvement, BoundingBox boundingBox){
        this.pos=pos;
        this.vectorMouvement=vectorMouvement;
        this.boundingBox=boundingBox;
    }

    public P2d getPos(){
        return this.pos;
    }

    public void setPos(P2d pos) {
        this.pos = pos;
    }

    public Vector2d getVectorMouvement() {
        return vectorMouvement;
    }

    public void setVectorMouvement(Vector2d vectorMouvement) {
        this.vectorMouvement = vectorMouvement;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }


    public abstract void updateVel(InputController input);   
    
    public abstract void updatePos(int dt);


}
