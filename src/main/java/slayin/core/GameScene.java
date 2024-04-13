package slayin.core;

import java.awt.Container;

import slayin.model.utility.SceneType;

public interface GameScene {
    public Container getContent();
    public void drawGraphics();
    public SceneType getSceneType();
}
