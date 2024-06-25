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

    /**
     * Creates the main window of the game.
     */
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

    /**
     * Closes the main window of the game.
     */
    public void closeWindow() {
        this.gameFrame.setVisible(false);
        this.gameFrame.dispose();
    }

    /**
     * Switches the scene to the specified one.
     * 
     * @param sceneType the type of the scene to switch to
     * @param gameStatus the game status to pass to the new scene
     */
    private void switchScene(SceneType sceneType, GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.switchScene(sceneType);
    }

    /**
     * Switches the scene to the specified one.
     * 
     * @param sceneType the type of the scene to switch to
     */
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

    /**
     * Renders the entities in the active scene.
     */
    public void renderEntitiesInScene() {
        if (activeScene.isEmpty())
            return;

        if (activeScene.get() instanceof GameScene)
            ((GameScene) activeScene.get()).drawGraphics();
    }

    /**
     * Shows the main menu scene.
     * 
     * @param gameStatus the game status to pass to the new scene
     */
    public void showGameScene(GameStatus gameStatus) {
        this.switchScene(SceneType.GAME_LEVEL, gameStatus);
    }

    /**
     * Shows pause menu scene.
     */
    public void setPauseMenuOpen(boolean inMenu) {
        if (inMenu)
            this.switchScene(SceneType.PAUSE_MENU);
        else
            this.switchScene(SceneType.GAME_LEVEL);
    }

    /**
     * Checks if the active scene is a menu.
     */
    public boolean isInMenu() {
        if (activeScene.isEmpty())
            return false;

        return activeScene.get().getSceneType().isMenu();
    }

    /**
     * Returns the active scene.
     */
    public Optional<SimpleGameScene> getActiveScene() {
        return this.activeScene;
    }

    /**
     * Changes the resolution of the game window.
     */
    public void updateResolution() {
        this.gameFrame.setSize(Globals.RESOLUTION.getWidth(), Globals.RESOLUTION.getHeight());
    }
}
