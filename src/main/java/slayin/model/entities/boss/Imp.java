package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Imp extends Boss {

    public static enum State { START, ATTACK, WAITING, PUFF, HITTED }
    private State state;
    private int numShots;

    public Imp(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
        //TODO pos spawn, dimensioni, bbox

        this.setHealth(10); //The Imp must receive 10 hits to be defeated
        this.setNumShots(0);//Imp attacks after first hit
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentImp(this);
    }

    @Override
    public void updatePos(int dt){
        switch(this.state) {
            case START:
                //spawn in a casual position, wait 5 seconds then attacks
                if(this.secondDifference(5.0)){
                    changeState(State.ATTACK);
                }
                break;
            case ATTACK:
                //TODO: attacca -> spawn palla
                break;
            case WAITING:
                if(this.secondDifference(5.0)){
                    changeState(State.PUFF);
                }
            case PUFF:
                //TODO: invisibile, aggiorni nuova pos
                break;
            case HITTED:
                if(this.secondDifference(1.0)){
                    this.changeState(State.PUFF);

                    //every two hits updates num of shots
                    if(this.getHealth() % 3==0){
                        this.setNumShots(this.getNumShots()+1);
                    }
                }
                break;
            default:
                System.out.println("ERROR: Imp.state = "+ this.state);
        }
    }

    public void setNumShots(int numShots) {
        this.numShots=numShots;
    }

    public int getNumShots() {
        return this.numShots;
    }

    /**
     * if is hitted in any state, change state and decrease health 
     */
    @Override
    public boolean onHit() {
        boolean outcome= false;
        this.changeState(State.HITTED);
        this.diminishHealth(1);
        if(this.getHealth()==0){
            outcome = true;
        }
        return outcome;
    }
    
    /**
     * Change Boss State and reset previousTime
     * @param state 
     */
    private void changeState(State state){
        this.state=state;
        this.setCurrentTimeToPrevious();
    }

    /**
     * @return boss State
     */
    public State getState() {
        return state;
    }
}
