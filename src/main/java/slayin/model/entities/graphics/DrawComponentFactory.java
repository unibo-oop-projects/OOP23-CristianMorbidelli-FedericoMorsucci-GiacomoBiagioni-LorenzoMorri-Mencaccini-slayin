package slayin.model.entities.graphics;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

import slayin.model.World;
import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.character.CharacterImpl;
import slayin.model.entities.character.Health;
import slayin.model.entities.character.MeleeWeapon;
import slayin.model.entities.enemies.Enemy;
import slayin.model.entities.shots.HeadstoneShot;
import slayin.model.entities.shots.ImpShots;
import slayin.model.entities.GameObject.Direction;
import slayin.model.entities.boss.Boss;
import slayin.model.entities.boss.Boss.State;
import slayin.model.score.ScoreManager;
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
    public static DrawComponent graphicsComponentCharacter(CharacterImpl character) {
        return (g) -> {
            try {
                InputStream pathCharacter, pathWeapon;
                BufferedImage imgCharacter;
                List<BufferedImage> imgWeapons = new ArrayList<>();
                List<MeleeWeapon> weapons = character.getWeapons();

                //recupero le immagini
                String path = "slayin/assets/character/" + character.getName() + FORMAT_SPRITE;
                pathCharacter = ClassLoader.getSystemResourceAsStream(path);
                for( var weapon : weapons){
                    path = "slayin/assets/character/" + weapon.getName() + character.getName() + FORMAT_SPRITE;
                    pathWeapon = ClassLoader.getSystemResourceAsStream(path);
                    imgWeapons.add((BufferedImage) ImageIO.read(pathWeapon));
                }
                imgCharacter = (BufferedImage) ImageIO.read(pathCharacter);

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
                    //qui si può aggiungere volendo anche il disegno di boundingBox circolari
                });
            } catch (IOException e) {
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

    /**
     * Creates a DrawComponent for rendering an enemy.
     *
     * @param enemy the enemy entity to be drawn.
     * @return a DrawComponent for the specified enemy.
     */
    public static DrawComponent graphicsComponentEnemy(Enemy enemy){
        return (g) ->{
            try{
                InputStream pathEnemy;
                BufferedImage imgEnemy;
                String path = "slayin/assets/entities/enemies/" + enemy.getClass().getSimpleName().toLowerCase() + FORMAT_SPRITE;
                pathEnemy = ClassLoader.getSystemResourceAsStream(path);
                imgEnemy = (BufferedImage) ImageIO.read(pathEnemy);

                // if the direction is left, the image ll flip
                if (enemy.getDir() == Direction.RIGHT){
                    imgEnemy= ImageUtility.flipImage(imgEnemy);
                }
                BoundingBoxImplRet bBoxEnemy =(BoundingBoxImplRet)enemy.getBoundingBox();
                g.drawImage(imgEnemy, (int) bBoxEnemy.getX(), (int) bBoxEnemy.getY(),(int)bBoxEnemy.getWidth(),(int)bBoxEnemy.getHeight(), null);
            } catch (IOException e) {
                System.out.println("impossibile caricare l'immagine del nemico");
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
    public static DrawComponent graphicsComponentScore(ScoreManager scoreManager) {
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
            String path = "slayin/assets/entities/dummy" + FORMAT_SPRITE;
            InputStream pathDummy = ClassLoader.getSystemResourceAsStream(path);
            try {
                BoundingBoxImplRet entity = (BoundingBoxImplRet) dummy.getBoundingBox();
                Image img = ImageIO.read(pathDummy).getScaledInstance((int) entity.getWidth(), (int) entity.getHeight(), Image.SCALE_DEFAULT);
                g.drawImage(img, (int) entity.getX(), (int) entity.getY(), null);
            } catch (Exception e) {
                System.out.println("Unable to locate Dummy image from resources");
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

    public static DrawComponent graphicsComponentImpShots(ImpShots impShots) {
        return (g) -> {
            try{
                String path = "slayin/assets/entities/shots/ImpShots" + FORMAT_SPRITE;
                
                InputStream pathImpShots = ClassLoader.getSystemResourceAsStream(path);
                BufferedImage imgImpShots = ImageIO.read(pathImpShots);
                
                if (impShots.getDir() == Direction.RIGHT){
                    imgImpShots = ImageUtility.flipImage(imgImpShots);
                }

                BoundingBoxImplCirc bBoxImpShots =(BoundingBoxImplCirc)impShots.getBoundingBox();
                g.drawImage(imgImpShots, (int) bBoxImpShots.getX(), (int) bBoxImpShots.getY(),(int)bBoxImpShots.getRadius()*2,(int)bBoxImpShots.getRadius()*2, null);
            } catch (IOException e) {
                System.out.println("impossibile caricare l'immagine del personaggio");
                e.printStackTrace();
            }
        };
    }

    public static DrawComponent graphicsComponentBoss(Boss boss, String pathB) {
        return (g) -> {
            try{
                InputStream pathBoss = ClassLoader.getSystemResourceAsStream(
                    pathB + "/" + boss.getState() + FORMAT_SPRITE
                );
                BufferedImage imgBoss = ImageIO.read(pathBoss);
                if (boss.getDir() == Direction.RIGHT){
                    imgBoss = ImageUtility.flipImage(imgBoss);
                }
                if(boss.getState()==State.HITTED){
                    imgBoss = tintImage(imgBoss, Color.red);
                }
                if(boss.getState()==State.ATTACK){
                    imgBoss = tintImage(imgBoss, Color.pink);
                }
                BoundingBoxImplRet bBoxBoss =(BoundingBoxImplRet)boss.getBoundingBox();
                g.drawImage(imgBoss, (int) bBoxBoss.getX(), (int) bBoxBoss.getY(),(int)bBoxBoss.getWidth(),(int)bBoxBoss.getHeight(), null);
            } catch (IOException e) {
                System.out.println("impossibile caricare l'immagine del personaggio");
                e.printStackTrace();
            }
        };
    }

    /**
     * Creates a DrawComponent for rendering a HeadstoneShot.
     *
     * @param headstoneShot the HeadstoneShot entity to be drawn.
     * @return a DrawComponent for the specified HeadstoneShot.
     */
    public static DrawComponent graphicsComponentHeadstoneShot(HeadstoneShot headstoneShot) {
        return (g) -> {
            try{
                String path = "slayin/assets/entities/shots/skull" + FORMAT_SPRITE;
                InputStream pathSkull = ClassLoader.getSystemResourceAsStream(path);
                BufferedImage imgSkull = ImageIO.read(pathSkull);

                BoundingBoxImplCirc bBoxSkull =(BoundingBoxImplCirc)headstoneShot.getBoundingBox();
                g.drawImage(imgSkull, (int) bBoxSkull.getX(), (int) bBoxSkull.getY(),(int)bBoxSkull.getRadius()*2,(int)bBoxSkull.getRadius()*2, null);
            } catch (IOException e) {
                System.out.println("impossibile caricare l'immagine del personaggio");
                e.printStackTrace();
            }
        };
    }
}
