package slayin.model;

public class Vector2d {
    public double x,y;
    
    public Vector2d(double x,double y){
        this.x=x;
        this.y=y;
    }

    public Vector2d sum(Vector2d v){
        return new Vector2d(x+v.x,y+v.y);
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
}
