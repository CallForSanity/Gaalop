package de.gaalop.cfg;

/**
 * This interface provides a method for every concrete node class in a
 * control flow graph. It is the "Visitor" in the visitor design pattern.
 * <p/>
 * Implement this interface if you want to traverse a control flow graph.
 * <p/>
 * To continue traversing the graph in a visit method, the accept method of the
 * successor node has to be called.
 *
 * @author Sebastian Hartte
 * @author Christian Schwinn
 * @version 1.0
 * @since 1.0
 */
public interface ControlFlowVisitor {
    /**
     * This method is called by {@link StartNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(StartNode node);

    /**
     * This method is called by {@link AssignmentNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(AssignmentNode node);


    /**
     * This method is called by {@link de.gaalop.cfg.StoreResultNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(StoreResultNode node);
    
    /**
     * This method is called by {@link de.gaalop.cfg.IfThenElseNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(IfThenElseNode node);
    
    /**
     * This method is called by {@link de.gaalop.cfg.BlockEndNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(BlockEndNode node);

    /**
     * This method is called by {@link de.gaalop.cfg.LoopNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(LoopNode node);
    
    /**
     * This method is called by {@link de.gaalop.cfg.BreakNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(BreakNode node);

	/**
	 * This method is called by {@link de.gaalop.cfg.Macro#accept(ControlFlowVisitor)}.
	 *
	 * @param node The object that called this method.
	 */
	void visit(Macro node);

	/**
	 * This method is called by {@link de.gaalop.cfg.ExpressionStatement#accept(ControlFlowVisitor)}.
	 *
	 * @param node The object that called this method.
	 */
	void visit(ExpressionStatement node);
	
	/**
     * This method is called by {@link de.gaalop.cfg.EndNode#accept(ControlFlowVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(EndNode node);

}
