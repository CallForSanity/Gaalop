package de.gaalop.clucalc.input;

import de.gaalop.dfg.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Keeps track of variables that are actually in use. This can be used to detect unused variables in order to remove them from the
 * graph. Used variables can be queried by {@link #getVariables()}.
 */
public class UsedVariablesVisitor implements ExpressionVisitor {

	private Set<Variable> variables = new HashSet<Variable>();

	public Set<Variable> getVariables() {
		return variables;
	}

	private void visitBinaryOperation(BinaryOperation op) {
		op.getLeft().accept(this);
		op.getRight().accept(this);
	}

	@Override
	public void visit(Subtraction subtraction) {
		visitBinaryOperation(subtraction);
	}

	@Override
	public void visit(Addition addition) {
		visitBinaryOperation(addition);
	}

	@Override
	public void visit(Division division) {
		visitBinaryOperation(division);
	}

	@Override
	public void visit(InnerProduct innerProduct) {
		visitBinaryOperation(innerProduct);
	}

	@Override
	public void visit(Multiplication multiplication) {
		visitBinaryOperation(multiplication);
	}

	@Override
	public void visit(MathFunctionCall mathFunctionCall) {
		mathFunctionCall.getOperand().accept(this);
	}

	@Override
	public void visit(Variable variable) {
		variables.add(variable);
	}

	@Override
	public void visit(MultivectorComponent component) {
		variables.add(component);
	}

	@Override
	public void visit(Exponentiation exponentiation) {
		visitBinaryOperation(exponentiation);
	}

	@Override
	public void visit(FloatConstant floatConstant) {
	}

	@Override
	public void visit(OuterProduct outerProduct) {
		visitBinaryOperation(outerProduct);
	}

	@Override
	public void visit(BaseVector baseVector) {
	}

	@Override
	public void visit(Negation negation) {
		negation.getOperand().accept(this);
	}

	@Override
	public void visit(Reverse node) {
		node.getOperand().accept(this);
	}

	@Override
	public void visit(LogicalOr node) {
		visitBinaryOperation(node);
	}

	@Override
	public void visit(LogicalAnd node) {
		visitBinaryOperation(node);
	}

	@Override
	public void visit(Equality node) {
		visitBinaryOperation(node);
	}

	@Override
	public void visit(Inequality node) {
		visitBinaryOperation(node);
	}

	@Override
	public void visit(Relation relation) {
		visitBinaryOperation(relation);
	}

	@Override
	public void visit(FunctionArgument node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MacroCall node) {
		// TODO Auto-generated method stub
		
	}
	
}
