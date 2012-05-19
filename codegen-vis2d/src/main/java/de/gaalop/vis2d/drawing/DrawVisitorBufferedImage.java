package de.gaalop.vis2d.drawing;

import de.gaalop.vis2d.Multivector;
import de.gaalop.vis2d.MultivectorBuilder;
import de.gaalop.vis2d.Vis2dCodeGen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian Steinmetz
 */
public class DrawVisitorBufferedImage implements DrawVisitor {
    
    private Rectangle2D world;    
    private BufferedImage image;
    private Graphics graphics;
    
    private int dimension;
    private double scale;

    public DrawVisitorBufferedImage(Rectangle2D world, int width, int height) {
        this.world = world;
        scale = Math.max(width/world.getWidth(),height/world.getHeight());
        dimension = (int) Math.round(world.getWidth()*scale);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    private Point transformPoint(double x, double y) {
        return new Point((int) Math.round((x-world.getMinX())*scale),(int) Math.round(dimension-(y-world.getMinY())*scale));
    }
    
    private double transformLength(double length) {
        return transformPoint(0, 0).distance(transformPoint(0, length));
    }

    @Override
    public void visitCircle2d(Circle2d circle2d) {
        graphics.setColor(circle2d.color);
        int ovalWidth = (int) Math.round(transformLength(circle2d.radius*2));
        Point location = transformPoint(circle2d.centerX, circle2d.centerY);
        graphics.drawOval(location.x-ovalWidth/2, location.y-ovalWidth/2, ovalWidth, ovalWidth);
    }

    @Override
    public void visitGerade2d(Gerade2d gerade2d) {
        graphics.setColor(gerade2d.color);
        Line2D line = gerade2d.getLineOnRectangle(world);
        Point start = transformPoint(line.getX1(), line.getY1());
        Point end = transformPoint(line.getX2(), line.getY2());
        graphics.drawLine(start.x,start.y,end.x,end.y);
    }

    @Override
    public void visitPoint2d(Point2d point2d) {
        graphics.setColor(point2d.color);
        Point p = transformPoint(point2d.centerX, point2d.centerY);
        graphics.fillOval(p.x-3,p.y-3,6,6);
    }

    @Override
    public void visitPointpair2d(Pointpair2d pointpair2d) {
        graphics.setColor(pointpair2d.color);
        Point p1 = transformPoint(pointpair2d.p1X, pointpair2d.p1Y);
        graphics.fillOval(p1.x-3,p1.y-3,6,6);
        Point p2 = transformPoint(pointpair2d.p2X, pointpair2d.p2Y);
        graphics.fillOval(p2.x-3,p2.y-3,6,6);
    }

    @Override
    public void drawKOS() {
        for (int x=(int) world.getMinX();x<=(int) world.getMaxX();x++) 
            visitGerade2d(new Gerade2d(x, 0, 0, 1, Color.lightGray));
        for (int y=(int) world.getMinY();y<=(int) world.getMaxY();y++) 
            visitGerade2d(new Gerade2d(0, y, 1, 0, Color.lightGray));
        
        visitGerade2d(new Gerade2d(0, 0, 0, 1, Color.darkGray));
        visitGerade2d(new Gerade2d(0, 0, 1, 0, Color.darkGray));
        
    }

}
