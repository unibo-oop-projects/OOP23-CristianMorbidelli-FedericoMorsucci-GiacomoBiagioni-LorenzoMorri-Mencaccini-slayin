import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.InputController;
import slayin.model.Knight;
import slayin.model.P2d;

public class TestKnight {
    
    Knight k;
    InputController controller;

    @BeforeEach                                         
    void setUp() {
        k = new Knight(new P2d(0, 0));
        controller= new InputController();
    }

    @Test
    public void testPos(){
        controller.setMoveUp();
        for(int i=0;i<100;i++){
            k.updatePos(controller);
        }
        assertEquals(100, k.getPos().getY());
    }
}
