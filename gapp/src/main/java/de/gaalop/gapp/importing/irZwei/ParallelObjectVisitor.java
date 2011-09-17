package de.gaalop.gapp.importing.irZwei;

/**
 *
 * @author Christian Steinmetz
 */
public interface ParallelObjectVisitor {

    public Object visitSummands(Summands summands, Object arg);
    public Object visitFactors(Factors factors, Object arg);
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg);
    public Object visitExpressionContainer(ExpressionContainer expressionContainer, Object arg);
    public Object visitGAPPValueHolderContainer(GAPPValueHolderContainer gAPPValueHolderContainer, Object arg);

}
