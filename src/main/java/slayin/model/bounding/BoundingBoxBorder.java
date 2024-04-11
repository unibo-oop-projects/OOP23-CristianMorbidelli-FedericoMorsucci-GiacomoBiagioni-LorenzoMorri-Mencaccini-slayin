package slayin.model.bounding;

import slayin.model.utility.P2d;

public class BoundingBoxBorder implements BoundingBox{

    public static enum TypeAxis { AXIS_Y_DOWN, AXIS_X_LEFT ,AXIS_Y_TOP, AXIS_X_RIGHT }
    private double limit;
    private TypeAxis axis;

    public BoundingBoxBorder(double limit,TypeAxis axis){
        this.limit=limit;
        this.axis=axis;
    }

    public double getLimit() {
        return limit;
    }
    public TypeAxis getTypeAxis() {
        return this.axis;
    }

    @Override
    public boolean isCollidedWith(BoundingBox b) {
        boolean outcome=false;
        if(b instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox = (BoundingBoxImplRet) b;
            if(this.axis==TypeAxis.AXIS_X_LEFT && bBox.getX()<this.limit ) outcome= true;
            else if(this.axis==TypeAxis.AXIS_X_RIGHT && bBox.getX()+bBox.getWidht()>this.limit) outcome= true;
            else if(this.axis==TypeAxis.AXIS_Y_TOP && bBox.getY()-bBox.getHeight()<this.limit) outcome= true;
            else if(this.axis==TypeAxis.AXIS_Y_DOWN && bBox.getY()>this.limit) outcome= true;
        }else if(b instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) b;
            if(this.axis==TypeAxis.AXIS_X_LEFT && bBox.getX()<this.limit ) outcome= true;
            else if(this.axis==TypeAxis.AXIS_X_RIGHT && bBox.getX()+bBox.getRadius()>this.limit) outcome= true;
            else if(this.axis==TypeAxis.AXIS_Y_TOP && bBox.getY()<this.limit) outcome= true;
            else if(this.axis==TypeAxis.AXIS_Y_DOWN && bBox.getY()+bBox.getRadius()>this.limit) outcome= true;
        }
        return outcome;
    }

    @Override
    public void updatePoint(P2d p) {
        this.limit=p.getX();
    }
    
}
