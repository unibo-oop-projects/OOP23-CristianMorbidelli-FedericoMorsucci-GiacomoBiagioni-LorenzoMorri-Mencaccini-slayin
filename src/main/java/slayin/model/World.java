package slayin.model;

import java.util.ArrayList;
import java.util.List;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;

public class World {

    public static enum Edge { LEFT_BORDER, RIGHT_BORDER , TOP_BORDER , BOTTOM_BORDER  }
    private int width,height,ground;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.ground = (int) (height / 1.2);
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

    public boolean isTouchingGround(GameObject obj){
        return this.collidingWith(obj).stream().filter(e-> e==Edge.BOTTOM_BORDER).findFirst().isPresent();
    }

    // TODO: sostituire BoundingBox
    public List<Edge> collidingWith(GameObject obj){
        List<Edge> out= new ArrayList<>();
        if(obj.getBoundingBox() instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox= (BoundingBoxImplRet) obj.getBoundingBox();
            if(bBox.getX()<=0) out.add(Edge.LEFT_BORDER);
            if(bBox.getX()+bBox.getWidth()>=width) out.add(Edge.RIGHT_BORDER);
            if(bBox.getY()<=0) out.add(Edge.TOP_BORDER);
            if(bBox.getY()+bBox.getHeight()>=ground) out.add(Edge.BOTTOM_BORDER);
        }else if(obj.getBoundingBox() instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox= (BoundingBoxImplCirc) obj.getBoundingBox();
            if(bBox.getX()-bBox.getRadius()<=0) out.add(Edge.LEFT_BORDER);
            if(bBox.getX()+bBox.getRadius()>=width) out.add(Edge.RIGHT_BORDER);
            if(bBox.getY()-bBox.getRadius()<=0) out.add(Edge.TOP_BORDER);
            if(bBox.getY()+bBox.getRadius()>=ground) out.add(Edge.BOTTOM_BORDER);
        }
        return out;
    }

    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentWorld(this);
    }
}
