package de.gaalop.vis2d;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.vis2d.drawing.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian Steinmetz
 */
public class Vis2dCodeGen implements CodeGenerator {
    
    private static final double EPSILON = 10E-4;
    
    private Drawing drawing = new Drawing();

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        drawing.objects.clear();
        HashMap<String, Multivector> mapMv = MultivectorBuilder.buildMultivectors(in);
        
        for (String s: in.getRenderingExpressions().keySet()) {
            interpret(mapMv.get(s));
        }

        drawing.printOut();
        DrawVisitorBufferedImage visitor = new DrawVisitorBufferedImage(new Rectangle2D.Double(-5, -5, 10, 10), 1000,1000);
        drawing.draw(visitor);
        try {
            ImageIO.write(visitor.getImage(),"png",new File("D:\\drawing.png"));
        } catch (IOException ex) {
            Logger.getLogger(Vis2dCodeGen.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return new HashSet<OutputFile>();
    }
    
    private void interpret(Multivector mv) {
        boolean[] nonZero = getNonZeroComponents(mv.entries);
        
        
        if (hasBivecParts16(nonZero)) {
            //Point pair
            interpretPointPair(mv);
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
                
                drawing.objects.add(new Gerade2d(ox, oy, dx, dy, Color.red));
           
            } else {
                //Circle or point
                double x = mv.entries[1]/e0;
                double y = mv.entries[2]/e0;
                double r = Math.sqrt(Math.abs(2.0 * mv.entries[3] * mv.entries[4] - mv.entries[2] * mv.entries[2] - mv.entries[1] * mv.entries[1])) / Math.abs(mv.entries[4]);

                if (r < EPSILON) {
                    drawing.objects.add(new Point2d(x, y, Color.red));
                } else {
                    drawing.objects.add(new Circle2d(x, y, r, Color.red));
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

    private void interpretPointPair(Multivector mv) {
        double[] b = mv.entries;
        
        double x1 = (b[9] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) - b[5] * b[9] + b[10] * b[7]) / (b[9] * b[9] + b[7] * b[7]); // e1
	double y1 = (-((b[7] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) - b[10] * b[9] - b[5] * b[7]) / (b[9] * b[9] + b[7] * b[7]))); // e2

	double x2 = (-(((b[9] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) + b[5] * b[9]) - b[10] * b[7]) / (b[9] * b[9] + b[7] * b[7]))); // e1
	double y2 = (b[7] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) + b[10] * b[9] + b[5] * b[7]) / (b[9] * b[9] + b[7] * b[7]); // e2
    
        drawing.objects.add(new Pointpair2d(x1, y1, x2, y2, Color.red));
    }

}
