package slayin.model.utility.assets;

import java.nio.file.Paths;

/**
 * Enum class that contains all the assets used in the game
 * with the respective path and type.
 */
public enum Asset {
    MAIN_MENU_BG(AssetType.IMAGE, Paths.get("assets","backgrounds","mainmenu_bg.jpg").toString()),
    GAME_BG(AssetType.IMAGE, Paths.get("assets","backgrounds","game_bg.jpg").toString()),
    
    // UI Components
    LIFE_HEART(AssetType.IMAGE, Paths.get("assets","heart.png").toString()),
    
    // Characters
    DUMMY_ENEMY(AssetType.IMAGE, Paths.get("assets","entities","dummy.png").toString());

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