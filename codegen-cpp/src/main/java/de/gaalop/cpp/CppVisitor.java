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

	// Maps the nodes that output variables to their result parameter names
	private Map<StoreResultNode, String> outputNamesMap = new HashMap<StoreResultNode, String>();

	private int indentation = 0;

	private boolean standalone = true;

	private Set<String> assigned = new HashSet<String>();

	private Map<MultivectorComponent, Integer> newIndices = new HashMap<MultivectorComponent, Integer>();
	private int components;
	private int offset;

	public final boolean MINIMIZE_ARRAY_SIZE = false;

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
		for (StoreResultNode var : findOutput.getNodes()) {
			String outputName = var.getValue().getName() + "_out";
			outputNamesMap.put(var, outputName);
		}
		if (standalone) {
			code.append("void calculate(");

			// Input Parameters
			List<Variable> inputParameters = sortVariables(graph.getInputVariables());
			for (Variable var : inputParameters) {
				code.append("float "); // The assumption here is that they all are normal scalars
				code.append(var.getName());
				code.append(", ");
			}

			for (StoreResultNode var : findOutput.getNodes()) {
				code.append("float **");
				code.append(outputNamesMap.get(var));
				code.append(", ");
			}

			if (!graph.getInputVariables().isEmpty() || !findOutput.getNodes().isEmpty()) {
				code.setLength(code.length() - 2);
			}

			code.append(") {\n");
			indentation++;
		}

		if (!MINIMIZE_ARRAY_SIZE) {
			// Declare local variables with minimal size of array
			for (Variable var : graph.getLocalVariables()) {
				appendIndentation();
				code.append("float ");
				code.append(var.getName());
				code.append(suffix);
				code.append("[32];\n");
			}
		}

		if (!graph.getLocalVariables().isEmpty()) {
			code.append("\n");
		}

		offset = code.length() - 1;

		node.getSuccessor().accept(this);
	}

	/**
	 * Sorts a set of variables by name to make the order deterministic.
	 * 
	 * @param inputVariables
	 * @return
	 */
	private List<Variable> sortVariables(Set<Variable> inputVariables) {
		List<Variable> variables = new ArrayList<Variable>(inputVariables);
		Comparator<Variable> comparator = new Comparator<Variable>() {

			@Override
			public int compare(Variable o1, Variable o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		};

		Collections.sort(variables, comparator);
		return variables;
	}

	@Override
	public void visit(AssignmentNode node) {
		String variable = node.getVariable().getName();
		if (MINIMIZE_ARRAY_SIZE) {
			if (node.getVariable() instanceof MultivectorComponent) {
				newIndices.put((MultivectorComponent) node.getVariable(), new Integer(components));
				components++;
			}
		}
		if (assigned.contains(variable)) {
			String message = "Variable " + variable + " has been reset for reuse.";
			log.warn(message);
			Notifications.addWarning(message);
			code.append("\n");
			appendIndentation();
			code.append("// Warning: reuse of variable ");
			code.append(variable);
			code.append(".\n");
			appendIndentation();
			code.append("// Make sure to reset this variable or use another name.\n");
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
		if (MINIMIZE_ARRAY_SIZE) {
			StringBuilder init = new StringBuilder();
			init.append("\tfloat ");
			init.append(node.getValue().getName());
			init.append(suffix);
			init.append("[");
			init.append(components);
			init.append("];\n");
			code.insert(offset, init.toString());
		}

		assigned.add(node.getValue().getName());

		appendIndentation();
		code.append("memcpy(");
		code.append(outputNamesMap.get(node));
		code.append(", ");
		code.append(node.getValue().getName());
		code.append(suffix);
		code.append(", sizeof(");
		code.append(node.getValue().getName());
		code.append(suffix);
		code.append("));\n");

		// reset current list of assignments
		components = 0;
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		appendIndentation();
		code.append("if (");
		node.getCondition().accept(this);
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
		if (standalone) {
			indentation--;
			code.append("}\n");
		}
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
		if (MINIMIZE_ARRAY_SIZE) {
			code.append(newIndices.get(component));
		} else {
			code.append(component.getBladeIndex());
		}
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
		// TODO Auto-generated method stub

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
