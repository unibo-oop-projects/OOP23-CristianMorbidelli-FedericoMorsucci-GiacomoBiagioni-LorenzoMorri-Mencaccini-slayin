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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidht() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean isCollidedWith(BoundingBox b) {
        boolean outcome=false;
        if(b instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox = (BoundingBoxImplRet) b;
            outcome= !(this.x + this.width < bBox.getX() || bBox.getX() + bBox.getWidht() < this.x 
            || this.y + this.height < bBox.getY() || bBox.getY() + bBox.getHeight() < this.y);
        }else if(b instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) b;
            outcome= !(this.x + this.width < bBox.getX()-bBox.getRadius() || bBox.getX() + bBox.getRadius() < this.x 
            || this.y + this.height < bBox.getY()-bBox.getRadius() || bBox.getY() + bBox.getRadius() < this.y);
        }
        return outcome;
    }


    
}