package slayin.model.character;

import slayin.model.GameObject;
import slayin.model.InputController;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Character extends GameObject{
    private int life;


    public Character(P2d pos, Vector2d vectorMouvement, BoundingBox boundingBox,int life) {
        super(pos, vectorMouvement, boundingBox);
        this.life=life;
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
