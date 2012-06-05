package de.gaalop.vis2d.drawing;

import java.awt.Color;

/**
 *
 * @author Christian Steinmetz
 */
public class Point2d extends DrawObject {
    
    public double centerX;
    public double centerY;

    public Point2d(double centerX, double centerY, Color color) {
        super(color);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void accept(DrawVisitor visitor) {
        visitor.visitPoint2d(this);
    }
    
    @Override
    public String toString() {
        return "Point2d: x="+centerX+",y="+centerY;
    }    
}
