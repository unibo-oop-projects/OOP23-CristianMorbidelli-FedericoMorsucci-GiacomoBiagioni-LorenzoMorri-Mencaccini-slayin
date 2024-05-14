package slayin.model.entities.character.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.entities.GameObject;
import slayin.model.entities.character.Character;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import slayin.model.utility.Constants;

public class RoundBullet extends GameObject{

    public RoundBullet(P2d pos, Vector2d vectorMovement, BoundingBoxImplCirc boundingBox, World world,Direction dir) {
        super(pos, vectorMovement, boundingBox, world);
        this.setDir(dir);
    }
    public RoundBullet(Character c,World w){
        this(c.getPos(),c.getDir()==Direction.LEFT ? new Vector2d(-Constants.SPEEDX_BULLET_ROUND, 0) : new Vector2d(Constants.SPEEDX_BULLET_ROUND, 0), new BoundingBoxImplCirc(c.getPos(), Constants.RADIUS_BULLET_ROUND),w, c.getDir());
    }


    @Override
    public void updatePos(int dt) {
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }
    
}
