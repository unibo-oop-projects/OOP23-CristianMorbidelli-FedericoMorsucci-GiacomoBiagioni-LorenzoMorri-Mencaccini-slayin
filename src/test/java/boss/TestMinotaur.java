package boss;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
//import slayin.model.entities.boss.Boss;
import slayin.model.entities.boss.Minotaur;
import slayin.model.entities.boss.Minotaur.State;

public class TestMinotaur {
    
    Minotaur minotaur;
    World world = new World(100, 100, 80); 
    BoundingBoxImplRet boundingBox = new BoundingBoxImplRet(null, 10, 20);
    
    @BeforeEach
    void setUp(){
        minotaur= new Minotaur(null, boundingBox, world);
    }

    @Test
    void testPosLeft(){
        System.out.println(minotaur.getPos());
        assertTrue(minotaur.getPos().getX()==95.0);
        assertTrue(minotaur.getPos().getY()==70.0);
        minotaur.updatePos(1000); //parte timer prima di state run
        
        minotaur.setPreviousTime(this.getCurrentMinusFiveSeconds());
        
        minotaur.updatePos(1000); //va a sbattere -> collisione
        System.out.println(minotaur.getPos());
        minotaur.updatePos(1000);
        minotaur.updatePos(1000);

        System.out.println(minotaur.getPos());
        assertFalse(minotaur.getPos().getX()==95.0);
        assertTrue(minotaur.getPos().getX()==5.0);
        assertTrue(minotaur.getPos().getY()==70.0);
    }
    
    @Test
    void testLogic(){
        assertTrue(minotaur.getState()==State.START);//stato iniziale
        
        minotaur.updatePos(1000); //parte timer prima di state run
        
        minotaur.setPreviousTime(this.getCurrentMinusFiveSeconds());
        
        minotaur.updatePos(1000); //va a sbattere -> collisione
        assertTrue(minotaur.getState()==State.RUN);//si muove
        
        minotaur.updatePos(1000);
        minotaur.updatePos(1000);

        assertTrue(minotaur.getState()==State.STUNNED); //ha dato la testata
        minotaur.isHitted(); //colpito
        assertTrue(minotaur.getHealth()==4);
        assertTrue(minotaur.getState()==State.HITTED);

        minotaur.updatePos(1000);
        minotaur.setPreviousTime(this.getCurrentMinusFiveSeconds());
        minotaur.updatePos(1000);

        assertTrue(minotaur.getState()==State.START); //riparte
        minotaur.isHitted(); //colpito quando non e' stunned
        assertFalse(minotaur.getHealth()==3);
        assertFalse(minotaur.getState()==State.HITTED);
    }

    public double getCurrentMinusFiveSeconds(){
        double x = (double) System.currentTimeMillis();
        return (x-5000.0);
    }
}
