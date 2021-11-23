package de.gaalop.vis2d.drawing;

import java.awt.Color;

/**
 *
 * @author Christian Steinmetz
 */
public class Text2d extends DrawObject {
    
    public double x;
    public double y;
    public String text;

    public Text2d(double x, double y, String text, Color color) {
        super(color);
        this.x = x;
        this.y = y;
        this.text = text;
    }

    @Override
    public void accept(DrawVisitor visitor) {
        visitor.visitText2d(this);
    }

}
