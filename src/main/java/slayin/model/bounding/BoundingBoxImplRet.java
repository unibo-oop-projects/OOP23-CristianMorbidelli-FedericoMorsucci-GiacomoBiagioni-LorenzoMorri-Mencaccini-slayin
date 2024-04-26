package slayin.model.bounding;

import slayin.model.utility.P2d;

/**
 * BoundingBox
 */
public class BoundingBoxImplRet implements BoundingBox{

    private double width, height;
    // il punto sono le cordinate del centro del quadrato della boundingBox
    private P2d point;

    public BoundingBoxImplRet(P2d point,double width, double height){
        this.point=point;
        this.height=height;
        this.width=width;
    }
    // restituisce la x del punto in alto a drestra della BoundingBox
    public double getX() {
        return point.getX()-(this.width/2);
    }

    // restituisce la y del punto in alto a drestra della BoundingBox
    public double getY() {
        return point.getY()-(this.height/2);
    }
    @Override
    public P2d getPoint(){
        return this.point;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    
    @Override
    public boolean isCollidedWith(BoundingBox b) {
        double x= point.getX()-(this.width/2);
        double y= point.getY()-(this.height/2);
        boolean outcome=false;
        if(b instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox = (BoundingBoxImplRet) b;
            outcome= !(x + this.width < bBox.getX() || bBox.getX() + bBox.getWidth() < x 
            || y - this.height > bBox.getY() || bBox.getY() - bBox.getHeight() > y);
        }else if(b instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) b;
            outcome= !(x + this.width < bBox.getX()-bBox.getRadius() || bBox.getX() + bBox.getRadius() < x 
            || y - this.height > bBox.getY()-bBox.getRadius() || bBox.getY() - bBox.getRadius() > y);
        }
        return outcome;
    }

    @Override
    public void updatePoint(P2d p) {
        this.point=p;
    }


    
}