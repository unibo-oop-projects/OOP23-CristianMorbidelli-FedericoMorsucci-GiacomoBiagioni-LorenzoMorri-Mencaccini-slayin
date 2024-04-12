package slayin.model;


import java.util.ArrayList;
import java.util.List;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxBorder;
import slayin.model.bounding.BoundingBoxBorder.TypeAxis;
import slayin.model.entities.GameObject;

public class World {

    public static enum Edge { LEFT_BORDER, RIGHT_BORDER , TOP_BORDER , BOTTOM_BORDER  }
    private int width,height,ground;
    private BoundingBox borderTop,borderLeft,borderRight,borderGround;

    public World(int width, int height, int ground) {
        this.width = width;
        this.height = height;
        this.ground=ground;
        borderGround= new BoundingBoxBorder(ground, TypeAxis.AXIS_Y_DOWN);
        borderLeft= new BoundingBoxBorder(0, TypeAxis.AXIS_X_LEFT);
        borderRight= new BoundingBoxBorder(width, TypeAxis.AXIS_X_RIGHT);
        borderTop= new BoundingBoxBorder(height, TypeAxis.AXIS_Y_TOP);
    }

    public int getGround(){
        return this.ground;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    // TODO: sostituire BoundingBox
    public List<Edge> collidingWith(GameObject obj){
        BoundingBox bBox= obj.getBoundingBox();
        List<Edge> out= new ArrayList<>();
        if(borderGround.isCollidedWith(bBox)){
            out.add(Edge.BOTTOM_BORDER);
        }
        if(borderTop.isCollidedWith(bBox)){
            out.add(Edge.TOP_BORDER);
        }
        if(borderRight.isCollidedWith(bBox)){
            out.add(Edge.RIGHT_BORDER);
        }
        if(borderLeft.isCollidedWith(bBox)){
            out.add(Edge.LEFT_BORDER);
        }
        return out;
    }


}
