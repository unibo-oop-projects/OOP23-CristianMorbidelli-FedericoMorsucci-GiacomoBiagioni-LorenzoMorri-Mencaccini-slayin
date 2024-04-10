import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.model.GameObject;
import slayin.model.GameStatus;
import slayin.model.character.Knight;

public class TestGameStatus {

    GameStatus status;
    
    @BeforeEach
    void setUp(){
        status = new GameStatus();
    }

    @Test
    void testAddRemoveEnemy(){
        GameObject tmp = new Knight(null, null, null);

        assertEquals(status.getObjects().size(), 1);    // the status contains the main character only
        assertFalse(status.getObjects().contains(tmp));
        status.addEnemy(tmp);
        assertTrue(status.getObjects().contains(tmp));

        assertEquals(status.getObjects().size(), 2);
        status.removeEnemy(tmp);
        assertFalse(status.getObjects().contains(tmp));
        assertEquals(status.getObjects().size(), 1);    // the status contains the main character only
    }
}
