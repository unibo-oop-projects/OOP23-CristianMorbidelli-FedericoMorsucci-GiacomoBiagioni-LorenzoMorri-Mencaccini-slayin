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
import slayin.model.entities.enemies.Fire;
import slayin.model.entities.enemies.Slime;
import slayin.model.entities.GameObject.Direction;
import slayin.model.entities.boss.Minotaur;
import slayin.model.score.GameScore;
import slayin.model.utility.ImageUtility;
import slayin.model.utility.Pair;
import slayin.model.utility.assets.Asset;
import slayin.model.utility.assets.AssetsManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import slayin.model.entities.Dummy;
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

                //recupero le immagini
                pathCharacter = DrawComponentFactory.class.getResource("/assets/character/" + character.getName() + FORMAT_SPRITE);
                for( var weapon : weapons){
                    pathWeapon = DrawComponentFactory.class.getResource("/assets/character/" + weapon.getName() + character.getName() + FORMAT_SPRITE);
                    imgWeapons.add((BufferedImage) ImageIO.read(new File(pathWeapon.toURI())));
                }
                imgCharacter = (BufferedImage) ImageIO.read(new File(pathCharacter.toURI()));

                // se la direzione è a sinistra ruoto l'immagine
                if (character.getDir() == Direction.LEFT){
                    imgCharacter= ImageUtility.flipImage(imgCharacter);
                    imgWeapons = imgWeapons.stream().map(w->ImageUtility.flipImage(w)).toList();
                }

                //controllo se il personaggio ha preso danno da poco in tal caso coloro di rosso il personaggio
                // TODO: nono funziona per tutti i personaggi
                if(character.decLifeIsBlocked()) imgCharacter= tintImage(imgCharacter, Color.red);

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

    public static DrawComponent graphicsComponentSlime(Slime slime){
        return (g) ->{
            try{
                URL pathSlime;
                BufferedImage imgSlime;
                pathSlime = DrawComponentFactory.class.getResource("/assets/entities/enemies/Slime" + FORMAT_SPRITE);
                imgSlime = (BufferedImage) ImageIO.read(new File(pathSlime.toURI()));
                BoundingBoxImplRet bBoxSlime =(BoundingBoxImplRet)slime.getBoundingBox();
                g.drawImage(imgSlime, (int) bBoxSlime.getX(), (int) bBoxSlime.getY(),(int)bBoxSlime.getWidth(),(int)bBoxSlime.getHeight(), null);
            } catch (URISyntaxException | IOException e) {
                System.out.println("impossibile caricare l'immagine dello slime");
                e.printStackTrace();
            }
        };
    }

    public static DrawComponent graphicsComponentFire(Fire fire){
        return (g) ->{
            try{
                URL pathFire;
                BufferedImage imgFire;
                pathFire = DrawComponentFactory.class.getResource("/assets/entities/enemies/fire" + FORMAT_SPRITE);
                imgFire = (BufferedImage) ImageIO.read(new File(pathFire.toURI()));
                if (fire.getDir() == Direction.RIGHT){
                    imgFire= ImageUtility.flipImage(imgFire);
                }
                BoundingBoxImplRet bBoxFire =(BoundingBoxImplRet)fire.getBoundingBox();
                g.drawImage(imgFire, (int) bBoxFire.getX(), (int) bBoxFire.getY(),(int)bBoxFire.getWidth(),(int)bBoxFire.getHeight(), null);
            } catch (URISyntaxException | IOException e) {
                System.out.println("impossibile caricare l'immagine dello slime");
                e.printStackTrace();
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
            resetDrawSettings(g);

            var scoreHeight = 40;
            g.drawString("Score: " + scoreManager.getScore(), 10, scoreHeight);

            var comboHeight = 60;
            if (scoreManager.getComboFactor() == 0 || scoreManager.getRemainingTime() <= 0) {
                g.drawString("No Combo", 10, comboHeight);
                return;
            }

            float remainingTime = scoreManager.getRemainingTime() / 1000f;
            String comboText = "Combo: +" + scoreManager.getComboFactor();
            g.drawString(comboText, 10, comboHeight);

            FontMetrics fm = g.getFontMetrics(g.getFont());    
            float comboTimePercentage = remainingTime / (Constants.COMBO_RESET_TIME / 1000f);
            int pbarHeight = 10;
            int xSpacing = fm.stringWidth(comboText) + 20;

            g.drawRect(xSpacing, comboHeight - pbarHeight, 100, pbarHeight);
            g.setColor(Color.BLUE);                
            g.fillRect(xSpacing, comboHeight - pbarHeight, (int) (comboTimePercentage * 100), pbarHeight);
        };
    }

    public static DrawComponent graphicsComponentHealth(Character knight) {
        return (g) -> {
            resetDrawSettings(g);

            var imageWidth = 25;
            Image hp = AssetsManager.getImageAsset(Asset.LIFE_HEART);
            g.drawImage(hp, 5, 0, imageWidth, 25, null);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 20));
            g.drawString(String.valueOf(knight.getLife().getHealth()), 10 + imageWidth, 20);
        };
    }

    public static DrawComponent graphicsComponentWorld(World w) {
        return (g) -> {
            Image bImage = AssetsManager.getImageAsset(Asset.GAME_BG);
            g.drawImage(bImage, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, null);
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

    public static DrawComponent graphicsComponentMinotaur(Minotaur minotaur) {
        return (g) -> {
            try{
                URL pathMinotaur =DrawComponentFactory.class.getResource("/assets/boss/"+minotaur.getClass().getSimpleName().toLowerCase()+"/"+ minotaur.getState() + FORMAT_SPRITE);
                BufferedImage imgMinotaur = ImageIO.read(new File(pathMinotaur.toURI()));
                if (minotaur.getDir() == Direction.RIGHT){
                    imgMinotaur = ImageUtility.flipImage(imgMinotaur);
                }
                BoundingBoxImplRet bBoxMinotaur =(BoundingBoxImplRet)minotaur.getBoundingBox();
                g.drawImage(imgMinotaur, (int) bBoxMinotaur.getX(), (int) bBoxMinotaur.getY(),(int)bBoxMinotaur.getWidth(),(int)bBoxMinotaur.getHeight(), null);
                //uncomment to draw bbox
                //g.drawRect((int) bBoxMinotaur.getX(), (int) bBoxMinotaur.getY(), (int) bBoxMinotaur.getWidth(),(int) bBoxMinotaur.getHeight());
            } catch (URISyntaxException | IOException e) {
                System.out.println("impossibile caricare l'immagine del personaggio");
                e.printStackTrace();
            }
        };
    }

    private static void resetDrawSettings(Graphics g) {
        g.setFont(g.getFont().deriveFont(15.0f));
        g.setColor(Color.white); // Reset color to white
    }

    /** Tints the given image with the given color.
     * @param loadImg - the image to paint and tint
     * @param color - the color to tint. Alpha value of input color isn't used.
     * @return A tinted version of loadImg */
    private static BufferedImage tintImage(BufferedImage loadImg, Color color) {
        BufferedImage img = new BufferedImage(loadImg.getWidth(), loadImg.getHeight(),
                BufferedImage.TRANSLUCENT);
        final float tintOpacity = 0.45f;
        Graphics2D g2d = img.createGraphics(); 

        //Draw the base image
        g2d.drawImage(loadImg, null, 0, 0);
        //Set the color to a transparent version of the input color
        g2d.setColor(new Color(color.getRed() / 255f, color.getGreen() / 255f, 
            color.getBlue() / 255f, tintOpacity));

        //Iterate over every pixel, if it isn't transparent paint over it
        Raster data = loadImg.getData();
        for(int x = data.getMinX(); x < data.getWidth(); x++){
            for(int y = data.getMinY(); y < data.getHeight(); y++){
                int[] pixel = data.getPixel(x, y, new int[4]);
                if(pixel[3] > 0){ //If pixel isn't full alpha. Could also be pixel[3]==255
                    g2d.fillRect(x, y, 1, 1);
                }
            }
        }
        g2d.dispose();
        return img;
    }
}
