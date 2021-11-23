package de.gaalop.vis2d;

import de.gaalop.vis2d.drawing.DrawObject;
import de.gaalop.vis2d.drawing.DrawVisitor;
import java.awt.Color;

/**
 *
 * @author Christian Steinmetz
 */
public class Stretch2d extends DrawObject {
    
    public double x1;
    public double y1; 
    public double x2; 
    public double y2;

    public Stretch2d(double x1, double y1, double x2, double y2, Color color) {
        super(color);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void accept(DrawVisitor visitor) {
        visitor.visitStretch2d(this);
    }

}
