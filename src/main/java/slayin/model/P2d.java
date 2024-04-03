package slayin.model;

public class P2d {
    private double x,y;

    public P2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public P2d(P2d point){
        this(point.getX(),point.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "P2d [x=" + x + ", y=" + y + "]";
    }

    public double distanceFromPoint(double x, double y) {
        return Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y - y), 2));
    }

    public double distanceFromPoint(P2d point) {
        return Math.sqrt(Math.pow((this.x - point.getX()), 2) + Math.pow((this.y - point.getY()), 2));
    }

}
