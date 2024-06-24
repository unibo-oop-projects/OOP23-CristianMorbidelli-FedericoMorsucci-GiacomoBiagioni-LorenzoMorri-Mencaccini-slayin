package slayin.views;

import java.awt.Container;

import slayin.model.utility.SceneType;

/**
 * Interface that defines the methods that a simple scene must implement.
 */
public interface SimpleGameScene {
    /**
     * Returns a container with all the components of a scene.
     * 
     * @return The container of the scene.
     */
    public Container getContent();

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
