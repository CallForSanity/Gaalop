package de.gaalop.algebra;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.HashSet;

/**
 * Collects all variables in control flow graph
 * @author Christian Steinmetz
 */
public class VariableCollector extends EmptyControlFlowVisitor {
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

    @Override
    public void visit(ExpressionStatement node) {
        node.getExpression().accept(expressionVisitor);
        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {
        node.getValue().accept(expressionVisitor);
        super.visit(node);
    }
}
