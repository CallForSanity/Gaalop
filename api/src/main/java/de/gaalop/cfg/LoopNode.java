package de.gaalop.cfg;

import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;

public class LoopNode extends SequentialNode {

	private SequentialNode body;
	private int iterations;
	private Variable counter;

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
	
	public void setCounterVariable(Variable counter) {
		this.counter = counter;
	}
	
	public Variable getCounterVariable() {
		return counter;
	}
	
	public void setBody(SequentialNode body) {
		this.body = body;
	}

	public SequentialNode getBody() {
		return body;
	}
	
	@Override
	public void replaceExpression(Expression old, Expression newExpression) {
		replaceSubtree(body, old, newExpression);
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
	public void replaceSuccessor(Node oldSuccessor, Node newSuccessor) {
		if (oldSuccessor == body) {
			newSuccessor.removePredecessor(oldSuccessor);
			newSuccessor.addPredecessor(this);
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
		copy.setIterations(iterations);
		
		SequentialNode newBody = body.copy();
		newBody.removePredecessor(this);
		newBody.addPredecessor(copy);
		copy.setBody(newBody);
		copySubtree(newBody, copy);

		return copy;
	}

	private void copySubtree(SequentialNode root, SequentialNode newBase) {
		if (root instanceof BlockEndNode) {
			((BlockEndNode) root).updateBase(newBase);
			return;
		} else if (root.getSuccessor() instanceof SequentialNode) {
			SequentialNode successor = (SequentialNode) root.getSuccessor();
			SequentialNode newSuccessor = successor.copy();
			root.replaceSuccessor(successor, newSuccessor);
			copySubtree(newSuccessor, newBase);
		}
	}

	@Override
	public String toString() {
		return "loop { " + body + " ... }";
	}

}
