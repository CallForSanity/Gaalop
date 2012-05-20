package de.gaalop.vis2d;

import de.gaalop.vis2d.drawing.DrawVisitorGraphics;
import de.gaalop.vis2d.drawing.Drawing;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Christian Steinmetz
 */
public class MyPanel extends JPanel {
    
    private Drawing drawing;
    private DrawVisitorGraphics visitor;
    
    public void set(Drawing drawing, DrawVisitorGraphics visitor) {
        this.drawing = drawing;
        this.visitor = visitor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (drawing != null) {
            visitor.setGraphics(g);
            drawing.draw(visitor);
        }
    }
  
}
