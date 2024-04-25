package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Boss extends GameObject {

    private int health;
    
    
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
    public void changeHealth(int n){
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
}
