import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.World;
import slayin.model.entities.Dummy;
import slayin.model.entities.GameObject;
import slayin.model.utility.EntityFactory;

public class TestEntityFactory {

    EntityFactory entityFactory;
    final int worldWidth = 1000;
    final int worldHeight = 1000;
    final int groundLevel = 800;

    @BeforeEach
    void setUp(){
        entityFactory = new EntityFactory(new World(worldWidth, worldHeight, groundLevel),null);
    }

    @Test
    void testBuildDummy(){
        GameObject dummy = entityFactory.buildDummy();

        assertTrue(dummy instanceof Dummy);
        int rightHeight = groundLevel - (worldHeight/20)/2;
        assertTrue(dummy.getPos().getY() == rightHeight);
        assertTrue(dummy.getPos().getX() >= 0 && dummy.getPos().getX()<1000);
    }
}
