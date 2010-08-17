package de.gaalop.cpp;

import de.gaalop.Notifications;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++ code.
 */
public class CppVisitor implements ControlFlowVisitor, ExpressionVisitor {

	private Log log = LogFactory.getLog(CppVisitor.class);

	private final String suffix = "_opt";

	private StringBuilder code = new StringBuilder();

	private ControlFlowGraph graph;

	private int indentation = 0;

	private Set<String> assigned = new HashSet<String>();

	public String getCode() {
		return code.toString();
	}

	private void appendIndentation() {
		for (int i = 0; i < indentation; ++i) {
			code.append('\t');
		}
	}

	@Override
	public void visit(StartNode node) {
		graph = node.getGraph();

		FindStoreOutputNodes findOutput = new FindStoreOutputNodes();
		graph.accept(findOutput);

		// Declare local variables with minimal size of array
		for (Variable var : graph.getLocalVariables()) {
			appendIndentation();
			code.append("float ");
			code.append(var.getName());
			code.append(suffix);
			code.append("[32] = { 0.0f };\n");
		}

		if (graph.getScalarVariables().size() > 0) {
			appendIndentation();
			code.append("float ");
			for (Variable tmp : graph.getScalarVariables()) {
				code.append(tmp.getName());
				code.append(", ");
			}
			code.delete(code.length() - 2, code.length());
			code.append(";\n");
		}

		if (!graph.getLocalVariables().isEmpty()) {
			code.append("\n");
		}

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode node) {
		String variable = node.getVariable().getName();
		if (assigned.contains(variable)) {
			String message = "Variable " + variable + " has been reset for reuse.";
			log.warn(message);
			Notifications.addWarning(message);
			code.append("\n");
			appendIndentation();
			code.append("memset(");
			code.append(variable);
			code.append(", 0, sizeof(");
			code.append(variable);
			code.append(")); // Reset variable for reuse.\n");
			assigned.remove(variable);
		}

		appendIndentation();
		node.getVariable().accept(this);
		code.append(" = ");
		node.getValue().accept(this);
		code.append(";");

		if (node.getVariable() instanceof MultivectorComponent) {
			code.append(" // ");

			MultivectorComponent component = (MultivectorComponent) node.getVariable();
			Expression[] bladeList = node.getGraph().getBladeList();

			BladePrinter bladeVisitor = new BladePrinter();
			bladeList[component.getBladeIndex()].accept(bladeVisitor);
			code.append(bladeVisitor.getCode());
		}

		code.append('\n');

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(ExpressionStatement node) {
		appendIndentation();
		node.getExpression().accept(this);
		code.append(";\n");

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(StoreResultNode node) {
		assigned.add(node.getValue().getName() + suffix);
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		Expression condition = node.getCondition();

		appendIndentation();
		code.append("if (");
		condition.accept(this);
		code.append(") {\n");

		indentation++;
		node.getPositive().accept(this);
		indentation--;

		appendIndentation();
		code.append("}");

		if (node.getNegative() instanceof BlockEndNode) {
			code.append("\n");
		} else {
			code.append(" else ");

			boolean isElseIf = false;
			if (node.getNegative() instanceof IfThenElseNode) {
				IfThenElseNode ifthenelse = (IfThenElseNode) node.getNegative();
				isElseIf = ifthenelse.isElseIf();
			}
			if (!isElseIf) {
				code.append("{\n");
				indentation++;
			}

			node.getNegative().accept(this);

			if (!isElseIf) {
				indentation--;
				appendIndentation();
				code.append("}\n");
			}
		}

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(LoopNode node) {
		appendIndentation();
		code.append("while(true) {\n");

		indentation++;
		node.getBody().accept(this);
		indentation--;

		appendIndentation();
		code.append("}\n");

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(BreakNode breakNode) {
		appendIndentation();
		code.append("break;\n");
	}

	@Override
	public void visit(BlockEndNode node) {
		// nothing to do
	}

	@Override
	public void visit(EndNode node) {
	}

	private void addBinaryInfix(BinaryOperation op, String operator) {
		addChild(op, op.getLeft());
		code.append(operator);
		addChild(op, op.getRight());
	}

	private void addChild(Expression parent, Expression child) {
		if (OperatorPriority.hasLowerPriority(parent, child)) {
			code.append('(');
			child.accept(this);
			code.append(')');
		} else {
			child.accept(this);
		}
	}

	@Override
	public void visit(Subtraction subtraction) {
		addBinaryInfix(subtraction, " - ");
	}

	@Override
	public void visit(Addition addition) {
		addBinaryInfix(addition, " + ");
	}

	@Override
	public void visit(Division division) {
		addBinaryInfix(division, " / ");
	}

	@Override
	public void visit(InnerProduct innerProduct) {
		throw new UnsupportedOperationException("The C/C++ backend does not support the inner product.");
	}

	@Override
	public void visit(Multiplication multiplication) {
		addBinaryInfix(multiplication, " * ");
	}

	@Override
	public void visit(MathFunctionCall mathFunctionCall) {
		String funcName;
		switch (mathFunctionCall.getFunction()) {
		case ABS:
			funcName = "fabs";
			break;
		case SQRT:
			funcName = "sqrtf";
			break;
		default:
			funcName = mathFunctionCall.getFunction().toString().toLowerCase();
		}
		code.append(funcName);
		code.append('(');
		mathFunctionCall.getOperand().accept(this);
		code.append(')');
	}

	@Override
	public void visit(Variable variable) {
		// usually there are no
		code.append(variable.getName());
	}

	@Override
	public void visit(MultivectorComponent component) {
		code.append(component.getName());
		code.append('[');
		code.append(component.getBladeIndex());
		code.append(']');
	}

	@Override
	public void visit(Exponentiation exponentiation) {
		if (isSquare(exponentiation)) {
			Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
			m.accept(this);
		} else {
			code.append("pow(");
			exponentiation.getLeft().accept(this);
			code.append(',');
			exponentiation.getRight().accept(this);
			code.append(')');
		}
	}

	private boolean isSquare(Exponentiation exponentiation) {
		final FloatConstant two = new FloatConstant(2.0f);
		return two.equals(exponentiation.getRight());
	}

	@Override
	public void visit(FloatConstant floatConstant) {
		code.append(Float.toString(floatConstant.getValue()));
		code.append('f');
	}

	@Override
	public void visit(OuterProduct outerProduct) {
		throw new UnsupportedOperationException("The C/C++ backend does not support the outer product.");
	}

	@Override
	public void visit(BaseVector baseVector) {
		throw new UnsupportedOperationException("The C/C++ backend does not support base vectors.");
	}

	@Override
	public void visit(Negation negation) {
		code.append('-');
		addChild(negation, negation.getOperand());
	}

	@Override
	public void visit(Reverse node) {
		throw new UnsupportedOperationException("The C/C++ backend does not support the reverse operation.");
	}

	@Override
	public void visit(LogicalOr node) {
		addBinaryInfix(node, " || ");
	}

	@Override
	public void visit(LogicalAnd node) {
		addBinaryInfix(node, " && ");
	}

	@Override
	public void visit(LogicalNegation node) {
		code.append('!');
		addChild(node, node.getOperand());
	}

	@Override
	public void visit(Equality node) {
		addBinaryInfix(node, " == ");
	}

	@Override
	public void visit(Inequality node) {
		addBinaryInfix(node, " != ");
	}

	@Override
	public void visit(Relation relation) {
		addBinaryInfix(relation, relation.getTypeString());
	}

	@Override
	public void visit(Macro node) {
		throw new IllegalStateException("Macros should have been inlined and removed from the graph.");
	}

	@Override
	public void visit(FunctionArgument node) {
		throw new IllegalStateException("Macros should have been inlined and no function arguments should be the graph.");
	}

	@Override
	public void visit(MacroCall node) {
		throw new IllegalStateException("Macros should have been inlined and no macro calls should be in the graph.");
	}
}
