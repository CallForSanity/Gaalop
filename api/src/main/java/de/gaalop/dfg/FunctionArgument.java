package de.gaalop.dfg;

/**
 * Represents a reference to an argument passed to a macro. Arguments are referenced by <code>_P(x)</code>, where x is
 * the index to the argument array, starting with 1. Once a macro is called at any location in the input, the macro
 * should be inlined, using the actual parameters rather than this reference.
 * 
 * @author Christian Schwinn
 * 
 */
public class FunctionArgument extends Variable {

	private int index;

	public FunctionArgument(int index) {
		super("_P(" + index + ")");
		this.index = index;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	public int getIndex() {
		return index;
	}

}
