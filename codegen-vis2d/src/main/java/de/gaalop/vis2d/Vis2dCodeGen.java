package de.gaalop.vis2d;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.UnknownMacroCall;
import de.gaalop.dfg.*;
import de.gaalop.vis2d.drawing.*;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.JFrame;

/**
 *
 * @author Christian Steinmetz
 */
public class Vis2dCodeGen implements CodeGenerator {

    private static final double EPSILON = 10E-4;
    public Drawing drawing = new Drawing();
    public Rectangle2D.Double world = new Rectangle2D.Double(-5, -5, 10, 10);
    public MyPanel panel;
    public DrawVisitorGraphics visitor;
    public Vis2dUI vis2dUI;
    
    private HashMap<String, double[]> values;
    

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        values = ValueEvaluater.evaluateValues(in);
            
        HashMap<String, Color> colors = ColorEvaluater.getColors(in);

        drawing.objects.clear();

        HashMap<String, Multivector> mapMv = MultivectorBuilder.buildMultivectors(in);

        drawUnknownMacros(in.unknownMacros);
        for (String s : in.getRenderingExpressions().keySet()) {
            interpret(mapMv.get(s), colors.get(s));
        }

        vis2dUI = new Vis2dUI();
        panel = (MyPanel) vis2dUI.jPanel1;
        Interaction interaction = new Interaction(this);
        interaction.initialize();

