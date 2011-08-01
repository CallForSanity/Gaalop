package de.gaalop.dfg;

import java.util.Set;

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
                oldVar.getName() == name &&
                oldVar.getMinValue() == minValue &&
                oldVar.getMaxValue() == maxValue &&
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
                    oldVar.getName() == name &&
                    oldVar.getMinValue() == minValue &&
                    oldVar.getMaxValue() == maxValue &&
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

    public String getBladeName()
    {
	switch(bladeIndex)
	{
		case 0:
			return "1";

		case 1:
			return "e1";
		case 2:
			return "e2";
		case 3:
			return "e3";
		case 4:
			return "einf";
		case 5:
			return "e0";

		case 6:
			return "e1^e2";
		case 7:
			return "e1^e3";
		case 8:
			return "e1^einf";
		case 9:
			return "e1^e0";
		case 10:
			return "e2^e3";
		case 11:
			return "e2^einf";
		case 12:
			return "e2^e0";
		case 13:
			return "e3^einf";
		case 14:
			return "e3^e0";
		case 15:
			return "einf^e0";

		case 16:
			return "e1^e2^e3";
		case 17:
			return "e1^e2^einf";
		case 18:
			return "e1^e2^e0";
		case 19:
			return "e1^e3^einf";
		case 20:
			return "e1^e3^e0";
		case 21:
			return "e1^einf^e0";
		case 22:
			return "e2^e3^einf";
		case 23:
			return "e2^e3^e0";
		case 24:
			return "e2^einf^e0";
		case 25:
			return "e3^einf^e0";

		case 26:
			return "e1^e2^e3^einf";
		case 27:
			return "e1^e2^e3^e0";
		case 28:
			return "e1^e2^einf^e0";
		case 29:
			return "e1^e3^einf^e0";
		case 30:
			return "e2^e3^einf^e0";

		case 31:
			return "e1^e2^e3^einf^e0";
		default:
			return "error";
	}
    }

    public String getBladeHandle()
    {
	switch(bladeIndex)
	{
		case 0:
			return "SCALAR";

		case 1:
			return "E1";
		case 2:
			return "E2";
		case 3:
			return "E3";
		case 4:
			return "EINF";
		case 5:
			return "E0";

		case 6:
			return "E12";
		case 7:
			return "E13";
		case 8:
			return "E1INF";
		case 9:
			return "E10";
		case 10:
			return "E23";
		case 11:
			return "E2INF";
		case 12:
			return "E20";
		case 13:
			return "E3INF";
		case 14:
			return "E30";
		case 15:
			return "EINF0";

		case 16:
			return "E123";
		case 17:
			return "E12INF";
		case 18:
			return "E120";
		case 19:
			return "E13INF";
		case 20:
			return "E130";
		case 21:
			return "E1INF0";
		case 22:
			return "E23INF";
		case 23:
			return "E230";
		case 24:
			return "E2INF0";
		case 25:
			return "E3INF0";

		case 26:
			return "E123INF";
		case 27:
			return "E1230";
		case 28:
			return "E12INF0";
		case 29:
			return "E13INF0";
		case 30:
			return "E23INF0";

		case 31:
			return "E123INF0";
		default:
			return "error";
	}
   }

   public void gcdDefinition(StringBuilder code,Set<String> assigned,String suffix,boolean gcdMetaInfo)
   {
	String componentName = getName().replace(suffix, "") + '_' + getBladeHandle();
	if(gcdMetaInfo && !assigned.contains(componentName))
	{
		code.append("#pragma gcd multivector_component ");
		code.append(getName().replace(suffix, ""));
		code.append(' ');
		code.append(getBladeHandle());
		code.append(' ');
		code.append(getBladeName());
		code.append(' ');
		code.append(getBladeIndex());
		code.append('\n');
		code.append("const float ");
		code.append(componentName);
		code.append(" = ");
			
		assigned.add(componentName);
	}
   }

}

