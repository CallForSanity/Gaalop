package de.gaalop.gaalet.output;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import de.gaalop.gaalet.NameTable;
import de.gaalop.gaalet.GaaletMultiVector;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++ code.
 */
public class CppVisitor extends de.gaalop.cpp.CppVisitor {
	
	private boolean ifPossible = true; // dont know if we can misuse gealgs mv as arrays.
	
	// Maps the nodes that output variables to their result parameter names
	protected Map<StoreResultNode, String> outputNamesMap = new HashMap<StoreResultNode, String>();

	private Set<GaaletMultiVector> vectorSet = new HashSet<GaaletMultiVector>();

	private String variableType;
	
	public CppVisitor(boolean standalone) {
		super(standalone);
		variableType = "float";
	}

	public CppVisitor(String variableType) {
		super(false);
		this.variableType = variableType;
	}
	
	protected void printVarName(String key) {
		code.append(NameTable.getInstance().get(key));
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
				printVarName(var.getName());
				code.append(", ");
			}

			for (StoreResultNode var : findOutput.getNodes()) {
				code.append("float **");
				printVarName(outputNamesMap.get(var));
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
				code.append(fieldVisitor.giveDefinition(variableType));
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
	
	protected void createVectorSet (FindStoreOutputNodes findOutput) {
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
	*	 but first local variables in a set, so we reduce redundancy
	*/
	protected void handleLocalVariables() {
		Set <String> varNames = new HashSet<String> ();
		for (Variable var : graph.getLocalVariables()) {
			varNames.add(var.getName());			
		}		
		for (String var : varNames) {
			appendIndentation();
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var);
			graph.accept(fieldVisitor);
			code.append(fieldVisitor.giveDefinition(variableType)); 
			code.append("\n");
		}

		if (!graph.getLocalVariables().isEmpty()) {
			code.append("\n");
		}				
	}
	
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
}
