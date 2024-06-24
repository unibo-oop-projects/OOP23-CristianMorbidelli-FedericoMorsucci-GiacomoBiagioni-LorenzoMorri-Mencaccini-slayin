package slayin.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;

import slayin.model.GameStatus;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.SimpleChangeSceneEvent;
import slayin.model.utility.Globals;
import slayin.model.utility.SceneType;
import slayin.views.components.SlayinButton;
import slayin.views.components.SlayinCenteredPanel;
import slayin.views.components.SlayinLabel;

public class GameOverScene implements SimpleGameScene {
    private GameEventListener eventListener;
    private GameStatus gameStatus;

    public GameOverScene(GameEventListener eventListener, GameStatus gameStatus) {
        this.eventListener = eventListener;
        this.gameStatus = gameStatus;
    }

    @Override
    public Container getContent() {
        SlayinLabel gameOverLabel = new SlayinLabel(gameStatus.getCharacter().isAlive() ? "Hai vinto!" : "Game Over", 80f);
        SlayinLabel scoreLabel = new SlayinLabel("Score: " + gameStatus.getScoreManager().getScore(), 50f);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        SlayinButton restartButton = new SlayinButton("Restart",
                () -> eventListener.addEvent(new SimpleChangeSceneEvent(SceneType.CHARACTER_SELECTION)));
        SlayinButton quitButton = new SlayinButton("Quit", () -> eventListener.addEvent(new QuitGameEvent()));

        SlayinCenteredPanel panel = new SlayinCenteredPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                drawGameSnapshot(g2d);
                g2d.dispose();

                super.paintComponent(g);
            }
        };

        panel.addComponents(gameOverLabel, scoreLabel, restartButton, quitButton);

        return panel;
    }

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
        g2d.fillRect(0, 0, Globals.RESOLUTION.getWidth(), Globals.RESOLUTION.getHeight());
    }
}
