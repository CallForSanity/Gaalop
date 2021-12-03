package de.gaalop.tba.baseChange;

import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Variable;
import java.util.HashSet;

/**
 * Collects all output variables
 * @author Christian Steinmetz
 */
public class OutputVarsCollector extends EmptyControlFlowVisitor {
    
    public HashSet<String> variableNames = new HashSet<>();

    @Override
    public void visit(ExpressionStatement node) {
        if (node.getExpression() instanceof Variable)
            variableNames.add(((Variable) node.getExpression()).getName());
        super.visit(node); 
    }

    @Override
    public void visit(StoreResultNode node) {
        variableNames.add(node.getValue().getName());
        super.visit(node); 
    }

}
