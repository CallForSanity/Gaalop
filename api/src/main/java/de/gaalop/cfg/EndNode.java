package de.gaalop.cfg;

/**
 * This type of node is the end of control flow in a control flow graph. It
 * has one or more predecessor nodes.
 *
 * @author Sebastian Hartte
 * @version 1.0
 * @since 1.0
 */
public final class EndNode extends Node {
    /**
     * Constructs a new EndNode in a control flow graph. This method should only be called by the constructor of
     * ControlFlowGraph.
     *
     * @param graph The graph that this node will belong to.
     */
    EndNode(ControlFlowGraph graph) {
        super(graph);
    }

    /**
     * This method has no effect since an end node is not supposed to have a successor.
     */
    @Override
    public void replaceSuccessor(Node oldSuccessor, Node newSuccessor) {
        /*
            Please note that we fail silently here as specified in the documentation
            for Node. This node cannot have any successors, because of that
            we cannot replace any either.
        */
    }

    /**
     * This method calls {@link de.gaalop.cfg.ControlFlowVisitor#visit(EndNode)} on a visitor.
     *
     * @param visitor The visitor object that the visit method will be called on.
     */
    @Override
    public void accept(ControlFlowVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
    	return "End";
    }
}
