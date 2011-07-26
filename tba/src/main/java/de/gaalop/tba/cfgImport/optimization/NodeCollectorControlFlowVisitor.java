package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import java.util.LinkedList;

/**
 * This class collects all nodes in a control flow graph
 * 
 */
public class NodeCollectorControlFlowVisitor implements ControlFlowVisitor {

        private LinkedList<Node> nodeList = new LinkedList<Node>();

        public LinkedList<Node> getNodeList() {
            return nodeList;
        }

        

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
                nodeList.addLast(node);
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(ExpressionStatement node) {
                nodeList.addLast(node);
		node.getSuccessor().accept(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This empty implementation visits the successor node by default.
	 */
	@Override
	public void visit(StoreResultNode node) {
                nodeList.addLast(node);
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
		node.getBody().accept(this);
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

	@Override
	public void visit(ColorNode node) {
                nodeList.addLast(node);
		node.getSuccessor().accept(this);
	}

}
