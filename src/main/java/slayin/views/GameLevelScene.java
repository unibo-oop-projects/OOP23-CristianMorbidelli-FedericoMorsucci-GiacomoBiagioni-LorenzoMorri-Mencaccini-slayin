package slayin.views;

import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JPanel;

import slayin.core.GameScene;
import slayin.model.GameStatus;
import slayin.model.entities.graphics.DrawComponentFactory;
import slayin.model.utility.SceneType;
import slayin.model.utility.assets.AssetsManager;

public class GameLevelScene implements GameScene {
    private GameStatus gameStatus;
    private AssetsManager assetsManager;
    private JPanel gameViewPanel;

    public GameLevelScene(AssetsManager assetsManager, GameStatus gameStatus) {
        this.assetsManager = assetsManager;
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
        gameStatus.getWorld().getDrawComponent(this.assetsManager).draw(g);
        gameStatus.getScoreManager().getDrawComponent().draw(g);
        
        // TODO: sostituire DrawComponentFactory con la funzione relativa della classe health
        DrawComponentFactory.graphicsComponentHealth(this.assetsManager, gameStatus.getCharacter()).draw(g);
        gameStatus.getObjects().forEach(e ->{
            e.getDrawComponent().draw(g);
        } );
    }
}
