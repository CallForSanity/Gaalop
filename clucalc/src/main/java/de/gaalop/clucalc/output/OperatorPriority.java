package de.gaalop.clucalc.output;

import de.gaalop.dfg.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Models the priority of different operators. Two expressions can be compared using the
 * {@link #hasLowerPriority(Expression, Expression)} method. This is important since parenthesis
 * have to be introduced if necessary.
 */
public class OperatorPriority {

  private static final Map<Class<? extends Expression>, Integer> OPERATOR_PRIORITY;

  static {
    OPERATOR_PRIORITY = new HashMap<Class<? extends Expression>, Integer>();

    // Operator priority from high to low
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

  /**
   * Checks whether a child expression has a lower priority than the parent expression and needs to
   * be put into parenthesis.
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
