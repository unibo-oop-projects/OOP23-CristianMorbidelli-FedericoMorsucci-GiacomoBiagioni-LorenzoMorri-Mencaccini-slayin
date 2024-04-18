package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.entities.GameObject;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Minotaur extends GameObject implements Boss  {

    //TODO: initialize boss position & velocity
    public Minotaur(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void updatePos(int dt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePos'");
    }
    
}
