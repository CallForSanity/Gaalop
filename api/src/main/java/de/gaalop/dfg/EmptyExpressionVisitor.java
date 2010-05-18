package de.gaalop.dfg;

/**
 * This class provides an empty implementation of methods defined in the {@link ExpressionVisitor} interface. For
 * visitors that are interested in a subset of DFG nodes only, this class can be used to override relevant methods.
 * 
 * @author Christian Schwinn
 * 
 */
public class EmptyExpressionVisitor implements ExpressionVisitor {
	
	private void handleBinary(Expression left, Expression right) {
		left.accept(this);
		right.accept(this);
	}

	@Override
	public void visit(Subtraction node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(Addition node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(Division node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(InnerProduct node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(Multiplication node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(MathFunctionCall node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(Variable node) {
	}

	@Override
	public void visit(MultivectorComponent node) {
	}

	@Override
	public void visit(Exponentiation node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(FloatConstant node) {
	}

	@Override
	public void visit(OuterProduct node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(BaseVector node) {
	}

	@Override
	public void visit(Negation node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(Reverse node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(LogicalOr node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(LogicalAnd node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(Equality node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(Inequality node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(Relation node) {
		handleBinary(node.getLeft(), node.getRight());
	}

	@Override
	public void visit(FunctionArgument node) {
	}

	@Override
	public void visit(MacroCall node) {
	}

}
