import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.core.SceneController;
import slayin.model.GameStatus;
import slayin.model.movement.InputController;
import slayin.model.utility.SceneType;

public class TestScore {
    SceneController sceneController;
    GameStatus gameStatus;

    @BeforeEach
    void setUp() {
        gameStatus = new GameStatus(null);

        InputController inputController = new InputController(null);
        sceneController = new SceneController(null, inputController, null);
        sceneController.createWindow();
    }

    @Test
    void testPauseMenu() {
        sceneController.showGameScene(gameStatus);        

        var curScene = sceneController.getActiveScene();
        assertTrue(curScene.isPresent());
        assertEquals(curScene.get().getSceneType(), SceneType.GAME_LEVEL);

        gameStatus.getScoreManager().increaseScore(10);
        assertEquals(gameStatus.getScoreManager().getScore(), 10);
        
        gameStatus.getScoreManager().increaseScore(5);
        assertNotEquals(gameStatus.getScoreManager().getScore(), 15);
        assertEquals(gameStatus.getScoreManager().getScore(), 16);

        gameStatus.getScoreManager().increaseScore(2);
        assertNotEquals(gameStatus.getScoreManager().getScore(), 18);
        assertEquals(gameStatus.getScoreManager().getScore(), 20);
    }

    @AfterEach
    void close() {
        sceneController.closeWindow();
        sceneController = null;
    }
}

