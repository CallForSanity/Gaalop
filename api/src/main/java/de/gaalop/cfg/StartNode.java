package de.gaalop.cfg;

/**
 * This type of node represents the start of control flow in a control flow
 * graph. It has one successor node.
 *
 * @author Sebastian Hartte
 * @version 1.0
 * @since 1.0
 */
public final class StartNode extends SequentialNode {
    /**
     * Constructs a new StartNode in a control flow graph. This method should only be called by the constructor of
     * ControlFlowGraph.
     *
     * @param graph The graph that this node will belong to.
     */
    StartNode(ControlFlowGraph graph) {
        super(graph);
    }

    /**
     * Calls the {@link de.gaalop.cfg.ControlFlowVisitor#visit(StartNode)} method in a visitor.
     *
     * @param visitor The visitor object that the method will be called on.
     */
    @Override
    public void accept(ControlFlowVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * This method will always throw an UnsupportedOperationException since a StartNode must not have a predecessor.
     *
     * @param node The node that should be removed from the predecessors of this node.
     */
    @Override
    public void removePredecessor(Node node) {
        throw new UnsupportedOperationException("Start nodes do not have any predecessors that could be removed.");
    }

    /**
     * This method will always throw an UnsupportedOperationException since a StartNode must not have a predecessor.
     *
     * @param node The node that should be added to the predecessors of this node.
     */
    @Override
    public void addPredecessor(Node node) {
        throw new UnsupportedOperationException("The start node must not have any predecessors.");
    }
    
    @Override
    public StartNode copyElements(ControlFlowGraph graph) {
    	throw new UnsupportedOperationException("The start node is not supposed to be copied.");
    }
    
    @Override
    public String toString() {
    	return "Start";
    }
}
