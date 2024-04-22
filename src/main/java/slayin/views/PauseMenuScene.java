package slayin.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import slayin.core.GameScene;
import slayin.model.GameStatus;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.events.GameEventListener;
import slayin.model.events.menus.QuitGameEvent;
import slayin.model.events.menus.ShowPauseMenuEvent;
import slayin.model.utility.Constants;
import slayin.model.utility.SceneType;
import slayin.model.utility.assets.AssetsManager;
import slayin.views.components.SlayinButton;
import slayin.views.components.SlayinCenteredPanel;
import slayin.views.components.SlayinLabel;

public class PauseMenuScene implements GameScene {
    private GameEventListener eventListener;
    private AssetsManager assetsManager;
    private GameStatus gameStatus;

    public PauseMenuScene(GameEventListener eventListener, AssetsManager assetsManager, GameStatus gameStatus) {
        this.eventListener = eventListener;
        this.assetsManager = assetsManager;
        this.gameStatus = gameStatus;
    }

    @Override
    public Container getContent() {
        SlayinLabel title = new SlayinLabel("In Pausa", true);
        SlayinButton resumeButton = new SlayinButton("Riprendi", () -> eventListener.addEvent(new ShowPauseMenuEvent(false)));
        SlayinButton quitButton = new SlayinButton("Esci", () -> eventListener.addEvent(new QuitGameEvent()));

        SlayinCenteredPanel menu = new SlayinCenteredPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                drawGameSnapshot(g2d);
                g2d.dispose();

                super.paintComponent(g);
            }

        };
        menu.addComponents(title, resumeButton, quitButton);

        return menu;
    }

    @Override
    public void drawGraphics() {}

    @Override
    public SceneType getSceneType() {
        return SceneType.PAUSE_MENU;
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
        gameStatus.getWorld().getDrawComponent(this.assetsManager).draw(g2d);

        // Drawing the score and the health
        gameStatus.getScoreManager().getDrawComponent().draw(g2d);
        // TODO: sostituire DrawComponentFactory con la funzione relativa della classe health
        DrawComponentFactory.graphicsComponentHealth(this.assetsManager, gameStatus.getCharacter()).draw(g2d);

        // Drawing the entities
        gameStatus.getObjects().forEach(e -> e.getDrawComponent().draw(g2d));

        // Drawing the gray layer over the background
        g2d.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }
}
