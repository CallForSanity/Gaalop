package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.HashSet;

/**
 * Collects all variables in a Control Flow Graph
 * @author Christian Steinmetz
 */
public class VariablesCollector extends EmptyControlFlowVisitor {

    private HashSet<String> variables = new HashSet<String>();

    public HashSet<String> getVariables() {
        return variables;
    }

    private ExpressionVisitor expressionVisitor = new EmptyExpressionVisitor() {

        @Override
        public void visit(MultivectorComponent node) {
            variables.add(node.getName());
        }

        @Override
        public void visit(Variable node) {
            variables.add(node.getName());
        }
    };

    @Override
    public void visit(AssignmentNode node) {
        node.getVariable().accept(expressionVisitor);
        node.getValue().accept(expressionVisitor);
        
        super.visit(node);
    }

    


}
