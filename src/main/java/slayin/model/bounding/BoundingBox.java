package slayin.model.bounding;

import slayin.model.utility.P2d;

public interface BoundingBox {

    public void updatePoint(P2d p);

    public P2d getPoint();

    public boolean isCollidedWith(BoundingBox b);

}
