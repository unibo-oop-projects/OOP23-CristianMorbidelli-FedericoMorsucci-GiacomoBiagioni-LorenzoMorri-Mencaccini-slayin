package slayin.model.utility;

/**
 * Enum that represents the possible resolutions of the game
 */
public enum GameResolution {
    RES_1(640, 360),
    RES_2(854, 480),
    DEFAULT(1280, 720);

    private int width;
    private int height;

    GameResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the resolution in a string format
     * 
     * @return the resolution formatted as "width x height"
     */
    public String getShowText() {
        return width + "x" + height;
    }

    /**
     * Returns the width of the resolution
     * 
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the resolution
     * 
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
