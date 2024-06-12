package slayin.model.entities.enemies;

import java.util.Random;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import slayin.model.entities.character.Character;

public class Couatl extends Enemy{

    private static final int scorePerKill = 3;
    private int oldDt = 0;
    private Boolean pause = false;
    private Random random;
    private static int SPEEDX = 100;
    private static int SPEEDY = 100;
    private static double startingY;

    public Couatl(P2d pos, BoundingBox boundingBox, World world) {
        super(pos, new Vector2d(0, 0), boundingBox, world);
        random = new Random();
        startingY = this.getPos().getY();
    }

    @Override
    public void updatePos(int dt){
        oldDt+=dt;
        if(oldDt>3000){
            pause = !pause;
            oldDt = 0;
        }
        
        this.updateDir(pause, false);
        
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }
    
    public void updateDir(Boolean pause, Boolean down){
        if(this.getVectorMovement().equals(new Vector2d(0, 0)) && !pause){
            if(random.nextInt(2)==1){
                setDir(Direction.LEFT);
                this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
            }else{
                setDir(Direction.RIGHT);
                this.setVectorMovement(new Vector2d(SPEEDX, 0));
            }
        }
        //TODO engine stuff
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

    @Override
    public Direction getDir(){
        return super.getDir();
    }

    @Override
    public int getScorePerKill(){
        return scorePerKill;
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentEnemy((Enemy)this);
    }
}
