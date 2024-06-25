package slayin.model.entities.enemies;

import slayin.model.World;
import slayin.model.World.Edge;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.events.GameEventListener;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

import java.util.Random;

public class Slime extends Enemy {

    private Random random;
    private static final int damageOnHit = 1;
    private static final int scorePerKill = 1;
    private final int SPEEDY = - this.getWorld().getHeight() / 7;
    private final int SPEEDX = this.getWorld().getWidth() / 6;
    private int oldDt = 0;
    private Boolean changeDir = false;

    /**
     * Constructs a Slime with the specified position, bounding box, world, and event listener.
     * 
     * @param pos the initial position of the slime as a {@link P2d} object.
     * @param boundingBox the bounding box for collision detection as a {@link BoundingBox} object.
     * @param world the game world the slime exists in as a {@link World} object.
     * @param eventListener the event listener for handling game events as a {@link GameEventListener} object.
     */
    public Slime(P2d pos, BoundingBox boundingBox, World world, GameEventListener eventListener) {
        super(pos, new Vector2d(0, - world.getHeight() / 7), boundingBox, world, eventListener);
        random = new Random();
    }

    /**
     * Updates the position of the slime based on the time delta.
     * The direction of the slime may also change based on certain conditions.
     * 
     * @param dt the time delta in milliseconds.
     */
    @Override
    public void updatePos(int dt) {
        oldDt += dt;
        if (oldDt > 3500) {
            changeDir = !changeDir;
            oldDt = 0;
        }
        this.updateDir();
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001 * dt)));
        this.getBoundingBox().updatePoint(this.getPos());
    }

    /**
     * Updates the direction of the slime based on its position, ground contact, and random chance.
     */
    @Override
    public void updateDir() {
        BoundingBoxImplRet bBox = (BoundingBoxImplRet) this.getBoundingBox();
        if (this.getWorld().isTouchingGround(this) && this.getPos().getY() + bBox.getHeight() / 2 >= this.getWorld().getGround() && this.getVectorMovement().equals(new Vector2d(0, SPEEDY))) {
            // random direction when the slime climbs to the same Y as the player
            if (this.getPos().getY() + bBox.getHeight() / 2.7 <= this.getWorld().getGround()) {
                if (random.nextInt(2) == 1) {
                    setDir(Direction.LEFT);
                    this.setVectorMovement(new Vector2d(-SPEEDX, 0)); 
                } else {
                    setDir(Direction.RIGHT);
                    this.setVectorMovement(new Vector2d(SPEEDX, 0));
                }
            } 
        } else {
            this.setPos(new P2d(this.getPos().getX(), getWorld().getGround() - bBox.getHeight() / 2));
            // the slime can change direction every 3.5s, it's a 30% chance
            if (changeDir) {
                // if <4, slime changes direction
                if (random.nextInt(1, 11) < 4) {
                    if (this.getVectorMovement().equals(new Vector2d(-SPEEDX, 0))) {
                        setDir(Direction.RIGHT);
                        this.setVectorMovement(new Vector2d(SPEEDX, 0));
                    } else {
                        setDir(Direction.LEFT);
                        this.setVectorMovement(new Vector2d(-SPEEDX, 0));
                    }
                }
                changeDir = false;
            }
            
            // checking slime collision with borders
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
    }

    /**
     * Returns the score awarded for killing this slime.
     * 
     * @return the score per kill as an integer.
     */
    @Override
    public int getScorePerKill() {
        return scorePerKill;
    }

    /**
     * Returns the draw component for rendering this slime.
     * 
     * @return the {@link DrawComponent} instance for rendering the slime.
     */
    @Override
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentEnemy(this);
    }

    /**
     * Returns the damage inflicted by this slime on hit.
     * 
     * @return the damage on hit as an integer.
     */
    @Override
    public int getDamageOnHit() {
        return Slime.damageOnHit;
    }
}
