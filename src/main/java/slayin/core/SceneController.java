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
import slayin.model.utility.Globals;
import slayin.model.utility.SceneType;
import slayin.views.CharacterSelectionScene;
import slayin.views.GameLevelScene;
import slayin.views.GameOverScene;
import slayin.views.GameScene;
import slayin.views.MainMenuScene;
import slayin.views.OptionMenuScene;
import slayin.views.PauseMenuScene;
import slayin.views.SimpleGameScene;

public class SceneController {
    private JFrame gameFrame;
    private Optional<SimpleGameScene> activeScene;
    private GameEventListener eventListener;
    private InputController inputController;
    private GameStatus gameStatus;

    public SceneController(GameEventListener eventListener, InputController inputController) {
        this.activeScene = Optional.empty();
        this.eventListener = eventListener;
        this.inputController = inputController;
    }

    public void createWindow() {
        this.gameFrame = new JFrame("Slayin");
        this.gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventListener.addEvent(new QuitGameEvent());
            }
        });

        this.gameFrame.setPreferredSize(new Dimension(Globals.RESOLUTION.getWidth(), Globals.RESOLUTION.getHeight()));
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

    public void switchScene(SceneType sceneType) {
        SimpleGameScene newScene = null;
        this.gameFrame.removeKeyListener(inputController);

        switch (sceneType) {
            case MAIN_MENU:
                newScene = new MainMenuScene(eventListener);
                break;
            case GAME_LEVEL:
                newScene = new GameLevelScene(gameStatus);
                this.gameFrame.addKeyListener(inputController);
                this.gameFrame.requestFocusInWindow();
                break;
            case PAUSE_MENU:
                newScene = new PauseMenuScene(eventListener, gameStatus);
                break;
            case GAME_OVER:
                newScene = new GameOverScene(eventListener, gameStatus);
                break;
            case OPTION_MENU:
                newScene = new OptionMenuScene(eventListener);
                break;
            case CHARACTER_SELECTION:
                newScene = new CharacterSelectionScene(eventListener);
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
        if (activeScene.isEmpty())
            return;

        if (activeScene.get() instanceof GameScene)
            ((GameScene) activeScene.get()).drawGraphics();
    }

    public void showGameScene(GameStatus gameStatus) {
        this.switchScene(SceneType.GAME_LEVEL, gameStatus);
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

    public Optional<SimpleGameScene> getActiveScene() {
        return this.activeScene;
    }

    public void updateResolution() {
        this.gameFrame.setSize(Globals.RESOLUTION.getWidth(), Globals.RESOLUTION.getHeight());
    }
}
