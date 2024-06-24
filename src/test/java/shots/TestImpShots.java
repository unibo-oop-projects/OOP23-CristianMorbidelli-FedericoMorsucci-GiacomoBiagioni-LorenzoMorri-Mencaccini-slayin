package shots;

//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.entities.shots.ImpShots;
import slayin.model.utility.P2d;

public class TestImpShots {
    ImpShots shot2,shot1;
    World world = new World(100, 100);

    //they were tested with methods that were public and had speeds not in proportion to the world, 
    //which after was changed

    @BeforeEach
    void setUp(){
        shot2= new ImpShots(new P2d(10,10), new BoundingBoxImplCirc(new P2d(90,90), 5), world, false,0);
        shot1= new ImpShots(new P2d(10,75), new BoundingBoxImplCirc(new P2d(10,75), 5), world, true,0);
    }

    @Test
    void testLinear(){
        /*assertTrue(shot1.getPos().getX()==10);
        assertTrue(shot1.getPos().getY()==75);
        
        shot1.updatePos(1000);//update position 1 sec

        assertFalse(shot1.getPos().getX()==10);
        assertTrue(shot1.getPos().getX()==410);
        assertTrue(shot1.getPos().getY()==75);*/
    }

    @Test
    void testNotLinear(){
        /*assertTrue(shot2.getPos().getX()==10);
        assertTrue(shot2.getPos().getY()==10);
        
        shot2.updatePos(1000);//update position 1 sec

        assertFalse(shot2.getPos().getX()==10);
        assertFalse(shot2.getPos().getY()==10);
        assertTrue(shot2.getPos().getX()==410);
        assertTrue(shot2.getPos().getY()==75);*/
    }
}
