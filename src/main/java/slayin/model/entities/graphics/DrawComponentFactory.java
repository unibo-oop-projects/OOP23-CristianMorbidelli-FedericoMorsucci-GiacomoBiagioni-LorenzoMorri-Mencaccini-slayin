package slayin.model.entities.graphics;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.MeleeWeapon;
import slayin.model.entities.GameObject.Direction;
import slayin.model.score.GameScore;
import slayin.model.utility.ImageUtility;
import slayin.model.utility.Pair;

import java.awt.Image;
import java.awt.image.BufferedImage;
import slayin.model.entities.Dummy;
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
            try {
                URL pathCharacter,pathWeapon;
                BufferedImage imgCharacter;
                List<BufferedImage> imgWeapons = new ArrayList<>();
                List<MeleeWeapon> weapons = character.getWeapons();
                pathCharacter = DrawComponentFactory.class.getResource("/assets/character/" + character.getClass().getSimpleName() + FORMAT_SPRITE);
                for( var weapon : weapons){
                    pathWeapon = DrawComponentFactory.class.getResource("/assets/character/" + weapon.getName() + character.getClass().getSimpleName() + FORMAT_SPRITE);
                    imgWeapons.add((BufferedImage) ImageIO.read(new File(pathWeapon.toURI())));
                }
                imgCharacter = (BufferedImage) ImageIO.read(new File(pathCharacter.toURI()));
                // se la direzione è a sinistra ruoto l'immagine
                if (character.getDir() == Direction.LEFT){
                    imgCharacter= ImageUtility.flipImage(imgCharacter);
                    imgWeapons = imgWeapons.stream().map(w->ImageUtility.flipImage(w)).toList();
                }
                // disegno il personaggio
                BoundingBoxImplRet bBoxCharacter =(BoundingBoxImplRet)character.getBoundingBox();
                g.drawImage(imgCharacter, (int) bBoxCharacter.getX(), (int) bBoxCharacter.getY(),(int)bBoxCharacter.getWidth(),(int)bBoxCharacter.getHeight(), null);
                // disegno le armi
                var finlImgWeapons = imgWeapons;
                IntStream.range(0, imgWeapons.size()).mapToObj(i -> new Pair<>(weapons.get(i), finlImgWeapons.get(i))).forEach(t->{
                    if(t.get1().getBoxWeapon() instanceof BoundingBoxImplRet){
                        BoundingBoxImplRet bBox= (BoundingBoxImplRet) t.get1().getBoxWeapon();
                        g.drawImage(t.get2(), (int)bBox.getX(), (int) bBox.getY(),(int)bBox.getWidth(),(int)bBox.getHeight(), null);
                    }
                    //TODO: agigungi diesgni per bounding box circolari
                });
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
