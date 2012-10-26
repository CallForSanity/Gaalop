package de.gaalop.java;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import java.util.HashSet;

/**
 * Adds all components of output variables to a components set
 * @author Christian Steinmetz
 */
public class FindOutputComponents extends EmptyControlFlowVisitor {

    public HashSet<MultivectorComponent> outputComponents = new HashSet<MultivectorComponent>();

    @Override
    public void visit(AssignmentNode node) {
        outputComponents.add((MultivectorComponent) node.getVariable());
        super.visit(node);
    }

}
