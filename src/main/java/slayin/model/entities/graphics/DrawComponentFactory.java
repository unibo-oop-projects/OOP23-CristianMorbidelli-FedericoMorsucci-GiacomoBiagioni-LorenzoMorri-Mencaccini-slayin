package slayin.model.entities.graphics;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.character.Character;
import slayin.model.entities.Dummy;
import slayin.model.entities.GameObject.Direction;
import slayin.model.score.GameScore;
import slayin.model.utility.AssetsManager;
import slayin.model.utility.Constants;

/**
 * A class that generates the DrawComponent to draw objects.
 */
public class DrawComponentFactory {

    private final static String FORMAT_SPRITE = ".png";

    /**
     * constructs a drawcomponent to draw a character
     * 
     * @param character - character to draw
     * @return DrawComponent that draws the character passed by parameter
     */
    public static DrawComponent graphicsComponentCharacter(Character character) {
        return (g) -> {
            URL pathKnight;
            Image img;
            // cerco la x più piccola per disegnare correttaemente il personaggio
            double minX;
            if (character.getDir() == Direction.LEFT) {
                pathKnight = DrawComponentFactory.class.getResource(
                        "/assets/character/" + character.getClass().getSimpleName() + "Left" + FORMAT_SPRITE);
            } else {
                pathKnight = DrawComponentFactory.class.getResource(
                        "/assets/character/" + character.getClass().getSimpleName() + "Right" + FORMAT_SPRITE);
            }
            try {
                img = ImageIO.read(new File(pathKnight.toURI()));
                BoundingBoxImplRet boxCharacter = (BoundingBoxImplRet) character.getBoundingBox();
                minX = boxCharacter.getX();
                // confronto la x della boxCharacter con quella delle armi se quella delle armi
                // e minore cambio valore di minX
                for (var t : character.getWeapons()) {
                    if (t.getBoxWeapon() instanceof BoundingBoxImplRet) {
                        BoundingBoxImplRet bBox = (BoundingBoxImplRet) t.getBoxWeapon();
                        if (bBox.getX() < minX)
                            minX = bBox.getX();
                    } else if (t.getBoxWeapon() instanceof BoundingBoxImplCirc) {
                        BoundingBoxImplCirc bBox = (BoundingBoxImplCirc) t.getBoxWeapon();
                        // TODO: cambiare getx() del BoundinBoxImplCirc
                        if (bBox.getX() - bBox.getRadius() < minX)
                            minX = bBox.getX();
                    }
                }
                g.drawImage(img, (int) minX, (int) boxCharacter.getY(), null);
            } catch (URISyntaxException | IOException e) {
                System.out.println("impossibile caricare l'immagine del personaggio");
                e.printStackTrace();
            }
        };

    }

    /**
     * constructs a drawcomponent to draw a bounding box
     * 
     * @param bBox - bounding box to draw
     * @return DrawComponent that draws the boundind box passed by parameter
     */
    public static DrawComponent graphicsComponentBoundigBox(BoundingBox bBox) {
        return (g) -> {
            if (bBox instanceof BoundingBoxImplRet) {
                BoundingBoxImplRet newBBox = (BoundingBoxImplRet) bBox;
                g.drawRect((int) newBBox.getX(), (int) newBBox.getY(), (int) newBBox.getWidth(),
                        (int) newBBox.getHeight());
            } else if (bBox instanceof BoundingBoxImplCirc) {
                // TODO: da testare che sia giusto
                BoundingBoxImplCirc newBBox = (BoundingBoxImplCirc) bBox;
                g.drawOval((int) newBBox.getPoint().getX(), (int) newBBox.getPoint().getY(), (int) newBBox.getRadius(),
                        (int) newBBox.getRadius());
            }
        };

    }

    /**
     * Build a DrawComponent to draw the score and the combo factor
     * 
     * @param scoreManager - the score manager that holds the data to draw
     * @return DrawComponent that draws the score and the combo factor
     */
    public static DrawComponent graphicsComponentScore(GameScore scoreManager) {
        return (g) -> {
            g.drawString("Score: " + scoreManager.getScore(), 10, 20);

            if (scoreManager.getComboFactor() == 0 || scoreManager.getRemainingTime() <= 0)
                g.drawString("No Combo", 10, 40);
            else {
                String remainingSeconds = String.format("%.1f", scoreManager.getRemainingTime() / 1000.0f);
                g.drawString("Combo: +" + scoreManager.getComboFactor() + " (Time: " + remainingSeconds + "s)", 10, 40);
            }
        };
    }

    public static DrawComponent graphicsComponentHealth(Character knight) {
        return (g) -> {
            g.drawString("Health: " + knight.getLife(), 10, 60);
        };
    }

    public static DrawComponent graphicsComponentWorld(World w) {
        Image background = AssetsManager.loadImage("/assets/backgrounds/game_bg.jpg");
        return (g) -> {
            g.drawImage(background, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, null);
        };
    }

    public static DrawComponent graphicsComponentDummy(Dummy dummy){
        return (g) -> {
            URL pathDummy = DrawComponentFactory.class.getResource("/assets/entities/dummy.png");
            try {
                BoundingBoxImplRet entity = (BoundingBoxImplRet) dummy.getBoundingBox();
                Image img = ImageIO.read(new File(pathDummy.toURI())).getScaledInstance((int) entity.getWidth(), (int) entity.getHeight(), Image.SCALE_DEFAULT);
                g.drawImage(img, (int) entity.getX(), (int) entity.getY(), null);
            } catch (Exception e) {
                System.out.println("Unable to locate Dummy image from resources");
                e.printStackTrace();
            }
        };
    }
}
