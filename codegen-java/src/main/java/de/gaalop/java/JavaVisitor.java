package de.gaalop.java;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This visitor traverses the control and data flow graphs and generates Java code.
 */
public class JavaVisitor implements ControlFlowVisitor, ExpressionVisitor {

	protected Log log = LogFactory.getLog(JavaVisitor.class);

	protected final String suffix = "_opt";

	protected StringBuilder code = new StringBuilder();

	protected ControlFlowGraph graph;

	protected int indentation = 0;

	protected Set<String> assigned = new HashSet<String>();
	
	protected Set<String> declared = new HashSet<String>();

	public String getCode() {
		return code.toString();
	}

	protected void appendIndentation() {
		for (int i = 0; i < indentation; ++i) {
			code.append('\t');
		}
	}

	@Override
	public void visit(StartNode node) {
		graph = node.getGraph();

                //process all members

                FindStoreOutputNodes stores = new FindStoreOutputNodes();
                graph.accept(stores);


                //code.append("import java.util.Arrays;\n\n");

                code.append("public class "+graph.getSource().getName()+" implements GAProgram {\n");
                indentation++;


                appendIndentation();
                code.append("// input variables\n");
                for (Variable inputVar: graph.getInputVariables()) {

                    String variableName = getVarName(inputVar);
                    declared.add(variableName);
                    appendIndentation();
                    code.append("private float "+variableName+";\n");
                }
                code.append("\n");

                appendIndentation();
                code.append("// output variables\n");
                for (String outputVarStr: graph.getPragmaOutputVariables()) {
                    declared.add(outputVarStr);
                    appendIndentation();
                    code.append("private float "+outputVarStr+";\n");
                }
                code.append("\n");

                // getValue for output variables
                appendIndentation();
                code.append("@Override\n");
                appendIndentation();
                code.append("public float getValue(String varName) {\n");
                indentation++;

                for (String outputVar: graph.getPragmaOutputVariables()) {
                    appendIndentation();
                    code.append("if (varName.equals(\""+outputVar+"\")) return "+outputVar+";\n");
                }

                appendIndentation();
                code.append("return 0.0f;\n");

                indentation--;
                appendIndentation();
                code.append("}\n"); // close procedure getValue
                code.append("\n");
                // setValue for input variables
                appendIndentation();
                code.append("@Override\n");
                appendIndentation();
                code.append("public boolean setValue(String varName, float value) {\n");
                indentation++;

                for (Variable inputVar: graph.getInputVariables()) {
                    appendIndentation();
                    code.append("if (varName.equals(\""+getVarName(inputVar)+"\")) { "+getVarName(inputVar)+" = value; return true; }\n");
                }
                appendIndentation();
                code.append("return false;\n");

                indentation--;
                appendIndentation();
                code.append("}\n"); // close procedure setValue

                appendIndentation();
                code.append("\n");

                appendIndentation();
                code.append("@Override\n");
                appendIndentation();
                code.append("public void calculate() {\n");
                indentation++;

		node.getSuccessor().accept(this);
	}

	/**
	 * Sorts a set of variables by name to make the order deterministic.
	 * 
	 * @param inputVariables
	 * @return
	 */
	protected List<Variable> sortVariables(Set<Variable> inputVariables) {
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

        private String getVarName(Variable var) {
            if (!(var instanceof MultivectorComponent)) {
                return var.getName() + "_0";
            } else
                return var.getName() + "_" +((MultivectorComponent) var).getBladeIndex();

        }

	@Override
	public void visit(AssignmentNode node) {
		String varName = getVarName(node.getVariable());
		
                appendIndentation();
		if (!declared.contains(varName)) {
			declared.add(varName);
			code.append("float ");
		} 

                node.getVariable().accept(this);
		code.append(" = ");
		node.getValue().accept(this);
		code.append(";\n");

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
            indentation--;
            appendIndentation();
            code.append("}\n"); // close procedure calculate
            indentation--;
            appendIndentation();
            code.append("}\n"); // close class
	}

	@Override
	public void visit(ColorNode node) {
		node.getSuccessor().accept(this);
	}

	protected void addBinaryInfix(BinaryOperation op, String operator) {
		code.append("(");
		addChild(op, op.getLeft());
		code.append(operator);
		addChild(op, op.getRight());
		code.append(")");
	}

	protected void addChild(Expression parent, Expression child) {
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
		throw new UnsupportedOperationException("The Java backend does not support the inner product.");
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
			funcName = "Math.abs";
			break;
		case SQRT:
			funcName = "(float) Math.sqrt";
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
           code.append(getVarName(variable));
	}

	@Override
	public void visit(MultivectorComponent component) {
		code.append(getVarName(component));
	}

	@Override
	public void visit(Exponentiation exponentiation) {
		if (isSquare(exponentiation)) {
			Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
			m.accept(this);
		} else {
			code.append("Math.pow(");
			exponentiation.getLeft().accept(this);
			code.append(',');
			exponentiation.getRight().accept(this);
			code.append(')');
		}
	}

	protected boolean isSquare(Exponentiation exponentiation) {
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
		throw new UnsupportedOperationException("The Java backend does not support the outer product.");
	}

	@Override
	public void visit(BaseVector baseVector) {
		throw new UnsupportedOperationException("The Java backend does not support base vectors.");
	}

	@Override
	public void visit(Negation negation) {
		code.append("(-");
		addChild(negation, negation.getOperand());
		code.append(")");
	}

	@Override
	public void visit(Reverse node) {
		throw new UnsupportedOperationException("The Java backend does not support the reverse operation.");
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
