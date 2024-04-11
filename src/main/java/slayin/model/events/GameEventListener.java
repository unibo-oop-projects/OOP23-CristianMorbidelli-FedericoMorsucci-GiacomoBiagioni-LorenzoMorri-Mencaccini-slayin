package slayin.model.events;

import java.util.ArrayList;
import java.util.List;

public class GameEventListener {
    private final List<GameEvent> events;

    public GameEventListener() {
        this.events = new ArrayList<>();
    }

    public void addEvent(GameEvent event) {
        events.add(event);
    }

    public List<GameEvent> getEvents() {
        return events;
    }

    public void clearEvents() {
        events.clear();
    }
}
