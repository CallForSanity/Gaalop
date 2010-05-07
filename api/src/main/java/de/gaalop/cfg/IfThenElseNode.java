package de.gaalop.cfg;

import de.gaalop.dfg.Expression;

/**
 * Models a node for if-then(-else) statements. It consists of an {@link Expression} modeling the condition to be
 * evaluated, the first statement to be executed for the positive case and the first statement to be executed for the
 * negative case. The negative part is optional and is supposed to be represented by a {@link BlockEndNode} when there
 * is only a positive part. The next statement in the control flow graph to be executed is implicitly determined by this
 * node's successor.
 * 
 * @author Christian Schwinn
 * 
 */
public class IfThenElseNode extends SequentialNode {

	/** Evaluation condition. */
	private Expression condition;
	/** First statement to be evaluated when condition is true. */
	private Node positive;
	/** First statement to be evaluated when condition is false. */
	private Node negative;
	/** Whether this node represents the special case of an else-if part. */
	private boolean elseif;

	public IfThenElseNode(ControlFlowGraph graph, Expression condition) {
		super(graph);
		this.condition = condition;
	}

	/**
	 * @return the evaluation condition
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * @return the first node to be executed when <i>condition</i> evaluates to true
	 */
	public Node getPositive() {
		return positive;
	}

	/**
	 * Sets the property to be an else-if part for this node.
	 * 
	 * @param elseif whether this node is an else-if part.
	 */
	public void setElseIf(boolean elseif) {
		this.elseif = elseif;
	}

	/**
	 * Returns the else-if property of this node.
	 * 
	 * @return whether this node is an else-if part.
	 */
	public boolean isElseIf() {
		return elseif;
	}

	/**
	 * Sets the first node to be executed when the evaluation condition evaluates to true.
	 * 
	 * @param first control flow node to be executed in the positive case
	 */
	public void setPositive(Node positive) {
		this.positive = positive;
	}

	/**
	 * @return the first node to be executed when <i>condition</i> evaluates to false. If there is no negative part,
	 *         this function returns a {@link BlockEndNode}.
	 */
	public Node getNegative() {
		return negative;
	}

	/**
	 * Sets the first node to be executed when the evaluation condition evaluates to false.
	 * 
	 * @param first control flow nodes to be executed in the negative case. Should be a {@link BlockEndNode} in case of
	 *            a non-existent else part.
	 */
	public void setNegative(Node negative) {
		this.negative = negative;
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public void replaceExpression(Expression old, Expression newExpression) {
		if (condition == old) {
			condition = newExpression;
		} else {
			condition.replaceExpression(old, newExpression);
		}
		replaceSubtree(positive, old, newExpression);
		if (negative != null) {
			replaceSubtree(negative, old, newExpression);
		}
	}
	
	private void replaceSubtree(Node root, Expression old, Expression newExpression) {
		if (root instanceof BlockEndNode || root instanceof EndNode) {
			return;
		} else if (root instanceof SequentialNode) {
			root.replaceExpression(old, newExpression);
			replaceSubtree(((SequentialNode) root).getSuccessor(), old, newExpression);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("if(" + condition + ")...");
		// sb.append("[");
		//
		// sb.append("{");
		// sb.append(positive);
		// sb.append("...},");
		//
		// sb.append("{");
		// sb.append(negative);
		// sb.append("...}");
		//
		// sb.append("]");
		return sb.toString();
	}

}
