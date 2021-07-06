package de.gaalop.dfg;

import static de.gaalop.dfg.ToStringUtil.bracketComposite;

/**
 * This class models the relation <, <=, >= or > of two values. The concrete relation is specified by the type.
 * 
 * @author Christian Schwinn
 */
public final class Relation extends BinaryOperation {

  /**
   * Models a concrete binary relation, e.g. <.
   */
  public enum Type {

    /**
     * Less than ( < )
     */
    LESS {
      @Override
      public String toString() {
        return " < ";
      }
    },

    /**
     * Less or equal ( <= )
     */
    LESS_OR_EQUAL {
      @Override
      public String toString() {
        return " <= ";
      }
    },

    /**
     * Greater or equal ( >= )
     */
    GREATER_OR_EQUAL {
      @Override
      public String toString() {
        return " >= ";
      }
    },

    /**
     * Greater than ( > )
     */
    GREATER {
      @Override
      public String toString() {
        return " > ";
      }
    }
    ,
    
    COEFFICIENT {
        @Override
      public String toString() {
        return " | ";
      }
    }

  }
  
  /** Type representing the concrete relation. **/
  private final Type type;

  /**
   * Constructs a new node that models <code>left <code>relation</code> right</code>.
   * 
   * @param left The left operand of the relation.
   * @param right The right operand of the relation.
   */
  public Relation(Expression left, Expression right, Type type) {
    super(left, right);
    this.type = type;
  }

  /**
   * Converts this node into a human readable string.
   * 
   * @return The string "<code>left relation right</code>", where left and right are the results of the toString methods of
   * the left and right operand and relation is the concrete relation, e.g. <.
   */
  public String toString() {
    return bracketComposite(getLeft()) + type + bracketComposite(getRight());
  }

  @Override
  public Expression copy() {
	return new Relation(getLeft().copy(), getRight().copy(), type);
  }

  /**
   * Calls the {@link ExpressionVisitor#visit(Relation)} method on a visitor object.
   * 
   * @param visitor The visitor object to call the method on.
   */
  @Override
  public void accept(ExpressionVisitor visitor) {
    visitor.visit(this);
  }

  public String getTypeString() {
    return type.toString();
  }
  
  public Type getType() {
	return type;
}

}
