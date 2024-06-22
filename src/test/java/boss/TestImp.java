package boss;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.World;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.boss.Imp;
import slayin.model.entities.boss.Boss.State;

public class TestImp {
    Imp imp;
    World world = new World(100, 100); 
    BoundingBoxImplRet boundingBox = new BoundingBoxImplRet(null, 10, 10);
    
    @BeforeEach
    void setUp(){
        imp= new Imp(null, boundingBox, world, null);
    }

    //per testare funzionamento i metodi erano public e non protected

    @Test
    void testPos(){
        //posizione iniziale
        assertTrue(imp.getPos().getX()==50.0);
        assertTrue(imp.getPos().getY()==50.0);
        
        imp.updatePos(1);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato -> ATTACK

        //stessa posizione
        assertTrue(imp.getPos().getX()==50.0);
        assertTrue(imp.getPos().getY()==50.0);

        //(this.getCurrentMinusNSeconds(3.0));
        
        imp.updatePos(1);//cambio stato -> WAITING

        //stessa posizione
        assertTrue(imp.getPos().getX()==50.0);
        assertTrue(imp.getPos().getY()==50.0);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato -> INVISIBLE

        //stessa posizione
        assertTrue(imp.getPos().getX()==50.0);
        assertTrue(imp.getPos().getY()==50.0);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato ->START
        assertFalse(imp.getPos().getX()==50.0);
        assertTrue(imp.getPos().getX()==5.0 || imp.getPos().getX()==95.0);
    }

    @Test
    void testLogic(){
        assertTrue(imp.getState()==State.START);
        
        imp.updatePos(1);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato -> ATTACK

        assertFalse(imp.getState()==State.START);
        assertTrue(imp.getState()==State.ATTACK);

        //(this.getCurrentMinusNSeconds(3.0));
        
        imp.updatePos(1);//cambio stato -> WAITING

        assertFalse(imp.getState()==State.START);
        assertFalse(imp.getState()==State.ATTACK);
        assertTrue(imp.getState()==State.WAITING);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato -> INVISIBLE

        assertFalse(imp.getState()==State.START);
        assertFalse(imp.getState()==State.ATTACK);
        assertFalse(imp.getState()==State.WAITING);
        assertTrue(imp.getState()==State.INVISIBLE);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato ->START

        assertFalse(imp.getState()==State.INVISIBLE);
        assertTrue(imp.getState()==State.START);
    }

    @Test
    void testDamageAndNumShots(){
        assertTrue(imp.getNumShots()==0);

        imp.updatePos(1);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato -> ATTACK
        //(this.getCurrentMinusNSeconds(3.0));
        
        imp.updatePos(1);//cambio stato -> WAITING

        imp.onHit();//colpito
        assertTrue(imp.getHealth()==9);
        imp.onHit();
        assertFalse(imp.getHealth()==8);
        assertTrue(imp.getState()==State.HITTED);

        //(this.getCurrentMinusNSeconds(1.0));
        
        imp.updatePos(1);//cambio stato -> INVISIBLE

        assertTrue(imp.getState()==State.INVISIBLE);
        imp.onHit();
        assertFalse(imp.getHealth()==8);
        assertFalse(imp.getState()==State.HITTED);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato ->START
        assertTrue(imp.getNumShots()==1);//colpi aumentati

        assertFalse(imp.getState()==State.INVISIBLE);
        assertTrue(imp.getState()==State.START);

        imp.onHit();//colpito
        assertTrue(imp.getHealth()==8);
        imp.onHit();
        assertFalse(imp.getHealth()==7);
        assertTrue(imp.getState()==State.HITTED);

        //imp.setPreviousTime(this.getCurrentMinusNSeconds(1.0));
        
        imp.updatePos(1);//cambio stato -> INVISIBLE

        assertTrue(imp.getState()==State.INVISIBLE);
        assertFalse(imp.getState()==State.HITTED);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato ->START
        assertTrue(imp.getNumShots()==1);//colpi invariati

        //imp.diminishHealth(1); //tolgo manualmente 1
        assertTrue(imp.getState()==State.START);

        imp.onHit();//colpito
        assertTrue(imp.getHealth()==6);
        imp.onHit();
        assertFalse(imp.getHealth()==5);
        assertTrue(imp.getState()==State.HITTED);

        //(this.getCurrentMinusNSeconds(1.0));
        
        imp.updatePos(1);//cambio stato -> INVISIBLE

        assertTrue(imp.getState()==State.INVISIBLE);
        assertFalse(imp.getState()==State.HITTED);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato ->START
        assertTrue(imp.getNumShots()==2);//colpi aumentati
    }
/* 
    @Test
    void testShotsFired(){
        assertTrue(imp.getNumShots()==0);
        //(7);
        //imp.setNumShots(3);

        //(this.getCurrentMinusNSeconds(2.0));
        
        imp.updatePos(1);//cambio stato -> ATTACK

        //(this.getCurrentMinusNSeconds(0.0));
        imp.updatePos(1);//first shoot
        assertTrue(imp.getShotsFired()==1);
        imp.updatePos(1);//non deve sparare
        assertTrue(imp.getShotsFired()==1);

        //(this.getCurrentMinusNSeconds(1.0));
        imp.updatePos(1);//second shoot
        assertTrue(imp.getShotsFired()==2);
        imp.updatePos(1);//non deve sparare
        assertTrue(imp.getShotsFired()==2);

        //(this.getCurrentMinusNSeconds(2.0));
        imp.updatePos(1);//third shoot
        assertTrue(imp.getShotsFired()==3);
        imp.updatePos(1);//non deve sparare
        assertTrue(imp.getShotsFired()==3);

        //(this.getCurrentMinusNSeconds(3.0));
        imp.updatePos(1);//non deve sparare, colpi finiti -> passa anche a waiting
        assertTrue(imp.getShotsFired()==3);

        assertTrue(imp.getState()==State.WAITING);
    }
*/
    public double getCurrentMinusNSeconds(double n){
        double x = (double) System.currentTimeMillis();
        n=n*1000.0;
        return (x-n);
    }
}
