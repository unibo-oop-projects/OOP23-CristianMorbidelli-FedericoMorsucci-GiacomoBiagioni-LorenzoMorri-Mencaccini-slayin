import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.BoundingBox;
import slayin.model.BoundingBoxImplRet;
import slayin.model.P2d;
import java.util.ArrayList;



public class TestBoundingBoxImplRet {

    BoundingBox x;
    ArrayList<BoundingBox> list;

    @BeforeEach
    void setUp() {
        x = new BoundingBoxImplRet(new P2d(5, 6),6, 6);
        list=new ArrayList<>();
        list.add(new BoundingBoxImplRet(new P2d(10, 6), 6, 4));
        list.add(new BoundingBoxImplRet(new P2d(11, 6), 6, 4));
        list.add(new BoundingBoxImplRet(new P2d(12, 6), 6, 4));
    }

    @Test
    public void testColl(){
        for(var b: list){
            System.out.println(x.isCollidedWith(b));
        }
        assertTrue(x.isCollidedWith(list.get(0)));
        assertTrue(x.isCollidedWith(list.get(1)));
        assertFalse(x.isCollidedWith(list.get(2)));
    }

}
