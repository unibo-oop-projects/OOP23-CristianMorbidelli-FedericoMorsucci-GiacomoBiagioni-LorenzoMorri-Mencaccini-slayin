import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.entities.enemies.Enemy;
import slayin.model.utility.EntityFactory;
import slayin.model.GameStatus;
import slayin.model.World;

import java.util.Optional;

public class TestGameStatus {

    GameStatus status;
    
    @BeforeEach
    void setUp(){
        status = new GameStatus(null);
    }

    @Test
    void testAddRemoveEnemy(){
        EntityFactory ef = new EntityFactory(new World(100, 100), null);
        Optional<Enemy> tmp = Optional.of(ef.buildDummy());

        assertEquals(status.getObjects().size(), 1);    // the status contains the main character only
        assertFalse(status.getObjects().contains(tmp.get()));
        status.addEnemy(tmp);
        assertTrue(status.getObjects().contains(tmp.get()));

        assertEquals(status.getObjects().size(), 2);
        status.removeEnemy(tmp.get());
        assertFalse(status.getObjects().contains(tmp.get()));
        assertEquals(status.getObjects().size(), 1);    // the status contains the main character only
    }
}
