package de.gaalop.cfg;

import java.util.ArrayList;
import java.util.List;

/**
 * This visitor collects all nodes that store a result.
 */
public class FindStoreOutputNodes implements ControlFlowVisitor {

	private List<StoreResultNode> nodes = new ArrayList<StoreResultNode>();

	public List<StoreResultNode> getNodes() {
		return nodes;
	}

	@Override
	public void visit(StartNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(ExpressionStatement node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(StoreResultNode node) {
		nodes.add(node);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(EndNode node) {
	}

	@Override
	public void visit(IfThenElseNode node) {
		node.getPositive().accept(this);
		node.getNegative().accept(this);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(LoopNode node) {
		node.getBody().accept(this);
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(BreakNode breakNode) {
		breakNode.getSuccessor().accept(this);
	}

	@Override
	public void visit(BlockEndNode node) {
		// nothing to do
	}

	@Override
	public void visit(Macro node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(ColorNode node) {
		node.getSuccessor().accept(this);
	}
}
