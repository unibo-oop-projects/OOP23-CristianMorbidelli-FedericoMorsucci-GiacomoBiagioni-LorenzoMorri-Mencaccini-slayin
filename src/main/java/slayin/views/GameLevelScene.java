package slayin.views;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import slayin.core.GameScene;
import slayin.model.GameStatus;
import slayin.model.utility.SceneType;

public class GameLevelScene extends JPanel implements GameScene {
    private GameStatus gameStatus;

    public GameLevelScene(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public Container getContent() {
        return this;
    }

    @Override
    public void drawGraphics() {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameStatus.getObjects().forEach(e -> {
            e.getDrawComponent().draw(g);
        });
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME_LEVEL;
    }
}
