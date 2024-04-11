package slayin.model.character;

import slayin.model.GameObject;
import slayin.model.InputController;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Knight extends Character{
    private Vector2d velocity,gravity;
    private boolean jump;

    public Knight(P2d pos,Vector2d VectorMouvement,BoundingBox boundingBox, int life) {
        super(pos,VectorMouvement,boundingBox,life);
        velocity= new Vector2d(0, 0);
        gravity= new Vector2d(0, +1800);
    }

    public void updateVel(InputController input) {
        if(input.isMoveUp() && this.getPos().getY()==370 /*word.getXGround() */){
            this.setVectorMouvement(new Vector2d(0, -12000));
            jump=true;
        }else if(input.isMoveLeft() && !jump){
            this.velocity.setX(-150);
        }else if(input.isMoveRight() && !jump){
            this.velocity.setX(150);
        }
    }

    //I reset the y if it's touching the ground
    private boolean resetY(){
        boolean out=false;
        if(this.getPos().getY()>=370/*word.getXGround() */ && !jump){
            // azzero la y del vettore velocity
            this.velocity= new Vector2d(this.velocity.getX(), 0);
            this.setPos(new P2d(this.getPos().getX(),370));
            out=true;
        }
        return out;
    }
    //I check if I can stop with the jump
    private void stopJump(){
        if(jump && 370-this.getPos().getY()>20/*word.getXGround() */){
            jump=false;
            this.setVectorMouvement(new Vector2d(0, 0));
        }
    }

    @Override
    public void updatePos(int dt) {
        if(!(resetY())){
            //add the gravity 
            this.velocity= this.velocity.sum(gravity.mul(0.001*dt));
        }
        stopJump();
        if(jump){
            this.velocity= this.velocity.sum(this.getVectorMouvement().mul(0.001*dt));
        }
        this.setPos(this.getPos().sum(this.velocity.mul(0.001*dt)));
    }
    
}
