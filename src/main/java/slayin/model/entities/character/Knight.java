package slayin.model.entities.character;

import java.util.List;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
/**
 * class of the knight, 
 * compared to a normal character he can jump
 */
public class Knight extends Character{
    //values ​​for a window 1000*1000
    final static int GRAVITY=+3000,FJUMP=-17000,FLEFT=-430,FRIGHT=430,DELTAJUMP=40;
    private Vector2d velocity,gravity;
    private boolean jump;
    private int y_start_jump;

     /**
     * The constructor of the Knight class
     * @param pos -initial position of the character
     * @param vectorMouvement - displacement vector
     * @param boundingBox - boundinf box of the character
     * @param life - initial value of the character's life
     * @param world - reference world used the character
     * @param weapons - melee weapons belonging to the character
     */
    public Knight(P2d pos,Vector2d VectorMouvement,BoundingBox boundingBox,World world, int life,MeleeWeapon ... weapons) {
        super(pos,VectorMouvement,boundingBox,life,world, weapons);
        velocity= new Vector2d(0, 0);
        gravity= new Vector2d(0, GRAVITY);
        jump=false;
        
    }

    @Override
    public void updateVel(MovementController input) {

        if(input.isJumping() && this.getWorld().isTouchingGround(this)){
            this.setVectorMovement(new Vector2d(0, FJUMP));
            input.setJumping(false);
            jump=true;
            y_start_jump=(int)this.getPos().getY();
        }else if(input.isMovingLeft() && !jump){
            this.velocity.setX(FLEFT);
            this.setDir(Direction.LEFT);
        }else if(input.isMovingRight() && !jump){
            this.velocity.setX(FRIGHT);
            this.setDir(Direction.RIGHT);
        }
    }

    @Override
    public void updatePos(int dt) {
        List<Edge> collision;
        //jump management
        if(jump){
            if(y_start_jump-this.getPos().getY()>DELTAJUMP){
                jump=false;
                this.setVectorMovement(new Vector2d(0, 0));
            }else{
                this.velocity= this.velocity.sum(this.getVectorMovement().mul(0.001*dt));
            }
        }
        //add the gravity 
        this.velocity= this.velocity.sum(gravity.mul(0.001*dt));
        //actual movement
        this.setPos(this.getPos().sum(this.velocity.mul(0.001*dt)));

        //update BoundingBox
        this.getBoundingBox().updatePoint(this.getPos());

        //collision control
        collision= this.getWorld().collidingWith(this);
        for(var col : collision){

            if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                this.velocity.setX(0);
                this.getPos().setX(0);
            }

            if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                this.velocity.setX(0);
                this.getPos().setX(this.getWorld().getWidth());
            }
            if(col == Edge.BOTTOM_BORDER && !jump){
                //reset the y of the velocity vector
                this.velocity= new Vector2d(this.velocity.getX(), 0);
                BoundingBoxImplRet bBox = (BoundingBoxImplRet) this.getBoundingBox();
                this.setPos(new P2d(this.getPos().getX(),this.getWorld().getGround()-(bBox.getHeight()/2)));
            }
            // aggiorno di nuovo la BoundinBox
            this.getBoundingBox().updatePoint(this.getPos());
        }

        //update BoundingBox weapon
        if(this.getDir()==Direction.LEFT){
            this.getWeapons().stream().forEach(t->t.updateBoxWeapon(new P2d(this.getPos().getX()-t.getWidthFromPlayer(),this.getPos().getY()+t.getHeightFromPlayer())));
        }else{
            this.getWeapons().stream().forEach(t->t.updateBoxWeapon(new P2d(this.getPos().getX()+t.getWidthFromPlayer(),this.getPos().getY()+t.getHeightFromPlayer())));
        }
    }
    
}
