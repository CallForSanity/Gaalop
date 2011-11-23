package de.gaalop.productComputer.transformer;

import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;

/**
 * Determines a variable in an expression
 * @author Christian Steinmetz
 */
public class GetVariableVisitor extends EmptyExpressionVisitor {

    private Variable variable;

    public Variable getVariable() {
        return variable;
    }
    
    @Override
    public void visit(Variable node) {
        variable = node;
        super.visit(node);
    }

    /**
     * Returns a variable in an expression
     * @param e The expression
     * @return The variable
     */
    public static Variable getVariableInExpression(Expression e) {
        GetVariableVisitor visitor = new GetVariableVisitor();
        e.accept(visitor);
        return visitor.variable;
    }

}
