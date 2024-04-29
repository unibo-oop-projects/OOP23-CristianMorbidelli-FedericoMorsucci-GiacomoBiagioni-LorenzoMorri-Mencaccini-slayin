package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Minotaur extends Boss  {
    
    private int state;
    private final static int START=0, RUN=1, STUNNED=2, HITTED=3;
    private int SPEEDX = 300;
    
    /**
     * Minotaur constructor, set initial health, sprites
     * @param pos - intial position
     * @param vectorMovement - displacement vector
     * @param boundingBox -
     * @param world - reference world used the character
     * 
     */
    public Minotaur(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        //TODO: impostare set iniziale completo
        super(pos, vectorMovement, boundingBox, world);
        this.setHealth(5); //The Minotaur must receive 5 hits to be defeated
        this.state=START;
        this.setDir(Direction.LEFT);
    }

    @Override
    public void updatePos(int dt) {
        switch(this.state) {
            case START:
                if(this.secondDifference(5)){
                    this.state=RUN;
                }
                break;
            case RUN:
                this.updateDir();
                this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
                break;
            case STUNNED:
                if(this.secondDifference(5)){
                    this.state=START;
                }
                break;
            case HITTED:
                if(this.secondDifference(2)){
                    this.state=START;

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
        //TODO: metodo che chiama il biagion se mi colpisce l'arma (cambia stato e aggiorna health)
    }

    private void updateDir() {
        var collision = this.getWorld().collidingWith(this);
        for(var col : collision){

            if(col == Edge.LEFT_BORDER && this.getDir()==Direction.LEFT){
                this.setVectorMovement(new Vector2d(SPEEDX,0));
                setDir(Direction.RIGHT);
                this.state=STUNNED;
            }

            if(col == Edge.RIGHT_BORDER && this.getDir()==Direction.RIGHT){
                this.setVectorMovement(new Vector2d(-SPEEDX,0));
                setDir(Direction.LEFT);
                this.state=STUNNED;
            }
        }
    }

    public int getSPEEDX() {
        return SPEEDX;
    }

    public void setSPEEDX(int x) {
        SPEEDX = x;
    }

    public void updateSPEEDX(){
        this.SPEEDX= this.SPEEDX*2;
    }
    
}
