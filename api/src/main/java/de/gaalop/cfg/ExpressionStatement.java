package de.gaalop.cfg;

import de.gaalop.dfg.Expression;

public class ExpressionStatement extends SequentialNode {
	
	private Expression expression;
	
	public ExpressionStatement(ControlFlowGraph graph, Expression expression) {
		super(graph);
		this.expression = expression;
	}
	
	/**
	 * Returns the associated expression.
	 * 
	 * @return expression
	 */
	public Expression getExpression() {
		return expression;
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		// TODO: implement accept method
	}
	
	@Override
	public String toString() {
		return expression.toString();
	}

}
