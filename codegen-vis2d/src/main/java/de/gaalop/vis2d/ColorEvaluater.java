package de.gaalop.vis2d;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.FloatConstant;
import java.awt.Color;
import java.util.HashMap;

/**
 * Collects all AssignmentNode variables including their colors
 * @author Christian Steinmetz
 */
public class ColorEvaluater extends EmptyControlFlowVisitor {

    private HashMap<String, Color> colors = new HashMap<String, Color>();

    private Color currentColor = Color.white;

    private ColorEvaluater() {
    }

    /**
     * Returns all AssignmentNode variables including their colors
     * @param graph The graph
     * @return A map of the variables to their colors
     */
    public static HashMap<String, Color> getColors(ControlFlowGraph graph) {
        ColorEvaluater evaluater = new ColorEvaluater();
        graph.accept(evaluater);
        return evaluater.colors;
    }

    @Override
    public void visit(ColorNode node) {
        currentColor = new Color(
                (float) ((FloatConstant) node.getR()).getValue(),
                (float) ((FloatConstant) node.getG()).getValue(),
                (float) ((FloatConstant) node.getB()).getValue(),
                (float) ((FloatConstant) node.getAlpha()).getValue()
                );
        super.visit(node);
    }

    @Override
    public void visit(AssignmentNode node) {
        colors.put(node.getVariable().getName(), currentColor);
        super.visit(node);
    }

}
