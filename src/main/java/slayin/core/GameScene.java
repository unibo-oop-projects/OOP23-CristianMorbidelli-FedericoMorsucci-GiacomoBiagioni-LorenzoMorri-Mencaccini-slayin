package slayin.core;

import java.awt.Container;

import slayin.model.utility.SceneType;

/**
 * Interface that defines the methods that a scene must implement.
 */
public interface GameScene {
    /**
     * Returns a container with all the components of a scene.
     * 
     * @return The container of the scene.
     */
    public Container getContent();

    /**
     * This method is called inside the engine and draws the graphics of the scene.
     */
    public void drawGraphics();

    /**
     * Returns the type of the scene to understand if it is a menu or a game scene.
     * 
     * @return The type of the scene.
     */
    public SceneType getSceneType();

    /**
     * This method declare if after the scene change a revalidation is needed.
     * 
     * @return Whether the scene should be revalidated or not.
     */
    public boolean shouldRevalidate();
}
