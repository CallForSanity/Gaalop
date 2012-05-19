package de.gaalop.vis2d.drawing;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Christian Steinmetz
 */
public class DrawVisitorBufferedImage extends DrawVisitorGraphics {
    
    private BufferedImage image;

    public DrawVisitorBufferedImage(Rectangle2D world, int width, int height) {
        super(world, width, height);
        image = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_ARGB);
        setGraphics(image.getGraphics());
    }
    
    public BufferedImage getImage() {
        return image;
    }
}
