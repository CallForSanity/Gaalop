package de.gaalop.cfg;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;

/**
 * This node models the assignment of an expression to a variable or multivector component.
 *
 * @author Sebastian Hartte
 * @version 1.0
 * @since 1.0
 */
public final class AssignmentNode extends SequentialNode {

    private Variable variable;

    private Expression value;

    /**
     * Constructs a new assignment node in <code>graph</code>, that models the assignment of <code>value</code> to
     * <code>variable</code>.
     *
     * @param graph    The control flow graph the new node should belong to.
     * @param variable The variable that should be assigned to.
     * @param value    The expression that models the value that should be assigned to the variable.
     */
    public AssignmentNode(ControlFlowGraph graph, Variable variable, Expression value) {
        super(graph);
        this.variable = variable;
        this.value = value;
    }

    /**
     * Gets the variable that this assignment assigns to.
     *
     * @return The variable associated with this node.
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Gets the value that should be assigned to the variable.
     *
     * @return The dataflow graph that models the value.
     */
    public Expression getValue() {
        return value;
    }

    /**
     * Changes the variable that this assignment node assigns to.
     *
     * @param variable The new variable that should be assigned to.
     */
    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    /**
     * Changes the value that is assigned to the variable.
     *
     * @param value The value modeled as a dataflow graph.
     */
    public void setValue(Expression value) {
        this.value = value;
    }

    /**
     * Calls {@link de.gaalop.cfg.ControlFlowVisitor#visit(AssignmentNode)} on the <code>visitor</code> object.
     *
     * @param visitor The visitor that the method is called on.
     */
    public void accept(ControlFlowVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public void replaceExpression(Expression old, Expression newExpression) {
    	if (value == old) {
    		value = newExpression;
    	} else {
    		// recursively try to replace expression
    		value.replaceExpression(old, newExpression);
    	}
    }

    /**
     * Converts this assignment into a human readable string by calling the <code>toString</code> methods on both
     * the variable and value and then outputting:
     * <code>variable := value</code>.
     *
     * @return A human readable representation of this assignment node.
     */
    @Override
    public String toString() {
        return variable + " := " + value;
    }
}
