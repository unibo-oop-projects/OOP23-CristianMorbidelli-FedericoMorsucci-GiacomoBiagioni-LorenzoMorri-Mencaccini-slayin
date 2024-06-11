package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Boss extends GameObject {

    private int health;
    private double previousTime;

    /**
     * generic constructor
     * @param pos
     * @param vectorMovement
     * @param boundingBox
     * @param world
     * 
     */
    public Boss(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
    }


    @Override
    public void updatePos(int dt) {
        // every boss has a specific one
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
    public void setCurrentTimeToPrevious() {
        this.previousTime =(double) System.currentTimeMillis();
    }

    /**
     * set previous time 
     * @param previousTime
     */
    public void setPreviousTime(double previousTime) {
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
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * set boss life - n if n and health is greater then 0
     * @param n - damage
     */
    public void diminishHealth(int n){
        if(n>0 && this.health>0){
            this.health = (this.health-n);
        }
    }

    /**
     * @return true if health is less or equal then 0
     */
    public boolean idDead(){
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
    public boolean secondDifference(double seconds){
        boolean outcome=false;
        
        double difference=(double)((System.currentTimeMillis()-this.previousTime)/1000);
        if(difference>=seconds){
            outcome=true;
        }
        return outcome;
    }
}
