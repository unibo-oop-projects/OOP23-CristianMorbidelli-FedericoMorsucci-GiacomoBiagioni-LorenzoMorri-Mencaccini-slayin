package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

import java.util.Random;

public class Slime extends Enemy  {

    private Random random;
    private int scorePerKill;
    private static final int VELOCITAY = -50;
    private static final int VELOCITAX = 150;

    public Slime(P2d pos, BoundingBox boundingBox, World world) {
        super(pos, new Vector2d(0, VELOCITAY), boundingBox, world);
        random = new Random();
        scorePerKill = 1;
    }

    @Override
    public void updateVel(MovementController input) {
        return;
    }

    @Override
    public void updatePos(int dt) {
        /*switch (direction) {
            case LEFT:
                this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
                break;
            case RIGHT:
                this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
                break;
        
            default:
                break;
        }*/
        this.updateDir();
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
    }

    public void updateDir() {
        if(!this.getWorld().isTouchingGround(this)){
            if(this.getVectorMovement().equals(new Vector2d(0, VELOCITAY))){
                //random direction when the slime clim to the same Y as the player
                if(random.nextInt(2)==1){
                    setDir(Direction.LEFT);
                    this.setVectorMovement(new Vector2d(-VELOCITAX, 0)); 
                }else{
                    setDir(Direction.RIGHT);
                    this.setVectorMovement(new Vector2d(VELOCITAX, 0));
                }
            }
            var collision = this.getWorld().collidingWith(this);
            for(var col : collision){

                if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                    this.setVectorMovement(new Vector2d(VELOCITAX,0));
                    setDir(Direction.RIGHT);
                }
    
                if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                    this.setVectorMovement(new Vector2d(-VELOCITAX,0));
                    setDir(Direction.LEFT);
                }
            }
        }
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }

    @Override
    public Direction getDir(){
        return super.getDir();
    }
    
}
