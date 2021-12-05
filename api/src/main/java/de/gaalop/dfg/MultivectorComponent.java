package de.gaalop.dfg;


/**
 * This class represents a single multivector component. A multivector component is identified
 * by the variable that represents the multivector itself and the index of the component.
 * The index is an integer between 0 and 2<sup>n</sup>-1 where n is the dimension of the
 * underlying algebra.
 * <p/>
 * The value of a multivector component corresponds to the coefficient of a blade with the same
 * index.
 * <p/>
 * A multivector component should always contain a scalar value.
 */
public final class MultivectorComponent extends Variable {

    private int bladeIndex;

    /**
     * Constructs a new node that reprsents the component of a multivector.
     *
     * @param name       The name of the multivector variable.
     * @param bladeIndex The index of the component. This corresponds to the index of a blade in the algebras blade list.
     * @see de.gaalop.cfg.ControlFlowGraph#getBladeList()
     */
    public MultivectorComponent(String name, int bladeIndex) {
        super(name);
        this.bladeIndex = bladeIndex;
    }

    /**
     * Gets the index of the component modelled by this object.
     *
     * @return The index of the multivector component.
     */
    public int getBladeIndex() {
        return bladeIndex;
    }

    /**
     * A human readable string representation of this multivector component.
     * @return The string "name[bladeIndex]" where name is the name of the multivector and bladeIndex is the index
     * returned by <code>getBladeIndex()</code>.
     */
    @Override
    public String toString() {
        return super.toString() + "[" + bladeIndex + "]";
    }

    /**
     * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(MultivectorComponent)} on a visitor.
     *
     * @param visitor The visitor the method will be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Compares two objects for equality.
     *
     * Two multivector components are equal if their class is the same and both their name and blade index are equal
     * as well.
     *
     * @param o The other object.
     * @return True if and only if the other object is equal to this object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MultivectorComponent that = (MultivectorComponent) o;

        if (bladeIndex != that.bladeIndex) return false;

        return true;
    }
    
    @Override
    public MultivectorComponent copy() {
    	return new MultivectorComponent(getName(), bladeIndex);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + bladeIndex;
        return result;
    }

    @Override
    public void replaceExpression(Expression old, Expression newExpression) {

        if (old instanceof MultivectorComponent && newExpression instanceof MultivectorComponent) {
            MultivectorComponent oldVar = (MultivectorComponent) old;
            MultivectorComponent newVar = (MultivectorComponent) newExpression;

            if (
                oldVar.getName().equals(name) &&
                oldVar.getMinValue().equals(minValue) &&
                oldVar.getMaxValue().equals(maxValue) &&
                oldVar.getBladeIndex() == bladeIndex
               ) {
                    name = newVar.getName();
                    minValue = newVar.getMinValue();
                    maxValue = newVar.getMaxValue();
                    bladeIndex = newVar.getBladeIndex();
            }
        } else
            if (old instanceof MultivectorComponent && newExpression instanceof Variable) {
                MultivectorComponent oldVar = (MultivectorComponent) old;
                Variable newVar = (Variable) newExpression;

                if (
                    oldVar.getName().equals(name)  &&
                    oldVar.getMinValue().equals(minValue) &&
                    oldVar.getMaxValue().equals(maxValue) &&
                    oldVar.getBladeIndex() == bladeIndex
                   ) {
                        name = newVar.getName();
                        minValue = newVar.getMinValue();
                        maxValue = newVar.getMaxValue();
                        bladeIndex = 0;
                }
            } else
                super.replaceExpression(old, newExpression);
    }

}

