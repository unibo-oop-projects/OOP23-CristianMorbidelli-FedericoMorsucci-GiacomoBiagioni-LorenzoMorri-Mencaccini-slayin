package slayin.model.entities.graphics;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.nio.file.Paths;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.Health;
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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import slayin.model.entities.Dummy;
import slayin.model.utility.Globals;

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
                String path = Paths.get("assets","character",character.getName() + FORMAT_SPRITE).toString();
                pathCharacter = DrawComponentFactory.class.getClassLoader().getResource(path);
                for( var weapon : weapons){
                    path = Paths.get("assets","character",weapon.getName() + character.getName() + FORMAT_SPRITE).toString();
                    pathWeapon = DrawComponentFactory.class.getClassLoader().getResource(path);
                    imgWeapons.add((BufferedImage) ImageIO.read(new File(pathWeapon.toURI())));
                }
                imgCharacter = (BufferedImage) ImageIO.read(new File(pathCharacter.toURI()));

                // se la direzione è a sinistra ruoto l'immagine
                if (character.getDir() == Direction.LEFT){
                    imgCharacter= ImageUtility.flipImage(imgCharacter);
                    imgWeapons = imgWeapons.stream().map(w->ImageUtility.flipImage(w)).toList();
                }

                //controllo se il personaggio ha preso danno da poco in tal caso coloro di rosso il personaggio
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
                BoundingBoxImplCirc newBBox = (BoundingBoxImplCirc) bBox;
                g.fillOval((int) newBBox.getPoint().getX(), (int) newBBox.getPoint().getY(), (int) newBBox.getRadius(),
                        (int) newBBox.getRadius());
            }
        };

    }

    public static DrawComponent graphicsComponentSlime(Slime slime){
        return (g) ->{
            try{
                URL pathSlime;
                BufferedImage imgSlime;
                String path = Paths.get("assets","entities","enemies","slime" + FORMAT_SPRITE).toString();
                pathSlime = DrawComponentFactory.class.getClassLoader().getResource(path);
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
                String path = Paths.get("assets","entities","enemies","fire" + FORMAT_SPRITE).toString();
                pathFire = DrawComponentFactory.class.getClassLoader().getResource(path);
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
            float comboTimePercentage = remainingTime / (Globals.COMBO_RESET_TIME / 1000f);
            int pbarHeight = 10;
            int xSpacing = fm.stringWidth(comboText) + 20;

            g.drawRect(xSpacing, comboHeight - pbarHeight, 100, pbarHeight);
            g.setColor(Color.BLUE);                
            g.fillRect(xSpacing, comboHeight - pbarHeight, (int) (comboTimePercentage * 100), pbarHeight);
        };
    }

    public static DrawComponent graphicsComponentHealth(Health knightHealth) {
        return (g) -> {
            resetDrawSettings(g);

            var imageWidth = 25;
            Image hp = AssetsManager.getImageAsset(Asset.LIFE_HEART);
            g.drawImage(hp, 5, 0, imageWidth, 25, null);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 20));
            g.drawString(String.valueOf(knightHealth.getHealth()), 10 + imageWidth, 20);
        };
    }

    public static DrawComponent graphicsComponentWorld(World w) {
        return (g) -> {
            Image bImage = AssetsManager.getImageAsset(Asset.GAME_BG);
            g.drawImage(bImage, 0, 0, Globals.RESOLUTION.getWidth(), Globals.RESOLUTION.getHeight(), null);
        };
    }

    public static DrawComponent graphicsComponentDummy(Dummy dummy){
        return (g) -> {
            String path = Paths.get("assets","entities","dummy"+FORMAT_SPRITE).toString();
            URL pathDummy = DrawComponentFactory.class.getClassLoader().getResource(path);
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
                //URL pathMinotaur =DrawComponentFactory.class.getResource(File.separator+"assets"+File.separator+"boss"+File.separator+minotaur.getClass().getSimpleName().toLowerCase()+File.separator+ minotaur.getState() + FORMAT_SPRITE);
                String path = Paths.get("assets","boss",minotaur.getClass().getSimpleName().toLowerCase(),minotaur.getState() + FORMAT_SPRITE).toString();
                
                URL pathMinotaur =DrawComponentFactory.class.getClassLoader().getResource(path);
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

        // Disegna l'immagine di base
        g2d.drawImage(loadImg, 0, 0, null);
        
        // Imposta la modalità di composizione Alpha per fondere i colori
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, tintOpacity));

        // Imposta il colore con l'opacità desiderata
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * tintOpacity)));

        // Disegna il colore trasparente sopra l'immagine
        g2d.fillRect(0, 0, loadImg.getWidth(), loadImg.getHeight());
        
        g2d.dispose();
        return img;
    }
}
