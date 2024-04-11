package slayin.core;

import java.awt.Dimension;
import java.util.Optional;

import javax.swing.JFrame;

import slayin.model.utility.SceneType;

public class SceneController {
    private Optional<GameScene> currentScene;

    private JFrame gameFrame;

    public SceneController() {
        this.currentScene = Optional.empty();
    }

    public void createSceneWindow() {
        this.gameFrame = new JFrame("Slayin");
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gameFrame.setPreferredSize(new Dimension(1280, 720));
        this.gameFrame.setResizable(false);
        this.gameFrame.pack();
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
    }

    public void switchScene(GameScene menu) {
        currentScene = Optional.of(menu);
        this.gameFrame.setContentPane(menu.getContent());
        this.updateScene();
    }

    public void updateScene() {
        this.gameFrame.revalidate();
        this.gameFrame.repaint();
    }

    public boolean isInMenu() {
        if (currentScene.isEmpty())
            return false;

        return currentScene.get().getSceneType() == SceneType.MAIN_MENU
                || currentScene.get().getSceneType() == SceneType.PAUSE_MENU;
    }
}
