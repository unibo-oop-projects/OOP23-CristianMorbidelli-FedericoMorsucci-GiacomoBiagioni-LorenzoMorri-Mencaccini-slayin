package slayin.model.events.menus;

import slayin.model.entities.character.PlayableCharacter;
import slayin.model.events.GameEvent;

public class StartGameEvent implements GameEvent {
    private PlayableCharacter character;

    public StartGameEvent(PlayableCharacter character) {
        this.character = character;
    }

    public PlayableCharacter getPlayableCharacter() {
        return character;
    }
}
