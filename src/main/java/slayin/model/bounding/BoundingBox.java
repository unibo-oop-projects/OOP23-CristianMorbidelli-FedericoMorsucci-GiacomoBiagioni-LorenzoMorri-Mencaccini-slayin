package slayin.model.bounding;

import slayin.model.utility.P2d;

public interface BoundingBox {

    public void updatePoint(P2d p);

    public boolean isCollidedWith(BoundingBox b);

}
