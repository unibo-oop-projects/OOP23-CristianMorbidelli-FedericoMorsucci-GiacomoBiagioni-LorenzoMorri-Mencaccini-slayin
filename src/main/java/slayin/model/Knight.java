package slayin.model;

public class Knight extends GameObject{

    public Knight(P2d pos,Vector2d VectorMouvement) {
        super(pos,VectorMouvement);
    }

    @Override
    public void updateVel(InputController input) {
        double speed= this.vectorMouvement.module();
        if(input.isMoveUp()){
            this.setVectorMouvement(new Vector2d(0, 1).mul(speed));
         }else if(input.isMoveDown()){
            this.setVectorMouvement(new Vector2d(0,-1).mul(speed));
         }else if(input.isMoveLeft()){
            this.setVectorMouvement(new Vector2d(-1, 0).mul(speed));
         }else if(input.isMoveRight()){
            this.setVectorMouvement(new Vector2d(1, 0).mul(speed));
         }
    }

    @Override
    public void updatePos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePos'");
    }
    
}
