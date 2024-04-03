package slayin.model;

public class BoundingBoxImplCirc implements BoundingBox{

    private double radius,x,y;

    public BoundingBoxImplCirc(P2d point, double radius){
        x= point.getX();
        y= point.getY();
        this.radius=radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean isCollidedWith(BoundingBox b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCollidedWith'");
    }
    
}
