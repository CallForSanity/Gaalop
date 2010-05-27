package de.gaalop.maple;

import de.gaalop.dfg.*;

/**
 * Created by IntelliJ IDEA. User: Sebastian Date: 08.02.2009 Time: 17:21:49 To change this template use File | Settings | File
 * Templates.
 */
public class MapleDfgVisitor implements ExpressionVisitor {

	private StringBuilder codeBuffer = new StringBuilder();

	private boolean cliffordMode = true;

	public boolean isCliffordMode() {
		return cliffordMode;
	}

	public void setCliffordMode(boolean cliffordMode) {
		this.cliffordMode = cliffordMode;
	}

	public String getCode() {
		return codeBuffer.toString();
	}

	private void handleInfix(BinaryOperation op, String opcode) {
		codeBuffer.append("(");
		op.getLeft().accept(this);
		codeBuffer.append(' ');
		codeBuffer.append(opcode);
		codeBuffer.append(' ');
		op.getRight().accept(this);
		codeBuffer.append(")");
	}

	@Override
	public void visit(Subtraction subtraction) {
		handleInfix(subtraction, "-");
	}

	@Override
	public void visit(Addition addition) {
		handleInfix(addition, "+");
	}

	@Override
	public void visit(Division division) {
		// FIXME: division by zero is not detected
		codeBuffer.append("subs(Id=1,");
		handleInfix(division, ") &c inverse(");
		codeBuffer.append(')');
	}

	@Override
	public void visit(InnerProduct innerProduct) {
		if (!cliffordMode) {
			throw new IllegalStateException("This visitor cannot process the inner product in non-clifford mode.");
		}
		codeBuffer.append("subs(Id=1,innerproduct(");
		innerProduct.getLeft().accept(this);
		codeBuffer.append(',');
		innerProduct.getRight().accept(this);
		codeBuffer.append("))");
	}

	@Override
	public void visit(Multiplication multiplication) {
		if (cliffordMode) {
			codeBuffer.append("subs(Id=1,");
			handleInfix(multiplication, "&c");
			codeBuffer.append(')');
		} else {
			handleInfix(multiplication, "*");
		}
	}

	@Override
	public void visit(MathFunctionCall mathFunctionCall) {
		MathFunction function = mathFunctionCall.getFunction();

		// Special case: abs() has to be translated to self-defined mul_abs() (see cliffordfunctions.mws)
		if (function == MathFunction.ABS) {
			codeBuffer.append("mul_abs");
		} else {
			codeBuffer.append(function.toString().toLowerCase());
		}
		codeBuffer.append('(');
		mathFunctionCall.getOperand().accept(this);
		codeBuffer.append(')');
	}

	@Override
	public void visit(BaseVector baseVector) {
		if (!cliffordMode) {
			throw new IllegalStateException("This visitor cannot process base vectors in non-clifford mode.");
		}

		codeBuffer.append("e").append(baseVector.getIndex());
	}

	@Override
	public void visit(Negation negation) {
		codeBuffer.append("(-(");
		negation.getOperand().accept(this);
		codeBuffer.append("))");
	}

	@Override
	public void visit(Reverse node) {
		codeBuffer.append("reversion(");
		node.getOperand().accept(this);
		codeBuffer.append(")");
	}

	@Override
	public void visit(Variable variable) {
		codeBuffer.append(variable.getName());
	}

	@Override
	public void visit(MultivectorComponent component) {
		codeBuffer.append(component.getName());
		codeBuffer.append('[');
		codeBuffer.append(component.getBladeIndex());
		codeBuffer.append(']');
	}

	@Override
	public void visit(Exponentiation exponentiation) {
		handleInfix(exponentiation, "^");
	}

	@Override
	public void visit(FloatConstant floatConstant) {
		// For some obscure reason, the Clifford lib cannot handle
		// multiplications of the following form: 1.0 &c A, where A is a multivector.
		// It can handle 1 &c A however. For that reason we convert all
		// floating point numbers that can be represented as integrals without
		// data loss as integrals.
		if (Double.compare(floatConstant.getValue(), Math.floor(floatConstant.getValue())) == 0) {
			codeBuffer.append((int) floatConstant.getValue());
		} else {
			codeBuffer.append(Float.toString(floatConstant.getValue()));
		}
	}

	@Override
	public void visit(OuterProduct outerProduct) {
		if (!cliffordMode) {
			throw new IllegalStateException("This visitor cannot process the outer product in non-clifford mode.");
		}

		handleInfix(outerProduct, "&w");
	}

	@Override
	public void visit(LogicalOr node) {
		handleInfix(node, "or");
	}

	@Override
	public void visit(LogicalAnd node) {
		handleInfix(node, "and");
	}

	@Override
	public void visit(Equality node) {
		handleInfix(node, "=");
	}

	@Override
	public void visit(Inequality node) {
		handleInfix(node, "<>");
	}

	@Override
	public void visit(Relation relation) {
		handleInfix(relation, relation.getTypeString());
	}

	@Override
	public void visit(FunctionArgument node) {
		throw new IllegalStateException("Macros should have been inlined, so function arguments are not allowed here.");
	}

	@Override
	public void visit(MacroCall node) {		
		throw new IllegalStateException("Macros should have been inlined, so macro calls are not allowed here.");
	}
}
