package de.gaalop.vis2d.drawing;

import de.gaalop.vis2d.RecLineDouble;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Christian Steinmetz
 */
public class Gerade2d extends DrawObject {
    
    private static final double EPSILON = 10E-4;
    
    public double originX;
    public double originY;
    public double directionX;
    public double directionY;

    public Gerade2d(double originX, double originY, double directionX, double directionY, Color color) {
        super(color);
        this.originX = originX;
        this.originY = originY;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    @Override
    public void accept(DrawVisitor visitor) {
        visitor.visitGerade2d(this);
    }

    public Line2D getLineOnRectangle(Rectangle2D rectangle) {
        if (floatEquals(directionX,0)) {
            if (contains(originX,rectangle.getMinX(),rectangle.getMaxX())) 
                return new Line2D.Double(originX, rectangle.getMinY(), originX, rectangle.getMaxY());
            else
                return null;
        }
        if (floatEquals(directionY,0)) {
            if (contains(originY,rectangle.getMinY(),rectangle.getMaxY())) 
                return new Line2D.Double(rectangle.getMinX(), originY, rectangle.getMaxX(),originY);
            else
                return null;
        }

        Line2D thisLine = new Line2D.Double(originX, originY, originX+directionX, originY+directionY);
        
        Line2D o = new Line2D.Double(rectangle.getMinX(),rectangle.getMaxY(),rectangle.getMaxX(),rectangle.getMaxY());
        Line2D r = new Line2D.Double(rectangle.getMaxX(),rectangle.getMaxY(),rectangle.getMaxX(),rectangle.getMinY());
        Line2D u = new Line2D.Double(rectangle.getMaxX(),rectangle.getMinY(),rectangle.getMinX(),rectangle.getMinY());
        Line2D l = new Line2D.Double(rectangle.getMinX(),rectangle.getMinY(),rectangle.getMinX(),rectangle.getMaxY());
        
        RecLineDouble[] arr = new RecLineDouble[] {
           new RecLineDouble(intersectTwoLines(o, thisLine),o),
           new RecLineDouble(intersectTwoLines(u, thisLine),u),
           new RecLineDouble(intersectTwoLines(l, thisLine),l),
           new RecLineDouble(intersectTwoLines(r, thisLine),r)
        };
        
        Arrays.sort(arr, new Comparator<RecLineDouble>() {
            @Override
            public int compare(RecLineDouble o1, RecLineDouble o2) {
                return Double.compare(o1.f,o2.f);
            }
        });
        
        return new Line2D.Double(getX(arr[0]),getY(arr[0]),getX(arr[1]),getY(arr[1]));
    }
    
    private double getX(RecLineDouble r) {
        return r.l.getX1()+r.f*(r.l.getX2()-r.l.getX1());
    }
    
    private double getY(RecLineDouble r) {
        return r.l.getY1()+r.f*(r.l.getY2()-r.l.getY1());
    }
    
    public double intersectTwoLines(Line2D s, Line2D l) {
        double det = getDx(l)*getDy(s)-getDx(s)*getDy(l);
        return (getDx(l)*(l.getY1()-s.getY1())+getDy(l)*(s.getX1()-l.getX1()))/det;
    }
    
    private double getDx(Line2D l) {
        return l.getX2()-l.getX1();
    }

    private double getDy(Line2D l) {
        return l.getY2()-l.getY1();
    }
    
    private boolean contains(double test, double start, double end) {
        return (start <= test) && (test <= end);
    }

    private boolean floatEquals(double f1, double f2) {
        return Math.abs(f1-f2) <= EPSILON;
    }

    @Override
    public String toString() {
        return "Gerade2d ox="+originX+",oy="+originY+",dx="+directionX+",dy="+directionY;
    }
    
    
    
}
