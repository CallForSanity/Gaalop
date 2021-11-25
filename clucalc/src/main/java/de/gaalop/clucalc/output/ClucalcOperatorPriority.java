package de.gaalop.clucalc.output;

import de.gaalop.OperatorPriority;
import de.gaalop.dfg.*;

/**
 * Models the priority of different operators. Two expressions can be compared using the
 * {@link #hasLowerPriority(Expression, Expression)} method. This is important since parenthesis
 * have to be introduced if necessary.
 */
public class ClucalcOperatorPriority extends OperatorPriority {

    @Override
    protected void assignPriorities() {
        OPERATOR_PRIORITY.put(BaseVector.class, 1000);
        OPERATOR_PRIORITY.put(FloatConstant.class, 1000);
        OPERATOR_PRIORITY.put(Variable.class, 1000);
        OPERATOR_PRIORITY.put(MultivectorComponent.class, 1000);
        OPERATOR_PRIORITY.put(MathFunctionCall.class, 1000);
        OPERATOR_PRIORITY.put(Negation.class, 990);
        OPERATOR_PRIORITY.put(Exponentiation.class, 985);
        OPERATOR_PRIORITY.put(OuterProduct.class, 980);
        OPERATOR_PRIORITY.put(InnerProduct.class, 970);
        OPERATOR_PRIORITY.put(Multiplication.class, 960);
        OPERATOR_PRIORITY.put(Division.class, 960);
        OPERATOR_PRIORITY.put(Subtraction.class, 950);
        OPERATOR_PRIORITY.put(Addition.class, 940);
        OPERATOR_PRIORITY.put(Relation.class, 930);
        OPERATOR_PRIORITY.put(LogicalAnd.class, 930);
        OPERATOR_PRIORITY.put(LogicalOr.class, 930);
        OPERATOR_PRIORITY.put(Equality.class, 920);
        OPERATOR_PRIORITY.put(Inequality.class, 920);
    }

}
