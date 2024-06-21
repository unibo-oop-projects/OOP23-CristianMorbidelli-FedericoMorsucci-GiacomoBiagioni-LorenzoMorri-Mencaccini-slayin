package menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import slayin.core.SceneController;
import slayin.core.SimpleGameScene;
import slayin.model.GameStatus;
import slayin.model.movement.InputController;
import slayin.model.utility.SceneType;

public class TestPauseMenu {
    SceneController sceneController;
    GameStatus status;

    @BeforeEach
    void setUp() {
        status = new GameStatus(null);
        InputController inputController = new InputController(null);
        sceneController = new SceneController(null, inputController);
    }

    @Test
    void testPauseMenu() {
        sceneController.createWindow();

        sceneController.switchScene(SceneType.MAIN_MENU);
        Optional<SimpleGameScene> curScene = sceneController.getActiveScene();

        assertTrue(curScene.isPresent());
        SimpleGameScene scene = curScene.get();
        assertEquals(scene.getSceneType(), SceneType.MAIN_MENU);

        sceneController.showGameScene(status);
        curScene = sceneController.getActiveScene();
        assertTrue(curScene.isPresent());
        assertEquals(curScene.get().getSceneType(), SceneType.GAME_LEVEL);

        sceneController.setPauseMenuOpen(true);
        curScene = sceneController.getActiveScene();
        assertTrue(curScene.isPresent());
        assertEquals(curScene.get().getSceneType(), SceneType.PAUSE_MENU);

        sceneController.setPauseMenuOpen(false);
        curScene = sceneController.getActiveScene();
        assertTrue(curScene.isPresent());
        assertEquals(curScene.get().getSceneType(), SceneType.GAME_LEVEL);

        sceneController.closeWindow();
    }
}
