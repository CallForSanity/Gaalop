package de.gaalop.java;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import java.util.HashSet;

/**
 * Adds all components of variables to a components set
 * @author Christian Steinmetz
 */
public class FindMultivectorDestinationComponents extends EmptyControlFlowVisitor {

    public HashSet<MultivectorComponent> mvDestComponents = new HashSet<MultivectorComponent>();

    @Override
    public void visit(AssignmentNode node) {
        mvDestComponents.add((MultivectorComponent) node.getVariable());
        super.visit(node);
    }

}
