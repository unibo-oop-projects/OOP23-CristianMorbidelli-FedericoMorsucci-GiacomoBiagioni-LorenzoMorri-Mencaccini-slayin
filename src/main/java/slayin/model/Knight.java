package slayin.model;

public class Knight extends GameObject{

    public Knight(P2d pos,double speed) {
        super(pos,speed);
    }

    @Override
    public void updatePos(InputController input) {
        if(input.isMoveUp()){
            this.pos.setY(pos.getY()+speed);
         }else if(input.isMoveDown()){
            this.pos.setY(pos.getY()-speed);
         }else if(input.isMoveLeft()){
            this.pos.setX(pos.getX()-speed);
         }else if(input.isMoveRight()){
            this.pos.setX(pos.getX()+speed);
         }
    }
    
}
