package slayin.model.utility;

/**
 * This class represents a 2-dimensional vector and provides various methods to operate on it.
 */
public class Vector2d {
    private double x,y;
    

    /**
     * Constructs a new vector with the specified x and y components.
     *
     * @param x the x component of the vector.
     * @param y the y component of the vector.
     */
    public Vector2d(double x,double y){
        this.x=x;
        this.y=y;
    }


    /**
     * Sets the x component of the vector.
     *
     * @param x the new x component.
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Sets the y component of the vector.
     *
     * @param y the new y component.
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Gets the x component of the vector.
     *
     * @return the x component.
     */
    public double getX() {
        return x;
    }


    /**
     * Gets the y component of the vector.
     *
     * @return the y component.
     */
    public double getY() {
        return y;
    }


    /**
     * Returns the sum of this vector and another vector.
     *
     * @param v the vector to add.
     * @return a new vector that is the sum of this vector and the specified vector.
     */
    public Vector2d sum(Vector2d v){
        return new Vector2d(x+v.x,y+v.y);
    }


    /**
     * Returns the sum of this vector and the specified x and y components.
     *
     * @param x the x component to add.
     * @param y the y component to add.
     * @return a new vector that is the sum of this vector and the specified components.
     */
    public Vector2d sum(int x, int y){
        return new Vector2d(this.x+x,this.y+y);
    }


    /**
     * Returns the magnitude (module) of the vector.
     *
     * @return the magnitude of the vector.
     */
    public double module(){
        return (double)Math.sqrt(x*x+y*y);
    }


    /**
     * Returns a new vector that is the product of this vector and the specified scalar factor.
     *
     * @param fact the scalar factor to multiply.
     * @return a new vector that is the product of this vector and the specified factor.
     */
    public Vector2d mul(double fact){
        return new Vector2d(x*fact,y*fact);
    }


    /**
     * Returns a string representation of the vector.
     *
     * @return a string representation of the vector.
     */
    @Override
    public String toString() {
        return "Vector2d [x=" + x + ", y=" + y + "]";
    }


    /**
     * Checks if this vector is equal to another vector.
     *
     * @param vet the vector to compare with.
     * @return true if the vectors are equal, false otherwise.
     */
    public boolean equals(Vector2d vet){
        return this.getX()==vet.getX() && this.getY()==vet.getY();
    }
}
