package de.gaalop.vis2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Christian Steinmetz
 */
public class MyPanel extends JPanel {
    
    public BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (image != null) 
            g.drawImage(image, 0, 0, null);
    }
    
}
