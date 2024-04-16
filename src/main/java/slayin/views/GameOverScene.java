package slayin.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import slayin.core.GameScene;
import slayin.model.GameStatus;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.StartGameEvent;
import slayin.model.utility.Constants;
import slayin.model.utility.SceneType;
import slayin.views.components.SlayinButton;
import slayin.views.components.SlayinCenteredPanel;
import slayin.views.components.SlayinLabel;

public class GameOverScene implements GameScene {
    private GameEventListener eventListener;
    private GameStatus gameStatus;

    public GameOverScene(GameEventListener eventListener, GameStatus gameStatus) {
        this.eventListener = eventListener;
        this.gameStatus = gameStatus;
    }

    @Override
    public Container getContent() {
        SlayinLabel gameOverLabel = new SlayinLabel("Game Over", 80f);
        SlayinLabel scoreLabel = new SlayinLabel("Score: " + gameStatus.getScoreManager().getScore(), 50f);
        SlayinButton restartButton = new SlayinButton("Restart", () -> eventListener.addEvent(new StartGameEvent()));

        SlayinCenteredPanel panel = new SlayinCenteredPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                drawGameSnapshot(g2d);
                g2d.dispose();

                super.paintComponent(g);
            }
        };
        
        panel.addComponents(gameOverLabel, scoreLabel, restartButton);

        return panel;
    }

    @Override
    public void drawGraphics() {}

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME_OVER;
    }

    @Override
    public boolean shouldRevalidate() {
        return false;
    }
    
    private void drawGameSnapshot(Graphics2D g2d) {
        // Preparing
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(java.awt.AlphaComposite.SrcOver.derive(0.2f));
        g2d.setPaint(Color.GRAY);

        // Drawing the terrain
        gameStatus.getWorld().getDrawComponent().draw(g2d);

        // Drawing the entities
        gameStatus.getObjects().forEach(e -> e.getDrawComponent().draw(g2d));

        // Drawing the gray layer over the background
        g2d.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }
}
