package de.gaalop.cfg;

public class EmptyControlFlowVisitor implements ControlFlowVisitor {

	@Override
	public void visit(StartNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(StoreResultNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		node.getPositive().accept(this);
		node.getNegative().accept(this);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(BlockEndNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(LoopNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(BreakNode node) {
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(EndNode node) {
	}

	@Override
	public void visit(Macro node) {
		node.getSuccessor().accept(this);
	}

}
