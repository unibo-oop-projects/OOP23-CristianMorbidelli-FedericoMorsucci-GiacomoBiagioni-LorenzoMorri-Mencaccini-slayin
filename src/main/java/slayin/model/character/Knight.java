package slayin.model.character;

import slayin.model.GameObject;
import slayin.model.InputController;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Knight extends GameObject{

    private Vector2d velocity,gravity;
    private InputController lastInput;

    public Knight(P2d pos,Vector2d VectorMouvement,BoundingBox boundingBox) {
        super(pos,VectorMouvement,boundingBox);
        velocity= new Vector2d(0, 0);
        gravity= new Vector2d(0, +600);
    }

    @Override
    public void updateVel(InputController input) {
        lastInput=input;
        if(input.isMoveUp()){
            this.setVectorMouvement(new Vector2d(0, -4000));
        }else if(input.isMoveLeft()){
            this.setVectorMouvement(new Vector2d(-100, 0));
        }else if(input.isMoveRight()){
            this.setVectorMouvement(new Vector2d(100, 0));
        }
    }

    @Override
    public void updatePos(int dt) {
        if(this.pos.getY()>=370 && !(lastInput.isMoveUp())){
            this.velocity= new Vector2d(0, 0);
            this.setPos(new P2d(pos.getX(),370));
        }else{
            this.velocity= this.velocity.sum(vectorMouvement.mul(0.001*dt));
            //add the gravity 
            this.velocity= this.velocity.sum(gravity.mul(0.001*dt));
            System.out.println(velocity);
            this.setPos(pos.sum(this.velocity.mul(0.001*dt)));
        }
    }
    
}
