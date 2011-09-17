package de.gaalop.gapp.importing.parallelObjects;

/**
 *
 * @author Christian Steinmetz
 */
public interface ParallelObjectVisitor {

    public Object visitSum(Sum sum, Object arg);
    public Object visitProduct(Product product, Object arg);
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg);
    public Object visitConstant(Constant constant, Object arg);
    public Object visitMvComponent(MvComponent mvComponent, Object arg);
    
}
