package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

import java.util.Random;

public class SlimeFactory extends GameObject implements Slime  {

    private Random random;
    private Vector2d velocity;
    private int scorePerKill;
    private Direction direction;
    private static final int VELOCITAY = 50;
    private static final int VELOCITAX = 50;

    public SlimeFactory(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
        random = new Random();
        velocity = new Vector2d(0, VELOCITAY);
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
                this.setPos(this.getPos().sum(this.velocity.mul(0.001*dt)));
                break;
            case RIGHT:
                this.setPos(this.getPos().sum(this.velocity.mul(0.001*dt)));
                break;
        
            default:
                break;
        }*/
        this.setPos(this.getPos().sum(this.velocity.mul(0.001*dt)));
    }

    @Override
    public void setDir() {
        if(this.getWorld().isTouchingGround(this)){
            if(this.velocity.equals(new Vector2d(0, VELOCITAY))){
                //random direction when the slime clim to the same Y as the player
                if(random.nextInt(2)==1){
                    this.direction = Direction.LEFT;
                }else{
                    this.direction = Direction.RIGHT;
                }
            }
            var collision = this.getWorld().collidingWith(this);
            for(var col : collision){

                if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                    this.velocity = new Vector2d(-VELOCITAX,0);
                    this.direction = Direction.RIGHT;
                }
    
                if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                    this.velocity = new Vector2d(VELOCITAX,0);
                    this.direction = Direction.LEFT;
                }
            }
            // aggiorno di nuovo la BoundinBox
            this.getBoundingBox().updatePoint(this.getPos());
        }
    }

    @Override
    public Direction getDir(){
        return super.getDir();
    }
    
}
