package slayin.core;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

import javax.swing.JFrame;

import slayin.model.GameStatus;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.movement.InputController;
import slayin.model.utility.Constants;
import slayin.model.utility.SceneType;
import slayin.model.utility.assets.AssetsManager;
import slayin.views.GameLevelScene;
import slayin.views.GameOverScene;
import slayin.views.MainMenuScene;
import slayin.views.PauseMenuScene;

public class SceneController {
    private JFrame gameFrame;
    private Optional<GameScene> activeScene;
    private GameEventListener eventListener;
    private InputController inputController;
    private GameStatus gameStatus;
    private AssetsManager assetsManager;

    public SceneController(GameEventListener eventListener, InputController inputController, AssetsManager assetsManager) {
        this.activeScene = Optional.empty();
        this.eventListener = eventListener;
        this.inputController = inputController;
        this.assetsManager = assetsManager;
    }

    public void createWindow() {
        this.gameFrame = new JFrame("Slayin");
        this.gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                eventListener.addEvent(new QuitGameEvent());
            }
        });

        this.gameFrame.setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        this.gameFrame.setResizable(false);
        this.gameFrame.pack();
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
    }

    public void closeWindow() {
        this.gameFrame.setVisible(false);
        this.gameFrame.dispose();
    }

    private void switchScene(SceneType sceneType, GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.switchScene(sceneType);
    }

    private void switchScene(SceneType sceneType) {
        GameScene newScene = null;
        this.gameFrame.removeKeyListener(inputController);

        switch (sceneType) {
            case MAIN_MENU:
                newScene = new MainMenuScene(eventListener, assetsManager);
                break;
            case GAME_LEVEL:
                newScene = new GameLevelScene(assetsManager, gameStatus);
                this.gameFrame.addKeyListener(inputController);
                this.gameFrame.requestFocusInWindow();
                break;
            case PAUSE_MENU:
                newScene = new PauseMenuScene(eventListener, assetsManager, gameStatus);
                break;
            case GAME_OVER:
                newScene = new GameOverScene(eventListener, assetsManager, gameStatus);
                break;
            default:
                break;
        }

        this.gameFrame.getContentPane().removeAll();
        this.gameFrame.setContentPane(newScene.getContent());
        
        if (newScene.shouldRevalidate()) {
            this.gameFrame.revalidate();
        }

        activeScene = Optional.of(newScene);

        this.gameFrame.repaint();
    }

    public void renderEntitiesInScene() {
        if (activeScene.isEmpty()) return;

        activeScene.get().drawGraphics();
    }

    public void showMainMenuScene() {
        this.switchScene(SceneType.MAIN_MENU);
    }

    public void showGameScene(GameStatus gameStatus) {
        this.switchScene(SceneType.GAME_LEVEL, gameStatus);
    }

    public void showGameOverScene() {
        this.switchScene(SceneType.GAME_OVER);
    }

    public void setPauseMenuOpen(boolean inMenu) {
        if (inMenu)
            this.switchScene(SceneType.PAUSE_MENU);
        else
            this.switchScene(SceneType.GAME_LEVEL);
    }

    public boolean isInMenu() {
        if (activeScene.isEmpty())
            return false;

        return activeScene.get().getSceneType().isMenu();
    }

    public Optional<GameScene> getActiveScene() {
        return this.activeScene;
    }
}
