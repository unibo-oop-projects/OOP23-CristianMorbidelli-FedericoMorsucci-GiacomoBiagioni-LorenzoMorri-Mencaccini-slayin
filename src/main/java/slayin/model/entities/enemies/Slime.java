package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

import java.util.Random;

public class Slime extends Enemy  {

    private Random random;
    private static final int damageOnHit = 1;
    private static final int scorePerKill = 1;
    private final int SPEEDY = - this.getWorld().getHeight()/7;
    private final int SPEEDX = this.getWorld().getWidth()/6;
    private int oldDt = 0;
    private Boolean changeDir = false;

    public Slime(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, - world.getHeight()/7), boundingBox, world, eventListener);
        random = new Random();
    }

    @Override
    public void updatePos(int dt) {

        oldDt += dt;
        if(oldDt>3500){
            changeDir = !changeDir;
            oldDt = 0;
        }
        this.updateDir();
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }
    @Override
    public void updateDir() {
        BoundingBoxImplRet bBox = (BoundingBoxImplRet)this.getBoundingBox();
        if(this.getWorld().isTouchingGround(this) && this.getPos().getY()+bBox.getHeight()/2>=this.getWorld().getGround() && this.getVectorMovement().equals(new Vector2d(0, SPEEDY))){
            //random direction when the slime climb to the same Y as the player
            if(this.getPos().getY()+bBox.getHeight()/2.7<=this.getWorld().getGround()){
                if(random.nextInt(2)==1){
                    setDir(Direction.LEFT);
                    this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
                }else{
                    setDir(Direction.RIGHT);
                    this.setVectorMovement(new Vector2d(SPEEDX, 0));
                }
            } 
        }else{

            this.setPos(new P2d(this.getPos().getX(),getWorld().getGround()-bBox.getHeight()/2));
            //the slime can change direction every 3.5s, its a 30% chance
            if(changeDir){
                //se minore di 5 cambio direzione, 30% prob
                if(random.nextInt(1,11)<4){
                    if(this.getVectorMovement().equals(new Vector2d(-SPEEDX, 0))){
                        setDir(Direction.RIGHT);
                        this.setVectorMovement(new Vector2d(SPEEDX, 0));
                    }else{
                        setDir(Direction.LEFT);
                        this.setVectorMovement(new Vector2d(-SPEEDX, 0));
                    }
                }
                changeDir = false;
            }
            

            //checking slime collision with borders
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
    public int getScorePerKill(){
        return scorePerKill;
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentEnemy((Enemy)this);
    }

    @Override
    public int getDamageOnHit() {
        return Slime.damageOnHit;
    }
    
}
