package de.gaalop.visualizerC;

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

	protected Log log = LogFactory.getLog(CppVisitor.class);

	protected boolean standalone = true;

	protected final String suffix = "_opt";

	protected StringBuilder code = new StringBuilder();

	protected ControlFlowGraph graph;

	protected int indentation = 0;

	protected Set<String> assigned = new HashSet<String>();
        
        protected String variableType = "float";
       
        private HashMap<String, Integer> outputNames;
	
	public CppVisitor(boolean standalone, LinkedList<AssignmentNode> nodes) {
		this.standalone = standalone;
                outputNames = new HashMap<String, Integer>();
                int i=0;
                for (AssignmentNode node: nodes) {
                    outputNames.put(node.getVariable().getName(), new Integer(i));
                    i++;
                }
	}

	public void setStandalone(boolean standalone) {
		this.standalone = standalone;
	}

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
                int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();

		List<Variable> localVariables = sortVariables(graph.getLocalVariables());
		if (standalone) {
			code.append("void calculate(");

			// Input Parameters
			List<Variable> inputParameters = sortVariables(graph.getInputVariables());
			for (Variable var : inputParameters) {
				code.append(variableType).append(" "); // The assumption here is that they all are normal scalars
				code.append(var.getName());
				code.append(", ");
			}

			for (Variable var : localVariables) {
				code.append(variableType).append(" ");
				code.append(var.getName());
				code.append("["+bladeCount+"], ");
			}

			if (graph.getLocalVariables().size() > 0) {
				code.setLength(code.length() - 2);
			}

			code.append(") {\n");
			indentation++;
		} else {
			for (Variable var : localVariables) {
				appendIndentation();
				code.append(variableType).append(" ");
				code.append(var.getName());
				code.append("["+bladeCount+"] = { 0.0 };\n");
			}
		}

		if (graph.getScalarVariables().size() > 0) {
			appendIndentation();
			code.append(variableType).append(" ");
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
			code.append(node.getGraph().getAlgebraDefinitionFile().getBladeString(component.getBladeIndex()));
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
		if (standalone) {
			indentation--;
			code.append("}\n");
		}
	}

	@Override
	public void visit(ColorNode node) {
		node.getSuccessor().accept(this);
	}

	protected void addBinaryInfix(BinaryOperation op, String operator) {
		addChild(op, op.getLeft());
		code.append(operator);
		addChild(op, op.getRight());
	}

	protected void addChild(Expression parent, Expression child) {
            if (child.isComposite()) {
                code.append('(');
                child.accept(this);
                code.append(')');
            } else
                child.accept(this);
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
            String name = component.getName();
            if (name.equals("_V_ox")) {
                code.append("ox");
                return;
            }
            if (name.equals("_V_oy")) {
                code.append("oy");
                return;
            }
            if (name.equals("_V_oz")) {
                code.append("oz");
                return;
            }
            if (name.equals("_V_t")) {
                code.append("t");
                return;
            }
            
            if (name.endsWith("_S")) {
                Integer i = outputNames.get(name);
                code.append("outputsF["+i+"]");
                return;
            }
            
            if (name.endsWith("_SD")) {
                Integer i = outputNames.get(name.substring(0, name.length()-1));
                code.append("outputsDF["+i+"]");
                return;
            }
            
            code.append(component.getName().replace(suffix, ""));
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

	protected boolean isSquare(Exponentiation exponentiation) {
		final FloatConstant two = new FloatConstant(2.0f);
		return two.equals(exponentiation.getRight());
	}

	@Override
	public void visit(FloatConstant floatConstant) {
		code.append(Double.toString(floatConstant.getValue()));
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
                code.append('(');
		code.append('-');
		addChild(negation, negation.getOperand());
                code.append(')');
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
