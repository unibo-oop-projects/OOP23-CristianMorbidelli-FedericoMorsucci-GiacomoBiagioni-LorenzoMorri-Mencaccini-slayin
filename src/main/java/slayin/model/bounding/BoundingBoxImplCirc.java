package slayin.model.bounding;

import slayin.model.utility.P2d;


/**
 * Implementation of a circular BoundingBox.
 */
public class BoundingBoxImplCirc implements BoundingBox{

    private double radius;
    private P2d center;


    /**
     * Constructs a circular bounding box with the specified center point and radius.
     *
     * @param point  The center point of the bounding box.
     * @param radius The radius of the bounding box.
     */
    public BoundingBoxImplCirc(P2d point, double radius){
        this.center= new P2d(point);
        this.radius=radius;
    }


    /**
     * Returns the x-coordinate of the center of the bounding box.
     *
     * @return The x-coordinate of the center.
     */
    public double getX() {
        return this.center.getX();
    }


    /**
     * Returns the y-coordinate of the center of the bounding box.
     *
     * @return The y-coordinate of the center.
     */
    public double getY() {
        return this.center.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P2d getPoint(){
        return this.center;
    }


    /**
     * Returns the radius of the bounding box.
     *
     * @return The radius of the bounding box.
     */
    public double getRadius() {
        return radius;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCollidedWith(BoundingBox b) {
        boolean outcome=false;
        if(b instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox = (BoundingBoxImplRet) b;
            outcome= bBox.isCollidedWith(this);
        }else if(b instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) b;
            outcome= ((this.radius + bBox.getRadius()) >= this.center.distanceFromPoint(bBox.getPoint()));
        }
        return outcome;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePoint(P2d p) {
        this.center=p;
    }
    
}
