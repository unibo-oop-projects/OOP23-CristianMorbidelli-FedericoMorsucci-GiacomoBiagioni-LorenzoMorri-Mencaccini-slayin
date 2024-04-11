package slayin.views.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class JBackgroundImage extends JComponent {
    private Image backgroundImage;

    public JBackgroundImage(String imagePath) {
        try {
            URL path = this.getClass().getResource(imagePath);
            this.backgroundImage = ImageIO.read(new File(path.getPath()));
        } catch (IOException e) {
            System.out.println("Impossibile caricare l'immagine di sfondo " +
                    backgroundImage);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, 1280, 720, this);
    }
}
