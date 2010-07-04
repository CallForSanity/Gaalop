package de.gaalop.maple;

import java.util.HashSet;
import java.util.Set;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;


public class UnrollLoopsVisitor implements ControlFlowVisitor {

	private boolean copyMode = false;
	private boolean branchMode = false;
	private SequentialNode currentNode;
	private int loopDepth = 0;
	
	private Set<LoopNode> removeNodes = new HashSet<LoopNode>(); 
	
	/**
	 * Inserts a new node before the current node.
	 * 
	 * @param newNode node to be inserted before the current node
	 */
	private void insertNewNode(SequentialNode newNode) {
		currentNode.insertBefore(newNode);
	}
	
	@Override
	public void visit(StartNode node) {
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(AssignmentNode node) {
		if (copyMode) {
			insertNewNode(node.copy());
		}
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(StoreResultNode node) {
		if (copyMode) {
			insertNewNode(node.copy());
		}		
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(LoopNode node) {
		SequentialNode previousNode = currentNode;
		Node successor = node.getSuccessor();
		loopDepth++;
		if (loopDepth < 2 || branchMode) {
			currentNode = node;
			removeNodes.add(node);
		}
		copyMode = loopDepth > 0;
		int iterations = node.getIterations();
		for (int i = 0; i < iterations; i++) {
			node.getBody().accept(this);
		}
		
		currentNode = previousNode;
		
		loopDepth--;
		copyMode = loopDepth > 0;
		successor.accept(this);
	}
	
	@Override
	public void visit(IfThenElseNode node) {
		// FIXME: check correctness in case of loops in branches 
		if (copyMode) {
			IfThenElseNode newNode = (IfThenElseNode) node.copy();
			insertNewNode(newNode);
			
			branchMode = true;
			newNode.getPositive().accept(this);
			newNode.getNegative().accept(this);
			branchMode = false;
			
			if (newNode.getPositive() instanceof BlockEndNode && newNode.getNegative() instanceof BlockEndNode) {
				node.getGraph().removeNode(newNode);
			}
		} else {
			node.getPositive().accept(this);
			node.getNegative().accept(this);
		}

		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(BlockEndNode node) {
	}
	
	@Override
	public void visit(BreakNode node) {
		node.getGraph().removeNode(node);
	}
	
	@Override
	public void visit(ExpressionStatement node) {
		if (copyMode) {
			insertNewNode(node.copy());
		}
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(Macro node) {
		node.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(EndNode node) {
		for (LoopNode loop : removeNodes) {
			node.getGraph().removeNode(loop);
		}
	}
	
}
