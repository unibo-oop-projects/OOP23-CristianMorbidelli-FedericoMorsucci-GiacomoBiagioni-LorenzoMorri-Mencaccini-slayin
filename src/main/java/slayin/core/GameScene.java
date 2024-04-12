package slayin.core;

import java.awt.Container;

import slayin.model.entities.GameObject;
import slayin.model.utility.SceneType;

public interface GameScene {
    public Container getContent();
    public void drawGraphics(GameObject mainCharacter);
    public SceneType getSceneType();
}
