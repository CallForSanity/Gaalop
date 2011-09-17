package de.gaalop.gapp.importing.parallelObjects;

/**
 *
 * @author Christian Steinmetz
 */
public interface ParallelObjectVisitor {

    public Object visitSummands(Summands summands, Object arg);
    public Object visitFactors(Factors factors, Object arg);
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg);
    public Object visitConstant(Constant constant, Object arg);
    public Object visitMvComponent(MvComponent mvComponent, Object arg);
    
}
