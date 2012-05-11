package de.gaalop.gaalet.output;

import de.gaalop.dfg.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class encodes the operator priorities for C/C++
 */
public class OperatorPriority {

    private static final Map<Class<? extends Expression>, Integer> OPERATOR_PRIORITY;

    static {
        OPERATOR_PRIORITY = new HashMap<Class<? extends Expression>, Integer>();

        // Operator priority from high to low
        OPERATOR_PRIORITY.put(FloatConstant.class, 1000);
        OPERATOR_PRIORITY.put(Variable.class, 1000);
        OPERATOR_PRIORITY.put(MultivectorComponent.class, 1000);
        OPERATOR_PRIORITY.put(MathFunctionCall.class, 1000);
        OPERATOR_PRIORITY.put(Exponentiation.class, 1000);
        OPERATOR_PRIORITY.put(Negation.class, 990);
        OPERATOR_PRIORITY.put(Multiplication.class, 960);
        OPERATOR_PRIORITY.put(Division.class, 960);
        OPERATOR_PRIORITY.put(Subtraction.class, 950);
        OPERATOR_PRIORITY.put(Addition.class, 950);
        OPERATOR_PRIORITY.put(Relation.class, 940);
    }

    /**
     * Checks whether a child expression has a lower priority than the parent expression
     * and needs to be put into parenthesis.
     *
     * @param parent The parent expression that contains the child expression.
     * @param child The child expression that will be output.
     * @return True if parenthesis need to be used.
     */
    public static boolean hasLowerPriority(Expression parent, Expression child) {
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
