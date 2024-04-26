package bounding;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.utility.P2d;

import java.util.ArrayList;


// TODO: riscrivi bene i test
public class TestBoundingBoxImplCirc {

    BoundingBox x;
    ArrayList<BoundingBox> list;

    @BeforeEach
    void setUp() {
        x = new BoundingBoxImplCirc(new P2d(5, 6),3);
        list=new ArrayList<>();
        list.add(new BoundingBoxImplRet(new P2d(10, 6), 6, 4));//tocca
        list.add(new BoundingBoxImplCirc(new P2d(10, 3), 3));//tocca
        list.add(new BoundingBoxImplCirc(new P2d(1, 12), 3));//non tocca
        list.add(new BoundingBoxImplCirc(new P2d(1, 12), 3));//non tocca
        list.add(new BoundingBoxImplRet(new P2d(-3, 3), 6, 4));//non tocca
        list.add(new BoundingBoxImplCirc(new P2d(5, 12), 3));// tocca
    }

    @Test
    public void testColl(){
        for(var b: list){
            System.out.println(x.isCollidedWith(b));
        }
        assertTrue(x.isCollidedWith(list.get(0)));
        assertTrue(x.isCollidedWith(list.get(1)));
        assertFalse(x.isCollidedWith(list.get(2)));
        assertFalse(x.isCollidedWith(list.get(3)));
        assertFalse(x.isCollidedWith(list.get(4)));
        assertTrue(x.isCollidedWith(list.get(5)));
    }

}
