package slayin.model.utility;

/**
 * Represents the different types of scenes in the game.
 */
public enum SceneType {
    // Game Scene
    GAME_LEVEL(false),

    // Menus
    MAIN_MENU(true),
    PAUSE_MENU(true),
    OPTION_MENU(true),
    CHARACTER_SELECTION(true),
    GAME_OVER(true);

    private boolean isMenu;

    private SceneType(boolean isMenu) {
        this.isMenu = isMenu;
    }

    /**
     * Checks if the scene is a menu.
     *
     * @return true if the scene is a menu, false otherwise
     */
    public boolean isMenu() {
        return isMenu;
    }
}
