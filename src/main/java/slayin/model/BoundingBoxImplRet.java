package slayin.model;

/**
 * BoundingBox
 */
public class BoundingBoxImplRet implements BoundingBox{

    private double width, height,x,y;

    public BoundingBoxImplRet(P2d point,double width, double height){
        x= point.getX()-(width/2);
        y= point.getY()+(height/2);
        this.height=height;
        this.width=width;
    }

    public double getWidht() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean isCollidedWithRettagle(P2d point, double widht, double height) {
        double x= point.getX()-(widht/2);
        double y= point.getY()+(height/2);
        return !(this.x + this.width < x || x + width < this.x || this.y + this.height < y || y + height < this.y);
    }

    @Override
    public boolean isCollidedWithCircle(P2d point, double radius) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCollidedWithCircle'");
    }


    
}