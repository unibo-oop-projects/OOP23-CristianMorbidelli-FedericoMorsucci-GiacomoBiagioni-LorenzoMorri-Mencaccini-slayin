package slayin.model.entities.boss.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class ImpShots extends GameObject{

    private static final int SPEEDX = 800;
    private static final int SPEEDY = 800;

    public ImpShots(P2d pos, BoundingBox boundingBox, World world, boolean linear) {
        super(pos, new Vector2d(SPEEDX, 0), boundingBox, world);
        //if it must have a zig zag movement
        if(!linear){
            this.setVectorMovement(this.getVectorMovement().sum(0,SPEEDY));
        }
    }

    @Override
    public void updatePos(int dt) {
        //update pos
        this.getPos().sum(this.getVectorMovement().mul(0.001*dt));
        
        //update bBox point
        this.getBoundingBox().updatePoint(this.getPos());
    }
    
}
