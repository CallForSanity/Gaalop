package de.gaalop.cfg;

/**
 * This node models an empty node as mechanism for detection of the end of a block.
 *
 * @author Christian Schwinn
 */
public final class BlockEndNode extends SequentialNode {
	
	private SequentialNode base;

    /**
     * Constructs a block end node.
     *
     * @param graph    The control flow graph the new node should belong to.
     */
    public BlockEndNode(ControlFlowGraph graph, SequentialNode base) {
        super(graph);
        this.base = base;
    }

    /**
     * Calls {@link de.gaalop.cfg.ControlFlowVisitor#visit(BlockEndNode)} on the <code>visitor</code> object.
     *
     * @param visitor The visitor that the method is called on.
     */
        @Override
    public void accept(ControlFlowVisitor visitor) {
        visitor.visit(this);
    }
    
    public SequentialNode getBase() {
		return base;
	}
    
//    @Override
//    public Node getSuccessor() {
//      throw new UnsupportedOperationException("A block end node is not supposed to have a successor");
//    }
//    
//    @Override
//    void setSuccessor(Node successor) {
//      throw new UnsupportedOperationException("A block end node is not supposed to have a successor");
//    }
    
    @Override
    public BlockEndNode copyElements(ControlFlowGraph graph) {
    	return new BlockEndNode(graph, null);
    }
    
    /**
     * Has to be called each time {@link #copyElements()} is called on a block end node to update the new base. 
     * 
     * @param newBase new node where this block end belongs to
     */
    void updateBase(SequentialNode newBase) {
    	base = newBase;
    }

    @Override
    public String toString() {
        return "<block end>";
    }
}
