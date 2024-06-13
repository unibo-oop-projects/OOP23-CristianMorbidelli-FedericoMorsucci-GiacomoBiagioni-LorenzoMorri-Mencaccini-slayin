package slayin.model.utility;

public enum SceneType {
    MAIN_MENU(true),
    PAUSE_MENU(true),
    OPTION_MENU(true),
    GAME_LEVEL(false),
    GAME_OVER(true);

    private boolean isMenu;

    private SceneType(boolean isMenu) {
        this.isMenu = isMenu;
    }

    public boolean isMenu() {
        return isMenu;
    }
}
