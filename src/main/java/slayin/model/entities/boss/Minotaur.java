package slayin.model.entities.boss;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.utility.P2d;
import slayin.model.utility.Vector2d;

public class Minotaur extends Boss  {
    /**
     * Minotaur constructor, set initial health
     * @param pos
     * @param vectorMovement
     * @param boundingBox
     * @param world
     * 
     */
    public Minotaur(P2d pos, Vector2d vectorMovement, BoundingBox boundingBox, World world) {
        super(pos, vectorMovement, boundingBox, world);
        this.setHealth(5); //The Minotaur must receive 5 hits to be defeated
    }

    @Override
    public void updatePos(int dt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePos'");
    }
    
}
