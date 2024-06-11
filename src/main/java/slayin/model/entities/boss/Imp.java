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
    private boolean posFlag;

    public Imp(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(new P2d(
                world.getWidth()/2,
                world.getHeight()/2 //first position in the centre of the screen
            ), 
            new Vector2d(0,0), //Imp teleport in 4 different position
            boundingBox, 
            world);
        //TODO dimensioni bbox

        this.setHealth(10); //The Imp must receive 10 hits to be defeated
        this.setNumShots(0);//Imp attacks after first hit

        this.getBoundingBox().updatePoint(this.getPos());//set bounding box position

        this.changeState(State.START); //initial Minotaur state
        this.posFlag=false;
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentImp(this);
    }

    @Override
    public void updatePos(int dt){
        switch(this.state) {
            case START:
                if(posFlag){
                    //reset flag
                    this.posFlag=false;
                }
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
                break;
            case HITTED:
                if(this.secondDifference(1.0)){
                    this.changeState(State.PUFF);

                    //every three hits updates num of shots
                    if(this.getHealth() % 3==0){
                        this.setNumShots(this.getNumShots()+1);
                    }
                }
                break;
            case PUFF:
                if(secondDifference(1.0)){
                    this.update(); //update position
                    this.changeState(State.START);
                }
                break;
            default:
                System.out.println("ERROR: Imp.state = "+ this.state);
        }
    }

    private void update() {

        //if is in puff state 
        if(this.state==State.PUFF){
            if(!this.posFlag){
                //he changed position, set flag to true
                this.posFlag=true;
                
                //TODO: set pos
                this.setPos(new P2d(getPos()));
                
                //update bounding box position
                this.getBoundingBox().updatePoint(this.getPos());
            }
        }
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

    /**
     * set how many shots imp shoots
     * @param numShots
     */
    public void setNumShots(int numShots) {
        this.numShots=numShots;
    }

    /**
     * @return how many shots imp shoots
     */
    public int getNumShots() {
        return this.numShots;
    }
}
