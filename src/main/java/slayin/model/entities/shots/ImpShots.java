package slayin.model.entities.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class ImpShots extends ShotObject{

    private static final int SPEEDX = 400;
    private static final int SPEEDY = 500;
    private Double maxHeight;

    public ImpShots(P2d pos, BoundingBoxImplCirc boundingBox, World world, boolean linear) {
        super(pos, new Vector2d(0, 0), boundingBox, world);
        //if it must have a zig zag movement
        if(!linear){
            this.setVectorMovement(this.getVectorMovement().sum(0,SPEEDY));
        }

        if(this.getPos().getX()>(world.getWidth()/2)){ //if is in the right screen side
            this.setDir(Direction.LEFT);//the direction will no ever change
            this.setVectorMovement(this.getVectorMovement().sum(-SPEEDX,0));
        }else{
            this.setDir(Direction.RIGHT);//the direction will no ever change
            this.setVectorMovement(this.getVectorMovement().sum(SPEEDX,0));
        }

        this.maxHeight=this.getPos().getY();
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentImpShots(this);
    }

    @Override
    public void updatePos(int dt) {
        //update pos
        this.update(dt);

        System.out.println(this.getPos()+ " "+ this.getVectorMovement());
    }

    private void update(int dt) {
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        //update bBox point
        this.getBoundingBox().updatePoint(this.getPos());

        BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) this.getBoundingBox();
        
        if(this.getVectorMovement().getY()!=0){
            if(this.getWorld().isTouchingGround(this)){
                this.getPos().setY(this.getWorld().getGround()-(bBox.getRadius()));
                this.setVectorMovement(new Vector2d(this.getVectorMovement().getX(), -SPEEDY));
            }else{
                if(this.getPos().getY()<=maxHeight){
                    this.getPos().setY(maxHeight+bBox.getRadius());
                    this.setVectorMovement(new Vector2d(this.getVectorMovement().getX(), SPEEDY));
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
}
