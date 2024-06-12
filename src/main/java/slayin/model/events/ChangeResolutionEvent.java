package slayin.model.events;

import slayin.model.utility.GameResolution;

public class ChangeResolutionEvent implements GameEvent {
    private final GameResolution resolution;

    public ChangeResolutionEvent(GameResolution resolution) {
        this.resolution = resolution;
    }

    public GameResolution getResolution() {
        return resolution;
    }
}
