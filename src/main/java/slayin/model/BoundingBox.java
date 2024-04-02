package slayin.model;

public interface BoundingBox {

    public boolean isCollidedWith(P2d point,BoundingBox boundingBox);
}
