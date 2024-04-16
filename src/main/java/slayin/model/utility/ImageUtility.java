package slayin.model.utility;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageUtility {
    
    /**
     * Flip the image horizontally
     * @param img - buffered image to rotate
     * @return rotated buffered image
     */
    public static BufferedImage flipImage(BufferedImage img){
        // Flip the image horizontally
        var tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-img.getWidth(null), 0);
        var op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(img, null);
    }
}
