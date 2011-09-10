package de.gaalop.clucalc.algebra;

import de.gaalop.clucalc.input.GraphBuilder;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.ExpressionFactory;
import de.gaalop.dfg.FloatConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a convenient template method superclass for new algebra modes in CluCalc.
 */
public abstract class AbstractAlgebraMode implements AlgebraMode {
    private Map<String, Expression> constantMap;

    public AbstractAlgebraMode() {
        super();
        createConstants();
    }

    /*
        The constructor delegates the creation of all constants to this method.
    */
    private void createConstants() {
        constantMap = new HashMap<String, Expression>();

        for (int i = 1; i <= getDimension(); ++i) {
            constantMap.put("e" + i, new BaseVector(i));
        }

        constantMap.put("id", new FloatConstant(1.0f));

        // Create the pseudoscalar
        Expression[] bases = new Expression[getDimension()];
        for (int i = 0; i < bases.length; ++i) {
            bases[i] = new BaseVector(i + 1);
        }
        constantMap.put("I", ExpressionFactory.wedge(bases));

        addConstants(constantMap);
    }

    @Override
    public abstract String getDefinitionMethod();

    @Override
    public abstract int[] getSignature();

    /**
     * This method is called by the constructor of AbstractAlgebraMode and allows subclasses to
     * add their own constant definitions. The Expressions in constantMap will be copied by
     * <code>getConstant</code> to create new DFG nodes for constants. Please note that the
     * constuctor of this class will automatically add the base constants e1 to e<n> where <n>
     * is the dimension of this algebra as returned by <code>getDimension</code> as well as
     * id (=1) and I (the wedge of all atomic base vectors).
     */
    protected void addConstants(Map<String, Expression> constants) {
    }

    @Override
    public final int getDimension() {
        if (GraphBuilder.algebraDimension == 0)
            return getSignature().length;
        else
            return GraphBuilder.algebraDimension;
    }

    @Override
    public final boolean isConstant(String identifier) {
        return constantMap.containsKey(identifier);
    }

    @Override
    public final Expression getConstant(String identifier) {
        if (!isConstant(identifier)) {
            throw new IllegalArgumentException("Not a constant identifier: " + identifier);
        }

        return constantMap.get(identifier).copy();
    }
}
