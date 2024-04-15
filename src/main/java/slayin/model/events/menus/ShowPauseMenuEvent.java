    package slayin.model.events.menus;

    import slayin.model.events.GameEvent;

public class ShowPauseMenuEvent implements GameEvent {
    private boolean showPauseMenu;

    public ShowPauseMenuEvent(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    public boolean shouldShowPauseMenu() {
        return this.showPauseMenu;
    }
}