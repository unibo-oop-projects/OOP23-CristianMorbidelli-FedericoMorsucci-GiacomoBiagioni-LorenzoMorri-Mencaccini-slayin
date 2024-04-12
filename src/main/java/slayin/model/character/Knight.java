package slayin.model.character;

import java.util.List;
import slayin.model.InputController;
import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Knight extends Character{
    //values ​​for a window 1000*1000
    final static int GRAVITY=+3000,FJUMP=-20000,FLEFT=-300,FRIGHT=300,DELTAJUMP=40;
    private Vector2d velocity,gravity;
    private boolean jump;
    private int y_start_jump;

    public Knight(P2d pos,Vector2d VectorMouvement,BoundingBox boundingBox, int life) {
        super(pos,VectorMouvement,boundingBox,life);
        velocity= new Vector2d(0, 0);
        gravity= new Vector2d(0, GRAVITY);
        jump=false;
        
    }

    @Override
    public void updateVel(InputController input) {
        if(input.isMoveUp() && this.getPos().getY()==590 /*word.getXGround() */){
            this.setVectorMouvement(new Vector2d(0, FJUMP));
            jump=true;
            y_start_jump=(int)this.getPos().getY();
        }else if(input.isMoveLeft() && !jump){
            this.velocity.setX(FLEFT);
        }else if(input.isMoveRight() && !jump){
            this.velocity.setX(FRIGHT);
        }
    }

    @Override
    public void updatePos(int dt, World world) {
        List<Edge> collision;
        //jump management
        if(jump){
            if(y_start_jump-this.getPos().getY()>DELTAJUMP){
                jump=false;
                this.setVectorMouvement(new Vector2d(0, 0));
            }else{
                this.velocity= this.velocity.sum(this.getVectorMouvement().mul(0.001*dt));
                System.err.println("ciao");
            }
        }
        //add the gravity 
        this.velocity= this.velocity.sum(gravity.mul(0.001*dt));
        //actual movement
        this.setPos(this.getPos().sum(this.velocity.mul(0.001*dt)));
        //collision control
        collision= world.collidingWith(this);
        for(var col : collision){

            if(col == Edge.LEFT_BORDER && this.velocity.getX()<0){
                this.velocity.setX(0);
                this.getPos().setX(0);
            }

            if(col == Edge.RIGHT_BORDER && this.velocity.getX()>0){
                this.velocity.setX(0);
                this.getPos().setX(world.getWidth());
            }

            if(col == Edge.BOTTOM_BORDER && !jump){
                // azzero la y del vettore velocity
                this.velocity= new Vector2d(this.velocity.getX(), 0);
                this.setPos(new P2d(this.getPos().getX(),world.getGround()));
            }
        }
        //update BoundingBox
        this.getBoundingBox().updatePoint(this.getPos());
        this.getWeapons().stream().forEach(t->t.updateBoxWeapon(this.getPos()));
    }
    
}
