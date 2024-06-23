package slayin.model.bounding;

import slayin.model.utility.P2d;

/**
 * Implementation of a rectangular BoundingBox.
 */
public class BoundingBoxImplRet implements BoundingBox{

    private double width, height;
    // il punto sono le cordinate del centro del quadrato della boundingBox
    private P2d point;


    /**
     * Constructs a rectangular bounding box with the specified center point, width, and height.
     *
     * @param point  The center point of the bounding box.
     * @param width  The width of the bounding box.
     * @param height The height of the bounding box.
     */
    public BoundingBoxImplRet(P2d point,double width, double height){
        this.point=point;
        this.height=height;
        this.width=width;
    }
    
    
    /**
     * Returns the x-coordinate of the top-left corner of the bounding box.
     *
     * @return The x-coordinate of the top-left corner.
     */
    public double getX() {
        return point.getX()-(this.width/2);
    }

    /**
     * Returns the y-coordinate of the top-left corner of the bounding box.
     *
     * @return The y-coordinate of the top-left corner.
     */
    public double getY() {
        return point.getY()-(this.height/2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P2d getPoint(){
        return this.point;
    }


    /**
     * Returns the width of the bounding box.
     *
     * @return The width of the bounding box.
     */
    public double getWidth() {
        return width;
    }


    /**
     * Returns the height of the bounding box.
     *
     * @return The height of the bounding box.
     */
    public double getHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCollidedWith(BoundingBox b) {
        double x= point.getX()-(this.width/2);
        double y= point.getY()-(this.height/2);
        boolean outcome=false;
        if(b instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox = (BoundingBoxImplRet) b;
            outcome= !(x + this.width < bBox.getX() || bBox.getX() + bBox.getWidth() < x 
            || y + this.height < bBox.getY() || bBox.getY() + bBox.getHeight() < y );
        }else if(b instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) b;
            outcome= !(x + this.width < bBox.getX()-bBox.getRadius() || bBox.getX() + bBox.getRadius() < x 
            || y + this.height < bBox.getY()-bBox.getRadius() || bBox.getY() + bBox.getRadius() < y);
        }
        return outcome;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePoint(P2d p) {
        this.point=p;
    }


    
}