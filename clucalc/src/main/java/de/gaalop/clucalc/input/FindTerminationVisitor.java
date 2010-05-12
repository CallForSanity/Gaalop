package de.gaalop.clucalc.input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;

/**
 * A visitor that tries to find the conditions that lead to the termination of a loop statement. Therefore,
 * {@link IfThenElseNode} nodes are tracked, in order to find combinations of conditions that lead to the termination.
 * Nested conditional statements result in a conjunction of related conditions.
 * 
 * @author Christian Schwinn
 */
public class FindTerminationVisitor extends EmptyControlFlowVisitor {

	private boolean insideLoop = false;
	private final LoopNode loop;
	private Set<Expression> termination = new HashSet<Expression>();
	private List<IfThenElseNode> conditions = new ArrayList<IfThenElseNode>();

	/**
	 * Creates a new visitor that tries to find the termination conditions for the given loop.
	 * 
	 * @param loop loop node for which to find termination conditions
	 */
	public FindTerminationVisitor(LoopNode loop) {
		this.loop = loop;
	}

	/**
	 * Returns the termination conditions for the loop visited by this visitor.
	 * 
	 * @return set of termination conditions
	 */
	public Set<Expression> getTermination() {
		return termination;
	}

	@Override
	public void visit(IfThenElseNode node) {
		if (insideLoop) {
			conditions.add(node);
			node.getPositive().accept(this);
			node.getNegative().accept(this);
			conditions.remove(node);
		}
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(LoopNode node) {
		if (node == loop) {
			insideLoop = true;
			node.getBody().accept(this);
			insideLoop = false;
		} else {
			node.getSuccessor().accept(this);
		}
	}

	@Override
	public void visit(BreakNode node) {
		if (insideLoop) {
			Expression[] expressions = new Expression[conditions.size()];
			for (int i = 0; i < conditions.size(); i++) {
				expressions[i] = conditions.get(i).getCondition();
			}
			if (expressions.length == 1) {
				termination.add(expressions[0]);
			} else if (expressions.length > 1) {
				termination.add(ExpressionFactory.and(expressions));
			}
		}
	}

}
