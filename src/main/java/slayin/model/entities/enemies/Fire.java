package slayin.model.entities.enemies;

import java.util.Random;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Fire extends Enemy {

    private Random random;
    private static final int damageOnHit = 1;
    private static final int scorePerKill = 2;
    private final int SPEEDY = this.getWorld().getHeight() / 12;
    private final int SPEEDX = this.getWorld().getWidth() / 12;
    private int oldDt = 0;
    private Boolean down = false;
    private double startingY;

    /**
     * Constructs a Fire with the specified position, bounding box, world, and event listener.
     * 
     * @param pos the initial position of the fire as a {@link P2d} object.
     * @param boundingBox the bounding box for collision detection as a {@link BoundingBox} object.
     * @param world the game world the fire exists in as a {@link World} object.
     * @param eventListener the event listener for handling game events as a {@link GameEventListener} object.
     */
    public Fire(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, 0), boundingBox, world, eventListener);
        random = new Random();
        startingY = this.getPos().getY();
    }
    
    /**
     * Updates the position of the fire based on the time delta.
     * The direction of the fire may also change based on certain conditions.
     * 
     * @param dt the time delta in milliseconds.
     */
    @Override
    public void updatePos(int dt) {
        oldDt += dt;
        // Check if the fire needs to go down every 4s or stop going down and start moving left or right
        if (oldDt > 4000 && !down || oldDt > 400 && down) {
            down = !down;
            oldDt = 0;
        }
        this.updateDir();
        
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001 * dt)));
        this.getBoundingBox().updatePoint(this.getPos());
    }

    /**
     * Updates the direction of the fire based on its position, ground contact, and random chance.
     */
    @Override
    public void updateDir() {
        if (this.getWorld().isTouchingGround(this)) {
            down = false;
        }
        if (down) {
            // 60% probability to go up if the fire is under its starting Y
            if (this.getPos().getY() > startingY && random.nextInt(1, 11) < 7 && !this.getDir().equals(Direction.DOWN)) {
                this.setDir(Direction.UP);
                this.setVectorMovement(new Vector2d(0, -SPEEDY));
            } else if (!this.getDir().equals(Direction.UP)) {
                this.setDir(Direction.DOWN);
                this.setVectorMovement(new Vector2d(0, SPEEDY));
            }
        }
        if ((this.getDir().equals(Direction.DOWN) || this.getDir().equals(Direction.UP)) && !down || this.getVectorMovement().equals(new Vector2d(0, 0))) {
            // If the fire should stop going down or up, start moving left or right randomly
            if (random.nextInt(2) == 1) {
                setDir(Direction.LEFT);
                this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
            } else {
                setDir(Direction.RIGHT);
                this.setVectorMovement(new Vector2d(SPEEDX, 0));
            }
        }
        // Check for collisions with borders
        var collision = this.getWorld().collidingWith(this);
        for (var col : collision) {
            if (col == Edge.LEFT_BORDER && this.getDir() == Direction.LEFT) {
                this.setVectorMovement(new Vector2d(SPEEDX, 0));
                setDir(Direction.RIGHT);
            }
            if (col == Edge.RIGHT_BORDER && this.getDir() == Direction.RIGHT) {
                this.setVectorMovement(new Vector2d(-SPEEDX, 0));
                setDir(Direction.LEFT);
            }
        }
    }

    /**
     * Returns the score awarded for killing this fire.
     * 
     * @return the score per kill as an integer.
     */
    @Override
    public int getScorePerKill() {
        return scorePerKill;
    }

    /**
     * Returns the draw component for rendering this fire.
     * 
     * @return the {@link DrawComponent} instance for rendering the fire.
     */
    @Override
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentEnemy(this);
    }

    /**
     * Returns the damage inflicted by this fire on hit.
     * 
     * @return the damage on hit as an integer.
     */
    @Override
    public int getDamageOnHit() {
        return Fire.damageOnHit;
    }
}
