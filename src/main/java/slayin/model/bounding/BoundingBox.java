package slayin.model.bounding;

import slayin.model.utility.P2d;


/**
 * Interface representing a bounding box used for collision detection.
 */
public interface BoundingBox {


    /**
     * Updates the center point of the bounding box.
     *
     * @param p The new center point of the bounding box.
     */
    public void updatePoint(P2d p);


    /**
     * Returns the current center point of the bounding box.
     *
     * @return The current center point.
     */
    public P2d getPoint();


    /**
     * Checks if this bounding box is collided with another bounding box.
     *
     * @param b The other bounding box to check collision with.
     * @return True if the bounding boxes collide, false otherwise.
     */
    public boolean isCollidedWith(BoundingBox b);

}
