package slayin.model.utility.assets;

/**
 * Enum class that contains all the assets used in the game
 * with the respective path and type.
 */
public enum Asset {
    MAIN_MENU_BG(AssetType.IMAGE, "/assets/backgrounds/mainmenu_bg.jpg"),
    GAME_BG(AssetType.IMAGE, "/assets/backgrounds/game_bg.jpg"),
    
    // UI Components
    LIFE_HEART(AssetType.IMAGE, "/assets/heart.png"),
    
    // Characters
    DUMMY_ENEMY(AssetType.IMAGE, "/assets/entities/dummy.png");

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