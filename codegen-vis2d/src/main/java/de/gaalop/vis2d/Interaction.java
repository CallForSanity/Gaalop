package de.gaalop.vis2d;

import de.gaalop.vis2d.drawing.DrawVisitorBufferedImage;
import de.gaalop.vis2d.drawing.DrawVisitorPrintable;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Christian Steinmetz
 */
public class Interaction implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    
    public Vis2dCodeGen vis2dCodeGen;
    
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    
    private Point from;

    public Interaction(Vis2dCodeGen vis2dCodeGen) {
        this.vis2dCodeGen = vis2dCodeGen;
    }

    public void initialize() {
        vis2dCodeGen.vis2dUI.addKeyListener(this);
        vis2dCodeGen.panel.addMouseMotionListener(this);
        vis2dCodeGen.panel.addMouseListener(this);
        vis2dCodeGen.panel.addMouseWheelListener(this);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Rectangle2D.Double world = vis2dCodeGen.world;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                world.x-=world.getWidth()/10;
                break;
            case KeyEvent.VK_RIGHT:
                world.x+=world.getWidth()/10;
                break;
            case KeyEvent.VK_UP:
                world.y+=world.getHeight()/10;
                break;
            case KeyEvent.VK_DOWN:
                world.y-=world.getHeight()/10;
                break;
            case KeyEvent.VK_MINUS:
                world.x -= world.width/2;
                world.y -= world.height/2;
                world.width *= 2;
                world.height *= 2;
                break;
            case KeyEvent.VK_PLUS:
                world.width /= 2;
                world.height /= 2;
                world.x += world.width/2;
                world.y += world.height/2;
                break;
            case KeyEvent.VK_P:
                //Print
                PrinterJob job = PrinterJob.getPrinterJob();
                if ( job.printDialog() == false )
                    return;
                job.setPrintable( new DrawVisitorPrintable(vis2dCodeGen.drawing, world) );
                try {
                    job.print();
                } catch (PrinterException ex) {
                    Logger.getLogger(Vis2dCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;
            case KeyEvent.VK_S:
                //Save as PNG-File
                JFileChooser jFC = new JFileChooser();
                jFC.setFileFilter(new FileNameExtensionFilter("PNG-Dateien", "png"));
                if (jFC.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File f = jFC.getSelectedFile();
                    DrawVisitorBufferedImage imageVisitor = new DrawVisitorBufferedImage(world, 500, 500);
                    vis2dCodeGen.drawing.draw(imageVisitor);
                    try {
                        ImageIO.write(imageVisitor.getImage(),"png",f);
                    } catch (IOException ex) {
                        Logger.getLogger(Vis2dCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
        }
        vis2dCodeGen.repaintDrawing();
        updateLabelPosition();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D.Double pFrom = vis2dCodeGen.visitor.transformPointBack(from.x,from.y);
        Point to = e.getPoint();
        Point2D.Double pTo = vis2dCodeGen.visitor.transformPointBack(to.x,to.y);
        vis2dCodeGen.world.x -= pTo.x-pFrom.x;
        vis2dCodeGen.world.y -= pTo.y-pFrom.y;
        from = to;
        vis2dCodeGen.repaintDrawing();
        updateLabelPosition();
    }

  
    @Override
    public void mouseMoved(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        updateLabelPosition();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        from = new Point(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Rectangle2D.Double world = vis2dCodeGen.world;
        int wheelRotation = e.getWheelRotation();
        if (wheelRotation >= 0) {
            for (int i=0;i<wheelRotation;i++) {
                world.x -= world.width/2;
                world.y -= world.height/2;
                world.width *= 2;
                world.height *= 2;
            }
        } else {
            for (int i=-wheelRotation;i>0;i--) {
                world.width /= 2;
                world.height /= 2;
                world.x += world.width/2;
                world.y += world.height/2;
            }
        }
        vis2dCodeGen.repaintDrawing();
    }
    
    private void updateLabelPosition() {
        Point2D.Double p = vis2dCodeGen.visitor.transformPointBack(lastMouseX, lastMouseY);
        vis2dCodeGen.vis2dUI.laPosition.setText(round(p.x)+" | "+round(p.y));
    }    
    
    
    public double round(double val) {
        double f = vis2dCodeGen.panel.getWidth()/vis2dCodeGen.world.width;
        int v = (int) Math.round(val * f);
        return v / f;
    }
}
