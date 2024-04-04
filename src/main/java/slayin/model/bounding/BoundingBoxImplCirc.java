package slayin.model.bounding;

import slayin.model.utility.P2d;

public class BoundingBoxImplCirc implements BoundingBox{

    private double radius;
    private P2d center;

    public BoundingBoxImplCirc(P2d point, double radius){
        this.center= new P2d(point);
        this.radius=radius;
    }

    public double getX() {
        return this.center.getX();
    }

    public double getY() {
        return this.center.getY();
    }

    public P2d getCenter(){
        return this.center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean isCollidedWith(BoundingBox b) {
        boolean outcome=false;
        if(b instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox = (BoundingBoxImplRet) b;
            outcome= bBox.isCollidedWith(this);
        }else if(b instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) b;
            outcome= ((this.radius + bBox.getRadius()) >= this.center.distanceFromPoint(bBox.getCenter()));
        }
        return outcome;
    }
    
}
