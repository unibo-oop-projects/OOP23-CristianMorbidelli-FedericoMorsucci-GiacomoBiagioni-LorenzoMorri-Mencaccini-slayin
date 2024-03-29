package slayin.model;

public abstract class GameObject {
    protected P2d pos;
    protected double speed;


    public GameObject(P2d pos,double speed){
        this.pos=pos;
        this.speed=speed;
    }

    public P2d getPos(){
        return this.pos;
    }

    public void setPos(P2d pos) {
        this.pos = pos;
    }

    public double getSpeed() {
        return speed;
    }

    public void updateSpeed(double speed) {
        this.speed = speed;
    } 
    
    public abstract void updatePos(InputController input);

}
