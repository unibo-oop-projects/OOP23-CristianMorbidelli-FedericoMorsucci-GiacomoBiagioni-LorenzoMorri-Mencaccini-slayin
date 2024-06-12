package slayin.core;

/**
 * Extension of the SimpleGameScene interface that adds the drawGraphics method to draw the graphics of the scene.
 */
public interface GameScene extends SimpleGameScene {
    /**
     * This method is called inside the engine and draws the graphics of the scene.
     */
    public void drawGraphics();
}