package slayin.views;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import slayin.model.GameStatus;
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

    @Override
    public boolean shouldRevalidate() {
        return true;
    }

    private void drawGame(Graphics g) {
        gameStatus.getWorld().getDrawComponent().draw(g);
        gameStatus.getScoreManager().getDrawComponent().draw(g);

        gameStatus.getCharacter().getLife().getDrawComponent().draw(g);
        gameStatus.getObjects().forEach(e -> e.getDrawComponent().draw(g));
    }
}
