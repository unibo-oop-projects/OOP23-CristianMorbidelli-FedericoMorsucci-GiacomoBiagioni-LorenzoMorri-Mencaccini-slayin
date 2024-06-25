package slayin.model.entities.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class HeadstoneShot extends ShotObject {

    private final int damagePerShot;

    /**
     * Constructs a HeadstoneShot with the specified position, vector movement, bounding box, and world.
     * 
     * @param pos the initial position of the shot as a {@link P2d} object.
     * @param vectorMovement the movement vector of the shot as a {@link Vector2d} object.
     * @param boundingBox the bounding box for collision detection as a {@link BoundingBoxImplCirc} object.
     * @param world the game world the shot exists in as a {@link World} object.
     */
    public HeadstoneShot(P2d pos, Vector2d vectorMovement, BoundingBoxImplCirc boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
        damagePerShot = 1;
    }

    /**
     * Indicates if the shot is from an enemy.
     * 
     * @return true, indicating the shot is from an enemy.
     */
    @Override
    public boolean isFromEnemy() {
        return true;
    }

    /**
     * Updates the position of the HeadstoneShot based on the time delta.
     * 
     * @param dt the time delta in milliseconds.
     */
    @Override
    public void updatePos(int dt) {
        // Update position
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001 * dt)));
        // Update bounding box point
        this.getBoundingBox().updatePoint(this.getPos());
    }

    /**
     * Returns the draw component for rendering this HeadstoneShot.
     * 
     * @return the {@link DrawComponent} instance for rendering the HeadstoneShot.
     */
    @Override
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentHeadstoneShot(this);
    }

    /**
     * Returns the damage inflicted by this HeadstoneShot on hit.
     * 
     * @return the damage per shot as an integer.
     */
    @Override
    public int getDamageOnHit() {
        return damagePerShot;
    } 
}
