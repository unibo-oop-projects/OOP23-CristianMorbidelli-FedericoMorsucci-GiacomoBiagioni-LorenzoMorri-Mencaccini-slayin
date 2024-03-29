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
        k = new Knight(new P2d(0, 0), 2);
        controller= new InputController();
    }

    @Test
    public void testVel(){
        controller.setMoveUp();
        for(int i=0;i<100;i++){
            k.updatePos(controller);
        }
        assertEquals(200, k.getPos().getY());
    }
}
