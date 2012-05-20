package de.gaalop.vis2d;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.vis2d.drawing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Christian Steinmetz
 */
public class Vis2dCodeGen implements CodeGenerator, KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    
    private static final double EPSILON = 10E-4;
    
    private Drawing drawing = new Drawing();
    
    private Rectangle2D.Double world = new Rectangle2D.Double(-5, -5, 10, 10);

    private MyPanel panel;
    
    private DrawVisitorGraphics visitor;
    
    private Vis2dUI vis2dUI;
    
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    
    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        
        HashMap<String, Color> colors = ColorEvaluater.getColors(in);
        
        drawing.objects.clear();
        
        HashMap<String, Multivector> mapMv = MultivectorBuilder.buildMultivectors(in);
        
        for (String s: in.getRenderingExpressions().keySet()) {
            interpret(mapMv.get(s), colors.get(s));
        }
        
        File f = new File("D:\\drawing.png");
        DrawVisitorBufferedImage imageVisitor = new DrawVisitorBufferedImage(world, 500, 500);
                    drawing.draw(imageVisitor);
                    try {
                        ImageIO.write(imageVisitor.getImage(),"png",f);
                    } catch (IOException ex) {
                        Logger.getLogger(Vis2dCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
        

        vis2dUI = new Vis2dUI();
        
        vis2dUI.addKeyListener(this);
        panel = (MyPanel) vis2dUI.jPanel1;
        panel.addMouseMotionListener(this);
        panel.addMouseListener(this);
        panel.addMouseWheelListener(this);
        
        
        vis2dUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vis2dUI.setVisible(true);
        repaintDrawing();
        return new HashSet<OutputFile>();
    }
    
    private void repaintDrawing() {
        visitor = new DrawVisitorBufferedImage(world, 500,500);
        panel.set(drawing, visitor);
        drawing.draw(visitor);
        
        panel.repaint();
    }
    
    private void interpret(Multivector mv, Color c) {
        boolean[] nonZero = getNonZeroComponents(mv.entries);
        
        
        if (hasBivecParts16(nonZero)) {
            //Point pair
            interpretPointPair(mv, c);
        } else {
            //Vector
            double e0 = mv.entries[4];
            if (Math.abs(e0) < EPSILON) {
                //Line
                double ox = 0;
                double oy = 0;
                double dx = 0;
                double dy = 0;
                if (Math.abs(mv.entries[2])<EPSILON) {
                    // parallel to y-axis
                    ox = mv.entries[3]/mv.entries[1];
                    dy = 1;
                } else {
                    dx = 1;
                    dy = -mv.entries[1]/mv.entries[2];
                    oy = mv.entries[3]/mv.entries[2];
                }
                
                drawing.objects.add(new Gerade2d(ox, oy, dx, dy, c));
           
            } else {
                //Circle or point
                double x = mv.entries[1]/e0;
                double y = mv.entries[2]/e0;
                double r = Math.sqrt(Math.abs(2.0 * mv.entries[3] * mv.entries[4] - mv.entries[2] * mv.entries[2] - mv.entries[1] * mv.entries[1])) / Math.abs(mv.entries[4]);

                if (r < EPSILON) {
                    drawing.objects.add(new Point2d(x, y, c));
                } else {
                    drawing.objects.add(new Circle2d(x, y, r, c));
                }
            }
            
            
            

            
        }
    }

    private boolean[] getNonZeroComponents(double[] entries) {
        boolean[] result = new boolean[entries.length];
        for (int i=0;i<entries.length;i++)
            result[i] = (Math.abs(entries[i]) > EPSILON);
        return result;
    }

    private boolean hasBivecParts16(boolean[] nonZero) {
        for (int i=5;i<=10;i++)
            if (nonZero[i])
                return true;
        
        return false;
    }

    private void interpretPointPair(Multivector mv, Color c) {
        double[] b = mv.entries;
        
        double x1 = (b[9] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) - b[5] * b[9] + b[10] * b[7]) / (b[9] * b[9] + b[7] * b[7]); // e1
	double y1 = (-((b[7] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) - b[10] * b[9] - b[5] * b[7]) / (b[9] * b[9] + b[7] * b[7]))); // e2

	double x2 = (-(((b[9] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) + b[5] * b[9]) - b[10] * b[7]) / (b[9] * b[9] + b[7] * b[7]))); // e1
	double y2 = (b[7] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) + b[10] * b[9] + b[5] * b[7]) / (b[9] * b[9] + b[7] * b[7]); // e2
    
        drawing.objects.add(new Pointpair2d(x1, y1, x2, y2, c));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
                job.setPrintable( new DrawVisitorPrintable(drawing, world) );
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
                    drawing.draw(imageVisitor);
                    try {
                        ImageIO.write(imageVisitor.getImage(),"png",f);
                    } catch (IOException ex) {
                        Logger.getLogger(Vis2dCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
        }
        repaintDrawing();
        updateLabelPosition();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D.Double pFrom = visitor.transformPointBack(from.x,from.y);
        Point to = e.getPoint();
        Point2D.Double pTo = visitor.transformPointBack(to.x,to.y);
        world.x -= pTo.x-pFrom.x;
        world.y -= pTo.y-pFrom.y;
        from = to;
        repaintDrawing();
        updateLabelPosition();
    }

  
    @Override
    public void mouseMoved(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
        updateLabelPosition();
    }

    public double round(double val) {
        double f = panel.getWidth()/world.width;
        int v = (int) Math.round(val * f);
        return v / f;
    }

    private void updateLabelPosition() {
        Point2D.Double p = visitor.transformPointBack(lastMouseX, lastMouseY);
        vis2dUI.laPosition.setText(round(p.x)+" | "+round(p.y));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        from = new Point(e.getPoint());
    }
    
    private Point from;

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
        repaintDrawing();
    }
    

    

}
