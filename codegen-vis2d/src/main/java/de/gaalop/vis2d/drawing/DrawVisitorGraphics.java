package de.gaalop.vis2d.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Christian Steinmetz
 */
public class DrawVisitorGraphics implements DrawVisitor {
    
    protected Rectangle2D world;    
    private Graphics graphics;
    
    protected int dimension;
    protected double scale;

    public DrawVisitorGraphics(Rectangle2D world, int width, int height) {
        this.world = world;
        scale = Math.max(width/world.getWidth(),height/world.getHeight());
        dimension = (int) Math.round(world.getWidth()*scale);
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
        graphics.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private Point transformPoint(double x, double y) {
        return new Point((int) Math.round((x-world.getMinX())*scale),(int) Math.round(dimension-(y-world.getMinY())*scale));
    }
    
    private double transformLength(double length) {
        return transformPoint(0, 0).distance(transformPoint(0, length));
    }
    
    public Point2D.Double transformPointBack(int x, int y) {
        return new Point2D.Double(
                world.getMinX() + x/scale,
                world.getMinY() - (y-dimension)/scale 
                );
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
        if (line != null) {
            Point start = transformPoint(line.getX1(), line.getY1());
            Point end = transformPoint(line.getX2(), line.getY2());
            graphics.drawLine(start.x,start.y,end.x,end.y);
        }
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
        double dx = world.getWidth()/10;
        double dy = world.getHeight()/10;        
        
        for (double x=0;x>=world.getMinX();x-=dx) {
            visitGerade2d(new Gerade2d(x, 0, 0, 1, Color.lightGray));
            visitText2d(new Text2d(x,0,x+"", Color.lightGray));
        }
        
        for (double x=0;x<=world.getMaxX();x+=dx) {
            visitGerade2d(new Gerade2d(x, 0, 0, 1, Color.lightGray));
            visitText2d(new Text2d(x,0,x+"", Color.lightGray));
        }
        
        for (double y=0;y>=world.getMinY();y-=dy) {
            visitGerade2d(new Gerade2d(0, y, 1, 0, Color.lightGray));
            visitText2d(new Text2d(0,y,y+"", Color.lightGray));
        }
        
        for (double y=0;y<=world.getMaxY();y+=dy) {
            visitGerade2d(new Gerade2d(0, y, 1, 0, Color.lightGray));
            visitText2d(new Text2d(0,y,y+"", Color.lightGray));
        }
         
        visitGerade2d(new Gerade2d(0, 0, 0, 1, Color.darkGray));
        visitGerade2d(new Gerade2d(0, 0, 1, 0, Color.darkGray));
        
    }

    @Override
    public void visitText2d(Text2d text2d) {
        graphics.setColor(text2d.color);
        Point p = transformPoint(text2d.x, text2d.y);
        graphics.drawString(text2d.text, p.x, p.y);
    }

}