        vis2dUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vis2dUI.setVisible(true);
        repaintDrawing();
        return new HashSet<OutputFile>();
    }

    public void repaintDrawing() {
        visitor = new DrawVisitorBufferedImage(world, 500, 500);
        panel.set(drawing, visitor);
        drawing.draw(visitor);

        panel.repaint();
    }

    private void interpret(Multivector mv, Color c) {
        boolean[] nonZero = getNonZeroComponents(mv.entries);

        if (hasBivecParts16(nonZero)) {
            //Point pair
            interpretPointPair(mv, c, nonZero);
        } else {
            //Vector
            double e0 = mv.entries[4];
            if (Math.abs(e0) < EPSILON) {
                //Line
                double ox = 0;
                double oy = 0;
                double dx = 0;
                double dy = 0;
                if (Math.abs(mv.entries[2]) < EPSILON) {
                    // parallel to y-axis
                    ox = mv.entries[3] / mv.entries[1];
                    dy = 1;
                } else {
                    dx = 1;
                    dy = -mv.entries[1] / mv.entries[2];
                    oy = mv.entries[3] / mv.entries[2];
                }

                drawing.objects.add(new Gerade2d(ox, oy, dx, dy, c));

            } else {
                //Circle or point   x+(0.5*x*x-0.5*r*r)*(einf)+e0
                double x = mv.entries[1] / e0;
                double y = mv.entries[2] / e0;

                double r = Math.sqrt(Math.abs(2.0 * mv.entries[3] * mv.entries[4] - mv.entries[2] * mv.entries[2] - mv.entries[1] * mv.entries[1])) / Math.abs(mv.entries[4]);

                if (r < EPSILON) {
                    drawing.objects.add(new Point2d(x, y, c));
                } else {
				
					if (x*x+y*y-2*mv.entries[3] < 0) {
						//imaginary radius
						drawing.objects.add(new CircleDashed2d(x, y, r, c));
					} else {
						//real radius
						drawing.objects.add(new Circle2d(x, y, r, c));
					}
				
                    
                }
            }
        }
    }

    private boolean[] getNonZeroComponents(double[] entries) {
        boolean[] result = new boolean[entries.length];
        for (int i = 0; i < entries.length; i++) {
            result[i] = (Math.abs(entries[i]) > EPSILON);
        }
        return result;
    }

    private boolean hasBivecParts16(boolean[] nonZero) {
        for (int i = 5; i <= 10; i++) 
            if (nonZero[i]) 
                return true;
        return false;
    }

    private void interpretPointPair(Multivector mv, Color c, boolean[] nonZero) {
        double[] b = mv.entries;
        if (nonZero[7]) {
            //2 conformal points
            double x1 = (b[9] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) - b[5] * b[9] + b[10] * b[7]) / (b[9] * b[9] + b[7] * b[7]); // e1
            double y1 = (-((b[7] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) - b[10] * b[9] - b[5] * b[7]) / (b[9] * b[9] + b[7] * b[7]))); // e2

            double x2 = (-(((b[9] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) + b[5] * b[9]) - b[10] * b[7]) / (b[9] * b[9] + b[7] * b[7]))); // e1
            double y2 = (b[7] * Math.sqrt(Math.abs((2.0 * b[8] * b[9] + 2.0 * b[6] * b[7]) - b[5] * b[5] + b[10] * b[10])) + b[10] * b[9] + b[5] * b[7]) / (b[9] * b[9] + b[7] * b[7]); // e2

            drawing.objects.add(new Pointpair2d(x1, y1, x2, y2, c));
        } else {
            //One conformal point and einf
            if (nonZero[5])
                drawing.objects.add(new Point2d(-(b[8] / b[5]), b[6] / b[5], c));
        }
    }

    private void drawUnknownMacros(LinkedList<UnknownMacroCall> unknownMacros) {
        for (UnknownMacroCall unknownMacroCall: unknownMacros) {
            MacroCall macroCall = unknownMacroCall.macroCall;
            Color color = getColor(unknownMacroCall.colorNode);
            String name = macroCall.getName();
            if (name.equals("DrawArrow")) {
                drawArrow(macroCall.getArguments(), color);
            } else
                if (name.equals("DrawLine")) {
                    drawLine(macroCall.getArguments(), color);
                } else    
                    if (name.equals("DrawTriangle")) {
                        drawTriangle(macroCall.getArguments(), color);
                    } else 
                        System.out.println("Unknown macro: "+name);
        }
    }

    private void drawArrow(List<Expression> arguments, Color color) {
        throw new UnsupportedOperationException("DrawArrow is not implemented yet");
    }
    
    private double[] getCoordFromMv(Expression mvE) {
        double[] result = new double[2];
        double[] mv = values.get(((Variable) mvE).getName());
        result[0] = mv[1] / mv[4]; //x
        result[1] = mv[2] / mv[4]; //y
        return result;
    }

    private void drawLine(List<Expression> arguments, Color color) {
        double x1;
        double y1;
        double x2;
        double y2;
        switch (arguments.size()) {
            case 2: //Two multivectors representing points
                double[] startMv = getCoordFromMv(arguments.get(0));
                double[] endMv = getCoordFromMv(arguments.get(1));
                x1 = startMv[0];
                y1 = startMv[1];
                x2 = endMv[0];
                y2 = endMv[1];
                break;
            case 4: //Four scalar values representing coordinates
                x1 = getValueOfExpression(arguments.get(0));
                y1 = getValueOfExpression(arguments.get(1));
                x2 = getValueOfExpression(arguments.get(2));
                y2 = getValueOfExpression(arguments.get(3));
                break;
            default:
                return;
        }
        drawing.objects.add(new Stretch2d(x1,y1,x2,y2,color));
    }

    private void drawTriangle(List<Expression> arguments, Color color) {
        //All arguments are variables (See de.gaalop.algebra.Inliner)
        double x1;
        double y1;
        double x2;
        double y2;
        double x3;
        double y3;
        switch (arguments.size()) {
            case 3: //Two multivectors representing points
                double[] p1 = getCoordFromMv(arguments.get(0));
                double[] p2 = getCoordFromMv(arguments.get(1));
                double[] p3 = getCoordFromMv(arguments.get(2));
                x1 = p1[0];
                y1 = p1[1];
                x2 = p2[0];
                y2 = p2[1];
                x3 = p3[0];
                y3 = p3[1];
                break;
            case 6: //Four scalar values representing coordinates
                x1 = getValueOfExpression(arguments.get(0));
                y1 = getValueOfExpression(arguments.get(1));
                x2 = getValueOfExpression(arguments.get(2));
                y2 = getValueOfExpression(arguments.get(3));
                x3 = getValueOfExpression(arguments.get(4));
                y3 = getValueOfExpression(arguments.get(5));
                break;
            default:
                return;
        }
        drawing.objects.add(new Stretch2d(x1,y1,x2,y2,color));
        drawing.objects.add(new Stretch2d(x1,y1,x3,y3,color));
        drawing.objects.add(new Stretch2d(x2,y2,x3,y3,color));
    }

    private Color getColor(ColorNode colorNode) {
        if (colorNode == null)
            return Color.black; //default color
        else
            return new Color(
                    (float) getValueOfExpression(colorNode.getR()),
                    (float) getValueOfExpression(colorNode.getG()),
                    (float) getValueOfExpression(colorNode.getB()),
                    (float) getValueOfExpression(colorNode.getAlpha())
                    );
    }
    
    private double getValueOfExpression(Expression e) {
        if (e instanceof FloatConstant) {
            return ((FloatConstant) e).getValue();
        } else
            return values.get(((Variable) e).getName())[0];
    }
}
