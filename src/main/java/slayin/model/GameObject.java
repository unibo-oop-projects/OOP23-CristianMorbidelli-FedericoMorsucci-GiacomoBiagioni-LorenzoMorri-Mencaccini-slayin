package slayin.model;

abstract class GameObject {
    private P2d pos;


    public GameObject(P2d pos){
        this.pos=pos;
    }

    public P2d getPos(){
        return this.pos;
    }

    public P2d setPos(){
        return this.pos;
    }


}
