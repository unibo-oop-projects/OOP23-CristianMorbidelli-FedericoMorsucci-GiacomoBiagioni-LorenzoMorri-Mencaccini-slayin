package slayin.model.utility;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Temporary utility class to manage the assets of the game.
 */
public class AssetsManager {

    /**
     * Loads an image from the given path.
     * 
     * @param path The path of the image to load.
     * @return The loaded image or null if an error occurred.
     */
    public static Image loadImage(String path) {
        Image image = null;

        try {
            URL imageUrl = AssetsManager.class.getResource(path);
            image = ImageIO.read(new File(imageUrl.toURI()));
        } catch (URISyntaxException | IOException e) {
            System.out.println("Impossibile caricare l'immagine di sfondo\n> " + e.getMessage());
        }

        return image;
    }
}
