package de.gaalop.gaalet.output;

import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import de.gaalop.NameTable;
import de.gaalop.StringList;
import de.gaalop.gaalet.GaaletMultiVector;
import java.util.*;


/**
 * This visitor traverses the control and data flow graphs and generates C/C++ code.
 */
public class CppVisitor extends de.gaalop.cpp.CppVisitor {
    
    private String suffix = "_opt";
	
	private boolean ifPossible = true; // dont know if we can misuse gealgs mv as arrays.
	
	protected Set<GaaletMultiVector> vectorSet = new HashSet<GaaletMultiVector>();
	
	public CppVisitor(boolean standalone) {
		super(standalone);
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

		if (standalone) {
			code.append("void calculate(");

			// Input Parameters
			StringList list = new StringList();
			for (String var : graph.getInputs()) {
                            list.add(variableType + " "+ NameTable.getInstance().get(var));
			}

			for (String var : graph.getOutputs()) {
                            list.add(variableType + NameTable.getInstance().get(var+suffix));
			}
                        
                        code.append(list.join());


			code.append(") {\n");
			indentation++;
		}


		if (ifPossible && !standalone) {		
			for (String var: graph.getInputs()) {
				FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var);
				graph.accept(fieldVisitor);
				code.append("// This is input: ");
				code.append(fieldVisitor.giveDefinition(variableType));
				code.append("\n");	
			}
			code.append("\n");	
			
			for (String var: graph.getOutputs()) {
				FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var);
				graph.accept(fieldVisitor);
				code.append("// This is output: ");
				code.append(fieldVisitor.giveDefinition());
				code.append("\n");				
			}
		}

		handleLocalVariables();
		createVectorSet();
		node.getSuccessor().accept(this);
	}
	
	protected void createVectorSet () {
		for (String var: graph.getInputs()) {
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var);
			graph.accept(fieldVisitor);
			vectorSet.add(fieldVisitor.getMultiVector());		
		}
		for (String var : graph.getLocals()) {
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var);
			graph.accept(fieldVisitor);
			vectorSet.add(fieldVisitor.getMultiVector());		
		}	
		for (String var: graph.getOutputs()) {
			FieldsUsedVisitor fieldVisitor = new FieldsUsedVisitor(var);
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
