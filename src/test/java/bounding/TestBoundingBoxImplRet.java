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
        list.add(new BoundingBoxImplCirc(new P2d(9, 6), 3));
        list.add(new BoundingBoxImplCirc(new P2d(11, 9), 3));
        list.add(new BoundingBoxImplCirc(new P2d(13, 6), 3));
        list.add(new BoundingBoxImplCirc(new P2d(9, 11), 3));
        list.add(new BoundingBoxImplCirc(new P2d(-2, 8), 2));
        list.add(new BoundingBoxImplCirc(new P2d(0, 6), 3));
        list.add(new BoundingBoxImplRet(new P2d(-1, 6), 8, 4));
        list.add(new BoundingBoxImplRet(new P2d(-1, 6), 4, 4));
    }

    @Test
    public void testColl(){
        for(var b: list){
            System.out.println(x.isCollidedWith(b));
        }
        assertTrue(x.isCollidedWith(list.get(0)));
        assertTrue(x.isCollidedWith(list.get(1)));
        assertFalse(x.isCollidedWith(list.get(2)));
        assertTrue(x.isCollidedWith(list.get(3)));
        assertTrue(x.isCollidedWith(list.get(4)));
        assertFalse(x.isCollidedWith(list.get(5)));
        assertTrue(x.isCollidedWith(list.get(6)));
        assertFalse(x.isCollidedWith(list.get(7)));
        assertTrue(x.isCollidedWith(list.get(8)));
        assertTrue(x.isCollidedWith(list.get(9)));
        assertFalse(x.isCollidedWith(list.get(10)));
    }

}
