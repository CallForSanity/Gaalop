package de.gaalop.vis2d.drawing;

import java.awt.Color;

/**
 *
 * @author Christian Steinmetz
 */
public class Circle2d extends DrawObject {
    
    public double centerX;
    public double centerY;
    public double radius;

    public Circle2d(double centerX, double centerY, double radius, Color color) {
        super(color);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public void accept(DrawVisitor visitor) {
        visitor.visitCircle2d(this);
    }

    @Override
    public String toString() {
        return "Circle2d: x="+centerX+",y="+centerY+",r="+radius;
    }
    
    
    
 
}
