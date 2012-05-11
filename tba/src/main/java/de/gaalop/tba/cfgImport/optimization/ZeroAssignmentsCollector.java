package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.dfg.FloatConstant;
import java.util.LinkedList;

/**
 * This class collects all zero assignments in a graph
 * @author Christian Steinmetz
 */
public class ZeroAssignmentsCollector extends EmptyControlFlowVisitor {

    private static final double EPSILON = (double) 10E-10; //TODO pre

    private LinkedList<SequentialNode> toRemove = new LinkedList<SequentialNode>();

    public LinkedList<SequentialNode> getToRemove() {
        return toRemove;
    }

    

    @Override
    public void visit(AssignmentNode node) {

        if (node.getValue() instanceof FloatConstant) {
            double value = ((FloatConstant) node.getValue()).getValue();
            if (Math.abs(value) <= EPSILON)
                toRemove.add(node);
        }

        super.visit(node);
        
    }



}
