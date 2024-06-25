package slayin.model;

import java.util.ArrayList;
import java.util.List;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject;
import slayin.model.entities.graphics.DrawComponent;
import slayin.model.entities.graphics.DrawComponentFactory;

/**
 * Represents the game world with specified dimensions and ground level.
 */
public class World {

    public static enum Edge { LEFT_BORDER, RIGHT_BORDER , TOP_BORDER , BOTTOM_BORDER  }
    private int width,height,ground;


    /**
     * Constructs a new World with the given width and height.
     *
     * @param width  the width of the world
     * @param height the height of the world
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.ground = (int) (height / 1.2);
    }


    /**
     * Returns the ground level of the world.
     *
     * @return the ground level
     */
    public int getGround(){
        return this.ground;
    }


    /**
     * Returns the width of the world.
     *
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }


    /**
     * Returns the height of the world.
     *
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }


    /**
     * Checks if the given game object is touching the ground.
     *
     * @param obj the game object to check
     * @return true if the object is touching the ground, false otherwise
     */
    public boolean isTouchingGround(GameObject obj){
        return this.collidingWith(obj).stream().filter(e-> e==Edge.BOTTOM_BORDER).findFirst().isPresent();
    }

   
    /**
     * Returns a list of edges with which the given game object is colliding.
     *
     * @param obj the game object to check
     * @return a list of edges the object is colliding with
     */
    public List<Edge> collidingWith(GameObject obj){
        List<Edge> out= new ArrayList<>();
        out.addAll(this.collidingWithSides(obj));
        out.addAll(this.collidingWithUpOrDown(obj));
        return out;
    }


    /**
     * Returns a list of side edges (left or right) with which the given game object is colliding.
     *
     * @param obj the game object to check
     * @return a list of side edges the object is colliding with
     */
    public List<Edge> collidingWithSides(GameObject obj){
        List<Edge> out= new ArrayList<>();
        if(obj.getBoundingBox() instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox= (BoundingBoxImplRet) obj.getBoundingBox();
            if(bBox.getX()<=0) out.add(Edge.LEFT_BORDER);
            if(bBox.getX()+bBox.getWidth()>=width) out.add(Edge.RIGHT_BORDER);
        }else if(obj.getBoundingBox() instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox= (BoundingBoxImplCirc) obj.getBoundingBox();
            if(bBox.getX()-bBox.getRadius()<=0) out.add(Edge.LEFT_BORDER);
            if(bBox.getX()+bBox.getRadius()>=width) out.add(Edge.RIGHT_BORDER);
        }
        return out;
    }


    /**
     * Returns a list of vertical edges (top or bottom) with which the given game object is colliding.
     *
     * @param obj the game object to check
     * @return a list of vertical edges the object is colliding with
     */
    public List<Edge> collidingWithUpOrDown(GameObject obj){
        List<Edge> out= new ArrayList<>();
        if(obj.getBoundingBox() instanceof BoundingBoxImplRet){
            BoundingBoxImplRet bBox= (BoundingBoxImplRet) obj.getBoundingBox();
            if(bBox.getY()<=0) out.add(Edge.TOP_BORDER);
            if(bBox.getY()+bBox.getHeight()>=ground) out.add(Edge.BOTTOM_BORDER);
        }else if(obj.getBoundingBox() instanceof BoundingBoxImplCirc){
            BoundingBoxImplCirc bBox= (BoundingBoxImplCirc) obj.getBoundingBox();
            if(bBox.getY()-bBox.getRadius()<=0) out.add(Edge.TOP_BORDER);
            if(bBox.getY()+bBox.getRadius()>=ground) out.add(Edge.BOTTOM_BORDER);
        }
        return out;
    } 


    /**
     * Returns the DrawComponent for rendering the world.
     *
     * @return the DrawComponent for this world
     */
    public DrawComponent getDrawComponent() {
        return DrawComponentFactory.graphicsComponentWorld(this);
    }
}
