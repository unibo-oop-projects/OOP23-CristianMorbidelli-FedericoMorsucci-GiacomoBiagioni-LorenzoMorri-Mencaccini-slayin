package slayin.model;

public class Knight extends GameObject{

    public Knight(P2d pos) {
        super(pos);
    }

    public void updatePos(InputController input){
        if(input.isMoveUp()){
           this.pos.setY(pos.getY()+1);
        }else if(input.isMoveDown()){
            this.pos.setY(pos.getY()-1);
        }else if(input.isMoveLeft()){
            this.pos.setX(pos.getX()-1);
        }else if(input.isMoveRight()){
            this.pos.setX(pos.getX()+1);
        }
    }
    
}
