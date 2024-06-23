package slayin.model.entities;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.enemies.Enemy;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

/**
 * A class representing a concrete implementation of a basic GameObject.
 * It is a simple entity that does not move, doesn't deal damage and can't be killed.
 * Its purpose is solely to help in tests, so it won't actually appear in the actual game.
 */
public class Dummy extends Enemy {

    public Dummy(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world, null);
    }

    @Override
    public void updatePos(int dt) {
        // the dummy has no movement
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

    @Override
    protected void updateDir() {
        // the dummy has no movement
    }

    @Override
    public int getScorePerKill() {
        return 0;
    }

    @Override
    public int getDamageOnHit() {
        return 0;
    }
    
}
