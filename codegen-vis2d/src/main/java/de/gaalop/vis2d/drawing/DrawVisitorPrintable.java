package de.gaalop.vis2d.drawing;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author Christian Steinmetz
 */
public class DrawVisitorPrintable implements Printable {

    private Drawing drawing;
    private Rectangle2D.Double world;

    public DrawVisitorPrintable(Drawing drawing, Double world) {
        this.drawing = drawing;
        this.world = world;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        DrawVisitorGraphics drawVisitorGraphics = new DrawVisitorGraphics(world, (int) (pageFormat.getWidth()), (int) (pageFormat.getHeight()));
        drawVisitorGraphics.setGraphics(graphics);
        drawing.draw(drawVisitorGraphics);
        return (pageIndex == 0) ? Printable.PAGE_EXISTS : Printable.NO_SUCH_PAGE;
    }

}
