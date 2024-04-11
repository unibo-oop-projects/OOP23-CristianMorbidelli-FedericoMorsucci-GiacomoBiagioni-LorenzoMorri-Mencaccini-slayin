package slayin.model.character;

import slayin.model.GameObject;
import slayin.model.InputController;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Character extends GameObject{

    public static enum Direction { LEFT, RIGHT }
    private int life;
    private Direction dir;


    public Character(P2d pos, Vector2d vectorMouvement, BoundingBox boundingBox,int life) {
        super(pos, vectorMouvement, boundingBox);
        this.life=life;
    }

    public void setDir(Direction dir){
        this.dir=dir;
    }

    public Direction getDir(){
        return this.dir;
    }

    public boolean isAlive(){
        return life>0;
    }

    public void decLife(int damage){
        this.life= life-damage;
    }

    @Override
    public void updateVel(InputController input) {
        if(input.isMoveLeft()){
            this.getVectorMouvement().setX(-150);
        }else if(input.isMoveRight()){
            this.getVectorMouvement().setX(150);
        }
    }

    @Override
    public void updatePos(int dt) {
        this.setPos(this.getPos().sum(this.getVectorMouvement().mul(0.001*dt)));
    }

    
}
