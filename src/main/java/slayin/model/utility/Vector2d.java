package slayin.model.utility;

public class Vector2d {
    private double x,y;
    
    public Vector2d(double x,double y){
        this.x=x;
        this.y=y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2d sum(Vector2d v){
        return new Vector2d(x+v.x,y+v.y);
    }

    public Vector2d sum(int x, int y){
        return new Vector2d(this.x+x,this.y+y);
    }


    public double module(){
        return (double)Math.sqrt(x*x+y*y);
    }

    public Vector2d mul(double fact){
        return new Vector2d(x*fact,y*fact);
    }


    @Override
    public String toString() {
        return "Vector2d [x=" + x + ", y=" + y + "]";
    }

    public boolean equals(Vector2d vet){
        return this.getX()==vet.getX() && this.getY()==vet.getY();
    }
}
