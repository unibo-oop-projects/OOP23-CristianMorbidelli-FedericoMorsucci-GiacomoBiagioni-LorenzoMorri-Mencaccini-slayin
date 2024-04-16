package slayin.model.entities;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.movement.MovementController;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

/**
 * A class representing a concrete implementation of a basic GameObject.
 * It is a simple entity that does not move, doesn't deal damage and can't be killed.
 * Its purpose is solely to help in tests, so it won't actually appear in the actual game.
 */
public class Dummy extends GameObject {

    public Dummy(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
    }

    @Override
    public void updateVel(MovementController input) {
        //
    }

    @Override
    public void updatePos(int dt) {
        // the dummy has no movement
        // TODO: add the possibility to make the dummy fall to the ground, if placed mid air?
    }

    @Override
    public boolean onHit() {
        // The dummy can't be hit: it stays forever in the scene unless manually removed.
        return false;
    }

    @Override
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentDummy(this);
    }
    
}
