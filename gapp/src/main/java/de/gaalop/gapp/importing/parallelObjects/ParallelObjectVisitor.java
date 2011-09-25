package de.gaalop.gapp.importing.parallelObjects;

/**
 * Declares an interface of a visitor for ParallelObjects
 * @author Christian Steinmetz
 */
public interface ParallelObjectVisitor {

    public Object visitSum(Sum sum, Object arg);
    public Object visitProduct(Product product, Object arg);
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg);
    public Object visitConstant(Constant constant, Object arg);
    public Object visitMvComponent(MvComponent mvComponent, Object arg);
    public Object visitDotProduct(DotProduct dotProduct, Object arg);
    public Object visitVariable(ParVariable variable, Object arg);
    
}
