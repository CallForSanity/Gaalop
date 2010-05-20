package de.gaalop.cfg;

import java.util.HashSet;
import java.util.Set;

import de.gaalop.dfg.Expression;

public class LoopNode extends SequentialNode {
	
	private SequentialNode body;
	private Set<Expression> terminationConditions = new HashSet<Expression>();

	public LoopNode(ControlFlowGraph graph) {
		super(graph);
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
