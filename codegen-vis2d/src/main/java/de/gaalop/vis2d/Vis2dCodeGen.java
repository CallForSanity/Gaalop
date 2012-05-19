package de.gaalop.vis2d;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.vis2d.drawing.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Christian Steinmetz
 */
public class Vis2dCodeGen implements CodeGenerator, KeyListener {
    
    private static final double EPSILON = 10E-4;
    
    private Drawing drawing = new Drawing();
    
    private Rectangle2D.Double world = new Rectangle2D.Double(-5, -5, 10, 10);

    private MyPanel panel;
    
    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        HashMap<String, Color> colors = ColorEvaluater.getColors(in);
        
        drawing.objects.clear();
        
        HashMap<String, Multivector> mapMv = MultivectorBuilder.buildMultivectors(in);
        
        for (String s: in.getRenderingExpressions().keySet()) {
            interpret(mapMv.get(s), colors.get(s));
        }

        Vis2dUI vis2dUI = new Vis2dUI();
        vis2dUI.addKeyListener(this);
        panel = (MyPanel) vis2dUI.jPanel1;
        
        vis2dUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vis2dUI.setVisible(true);
        repaintDrawing();
        return new HashSet<OutputFile>();
    }
    
    private void repaintDrawing() {
        DrawVisitorBufferedImage visitor = new DrawVisitorBufferedImage(world, 500,500);
        
        drawing.draw(visitor);
        panel.image = visitor.getImage();
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
                world.x--;
                break;
            case KeyEvent.VK_RIGHT:
                world.x++;
                break;
            case KeyEvent.VK_UP:
                world.y++;
                break;
            case KeyEvent.VK_DOWN:
                world.y--;
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
                
        }
        repaintDrawing();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
