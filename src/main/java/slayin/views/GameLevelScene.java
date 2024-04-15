package slayin.views;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import slayin.core.GameScene;
import slayin.model.GameStatus;
import slayin.model.utility.Constants;
import slayin.model.utility.SceneType;

public class GameLevelScene implements GameScene {
    private GameStatus gameStatus;
    private JPanel gameViewPanel;

    public GameLevelScene(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public Container getContent() {
        this.gameViewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);
            }
        };

        return this.gameViewPanel;
    }

    @Override
    public void drawGraphics() {
        this.gameViewPanel.repaint();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME_LEVEL;
    }

    private void drawGame(Graphics g) {
        g.drawLine(0,365,Constants.WINDOW_WIDTH,365);
        gameStatus.getObjects().forEach(e -> e.getDrawComponent().draw(g));
    }
}
