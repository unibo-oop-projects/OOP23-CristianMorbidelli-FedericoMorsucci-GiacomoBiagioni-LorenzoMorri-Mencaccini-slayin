package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Minotaur extends Boss  {
    
    public static enum State { START, RUN, STUNNED, HITTED }
    private State state;
    private int SPEEDX;
    
    /**
     * Minotaur constructor, set initial health, position and speed
     * @param pos - intial position (will be changed to the default one)
     * @param boundingBox - bounding box rectangular
     * @param world - reference world used the boss
     */
    public Minotaur(P2d pos, BoundingBoxImplRet boundingBox, World world) {
        super(new P2d(
                world.getWidth()-(boundingBox.getWidth()/2), //spawn on right side
                world.getGround()-(boundingBox.getHeight()/2)//ground height
            ), 
            new Vector2d(0, 0), 
            boundingBox, world
        );

        this.setHealth(5); //The Minotaur must receive 5 hits to be defeated
        
        this.getBoundingBox().updatePoint(this.getPos());//set bounding box position
        
        this.state=State.START; //initial Minotaur state
        this.setDir(Direction.LEFT); //because spawn is on the right side
        
        //initial speed
        this.setSPEEDX(300); 
        this.setVectorMovement(new Vector2d(-SPEEDX, 0));
    }

    @Override
    public void updatePos(int dt) {
        switch(this.state) {
            case START:
                //it can't be hitted, wait 5 seconds and runs on the other side
                if(this.secondDifference(5.0)){
                    this.state=State.RUN;
                }
                break;
            case RUN:
                //update position or state if is touching border side 
                this.update(dt);
                break;
            case STUNNED:
                //if it isn't hitted, return to START state
                if(this.secondDifference(5.0)){
                    this.state=State.START;
                }
                break;
            case HITTED:
                if(this.secondDifference(2.0)){
                    this.state=State.START;

                    //every two hits updates speedx (first with 4 health)
                    if(this.getHealth() % 2==0){
                        this.updateSPEEDX();
                    }
                }
                break;
            default:
                System.out.println("ERROR: Minotuar.state = "+ this.state);
        }
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentMinotaur(this);
    }

    
    /**
     * if is hitted in STUNNED state, change state and decrease health 
     */
    public void isHitted() {
        if(this.state==State.STUNNED){
            this.state=State.HITTED;
            this.diminishHealth(1);
            this.resetTimeFlag();
        }
    }

    /**
     * Update position or state if is in collision with an edge side
     * @param dt
     */
    private void update(int dt) {
        var collision = this.getWorld().collidingWith(this);
        BoundingBoxImplRet boundingBox = (BoundingBoxImplRet) this.getBoundingBox();
 
        for(var col : collision){

            if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                this.setVectorMovement(new Vector2d(SPEEDX,0));
                setDir(Direction.RIGHT);
                //in case he went over the edge
                this.getPos().setX((boundingBox.getWidth()/2));
                this.state=State.STUNNED;
            }

            if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                this.setVectorMovement(new Vector2d(-SPEEDX,0));
                setDir(Direction.LEFT);
                //same as the previous one
                this.getPos().setX((this.getWorld().getWidth()-(boundingBox.getWidth()/2)));
                this.state=State.STUNNED;
            }
        }

        //if is running update position
        if(this.state==State.RUN){
            this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        }

        //update bounding box position
        boundingBox.updatePoint(this.getPos());
    }

    /**
     * @return SPEEDX of the vector movement
     */
    public int getSPEEDX() {
        return SPEEDX;
    }

    /**
     * @param x - SPEEDX setted with x 
     */
    private void setSPEEDX(int x) {
        SPEEDX = x;
    }

    /**
     * Double the value of SPEEDX
     */
    private void updateSPEEDX(){
        this.SPEEDX= this.SPEEDX*2;
    }

    /**
     * @return boss State
     */
    public State getState() {
        return state;
    }
    
}
