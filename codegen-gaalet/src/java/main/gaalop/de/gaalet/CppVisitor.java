package de.gaalop.gealg.output;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import de.gaalop.gealg.GealgMultiVector;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++ code.
 */
public class CppVisitor implements ControlFlowVisitor, ExpressionVisitor {

	private Log log = LogFactory.getLog(CppVisitor.class);

	private StringBuilder code = new StringBuilder();

	private ControlFlowGraph graph;

	// Maps the nodes that output variables to their result parameter names
	private Map<StoreResultNode, String> outputNamesMap = new HashMap<StoreResultNode, String>();

	private int indentation = 0;

	private boolean standalone = false;
	
	private boolean ifPossible = true; // dont know if we can misuse gealgs mv as arrays.
	
	private Set<GealgMultiVector> vectorSet = new HashSet<GealgMultiVector>();

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


		if (ifPossible && !standalone) {
			List<Variable> inputParameters = sortVariables(graph.getInputVariables());			
			for (Variable var : inputParameters) {
				FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var.getName());
				graph.accept(fieldVisitor);
				code.append("// This is input: ");
				code.append(fieldVisitor.giveDefinition());
				code.append("\n");	
			}
			code.append("\n");	
			
			for (StoreResultNode var : findOutput.getNodes()) {
				FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var.getValue().getName());
				graph.accept(fieldVisitor);
				code.append("// This is output: ");
				code.append(fieldVisitor.giveDefinition());
				code.append("\n");				
			}
		}

		handleLocalVariables();
		createVectorSet(findOutput);
		node.getSuccessor().accept(this);
	}
	
	private void createVectorSet (FindStoreOutputNodes findOutput) {
		for (Variable var : graph.getInputVariables()) {
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var.getName());
			graph.accept(fieldVisitor);
			vectorSet.add(fieldVisitor.getMultiVector());		
		}
		for (Variable var : graph.getLocalVariables()) {
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var.getName());
			graph.accept(fieldVisitor);
			vectorSet.add(fieldVisitor.getMultiVector());		
		}	
		for (StoreResultNode var : findOutput.getNodes()) {
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var.getValue().getName());
			graph.accept(fieldVisitor);
			vectorSet.add(fieldVisitor.getMultiVector());							
		}
	}
	
	/**	
	* Declare local variables
	*	 but first but local variables in a set, so we reduce redundancy
	*/
	private void handleLocalVariables() {
		Set <String> varNames = new HashSet<String> ();
		for (Variable var : graph.getLocalVariables()) {
			varNames.add(var.getName());			
		}		
		for (String var : varNames) {
			appendIndentation();
//			code.append("float ");
//			code.append(var);
//			code.append("[32];\n");
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var);
			graph.accept(fieldVisitor);
			code.append(fieldVisitor.giveDefinition());
			code.append("\n");
		}

		if (!graph.getLocalVariables().isEmpty()) {
			code.append("\n");
		}				
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

	Set<String> assigned = new HashSet<String>();

	@Override
	public void visit(AssignmentNode node) {
		if (assigned.contains(node.getVariable().getName())) {
			log.warn("Reuse of variable " + node.getVariable().getName()
				+ ". Make sure to reset this variable or use another name.");
			code.append("\n");
			appendIndentation();
			code.append("// Warning: reuse of variable ");
			code.append(node.getVariable().getName());
			code.append(".\n");
			appendIndentation();
			code.append("// Make sure to reset this variable or use another name.\n");
			assigned.remove(node.getVariable().getName());
		}

		appendIndentation();
		node.getVariable().accept(this);
		code.append(" = ");
		node.getValue().accept(this);
		code.append(";\n");

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(StoreResultNode node) {
		assigned.add(node.getValue().getName());
//		appendIndentation();
//		code.append("memcpy(");
//		code.append(outputNamesMap.get(node));
//		code.append(", ");
//		code.append(node.getValue().getName());
//		code.append(", sizeof(");
//		code.append(node.getValue().getName());
//		code.append("));\n");


		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		appendIndentation();
		code.append("\n");
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
			code.append("\n");
		}

		node.getSuccessor().accept(this);
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
		addBinaryInfix(innerProduct, " % ");
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
		int pos = -1;
		for (GealgMultiVector vec : vectorSet) {
			if (component.getName().equals(vec.getName()))
				pos = vec.getBladePosInArray(component.getBladeIndex());
		}
		
		code.append(component.getName());
		code.append('[');
		code.append(pos);
		code.append(']');
		
		//System.out.println("but here is a mv");
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
		//code.append('f');
	}

	@Override
	public void visit(OuterProduct outerProduct) {
		addBinaryInfix(outerProduct, " & ");
	}

	@Override
	public void visit(BaseVector baseVector) {
		code.append(baseVector.toString());
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
}
