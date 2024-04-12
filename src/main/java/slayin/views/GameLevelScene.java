package slayin.views;

import java.awt.Container;

import slayin.core.GameScene;
import slayin.model.entities.GameObject;
import slayin.model.events.GameEventListener;
import slayin.model.utility.SceneType;
import slayin.views.utils.JEntityGraphic;

public class GameLevelScene implements GameScene {
    private GameEventListener eventListener;
    private JEntityGraphic entity;

    public GameLevelScene(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public Container getContent() {
        entity = new JEntityGraphic();        
        return entity;
    }

    @Override
    public void drawGraphics(GameObject mainCharacter) {
        entity.updatePos(mainCharacter.getPos());
        entity.repaint();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.GAME_LEVEL;
    }
}
