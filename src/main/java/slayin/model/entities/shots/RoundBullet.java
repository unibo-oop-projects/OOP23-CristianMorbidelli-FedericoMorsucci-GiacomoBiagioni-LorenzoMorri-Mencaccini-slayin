package slayin.model.entities.shots;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.entities.character.Character;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;
import slayin.model.utility.Constants;

/**
 * Represents a round bullet shot by a character in the game.
 */
public class RoundBullet extends ShotObject{


    /**
     * Constructs a RoundBullet with specified position, movement vector, bounding box, world, and direction.
     *
     * @param pos             the initial position of the bullet
     * @param vectorMovement  the movement vector of the bullet
     * @param boundingBox     the bounding box of the bullet
     * @param world           the world in which the bullet exists
     * @param dir             the direction of the bullet
     */
    public RoundBullet(P2d pos, Vector2d vectorMovement, BoundingBoxImplCirc boundingBox, World world,Direction dir) {
        super(pos, vectorMovement, boundingBox, world);
        this.setDir(dir);
    }

    /**
     * Constructs a RoundBullet shot by a character.
     *
     * @param c  the character shoots the bullet
     * @param w  the world in which the bullet exists
     */
    public RoundBullet(Character c,World w){
        this(c.getPos(),c.getDir()==Direction.LEFT ? new Vector2d(-Constants.SPEEDX_BULLET_ROUND, 0) : new Vector2d(Constants.SPEEDX_BULLET_ROUND, 0), new BoundingBoxImplCirc(c.getPos(), Constants.RADIUS_BULLET_ROUND),w, c.getDir());
    }



    /**
     * Updates the position of the bullet based on the elapsed time.
     * The new position is calculated by adding the product of the movement vector and the elapsed time to the current position.
     * The bounding box is also updated to reflect the new position.
     *
     * @param dt  the elapsed time in milliseconds
     */
    @Override
    public void updatePos(int dt) {
        this.setPos(this.getPos().sum(this.getVectorMovement().mul(0.001*dt)));
        // aggiorno di nuovo la BoundinBox
        this.getBoundingBox().updatePoint(this.getPos());
    }

    @Override
    public boolean isFromEnemy() {
        return false;
    }
    
}
