package de.gaalop.vis2d.drawing;

import java.awt.Color;

/**
 *
 * @author Christian Steinmetz
 */
public class Pointpair2d extends DrawObject {
    
    public double p1X;
    public double p1Y;
    public double p2X;
    public double p2Y;

    public Pointpair2d(double p1X, double p1Y, double p2X, double p2Y, Color color) {
        super(color);
        this.p1X = p1X;
        this.p1Y = p1Y;
        this.p2X = p2X;
        this.p2Y = p2Y;
    }

    @Override
    public void accept(DrawVisitor visitor) {
        visitor.visitPointpair2d(this);
    }
    
    @Override
    public String toString() {
        return "Pointpair2d: x1="+p1X+",y1="+p1Y+",x2="+p2X+",y2="+p2Y;
    }    
    
}
