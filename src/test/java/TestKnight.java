import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.InputController;
import slayin.model.Knight;
import slayin.model.P2d;
import slayin.model.Vector2d;

public class TestKnight {
    
    Knight k;
    InputController controller;

    @BeforeEach                                         
    void setUp() {
        k = new Knight(new P2d(0, 0), new Vector2d(3, 9), null);
        controller= new InputController();
    }

    @Test
    public void testVel(){
        controller.setMoveUp();
        for(int i=0;i<100;i++){
            k.updateVel(controller);
        }
        //il modulo resta lo stesso anche se l'ogetto cambia direzione
        assertEquals((double)Math.sqrt(3*3+9*9), k.getVectorMouvement().module());
    }
}
