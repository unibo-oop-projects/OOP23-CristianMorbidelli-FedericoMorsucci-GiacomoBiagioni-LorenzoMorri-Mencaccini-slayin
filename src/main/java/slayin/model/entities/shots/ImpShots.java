package slayin.model.entities.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class ImpShots extends ShotObject{

    private int SPEEDX;
    private int SPEEDY;
    private Double maxHeight;

    /**
     * set initial position, movement and define the trajectory of the shot
     * @param pos - initial position
     * @param boundingBox - circular bounding box
     * @param world - world of the shot
     * @param linear - true if has a linear trajectory
     */
    public ImpShots(P2d pos, BoundingBoxImplCirc boundingBox, World world, boolean linear) {
        super(pos, new Vector2d(0, 0), boundingBox, world);
        
        this.setSPEEDX(world.getWidth()/3); //SPEEDX shot (in propotion with world)
        this.setSPEEDY((int)(world.getHeight()/1.44));//SPEEDY shot (in propotion with world)

        //if it must have a zig zag movement
        if(!linear){
            //to set vertical speed
            this.setVectorMovement(this.getVectorMovement().sum(0,SPEEDY));
        }

        if(this.getPos().getX()>(world.getWidth()/2)){ //if is in the right screen side
            this.setDir(Direction.LEFT);//the direction will no ever change
            this.setVectorMovement(this.getVectorMovement().sum(-SPEEDX,0));//sx
        }else{
            this.setDir(Direction.RIGHT);//the direction will no ever change
            this.setVectorMovement(this.getVectorMovement().sum(SPEEDX,0));//dx
        }

        this.maxHeight=this.getPos().getY();//set max height for the shot, the same of imp
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentImpShots(this);
    }

    @Override
    public void updatePos(int dt) {
        //update pos
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        //update bBox point
        this.getBoundingBox().updatePoint(this.getPos());

        BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) this.getBoundingBox();
        
        if(this.getVectorMovement().getY()!=0){
            if(this.getWorld().isTouchingGround(this)){
                this.getPos().setY(this.getWorld().getGround()-(bBox.getRadius()));//if got over limit
                this.setVectorMovement(new Vector2d(this.getVectorMovement().getX(), -SPEEDY));//invert polarity
            }else{
                if(this.getPos().getY()<=maxHeight){
                    this.getPos().setY(maxHeight+bBox.getRadius());//if got over limit
                    this.setVectorMovement(new Vector2d(this.getVectorMovement().getX(), SPEEDY));//invert polarity
                }
            }
        }

        //update bBox point
        this.getBoundingBox().updatePoint(this.getPos());
    }

    @Override
    public boolean onHit(){
        //return always false, ImpShots can't be removed by character hit
        return false;
    }

    @Override
    public boolean isFromEnemy() {
        return true;
    }

    /**
     * @return - SPEEDX of the imp shot
     */
    public int getSPEEDX() {
        return SPEEDX;
    }

    
    /**
     * set SPEEDX with the param value
     * @param SPEEDX
     */
    private void setSPEEDX(int SPEEDX) {
        this.SPEEDX=SPEEDX;
    }

    /**
     * @return - SPEEDY of the imp shot
     */
    public int getSPEEDY() {
        return SPEEDY;
    }

    /**
     * set SPEEDY with the param value
     * @param sPEEDY
     */
    private void setSPEEDY(int sPEEDY) {
        SPEEDY = sPEEDY;
    }
}
