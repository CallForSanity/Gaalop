package de.gaalop.dfg;

/**
 * This class provides an empty implementation of methods defined in the {@link ExpressionVisitor} interface. For
 * visitors that are interested in a subset of DFG nodes only, this class can be used to override relevant methods.
 * 
 * @author Christian Schwinn
 * 
 */
public class EmptyExpressionVisitor implements ExpressionVisitor {

	@Override
	public void visit(Subtraction node) {
	}

	@Override
	public void visit(Addition node) {
	}

	@Override
	public void visit(Division node) {
	}

	@Override
	public void visit(InnerProduct node) {
	}

	@Override
	public void visit(Multiplication node) {
	}

	@Override
	public void visit(MathFunctionCall node) {
	}

	@Override
	public void visit(Variable node) {
	}

	@Override
	public void visit(MultivectorComponent node) {
	}

	@Override
	public void visit(Exponentiation node) {
	}

	@Override
	public void visit(FloatConstant node) {
	}

	@Override
	public void visit(OuterProduct node) {
	}

	@Override
	public void visit(BaseVector node) {
	}

	@Override
	public void visit(Negation node) {
	}

	@Override
	public void visit(Reverse node) {
	}

	@Override
	public void visit(LogicalOr node) {
	}

	@Override
	public void visit(LogicalAnd node) {
	}

	@Override
	public void visit(Equality node) {
	}

	@Override
	public void visit(Inequality node) {
	}

	@Override
	public void visit(Relation relation) {
	}

	@Override
	public void visit(FunctionArgument node) {
	}

	@Override
	public void visit(MacroCall node) {
	}

}
