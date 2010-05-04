package de.gaalop.dfg;

public class FunctionArgument extends Variable {
	
	int index;
	
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
