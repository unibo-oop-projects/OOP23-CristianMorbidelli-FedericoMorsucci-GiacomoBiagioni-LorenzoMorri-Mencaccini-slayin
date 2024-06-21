package slayin.model.entities.boss;

import java.nio.file.Paths;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public abstract class Boss extends GameObject {

    public static enum State { START, RUN, STUNNED, HITTED, ATTACK, WAITING, INVISIBLE}
    private State state;
    private int health;
    private double previousTime;
    private final GameEventListener eventListener;

    /**
     * generic constructor
     * @param pos
     * @param vectorMovement
     * @param boundingBox
     * @param world
     * @param eventListener
     */
    public Boss(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, vectorMovement, boundingBox, world);
        this.eventListener=eventListener;
    }

    /**
     * @return previous time memorized
     */
    public double getPreviousTime() {
        return previousTime;
    }


    /**
     * set CurrentTime;
     */
    protected void setCurrentTimeToPrevious() {
        this.previousTime =(double) System.currentTimeMillis();
    }

    /**
     * set previous time 
     * @param previousTime
     */
    protected void setPreviousTime(double previousTime) {
        this.previousTime = previousTime;
    }
    
    /**
     * @return boss life
     */
    public int getHealth() {
        return health;
    }

    /**
     * set boss life
     * @param health 
     */
    protected void setHealth(int health) {
        this.health = health;
    }

    /**
     * set boss life - n if n and health is greater then 0
     * @param n - damage
     */
    protected void diminishHealth(int n){
        if(n>0 && this.health>0){
            this.health = (this.health-n);
        }
    }

    /**
     * @return true if health is less or equal then 0
     */
    protected boolean isDead(){
        boolean outcome=false;
        if(this.health<=0){
            outcome=true;
        }
        return outcome;
    }

    /**
     * Check with this.previousTime
     * @param seconds
     * @return - true if the difference is greater than or equal to the seconds
     */
    protected boolean secondDifference(double seconds){
        boolean outcome=false;
        
        double difference=(double)((System.currentTimeMillis()-this.previousTime)/1000);
        if(difference>=seconds){
            outcome=true;
        }
        return outcome;
    }

    /**
     * @return - Event listener for call the Engine
     */
    protected GameEventListener getEventListener() {
        return eventListener;
    }

    /**
     * @return boss State
     */
    public State getState() {
        return state;
    }

    /**
     * Change Boss State and reset previousTime
     * @param state 
     */
    protected void changeState(State state){
        this.state=state;
        this.setCurrentTimeToPrevious();
    }

    /**
     * @return asset string
     */
    public static final String getPath(){
        return Paths.get("assets","entities","boss").toString();
    }
}
