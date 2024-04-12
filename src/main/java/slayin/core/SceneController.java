package slayin.core;

import java.awt.Dimension;
import java.util.Optional;

import javax.swing.JFrame;

import slayin.model.entities.GameObject;
import slayin.model.events.GameEventListener;
import slayin.model.movement.InputController;
import slayin.model.utility.SceneType;
import slayin.views.GameLevelScene;
import slayin.views.MainMenuScene;

public class SceneController {
    private JFrame gameFrame;
    private Optional<GameScene> currentScene;
    private GameEventListener eventListener;
    private InputController inputController;

    public SceneController(GameEventListener eventListener, InputController inputController) {
        this.currentScene = Optional.empty();
        this.eventListener = eventListener;
        this.inputController = inputController;
    }

    public void createWindow() {
        this.gameFrame = new JFrame("Slayin");
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gameFrame.setPreferredSize(new Dimension(1280, 720));
        this.gameFrame.setResizable(false);
        this.gameFrame.pack();
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
    }

    public void closeWindow() {
        this.gameFrame.setVisible(false);
        this.gameFrame.dispose();
    }

    public void switchScene(SceneType sceneType) {
        GameScene menu = null;
        switch (sceneType) {
            case MAIN_MENU:
                menu = new MainMenuScene(eventListener);
                break;
            case GAME_LEVEL:
                menu = new GameLevelScene(eventListener);
                this.gameFrame.addKeyListener(inputController);
                this.gameFrame.requestFocusInWindow();
                break;
            default:
                break;
        }

        this.gameFrame.setContentPane(menu.getContent());
        currentScene = Optional.of(menu);
        this.updateScene();
    }

    private void updateScene() {
        this.gameFrame.revalidate();
        this.gameFrame.repaint();
    }

    public void render(GameObject o) {
        if (currentScene.isPresent()) {
            currentScene.get().drawGraphics(o);
        }   
    }

    public boolean isInMenu() {
        if (currentScene.isEmpty())
            return false;

        return currentScene.get().getSceneType().isMenu();
    }
}
