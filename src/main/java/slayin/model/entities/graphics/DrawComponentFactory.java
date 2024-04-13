package slayin.model.entities.graphics;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.net.URISyntaxException;

import slayin.model.entities.character.Character;

public class DrawComponentFactory {
    
    public static DrawComponent graphicsComponentKnight(Graphics g,Character character){
        return new DrawComponent() {

            @Override
            public void draw(Graphics g) {
                URL pathKnight = this.getClass().getResource("/assets/character/knight.jpg");
                Image img;
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


}
