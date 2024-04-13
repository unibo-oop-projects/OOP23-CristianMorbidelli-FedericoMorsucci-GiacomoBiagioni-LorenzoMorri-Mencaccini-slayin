package slayin.model.entities.graphics;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;

import slayin.model.bounding.BoundingBox;
import slayin.model.bounding.BoundingBoxImplCirc;
import slayin.model.bounding.BoundingBoxImplRet;
import slayin.model.entities.character.Character;
import slayin.model.entities.character.Character.Direction;

public class DrawComponentFactory {

    private final static String FORMAT_SPRITE=".png";
    
    public static DrawComponent graphicsComponentCharacter(Character character){
        return new DrawComponent() {

            @Override
            public void draw(Graphics g) {
                URL pathKnight;
                Image img;
                if(character.getDir()==Direction.LEFT){
                    pathKnight = this.getClass().getResource("/assets/character/"+character.getClass().getSimpleName()+"Left"+FORMAT_SPRITE);
                }else{
                    pathKnight = this.getClass().getResource("/assets/character/"+character.getClass().getSimpleName()+"Right"+FORMAT_SPRITE);
                }
                try {
                    img = ImageIO.read(new File(pathKnight.toURI()));
                    g.drawImage(img,(int)character.getPos().getX(),(int)character.getPos().getY(), null);
                }catch (URISyntaxException | IOException e) {
                    System.out.println("impossibile caricare l'immagine del personaggio");
                    e.printStackTrace();
                }
            }
            
        };

    }


    public static DrawComponent graphicsComponentBoundigBox(BoundingBox bBox){
        return new DrawComponent() {

            @Override
            public void draw(Graphics g) {
                if(bBox instanceof BoundingBoxImplRet){
                    BoundingBoxImplRet newBBox= (BoundingBoxImplRet)bBox;
                    g.drawRect((int)newBBox.getPoint().getX(),(int) newBBox.getPoint().getY(), (int)newBBox.getWidth()/2,(int) newBBox.getHeight()/2);
                }else if(bBox instanceof BoundingBoxImplCirc){
                    BoundingBoxImplCirc newBBox= (BoundingBoxImplCirc) bBox;
                    g.drawOval((int)(newBBox.getPoint().getX()-(newBBox.getRadius()/2)),(int) (newBBox.getPoint().getY()-(newBBox.getRadius()/2)), (int)newBBox.getRadius(),(int) newBBox.getRadius());
                }
            }
            
        };

    }


}
