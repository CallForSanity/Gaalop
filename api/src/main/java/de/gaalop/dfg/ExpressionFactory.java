package de.gaalop.dfg;

/**
 * This class contains factory methods for additional convenience.
 */
public final class ExpressionFactory {

	private ExpressionFactory() {
	}

	/**
	 * This class is the base class for creators of binary operations. This class uses the template method/factory
	 * method pattern to decide which operation should be created.
	 * 
	 * @param <T> The created type.
	 */
	private static abstract class BinaryCreator<T extends BinaryOperation> {
		protected abstract T create(Expression left, Expression right);

		/**
		 * Creates a tree of concatenated binary expressions that operate on the given array of operands.
		 * 
		 * @param operands The operations to operate on.
		 * @return The root expression of the created expression tree.
		 */
		public T createBinary(Expression... operands) {
			if (operands.length < 2) {
				throw new IllegalArgumentException("At least two arguments are required.");
			}

			int i = operands.length - 2;
			Expression lastOperand = operands[operands.length - 1];
			T result;

			do {
				lastOperand = result = create(operands[i], lastOperand);
			} while (--i >= 0);

			return result;
		}
	}

	/**
	 * This is a class that creates Addition nodes.
	 */
	private static final class AdditionCreator extends BinaryCreator<Addition> {
		@Override
		protected Addition create(Expression left, Expression right) {
			return new Addition(left, right);
		}
	}

	/**
	 * Constructs an expression tree that models the sum of all given operands.
	 * 
	 * @param operands The operands for the sum. A deep copy of each expression will be created. At least two operands
	 *            are required.
	 * @return A new Addition expression that represents the sum of the operands.
	 */
	public static Addition sum(Expression... operands) {
		return new AdditionCreator().createBinary(operands);
	}

	/**
	 * This is a class that creates Subtraction nodes.
	 */
	private static final class SubtractionCreator extends BinaryCreator<Subtraction> {
		@Override
		protected Subtraction create(Expression left, Expression right) {
			return new Subtraction(left, right);
		}
	}

	/**
	 * Constructs an expression tree that models the subtraction of all given operands.
	 * 
	 * @param operands The operands for the subtraction. A deep copy of each expression will be created. At least two
	 *            operands are required.
	 * @return A new Addition expression that represents the subtraction of the operands.
	 */
	public static Subtraction subtract(Expression... operands) {
		return new SubtractionCreator().createBinary(operands);
	}

	/**
	 * This is a creator class for multiplication expressions.
	 */
	private static final class MultiplicationCreator extends BinaryCreator<Multiplication> {
		@Override
		protected Multiplication create(Expression left, Expression right) {
			return new Multiplication(left, right);
		}
	}

	/**
	 * Constructs an expression that represents the multiplication of all given factors.
	 * 
	 * @param factors The factors of the product. At least two have to be present. A deep copy of each operand is
	 *            created.
	 * @return A Multiplication object that represents the product of all factors.
	 */
	public static Multiplication product(Expression... factors) {
		return new MultiplicationCreator().createBinary(factors);
	}

	/**
	 * This is a creator class for division expressions.
	 */
	private static final class DivisionCreator extends BinaryCreator<Division> {
		@Override
		protected Division create(Expression left, Expression right) {
			return new Division(left, right);
		}
	}

	/**
	 * Constructs an expression that represents the division of all given factors.
	 * 
	 * @param factors The factors of the product. At least two have to be present. A deep copy of each operand is
	 *            created.
	 * @return A Division object that represents the division of all factors.
	 */
	public static Division divide(Expression... factors) {
		return new DivisionCreator().createBinary(factors);
	}

	/**
	 * An creator for outer products.
	 */
	private static final class OuterProductCreator extends BinaryCreator<OuterProduct> {
		@Override
		public OuterProduct create(Expression left, Expression right) {
			return new OuterProduct(left, right);
		}
	}

	/**
	 * Constructs an expression that represents the outer product of all given factors in the order they are given.
	 * 
	 * @param factors The factors of the resulting outer product. At least two have to be present. A deep copy of each
	 *            is created.
	 * @return An OuterProduct object representing the outer product of all given factors.
	 */
	public static OuterProduct wedge(Expression... factors) {
		return new OuterProductCreator().createBinary(factors);
	}

	/**
	 * Creates an exponentation node that squares the given expression.
	 * 
	 * @param base The expression that should be squared. A deep copy will be created.
	 * @return An exponentation object that exponentiates the given base with a float constant of 2.0.
	 */
	public static Multiplication square(Expression base) {
		return new Multiplication(base, base.copy());
	}

	/**
	 * Creates an expression node that negates the given expression.
	 * 
	 * @param value The expression that should be negated. A deep copy will be created.
	 * @return A Negation object that negates the given expression.
	 */
	public static Negation negate(Expression value) {
		return new Negation(value.copy());
	}

	/**
	 * A creator for logical and statements.
	 */
	private static final class LogicalAndCreator extends BinaryCreator<LogicalAnd> {
		@Override
		protected LogicalAnd create(Expression left, Expression right) {
			return new LogicalAnd(left, right);
		}
	}

	/**
	 * Constructs an expression that represents the logical and of all given elements in the order they are given.
	 * 
	 * @param booleanValues The values of the resulting conjunction. At least two have to be present. A deep copy of
	 *            each is created.
	 * @return A LogicalAnd object representing the conjunction of all given boolean values.
	 */
	public static LogicalAnd and(Expression... booleanValues) {
		return new LogicalAndCreator().createBinary(booleanValues);
	}

	/**
	 * Creates a negated version of the given logical expression.
	 * 
	 * @param expression logical expression to be negated
	 * @return negated logical expression
	 */
	public static LogicalNegation logicalNegation(Expression expression) {
		return new LogicalNegation(expression);
	}
}
