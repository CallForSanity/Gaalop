package de.gaalop.dfg;

/**
 * This interface needs to be implemented by classes that want to iterate over data flow graphs.
 * <p/>
 * It provides a method for each concrete class that a node in a dataflow graph can be of.
 */
public interface ExpressionVisitor {

    /**
     * This method is called by {@link de.gaalop.dfg.Subtraction#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Subtraction node);

    /**
     * This method is called by {@link de.gaalop.dfg.Addition#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Addition node);

    /**
     * This method is called by {@link de.gaalop.dfg.Division#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Division node);

    /**
     * This method is called by {@link de.gaalop.dfg.InnerProduct#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(InnerProduct node);

    /**
     * This method is called by {@link de.gaalop.dfg.Multiplication#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Multiplication node);

    /**
     * This method is called by {@link de.gaalop.dfg.MathFunctionCall#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(MathFunctionCall node);

    /**
     * This method is called by {@link de.gaalop.dfg.Variable#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Variable node);

    /**
     * This method is called by {@link de.gaalop.dfg.MultivectorComponent#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(MultivectorComponent node);

    /**
     * This method is called by {@link de.gaalop.dfg.Exponentiation#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Exponentiation node);

    /**
     * This method is called by {@link de.gaalop.dfg.FloatConstant#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(FloatConstant node);

    /**
     * This method is called by {@link de.gaalop.dfg.OuterProduct#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(OuterProduct node);

    /**
     * This method is called by {@link de.gaalop.dfg.BaseVector#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(BaseVector node);

    /**
     * This method is called by {@link de.gaalop.dfg.Negation#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Negation node);

    /**
     * This method is called by {@link de.gaalop.dfg.Reverse#accept(ExpressionVisitor)}.
     * @param node The reverse node that called this method.
     */
    void visit(Reverse node);
    
    /**
     * This method is called by {@link de.gaalop.dfg.LogicalOr#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(LogicalOr node);
    
    /**
     * This method is called by {@link de.gaalop.dfg.LogicalAnd#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(LogicalAnd node); 
    
    /**
     * This method is called by {@link LogicalNegation#accept(ExpressionVisitor)}.
     * 
     * @param node The object that called this method.
     */
    void visit(LogicalNegation node);
    
    /**
     * This method is called by {@link de.gaalop.dfg.Equality#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Equality node);
    
    /**
     * This method is called by {@link de.gaalop.dfg.Inequality#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
    void visit(Inequality node);

    /**
     * This method is called by {@link de.gaalop.dfg.Relation#accept(ExpressionVisitor)}.
     *
     * @param relation The object that called this method.
     */
    void visit(Relation relation);

    /**
     * This method is called by {@link de.gaalop.dfg.FunctionArgument#accept(ExpressionVisitor)}.
     *
     * @param node The object that called this method.
     */
	void visit(FunctionArgument node);
	
	/**
	 * This method is called by {@link de.gaalop.dfg.MacroCall#accept(ExpressionVisitor)}.
	 *
	 * @param node The object that called this method.
	 */
	void visit(MacroCall node);

}
