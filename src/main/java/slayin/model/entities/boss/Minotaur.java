package slayin.model.entities.boss;

import java.nio.file.Paths;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Minotaur extends Boss  {
    
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
            boundingBox, world, null
        );

        this.setHealth(5); //The Minotaur must receive 5 hits to be defeated
        
        this.getBoundingBox().updatePoint(this.getPos());//set bounding box position
        
        this.changeState(State.START); //initial Minotaur state
        this.setDir(Direction.LEFT); //because spawn is on the right side
        
        //initial speed
        this.setSPEEDX(world.getWidth()/3); //SPEEDX=(world width)/3
        this.setVectorMovement(new Vector2d(-SPEEDX, 0));
    }

    @Override
    public void updatePos(int dt) {
        switch(this.getState()) {
            case START:
                //it can't be hitted, wait 5 seconds and runs on the other side
                if(this.secondDifference(5.0)){
                    changeState(State.RUN);
                }
                break;
            case RUN:
                //update position or state if is touching border side 
                this.update(dt);
                break;
            case STUNNED:
                //if it isn't hitted, return to START state
                if(this.secondDifference(5.0)){
                    this.changeState(State.START);
                }
                break;
            case HITTED:
                if(this.secondDifference(2.0)){
                    this.changeState(State.START);

                    //every two hits updates speedx (first with 4 health)
                    if(this.getHealth() % 2==0){
                        this.updateSPEEDX();
                    }
                }
                break;
            default:
                System.out.println("ERROR: Minotuar.state = "+ this.getState());
        }
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentBoss(
            this, 
            Paths.get(getPath(),
                this.getClass().getSimpleName().toLowerCase()//his directory
            ).toString()
        );
    }

    
    /**
     * if is hitted in STUNNED state, change state and decrease health 
     */
    @Override
    public boolean onHit() {
        boolean outcome= false;
        if(this.getState()==State.STUNNED){
            this.changeState(State.HITTED);
            this.diminishHealth(1);
            if(this.isDead()){
                outcome = true;
            }
        }
        return outcome;
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
                this.changeState(State.STUNNED);
            }

            if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                this.setVectorMovement(new Vector2d(-SPEEDX,0));
                setDir(Direction.LEFT);
                //same as the previous one
                this.getPos().setX((this.getWorld().getWidth()-(boundingBox.getWidth()/2)));
                this.changeState(State.STUNNED);
            }
        }

        //if is running update position
        if(this.getState()==State.RUN){
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
    protected void setSPEEDX(int x) {
        SPEEDX = x;
    }

    /**
     * Double the value of SPEEDX
     */
    protected void updateSPEEDX(){
        this.SPEEDX= this.SPEEDX*2;
    }
    
}
