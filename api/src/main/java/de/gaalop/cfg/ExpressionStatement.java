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
		visitor.visit(this);
	}
	
	@Override
	public void replaceExpression(Expression old, Expression newExpression) {
		if (expression == old) {
			expression = newExpression;
		} else {
			expression.replaceExpression(old, newExpression);
		}
	}
	
	@Override
	public ExpressionStatement copyElements() {
		return new ExpressionStatement(getGraph(), expression.copy());
	}
	
	@Override
	public String toString() {
		return expression.toString();
	}

}
