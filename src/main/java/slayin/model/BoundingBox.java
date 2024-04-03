package slayin.model;

public interface BoundingBox {

    public boolean isCollidedWithRettagle(P2d point,double widht, double height);

    public boolean isCollidedWithCircle(P2d point,double radius);

}
