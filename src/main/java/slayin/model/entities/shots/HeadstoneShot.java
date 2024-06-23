package slayin.model.entities.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class HeadstoneShot extends ShotObject{

    public HeadstoneShot(P2d pos, Vector2d vectorMovement, BoundingBoxImplCirc boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
    }

    @Override
    public boolean isFromEnemy() {
        return true;
    }

    @Override
    public void updatePos(int dt) {
        //update pos
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        //update bBox point
        this.getBoundingBox().updatePoint(this.getPos());
    }

    @Override
    public DrawComponent getDrawComponent(){
        return DrawComponentFactory.graphicsComponentHeadstoneShot(this);
    }

    @Override
    public int getDamageOnHit() {
        return 1;
    } 
}
