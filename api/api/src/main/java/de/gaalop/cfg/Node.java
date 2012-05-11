package de.gaalop.cfg;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.gaalop.dfg.Expression;

/**
 * This abstract class serves as the base class for all control flow nodes in a control flow graph.
 * <p/>
 * Please note that all implementing classes should provide a meaningful implementation of {@link Object#toString()},
 * {@link Object#hashCode()} and {@link Object#equals(Object)}.
 * 
 * @author Sebastian Hartte
 * @version 1.0
 * @see ControlFlowGraph
 * @since 1.0
 */
public abstract class Node {
	/** All predecessors of this node. */
	private Set<Node> predecessors = new HashSet<Node>();

	/** A reference to the graph that contains this node. */
	private final ControlFlowGraph graph;

	/**
	 * Constructs a new control flow node.
	 * 
	 * @param graph The graph that will contain this node.
	 */
	public Node(ControlFlowGraph graph) {
		this.graph = graph;
	}

	/**
	 * Gets the control flow graph this node belongs to.
	 * 
	 * @return The control flow graph linked to this node.
	 */
	public ControlFlowGraph getGraph() {
		return graph;
	}

	/**
	 * This method must be implemented by every concrete subclass. It should call a visit method in ControlFlowVisitor whose only
	 * parameter has the concrete type of the object calling the method.
	 * <p/>
	 * Please see the Visitor Pattern for more detail.
	 * 
	 * @param visitor The visitor object that the visit method will be called on.
	 */
	public abstract void accept(ControlFlowVisitor visitor);

	/**
	 * Returns all nodes that have this node as their successor.
	 * 
	 * @return An unmodifiable set of nodes that contains all predecessors of this node.
	 */
	public Set<Node> getPredecessors() {
		return Collections.unmodifiableSet(predecessors);
	}

	/**
	 * Adds a predecessor to this node.
	 * <p/>
	 * This method may throw an UnsupportedOperationException if the subclass does not allow predecessors. This is only the case
	 * if this node is the start node.
	 * 
	 * @param node The new predecessor for this node.
	 * @see UnsupportedOperationException
	 */
	public void addPredecessor(Node node) {
		predecessors.add(node);
	}

	/**
	 * This method removes predecessors from this node.
	 * <p/>
	 * This method may throw an UnsupportedOperationException if the implementing node must not have any predecessors. This is
	 * only the case if this node is the start node.
	 * 
	 * @param node The node that should be removed from the predecessors of this node.
	 */
	public void removePredecessor(Node node) {
		predecessors.remove(node);
	}

	/**
	 * Replaces the connection from this node to oldSuccessor with a connection from this node to newSuccessor if it exists.
	 * <p/>
	 * This method can be used to insert or remove nodes in a control flow graph.
	 * 
	 * @param oldSuccessor A node that is currently a successor of this node.
	 * @param newSuccessor A node that should become a successor of this node instead of <code>oldSuccessor</code>.
	 */
	public abstract void replaceSuccessor(Node oldSuccessor, Node newSuccessor);

	/**
	 * Classes that store an {@link Expression} attribute should implement this method to allow recursive replacement of
	 * expressions. This implementation throws an {@link UnsupportedOperationException} by default.
	 * 
	 * @param old expression to be replaced.
	 * @param newExpression
	 */
	public void replaceExpression(Expression old, Expression newExpression) {
		throw new UnsupportedOperationException("Cannot replace an expression. This node type (" + toString()
				+ ") does not have an expression attribute.");
	}

	/**
	 * Inserts another node right before this node.
	 * <p/>
	 * The new node is added as a successor of all predecessors of this node and this node is set as the successor of the new
	 * node.
	 * 
	 * @param newNode The node that should be inserted.
	 */
	public void insertBefore(SequentialNode newNode) {
		newNode.setSuccessor(this);
		Set<Node> predecessors = new HashSet<Node>(getPredecessors());
		for (Node predecessor : predecessors) {
			predecessor.replaceSuccessor(this, newNode);
		}
		predecessors.clear(); // previous predecessors are no predecessors anymore
		addPredecessor(newNode);
	}
}
