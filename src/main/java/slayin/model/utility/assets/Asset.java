package slayin.model.utility.assets;

/**
 * Enum class that contains all static and heavy assets used in the game
 * with the respective path and type.
 */
public enum Asset {
    MAIN_MENU_BG(AssetType.IMAGE, "slayin/assets/backgrounds/mainmenu_bg.jpg"),
    GAME_BG(AssetType.IMAGE, "slayin/assets/backgrounds/game_bg.jpg"),

    // UI Components
    LIFE_HEART(AssetType.IMAGE, "slayin/assets/heart.png"); //Paths.get("slayin", "assets", "heart.png").toString());

    private String path;
    private AssetType assetType;

    private Asset(AssetType assetType, String path) {
        this.assetType = assetType;
        this.path = path;
    }

    /**
     * Returns the type of the asset.
     * 
     * @return the type of the asset
     */
    public AssetType getAssetType() {
        return assetType;
    }

    /**
     * Returns the path of the asset used to load the resource.
     * 
     * @return the path of the asset
     */
    public String getPath() {
        return this.path;
    }
}