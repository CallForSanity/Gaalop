package de.gaalop.gui;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * This class creates a filled background stripe for the gaalop logo.
 */
public class GaalopLogoFiller extends JComponent {

    private Log log = LogFactory.getLog(GaalopLogoFiller.class);

    private Image image;

    public GaalopLogoFiller() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("logo_bg.png"));
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Rectangle bounds = getBounds();
        if (image != null) {
            g2d.drawImage(image, 0, 0, bounds.width, image.getHeight(null), null);
        }
    }
}
