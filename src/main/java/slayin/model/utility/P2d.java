package slayin.model.utility;

/**
 * Represents a point in 2-dimensional space with coordinates (x, y).
 */
public class P2d {
    private double x,y;


    /**
     * Constructs a point with specified coordinates (x, y).
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public P2d(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Constructs a point as a copy of another point.
     *
     * @param point the point to copy
     */
    public P2d(P2d point){
        this(point.getX(),point.getY());
    }


    /**
     * Returns the x-coordinate of the point.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }


    /**
     * Sets the x-coordinate of the point.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Returns the y-coordinate of the point.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }


    /**
     * Sets the y-coordinate of the point.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Checks if the x-coordinate of this point is equal to the x-coordinate of another point.
     *
     * @param point the point to compare with
     * @return true if the x-coordinates are equal, otherwise false
     */
    public Boolean equalsX(P2d point){
        return this.x == point.getX();
    } 


    /**
     * Calculates the Euclidean distance from this point to another point specified by (x, y) coordinates.
     *
     * @param x the x-coordinate of the other point
     * @param y the y-coordinate of the other point
     * @return the Euclidean distance between the two points
     */
    public double distanceFromPoint(double x, double y) {
        return Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y - y), 2));
    }


    /**
     * Calculates the Euclidean distance from this point to another point.
     *
     * @param point the other point
     * @return the Euclidean distance between this point and the other point
     */
    public double distanceFromPoint(P2d point) {
        return Math.sqrt(Math.pow((this.x - point.getX()), 2) + Math.pow((this.y - point.getY()), 2));
    }


    /**
     * Computes the point resulting from adding a vector to this point.
     *
     * @param v the vector to add
     * @return a new point representing the sum of this point and the vector
     */
    public P2d sum(Vector2d v){
        return new P2d(x+v.getX(),y+v.getY());
    }


    /**
     * Returns a string representation of this point.
     *
     * @return a string representation of the point in the format "P2d [x=x, y=y]"
     */
    @Override
    public String toString() {
        return "P2d [x=" + x + ", y=" + y + "]";
    }

}
