package slayin.model.character;

import slayin.model.InputController;
import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Knight extends Character{
    //values ​​for a window 1000*1000
    final static int GRAVITY=+3000,FJUMP=-20000,FLEFT=-300,FRIGHT=300,DELTAJUMP=40;
    private Vector2d velocity,gravity;
    private boolean jump;

    public Knight(P2d pos,Vector2d VectorMouvement,BoundingBox boundingBox, int life) {
        super(pos,VectorMouvement,boundingBox,life);
        velocity= new Vector2d(0, 0);
        gravity= new Vector2d(0, GRAVITY);
        
    }

    @Override
    public void updateVel(InputController input) {
        if(input.isMoveUp() && this.getPos().getY()==610 /*word.getXGround() */){
            this.setVectorMouvement(new Vector2d(0, FJUMP));
            jump=true;
        }else if(input.isMoveLeft() && !jump){
            this.velocity.setX(FLEFT);
        }else if(input.isMoveRight() && !jump){
            this.velocity.setX(FRIGHT);
        }
    }

    //I reset the y if it's touching the ground
    private boolean resetY(World world){
        boolean out=false;
        if(this.getPos().getY()>=world.getGround() && !jump){
            // azzero la y del vettore velocity
            this.velocity= new Vector2d(this.velocity.getX(), 0);
            this.setPos(new P2d(this.getPos().getX(),world.getGround()));
            out=true;
        }
        return out;
    }
    //I check if I can stop with the jump
    private void stopJump(World world){
        if(jump && world.getGround()-this.getPos().getY()>DELTAJUMP){
            jump=false;
            this.setVectorMouvement(new Vector2d(0, 0));
        }
    }

    @Override
    public void updatePos(int dt, World world) {
        if(!(resetY(world))){
            //add the gravity 
            this.velocity= this.velocity.sum(gravity.mul(0.001*dt));
        }
        stopJump(world);
        if(jump){
            this.velocity= this.velocity.sum(this.getVectorMouvement().mul(0.001*dt));
        }
        System.out.println(this.getVectorMouvement());
        System.out.println(true);
        System.out.println(velocity);
        this.setPos(this.getPos().sum(this.velocity.mul(0.001*dt)));
    }
    
}
