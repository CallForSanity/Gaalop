package de.gaalop.cfg;

/**
 * This class provides "empty" implementations of the {@link ControlFlowVisitor} interface. Classes which need only a
 * subset of methods defined in {@link ControlFlowVisitor} can extend this class in order to override them accordingly.
 * Empty implementations mean that no transformations are applied to visited nodes. For each node type, the successor is
 * visited by default.
 * 
 * @author Christian Schwinn
 * 
 */
public class EmptyControlFlowVisitor implements ControlFlowVisitor {

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the successor node by default.
	 */
	@Override
	public void visit(StartNode node) {
		node.getSuccessor().accept(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the successor node by default.
	 */
	@Override
	public void visit(AssignmentNode node) {
		node.getSuccessor().accept(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the successor node by default.
	 */
	@Override
	public void visit(StoreResultNode node) {
		node.getSuccessor().accept(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the positive part, the negative part and finally the successor node by default.
	 */
	@Override
	public void visit(IfThenElseNode node) {
		node.getPositive().accept(this);
		node.getNegative().accept(this);
		node.getSuccessor().accept(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation does not call a successor's visit method.
	 */
	@Override
	public void visit(BlockEndNode node) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the successor node by default.
	 */
	@Override
	public void visit(LoopNode node) {
		node.getSuccessor().accept(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the successor node by default.
	 */
	@Override
	public void visit(BreakNode node) {
		node.getSuccessor().accept(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This is an entirely empty implementation.
	 */
	@Override
	public void visit(EndNode node) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the successor node by default.
	 */
	@Override
	public void visit(Macro node) {
		node.getSuccessor().accept(this);
	}

}
