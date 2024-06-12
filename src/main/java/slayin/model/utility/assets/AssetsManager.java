package slayin.model.utility.assets;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Utility class to load and manage the assets of the game.
 */
public class AssetsManager {
    private HashMap<Asset, Image> imageAssets;

    public AssetsManager() {
        imageAssets = new HashMap<>();
    }

    /**
     * Loads all the assets of the game that are defined inside the enum Asset.
     * {@link slayin.model.utility.assets.Asset}.
     */
    public void loadAssets() {
        long assetsTimeStart = System.currentTimeMillis();
        System.out.println("[AssetsManager] Loading assets...");
        for (Asset asset : Asset.values()) {
            switch (asset.getAssetType()) {
                case IMAGE:
                    this.loadAssetImage(asset);
                    System.out.println("[AssetsManager] Loaded asset: " + asset.name() + " (" + asset.getPath() + ")");       
                    break;
                default:
                    break;
            }
        }
        System.out.println("[AssetsManager] Assets loaded successfully! (" + (System.currentTimeMillis() - assetsTimeStart) + "ms)");
    }

    /**
     * Returns the image asset associated with the given asset.
     * 
     * @param asset The asset to get the image from.
     * @return The image asset or null if the asset doesn't exists.
     */
    public Image getImageAsset(Asset asset) {
        return imageAssets.get(asset);
    }

    /**
     * Loads an image from the given path.
     * 
     * @param path The path of the image to load.
     * @return The loaded image or null if an error occurred.
     */
    private Image loadAssetImage(Asset asset) {
        try {
            URL imageUrl = AssetsManager.class.getResource(asset.getPath());
            Image image = ImageIO.read(new File(imageUrl.toURI()));
            imageAssets.put(asset, image);

            return image;
        } catch (URISyntaxException | IOException e) {
            System.out.println("Impossibile caricare l'immagine di sfondo\n> " + e.getMessage());
        }

        return null;
    }
}
