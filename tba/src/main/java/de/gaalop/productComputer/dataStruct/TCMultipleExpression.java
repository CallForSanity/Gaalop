package de.gaalop.productComputer.dataStruct;

import java.util.Arrays;

/**
 * Represents a expression with multiple subexpressions
 * @author Christian Steinmetz
 */
public abstract class TCMultipleExpression extends TCExpression {

    protected TCExpression[] expressions;

    public TCMultipleExpression(TCExpression[] expressions) {
        this.expressions = expressions;
    }

    public TCExpression[] getExpressions() {
        return expressions;
    }

    public void setExpressions(TCExpression[] expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TCMultipleExpression other = (TCMultipleExpression) obj;
        if (!Arrays.deepEquals(this.expressions, other.expressions)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Arrays.deepHashCode(this.expressions);
        return hash;
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    /**
     * Builds a string that represents the operation
     * @param c The operator to use
     * @return The builded string
     */
    protected String bracketComposite(char c) {
        if (expressions.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (TCExpression expr: expressions) {
            sb.append(c);
            if (expr.isComposite()) {
                sb.append("(");
                sb.append(expr.toString());
                sb.append(")");
            } else
                sb.append(expr.toString());
        }
        return sb.substring(1);
    }

    /**
     * Adds an expression to this multiple expression
     * @param expression The expression to add
     */
    public void add(TCExpression expression) {
        expressions = Arrays.copyOf(expressions, expressions.length+1);
        expressions[expressions.length-1] = expression;
    }

}
