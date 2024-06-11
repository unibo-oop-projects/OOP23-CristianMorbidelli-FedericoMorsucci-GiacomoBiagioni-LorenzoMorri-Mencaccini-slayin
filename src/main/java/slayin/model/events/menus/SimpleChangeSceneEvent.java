package slayin.model.events.menus;

import slayin.model.events.GameEvent;
import slayin.model.utility.SceneType;

/**
 * Event to change the scene to a scene that does not require any additional information.
 * (Used to prevent the need to create a new event for each scene change)
 */
public class SimpleChangeSceneEvent implements GameEvent {
    private SceneType sceneType;

    public SimpleChangeSceneEvent(SceneType sceneType) {
        this.sceneType = sceneType;
    }

    public SceneType getSceneType() {
        return sceneType;
    }
}
