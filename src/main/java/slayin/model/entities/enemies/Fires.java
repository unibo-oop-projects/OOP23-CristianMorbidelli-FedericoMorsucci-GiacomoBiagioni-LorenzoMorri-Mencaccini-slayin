package slayin.model.entities.enemies;

import java.util.Random;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Fires extends Enemy{

    private Random random;
    private static final int scorePerKill = 2;
    private static final int SPEEDY = 100;
    private static final int SPEEDX = 100;
    private int oldDt = 0;
    private Boolean down = false;

    public Fires(P2d pos, BoundingBox boundingBox, World world) {
        super(pos, new Vector2d(0, 0), boundingBox, world);
        random = new Random();
    }
    
    @Override
    public void updatePos(int dt) {
        oldDt+=dt;
        //using dt to check if the fire need to go down (every 4s) or if he should stop to go down and start to go left of right
        if(oldDt>4000 && !down || oldDt>400 && down){
            down = !down;
        }
        this.updateDir(down);
        
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }

    public void updateDir(Boolean down){
        if(down){
            this.setVectorMovement(new Vector2d(0, SPEEDY));
        }if(this.getVectorMovement().equals(new Vector2d(0, SPEEDY)) && !down || this.getVectorMovement().equals(new Vector2d(0, 0))){
            //if im here, i should stop to go down, but i need to go left or right (random)
            if(random.nextInt(2)==1){
                setDir(Direction.LEFT);
                this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
            }else{
                setDir(Direction.RIGHT);
                this.setVectorMovement(new Vector2d(SPEEDX, 0));
            }
            var collision = this.getWorld().collidingWith(this);
            for(var col : collision){

                if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                    this.setVectorMovement(new Vector2d(SPEEDX,0));
                    setDir(Direction.RIGHT);
                }
    
                if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                    this.setVectorMovement(new Vector2d(-SPEEDX,0));
                    setDir(Direction.LEFT);
                }
            }
        }
    }

    @Override
    public Direction getDir(){
        return super.getDir();
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentBoundigBox(this.getBoundingBox());
    }
}
