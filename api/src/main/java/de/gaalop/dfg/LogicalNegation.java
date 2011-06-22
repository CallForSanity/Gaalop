package de.gaalop.dfg;

/**
 * Models a logical negation of a given operand. 
 * 
 * @author Christian Schwinn
 *
 */
public class LogicalNegation extends UnaryOperation {
	
	public LogicalNegation(Expression operand) {
		super(operand);
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Expression copy() {
		Expression result = new LogicalNegation(getOperand().copy());
    	result.setGAPP(copyGAPP());
    	return result;
	}
	
	@Override
	public String toString() {
		return "!(" + getOperand().toString() + ")" ;
	}

}
