package de.gaalop.gapp.importing.parallelObjects;

/**
 * Declares all types of a ParallelObject
 * @author Christian Steinmetz
 */
public enum ParallelObjectType implements ParallelObjectVisitor {

    summands, factors, extCalculation, constant, mvComponent, dotProduct, inputVariable;

    public static ParallelObjectType getType(ParallelObject object) {
        ParallelObjectType visitor = summands;
        return (ParallelObjectType) object.accept(visitor, null);
    }


    @Override
    public Object visitSum(Sum sum, Object arg) {
        return this.summands;
    }

    @Override
    public Object visitProduct(Product product, Object arg) {
        return this.factors;
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        return this.extCalculation;
    }

    @Override
    public Object visitConstant(Constant constant, Object arg) {
        return this.constant;
    }

    @Override
    public Object visitMvComponent(MvComponent mvComponent, Object arg) {
        return this.mvComponent;
    }

    @Override
    public Object visitDotProduct(DotProduct dotProduct, Object arg) {
        return this.dotProduct;
    }

    @Override
    public Object visitInputVariable(InputVariable inputVariable, Object arg) {
        return this.inputVariable;
    }



}
