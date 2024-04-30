package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Minotaur extends Boss  {
    
    public static enum State { START, RUN, STUNNED, HITTED }
    private State state;
    private int SPEEDX;
    
    /**
     * Minotaur constructor, set initial health, sprites
     * @param pos - intial position
     * @param boundingBox -
     * @param world - reference world used the character
     */
    public Minotaur(P2d pos, BoundingBox boundingBox, World world) {
        super(pos, new Vector2d(0, 0), boundingBox, world);
        
        this.setHealth(5); //The Minotaur must receive 5 hits to be defeated
        this.state=State.START;
        this.setDir(Direction.LEFT);
        this.setSPEEDX(300);
        this.setVectorMovement(new Vector2d(SPEEDX, 0));
    }

    @Override
    public void updatePos(int dt) {
        switch(this.state) {
            case START:
                if(this.secondDifference(5.0)){
                    this.state=State.RUN;
                }
                break;
            case RUN:
                this.updateDir();
                this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
                break;
            case STUNNED:
                if(this.secondDifference(5.0)){
                    this.state=State.START;
                }
                break;
            case HITTED:
                if(this.secondDifference(2.0)){
                    this.state=State.START;

                    //every two hits updates speedx
                    if(this.getHealth() % 2==0){
                        this.updateSPEEDX();
                    }
                }
                break;
            default:
                System.out.println("ERROR: Minotuar.state = "+ this.state);
        }
    }

    public void isHitted() {
        if(this.state==State.STUNNED){
            this.state=State.HITTED;
            this.diminishHealth(1);
            this.resetTimeFlag();
        }
    }

    private void updateDir() {
        var collision = this.getWorld().collidingWith(this);
        for(var col : collision){

            if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                this.setVectorMovement(new Vector2d(SPEEDX,0));
                setDir(Direction.RIGHT);
                this.state=State.STUNNED;
            }

            if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                this.setVectorMovement(new Vector2d(-SPEEDX,0));
                setDir(Direction.LEFT);
                this.state=State.STUNNED;
            }
        }
    }

    public int getSPEEDX() {
        return SPEEDX;
    }

    private void setSPEEDX(int x) {
        SPEEDX = x;
    }

    private void updateSPEEDX(){
        this.SPEEDX= this.SPEEDX*2;
    }

    public State getState() {
        return state;
    }
    
}
