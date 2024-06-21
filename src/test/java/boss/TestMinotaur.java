package boss;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.boss.Boss.State;
import slayin.model.entities.boss.Minotaur;

public class TestMinotaur {
    
    Minotaur minotaur;
    World world = new World(100, 100); 
    BoundingBoxImplRet boundingBox = new BoundingBoxImplRet(null, 10, 20);

    //per testare funzionamento i metodi erano public e non protected
    
    @BeforeEach
    void setUp(){
        minotaur= new Minotaur(null, boundingBox, world);
    }

    @Test
    void testPos(){
        assertTrue(minotaur.getPos().getX()==95.0);
        assertTrue(minotaur.getPos().getY()==70.0);
        minotaur.updatePos(1000); //parte timer prima di state run
        
        //minotaur.setPreviousTime(this.getCurrentMinusFiveSeconds());
        
        minotaur.updatePos(1000); //va a sbattere -> collisione
        minotaur.updatePos(1000);
        minotaur.updatePos(1000);

        assertFalse(minotaur.getPos().getX()==95.0);
        assertTrue(minotaur.getPos().getX()==5.0);
        assertTrue(minotaur.getPos().getY()==70.0);
    }
    
    @Test
    void testLogic(){
        assertTrue(minotaur.getState()==State.START);//stato iniziale
        
        minotaur.updatePos(1000); //parte timer prima di state run
        
        //minotaur.setPreviousTime(this.getCurrentMinusFiveSeconds());
        
        minotaur.updatePos(1000); //va a sbattere -> collisione
        assertTrue(minotaur.getState()==State.RUN);//si muove
        
        minotaur.updatePos(1000);
        minotaur.updatePos(1000);

        assertTrue(minotaur.getState()==State.STUNNED); //ha dato la testata
        minotaur.onHit(); //colpito
        assertTrue(minotaur.getHealth()==4);
        assertTrue(minotaur.getState()==State.HITTED);

        minotaur.updatePos(1000);
        //minotaur.setPreviousTime(this.getCurrentMinusFiveSeconds());
        minotaur.updatePos(1000);

        assertTrue(minotaur.getState()==State.START); //riparte
        minotaur.onHit(); //colpito quando non e' stunned
        assertFalse(minotaur.getHealth()==3);
        assertFalse(minotaur.getState()==State.HITTED);
    }

    public double getCurrentMinusFiveSeconds(){
        double x = (double) System.currentTimeMillis();
        return (x-5000.0);
    }
}
