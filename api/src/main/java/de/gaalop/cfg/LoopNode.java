package de.gaalop.cfg;

import java.util.HashSet;
import java.util.Set;

import de.gaalop.dfg.Expression;

public class LoopNode extends SequentialNode {

	private SequentialNode body;
	private Set<Expression> terminationConditions = new HashSet<Expression>();
	private int iterations;

	public LoopNode(ControlFlowGraph graph) {
		super(graph);
	}

	/**
	 * Sets the number of iterations. This can either be specified in advance, e.g. by a pragma value in the input code
	 * or determined at runtime, e.g. by inspecting the loop body.
	 * 
	 * @param iterations number of iterations, e.g. for unrolling
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	
	/**
	 * Returns the number of iterations. This can be used to unroll the loop, for instance.
	 * 
	 * @return number of iterations
	 */
	public int getIterations() {
		return iterations;
	}

	public void setTermination(Set<Expression> conditions) {
		terminationConditions.addAll(conditions);
	}

	public void setBody(SequentialNode body) {
		this.body = body;
	}

	public SequentialNode getBody() {
		return body;
	}

	@Override
	public void replaceSuccessor(Node oldSuccessor, Node newSuccessor) {
		if (oldSuccessor == body) {
			body = (SequentialNode) newSuccessor;
		} else {
			super.replaceSuccessor(oldSuccessor, newSuccessor);
		}
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public LoopNode copyElements() {
		LoopNode copy = new LoopNode(getGraph());
		Set<Expression> newConditions = new HashSet<Expression>();
		for (Expression e : terminationConditions) {
			newConditions.add(e.copy());
		}
		copy.setTermination(newConditions);

		SequentialNode newBody = body.copy();
		copy.setBody(newBody);
		copySubtree(newBody);

		return copy;
	}

	private void copySubtree(SequentialNode root) {
		if (root instanceof BlockEndNode) {
			return;
		} else if (root.getSuccessor() instanceof SequentialNode) {
			SequentialNode successor = (SequentialNode) root.getSuccessor();
			SequentialNode newSuccessor = successor.copy();
			root.replaceSuccessor(successor, newSuccessor);
			copySubtree(newSuccessor);
		}
	}

	@Override
	public String toString() {
		return "loop { " + body + " ... }";
	}

}
