package de.gaalop.dfg;

/**
 * This class represents a dataflow graph node that models a variable.
 */
public class Variable implements Expression {

    private String name;

    /**
     * Constructs a new node modelling a named variable.
     *
     * @param name The name of the variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the variable.
     * @return The string containing this variables name.
     */
    public String getName() {
        return name;
    }

    /**
     * Converts this variable node into a human readable string.
     * @return A string containing the name of this variable.
     */
    public String toString() {
        return name;
    }

    @Override
    public Expression copy() {
        return new Variable(this.name);
    }

    /**
     * @return false
     */
    @Override
    public boolean isComposite() {
        return false;
    }

    /**
     * Calls {@link de.gaalop.dfg.ExpressionVisitor#visit(Variable)} on a visitor.
     * @param visitor The object that the visit method should be called on.
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Compares two variables for equality.
     *
     * Two variables are equal if and only if the two variables have the same class and their name is equal.
     *
     * @param o The other object.
     * @return True if and only if this object is equal to the other object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (!name.equals(variable.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
