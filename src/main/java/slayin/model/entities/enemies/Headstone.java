package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.entities.shots.HeadstoneShot;
import slayin.model.events.GameEventListener;
import slayin.model.events.collisions.SpawnShotsEvent;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Headstone extends Enemy {

    private static final int damageOnHit = 2;
    private static final int scorePerKill = 3;
    private int oldDt = 0;

    /**
     * Constructs a Headstone with the specified position, bounding box, world, and event listener.
     * 
     * @param pos the initial position of the headstone as a {@link P2d} object.
     * @param boundingBox the bounding box for collision detection as a {@link BoundingBox} object.
     * @param world the game world the headstone exists in as a {@link World} object.
     * @param eventListener the event listener for handling game events as a {@link GameEventListener} object.
     */
    public Headstone(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, 0), boundingBox, world, eventListener);
        if (this.getPos().getX() > world.getWidth() / 2) {
            this.setDir(Direction.LEFT);
        } else {
            this.setDir(Direction.RIGHT);
        }
    }
    
    /**
     * Updates the position of the headstone based on the time delta.
     * The headstone shoots at regular intervals.
     * 
     * @param dt the time delta in milliseconds.
     */
    @Override
    public void updatePos(int dt) {
        oldDt += dt;
        if (oldDt > 3500) {
            this.shot();
            oldDt = 0;
        }
    }

    /**
     * Shoots a projectile from the headstone.
     */
    private void shot() {
        BoundingBoxImplRet bBox = (BoundingBoxImplRet) this.getBoundingBox();
        int direction = 1;
        double x = bBox.getWidth() / 2;
        if (this.getDir() == Direction.LEFT) {
            direction = -1;
        }
        P2d shotSpawn = new P2d(this.getPos().getX() + x * direction, this.getPos().getY() - bBox.getHeight() / 4);
        HeadstoneShot skull = new HeadstoneShot(shotSpawn, new Vector2d(this.getWorld().getWidth() / 3.5 * direction, 0), new BoundingBoxImplCirc(shotSpawn, bBox.getWidth() / 3), this.getWorld());

        this.getEventListener().addEvent(new SpawnShotsEvent(skull));
    }

    /**
     * Returns the score awarded for killing this headstone.
     * 
     * @return the score per kill as an integer.
     */
    @Override
    public int getScorePerKill() {
        return scorePerKill;
    }

    /**
     * Returns the draw component for rendering this headstone.
     * 
     * @return the {@link DrawComponent} instance for rendering the headstone.
     */
    @Override
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentEnemy(this);
    }

    /**
     * Updates the direction of the headstone.
     * Currently, this method does not perform any actions.
     */
    @Override
    public void updateDir() {
        // No direction update needed for Headstone
    }

    /**
     * Returns the damage inflicted by this headstone on hit.
     * 
     * @return the damage on hit as an integer.
     */
    @Override
    public int getDamageOnHit() {
        return Headstone.damageOnHit;
    }
}
