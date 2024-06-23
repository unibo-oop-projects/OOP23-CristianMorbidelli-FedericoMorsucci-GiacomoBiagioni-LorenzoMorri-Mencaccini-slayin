package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
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
    private static final int SPEEDY = -50;
    private static final int SPEEDX = 150;
    private int oldDt = 0;
    private Boolean changeDir = false;

    public Slime(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, SPEEDY), boundingBox, world, eventListener);
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
        if(!this.getWorld().isTouchingGround(this)){
            if(this.getVectorMovement().equals(new Vector2d(0, SPEEDY))){
                //random direction when the slime climb to the same Y as the player
                if(random.nextInt(2)==1){
                    setDir(Direction.LEFT);
                    this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
                }else{
                    setDir(Direction.RIGHT);
                    this.setVectorMovement(new Vector2d(SPEEDX, 0));
                }
            }
            //the slime can change direction every 3.5s, its a 20% chance
            if(changeDir){
                //se minore di 5 cambio direzione, 20% prob
                if(random.nextInt(1,11)<3){
                    if(this.getVectorMovement().equals(new Vector2d(-SPEEDX, 0))){
                        setDir(Direction.RIGHT);
                        this.setVectorMovement(new Vector2d(SPEEDX, 0));
                    }else{
                        setDir(Direction.LEFT);
                        this.setVectorMovement(new Vector2d(-SPEEDX, 0));
                    }
                changeDir = false;
                }
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
