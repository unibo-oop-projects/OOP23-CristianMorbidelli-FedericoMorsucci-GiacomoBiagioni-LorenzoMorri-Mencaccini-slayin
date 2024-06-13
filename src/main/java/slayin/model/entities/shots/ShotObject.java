package slayin.model.entities.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public abstract class ShotObject extends GameObject implements Shot{

    public ShotObject(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
    }
    
}
