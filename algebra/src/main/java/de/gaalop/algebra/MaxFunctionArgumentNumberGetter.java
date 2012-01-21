package de.gaalop.algebra;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FunctionArgument;
import de.gaalop.dfg.MacroCall;
import de.gaalop.visitors.DFGTraversalVisitor;

/**
 * Returns the maximum argument number
 * @author Christian Steinmetz
 */
public class MaxFunctionArgumentNumberGetter extends DFGTraversalVisitor {

    private int maxInt = 0;

    /**
     * Returns the maximum argument number of an expression
     * @param e The expression
     * @return The maxima number of arguments
     */
    public static int getMaxFunctionArgumentNumber(Expression e) {
        MaxFunctionArgumentNumberGetter visitor = new MaxFunctionArgumentNumberGetter();
        e.accept(visitor);
        return visitor.maxInt;
    }

    public int getMaxInt() {
        return maxInt;
    }

    @Override
    public void visit(FunctionArgument node) {
        maxInt = Math.max(maxInt, node.getIndex());
        super.visit(node);
    }

    @Override
    public void visit(MacroCall node) {
        for (Expression e: node.getArguments())
            e.accept(this);
        super.visit(node);
    }


}