package de.gaalop;

import de.gaalop.dfg.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class encodes the operator priorities, defaulting to C/C++ priorities
 */
public class OperatorPriority {
    
    protected final Map<Class<? extends Expression>, Integer> OPERATOR_PRIORITY = new HashMap<Class<? extends Expression>, Integer>();

    public OperatorPriority() {
        assignPriorities();
    }
    
    protected void assignPriorities() {
        // Operator priority from high to low
        OPERATOR_PRIORITY.put(BaseVector.class, 1000);
        OPERATOR_PRIORITY.put(FloatConstant.class, 1000);
        OPERATOR_PRIORITY.put(Variable.class, 1000);
        OPERATOR_PRIORITY.put(MultivectorComponent.class, 1000);
        OPERATOR_PRIORITY.put(MathFunctionCall.class, 1000);
        OPERATOR_PRIORITY.put(Exponentiation.class, 1000);
        OPERATOR_PRIORITY.put(OuterProduct.class, 995);
        OPERATOR_PRIORITY.put(Negation.class, 990);
        OPERATOR_PRIORITY.put(Division.class, 965);
        OPERATOR_PRIORITY.put(Multiplication.class, 960);
        OPERATOR_PRIORITY.put(Subtraction.class, 955);
        OPERATOR_PRIORITY.put(Addition.class, 950);
        OPERATOR_PRIORITY.put(Relation.class, 940);
        OPERATOR_PRIORITY.put(LogicalAnd.class, 940);
        OPERATOR_PRIORITY.put(LogicalOr.class, 940);
        OPERATOR_PRIORITY.put(Equality.class, 930);
        OPERATOR_PRIORITY.put(Inequality.class, 930);
    }

    /**
     * Checks whether a child expression has a lower priority than the parent expression
     * and needs to be put into parenthesis.
     *
     * @param parent The parent expression that contains the child expression.
     * @param child The child expression that will be output.
     * @return True if parenthesis need to be used.
     */
    public boolean hasLowerPriority(Expression parent, Expression child) {
        Integer parentPriority = OPERATOR_PRIORITY.get(parent.getClass());
        Integer childPriority = OPERATOR_PRIORITY.get(child.getClass());

        // Default to parenthesis for unknown combinations
        if (parentPriority == null || childPriority == null) {
            return true;
        } else {
            return childPriority < parentPriority;
        }
    }

}
