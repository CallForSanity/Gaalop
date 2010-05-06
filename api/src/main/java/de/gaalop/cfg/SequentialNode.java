package de.gaalop.cfg;

/**
 * This class models a control flow node that has one and only one successor.
 * 
 * @author Sebastian Hartte
 * @version 1.0
 * @since 1.0
 */
public abstract class SequentialNode extends Node {

	private Node successor;

	/**
	 * Constructs a new sequential node.
	 * 
	 * @param graph The control flow graph this node should belong to.
	 */
	public SequentialNode(ControlFlowGraph graph) {
		super(graph);
	}

	/**
	 * Returns the node that will gain control once this sequential statement has been executed.
	 * 
	 * @return The successor node for this sequential node.
	 */
	public Node getSuccessor() {
		return successor;
	}

	/**
	 * Overrides the successor node with the given one.
	 * 
	 * @param successor successor node to be replaced
	 */
	void setSuccessor(Node successor) {
		this.successor = successor;
	}

	/**
	 * Inserts a node after this sequential node and its successor. This method will rewire the nodes to keep the graph
	 * consistent. It will do the following:
	 * <ol>
	 * <li>Remove the connection between this node and its successor.</li>
	 * <li>Add a bi-directional connection between this node and the new node.</li>
	 * <li>Add a bi-directional connection between the new node and this nodes old successor.</li>
	 * </ol>
	 * 
	 * @param newNode The new node that should be inserted in the control flow after this one.
	 */
	public void insertAfter(SequentialNode newNode) {
		Node oldSuccessor = successor;
		successor = newNode;
		newNode.addPredecessor(this);
		if (oldSuccessor != null) {
			newNode.successor = oldSuccessor;
			oldSuccessor.removePredecessor(this);
			oldSuccessor.addPredecessor(newNode);
		}
	}

	@Override
	public void replaceSuccessor(Node oldSuccessor, Node newSuccessor) {
		if (successor == oldSuccessor) {
			successor = null;
			oldSuccessor.removePredecessor(this);
			newSuccessor.addPredecessor(this);
			successor = newSuccessor;
		}
	}
}
