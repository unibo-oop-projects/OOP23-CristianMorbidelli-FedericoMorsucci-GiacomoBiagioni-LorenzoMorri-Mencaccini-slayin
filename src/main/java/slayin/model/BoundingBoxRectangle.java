package slayin.model;

/**
 * BoundingBox
 */
public class BoundingBoxRectangle implements BoundingBox{

    private double lenght, height;

    public BoundingBoxRectangle(double lenght, double height){
        this.height=height;
        this.lenght=lenght;
    }

    public double getLenght() {
        return lenght;
    }

    public double getHeight() {
        return height;
    }

    public boolean isCollidedWith(P2d point, BoundingBox boundingBox){
        boolean outcome=false;
        /*TODO */
        return outcome;
    };


    
}