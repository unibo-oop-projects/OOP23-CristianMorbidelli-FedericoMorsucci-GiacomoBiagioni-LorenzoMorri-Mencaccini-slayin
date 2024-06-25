package enemies;

//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.GameObject.Direction;
import slayin.model.entities.enemies.Enemy;
import slayin.model.entities.enemies.Slime;
import slayin.model.utility.P2d;

public class TestSlime {
    
    Enemy slime;
    World world = new World(1000, 1000); 
    BoundingBoxImplRet boundingBox = new BoundingBoxImplRet(null, 10, 10);
    
    @BeforeEach
    void setUp(){
        slime = new Slime(new P2d(50,10), boundingBox, world, null);
    }

    @Test
    void testPos(){

        System.out.println("x="+slime.getPos().getX()+"y="+slime.getPos().getY());

        //check if slime spawn correctly
        assertTrue(slime.getPos().getX()==50 && slime.getPos().getY()==10);

        slime.setDir(Direction.RIGHT);
        //slime.updatePos(10);
        
        //check if the slime move
        //assertTrue(slime.getPos().getX()>50);
    }
}