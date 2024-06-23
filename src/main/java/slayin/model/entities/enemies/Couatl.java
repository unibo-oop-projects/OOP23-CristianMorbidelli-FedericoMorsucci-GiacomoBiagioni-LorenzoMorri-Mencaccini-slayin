package slayin.model.entities.enemies;

import java.util.Random;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Couatl extends Enemy{

    private static final int scorePerKill = 3;
    private static final int damageOnHit = 2;
    private int oldDt = 0;
    private Random random;
    private int SPEEDX = this.getWorld().getWidth()/13;
    private int SLOWSPEEDY = -this.getWorld().getWidth()/4;
    private int FASTSPEEDY = this.getWorld().getHeight()/2;
    private double startingY;
    private static enum State {PAUSE,DOWN,UP,FREE}
    private State state,oldState;

    public Couatl(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, 0), boundingBox, world, eventListener);
        random = new Random();
        startingY = this.getPos().getY();
        this.state = State.FREE;
        this.oldState = State.FREE;
    }

    

    @Override
    public void updatePos(int dt){
        oldDt+=dt;
        if(oldDt>2000){
            this.changeState();
            oldDt = 0;
        } 
        this.updateDir();
        
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }

    private void changeState(){
        if(this.state==State.FREE){
            this.oldState=State.FREE;
            this.state=State.PAUSE;
        }else if(this.oldState==State.FREE && this.state==State.PAUSE){
            this.oldState=State.PAUSE;
            this.state=State.DOWN;
        }else if(this.oldState==State.PAUSE && this.state==State.DOWN){
            this.oldState=State.DOWN;
            this.state=State.PAUSE;
        }else if(this.oldState==State.DOWN && this.state==State.PAUSE){
            this.oldState=State.PAUSE;
            this.state=State.UP;
        }else if(this.oldState==State.PAUSE && this.state==State.UP){
            this.oldState=State.UP;
            this.state=State.FREE;
        }
    }
    
    @Override
    public void updateDir(){
        if(this.state==State.FREE && (this.getVectorMovement().equals(new Vector2d(0, 0)) || this.getVectorMovement().equals(new Vector2d(0, SLOWSPEEDY)))){
            if(random.nextInt(2)==1){
                setDir(Direction.LEFT);
                this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
            }else{
                setDir(Direction.RIGHT);
                this.setVectorMovement(new Vector2d(SPEEDX, 0));
            }
        }else{
            if(this.state==State.PAUSE){
                this.setVectorMovement(new Vector2d(0, 0));
            }else if(this.state==State.UP){
                this.setVectorMovement(new Vector2d(0, SLOWSPEEDY));
                if(this.getPos().getY()<=startingY){
                    changeState();
                }
            }else if(this.state==State.DOWN){
                this.setVectorMovement(new Vector2d(0, FASTSPEEDY));
                if(this.getWorld().isTouchingGround(this)){
                    changeState();
                }
            }
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

    @Override
    public int getScorePerKill(){
        return scorePerKill;
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentEnemy((Enemy)this);
    }

    @Override
    public int getDamageOnHit() {
        return Couatl.damageOnHit;
    }
}
