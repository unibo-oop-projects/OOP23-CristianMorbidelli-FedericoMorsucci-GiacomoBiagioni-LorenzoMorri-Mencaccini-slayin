package slayin.model.events;

import java.util.ArrayList;
import java.util.List;

public class GameEventListener {
    private final List<GameEvent> events;
    private final List<GameEvent> buffer;

    public GameEventListener() {
        this.events = new ArrayList<>();
        this.buffer = new ArrayList<>();
    }

    public void addEvent(GameEvent event) {
        buffer.add(event);
    }

    /** A getter method for the {@code events} attribute. Before returning the list,
     * the temporary buffer is merged to the main list.
     * 
     * @return the full list of events
     */
    public List<GameEvent> getEvents() {
        events.addAll(buffer);
        buffer.clear();

        return events;
    }

    public void clearEvents() {
        events.clear();
    }
}
