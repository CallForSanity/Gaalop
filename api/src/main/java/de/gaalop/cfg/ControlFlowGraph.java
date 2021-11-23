package de.gaalop.cfg;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gaalop.InputFile;
import de.gaalop.StringList;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;

/**
 * This class models a control dataflow graph.
 * <p/>
 * It is usually created by a <code>CodeParser</code> and then processed by an <code>OptimizationStrategy</code> before
 * it is again converted to source code by a <code>CodeGenerator</code>.
 * <p/>
 * A ControlFlowGraph instance only holds references to the first and last node of the graph, which are modeled by the
 * classes StartNode and EndNode.
 * 
 * @author Sebastian Hartte
 * @author Christian Schwinn
 * @see de.gaalop.CodeGenerator
 * @see de.gaalop.CodeParser
 * @see de.gaalop.OptimizationStrategy
 * @see de.gaalop.cfg.StartNode
 * @see de.gaalop.cfg.EndNode
 */
public final class ControlFlowGraph {

	private Log log = LogFactory.getLog(ControlFlowGraph.class);

	private Set<Variable> localVariables = new HashSet<Variable>();
	private Set<Variable> scalarVariables = new HashSet<Variable>();
	private Set<Variable> inputVariables = new HashSet<Variable>();

        private AlgebraDefinitionFile algebraDefinitionFile = new AlgebraDefinitionFile();

	private final StartNode startNode;

	private final EndNode endNode;

	private InputFile source;

	private Map<String, Macro> macros = new HashMap<String, Macro>();
	private Set<Slider> sliders = new HashSet<Slider>();

	private ColorNode bgColor;

	/* store information about the pragmas */
	private Set<String> pragmaOutputVariables = new HashSet<String>();
	private Set<String> pragmaOnlyEvaluateVariables = new HashSet<String>();

	private HashMap<String, String> pragmaMinValue = new HashMap<String, String>();
	private HashMap<String, String> pragmaMaxValue = new HashMap<String, String>();
        
        public boolean syntaxSpecified = false;
        public StringList syntaxInputs = new StringList();
        public StringList syntaxOutputs = new StringList();
        
        public boolean tbaOptimized = true;
        
	public LinkedList<ExpressionStatement> visualizerExpressions = new LinkedList<ExpressionStatement>();
        private HashMap<String, Expression> renderingExpressions = new HashMap<String, Expression>();

        public GlobalSettings globalSettings = new GlobalSettings();
        
        public LinkedList<UnknownMacroCall> unknownMacros = new LinkedList<UnknownMacroCall>();
        
        public String algebraName;
        public boolean asRessource;
        public String algebraBaseDirectory;
        
        public HashMap<String, Expression> getRenderingExpressions() {
            return renderingExpressions;
        }
    
        public void setRenderingExpressions(HashMap<String, Expression> renderingExpressions) {
            this.renderingExpressions = renderingExpressions;
        }

	public HashMap<String, String> getPragmaMaxValue() {
		return pragmaMaxValue;
	}

	public HashMap<String, String> getPragmaMinValue() {
		return pragmaMinValue;
	}

	public Set<String> getPragmaOutputVariables() {
		return pragmaOutputVariables;
	}

        public Set<String> getPragmaOnlyEvaluateVariables() {
            return pragmaOnlyEvaluateVariables;
        }

	public void addPragmaOutputVariable(String name) {
		pragmaOutputVariables.add(name);
	}

        public void addPragmaOnlyEvaluateVariable(String name) {
		pragmaOnlyEvaluateVariables.add(name);
	}

	public void addScalarVariable(Variable tempVariable) {
		scalarVariables.add(tempVariable);
	}
	
	public void removeScalarVariable(String name) {
		scalarVariables.remove(new Variable(name));
	}

	public Set<Variable> getScalarVariables() {
		return scalarVariables;
	}

	/**
	 * Adds a pragma hint for a variable, which defines value range for it. The pragma must be set before the variable
	 * is added to the input variables, i.e. the pragma must appear for the use of the variable
	 */
	public void addPragmaMinMaxValues(String variable, String min, String max) {
		pragmaMaxValue.put(variable, max);
		pragmaMinValue.put(variable, min);
	}

	/**
	 * Adds a new macro definition.
	 * 
	 * @param macro new macro definition
	 */
	public void addMacro(Macro macro) {
		macros.put(macro.getName(), macro);
	}

	public void addSlider(Slider slider) {
		sliders .add(slider);
	}
	
	public Set<Slider> getSliders() {
		return sliders;
	}

	public void setBGColor(ColorNode color) {
		bgColor = color;
	}
	
	public ColorNode getBGColor() {
		return bgColor;
	}

	/**
	 * Returns the macro definition of a macro with given name, if available.
	 * 
	 * @param name name of macro to be returned
	 * @return macro definition according to name or null, if not found
	 */
	public Macro getMacro(String name) {
		return macros.get(name);
	}

	/**
	 * Returns a list of macro definitions that are contained in this graph.
	 * 
	 * @return list of macro definitions
	 */
	public Set<Macro> getMacros() {
		return new HashSet<Macro>(macros.values());
	}

	/**
	 * Constructs a new control flow graph.
	 * <p/>
	 * A new graph contains the start and end node and the connection between them.
	 */
	public ControlFlowGraph() {
		startNode = new StartNode(this);
		endNode = new EndNode(this);
		startNode.setSuccessor(endNode);
		endNode.addPredecessor(startNode);
	}

	/**
	 * Gets the last node in this control flow graph.
	 * 
	 * @return The last node in the graph.
	 */
	public EndNode getEndNode() {
		return endNode;
	}

	/**
	 * Gets the start node in this control flow graph.
	 * 
	 * @return The first node of the control flow graph.
	 */
	public StartNode getStartNode() {
		return startNode;
	}

	/**
	 * Gets the input file this graph was created from.
	 * 
	 * @return The input file this graph was created from or null if it is unknown.
	 */
	public InputFile getSource() {
		return source;
	}

	/**
	 * Sets the input file this graph was parsed from.
	 * 
	 * @param source The input file this graph resulted from.
	 */
	public void setSource(InputFile source) {
		this.source = source;
	}

	/**
	 * Gets the locally declared variables.
	 * 
	 * @return An unmodifiable set containing the locally declared variables in this cfg.
	 * @see #getInputVariables()
	 */
	public Set<Variable> getLocalVariables() {
		return Collections.unmodifiableSet(localVariables);
	}
        
        public Set<Variable> getLocalVariablesModifiable() {
		return localVariables;
	}

	/**
	 * Gets the set of variables that are expected as input parameters for the algorithm modeled by this graph.
	 * 
	 * @return An unmodifiable set containing the input variables for this graph.
	 */
	public Set<Variable> getInputVariables() {
		return Collections.unmodifiableSet(inputVariables);
	}

	/**
	 * Adds a new local variable.
	 * 
	 * @param variable The new local variable.
	 * @throws IllegalArgumentException If <code>variable</code> is an input variable in this graph.
	 */
	public void addLocalVariable(Variable variable) {
		// Check that the given variable is not part of the inputVariables set
		if (inputVariables.contains(variable)) {
			throw new IllegalArgumentException("The Variable " + variable.getName()
					+ " cannot be a local variable and an input variable at the same time.");
		}

		localVariables.add(variable);
	}

	/**
	 * Removes a local variable from this graph.
	 * <p/>
	 * If <code>variable</code> is also an output variable, it is removed from that set as well.
	 * 
	 * @param variable The variable that should be removed.
	 */
	public void removeLocalVariable(Variable variable) {
		localVariables.remove(variable);
	}

	/**
	 * Determines whether there is a local variable with given name defined in this graph.
	 * 
	 * @param name name of variable
	 * @return true, if a local variable with given name exists, false otherwise
	 */
	public boolean containsLocalVariable(String name) {
		for (Variable v : localVariables) {
			if (v.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds an input parameter.
	 * 
	 * @param variable The new input parameter.
	 * @throws IllegalArgumentException If <code>variable</code> is a local variable in this graph.
	 */
	public void addInputVariable(Variable variable) {
		String name = variable.getName();
		if ("true".equals(name) || "false".equals(name)) {
			// ignore these keywords
			return;
		}
		
		// Check that the given variable is not part of the localVariables set
		if (localVariables.contains(variable)) {
			throw new IllegalArgumentException("The Variable " + name
					+ " cannot be a local variable and an input variable at the same time.");
		}

//		if (name.contains("e")) {
//			throw new IllegalArgumentException("Input variable " + variable
//					+ " contains 'e' which is not supported by Maple.");
//		}

		inputVariables.add(variable);
	}

	/**
	 * Removes an input variable.
	 * 
	 * @param variable The variable that should be removed.
	 */
	public void removeInputVariable(Variable variable) {
		inputVariables.remove(variable);
	}

	/**
	 * Starts the traversal of this control flow graph using a visitor.
	 * <p/>
	 * Since the first node of the graph will always be a StartNode object, this method forwards the call to
	 * {@link StartNode#accept(ControlFlowVisitor)}.
	 * 
	 * @param visitor The visitor that provides the callback methods.
	 */
	public void accept(ControlFlowVisitor visitor) {
		startNode.accept(visitor);
	}

	/**
	 * Removes the given node from the control flow graph. The graph will be traversed in order to find the node to be
	 * removed. Once the desired node is found, its predecessors are rewired to have the old node's successor as direct
	 * successors.
	 * 
	 * @param node node to be removed
	 */
	public void removeNode(SequentialNode node) {
		Node successor = node.getSuccessor();
		successor.removePredecessor(node);
		Set<Node> predecessors = new HashSet<Node>(node.getPredecessors());
		for (Node predecessor : predecessors) {
			successor.addPredecessor(predecessor);
			predecessor.replaceSuccessor(node, successor);
		}
	}

	@Override
	public String toString() {
		SequentialNode curr = startNode;
		StringBuilder sb = new StringBuilder();
		sb.append("CFG:\n");
		sb.append(startNode);
		do {
			sb.append("\n--> ");
			if (curr.getSuccessor() instanceof SequentialNode) {
				curr = (SequentialNode) curr.getSuccessor();
				sb.append(curr);
			} else {
				Node end = curr.getSuccessor();
				sb.append(end);
				break;
			}
		} while (true);
		return sb.toString();
	}

	public String prettyPrint() {
		Printer printer = new Printer();
		accept(printer);
		return printer.getCode();
	}
        
        /**
         * Returns the blade string representation of a given multivector component.
         * @param multivectorComponent
         * @return 
         */
        public String getBladeString(MultivectorComponent multivectorComponent) {
            return getAlgebraDefinitionFile().getBladeString(multivectorComponent.getBladeIndex());
        }

	private static class Printer implements ControlFlowVisitor {

		private int indent = 0;
		private StringBuilder code = new StringBuilder();

		Printer() {
			// empty non-private constructor
		}

		String getCode() {
			return code.toString();
		}

		private void appendIndent() {
			for (int i = 0; i < indent; i++) {
				code.append('\t');
			}
		}

		@Override
		public void visit(StartNode node) {
			code.append("START\n");
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(AssignmentNode node) {
			appendIndent();
			code.append(node.getVariable());
			code.append(" = ");
			code.append(node.getValue());
			code.append(";\n");
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(StoreResultNode node) {
			appendIndent();
			code.append(node);
			code.append(";\n");
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(IfThenElseNode node) {
			appendIndent();
			code.append("if (");
			code.append(node.getCondition());
			code.append(") {\n");
			indent++;
			node.getPositive().accept(this);
			indent--;
			appendIndent();
			code.append("} else {");
			indent++;
			node.getNegative().accept(this);
			indent--;
			appendIndent();
			code.append("}\n");
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(BlockEndNode node) {
		}

		@Override
		public void visit(LoopNode node) {
			appendIndent();
			code.append("loop {\n");
			indent++;
			node.getBody().accept(this);
			indent--;
			appendIndent();
			code.append("}\n");
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(BreakNode node) {
			appendIndent();
			code.append("break;\n");
		}

		@Override
		public void visit(Macro node) {
			appendIndent();
			code.append(node.getName());
			code.append(" = {\n");
			indent++;
			if (node.getBody().size() > 0) {
				node.getBody().get(0).accept(this);
			}
			if (node.getReturnValue() != null) {
				appendIndent();
				code.append(node.getReturnValue());
			}
			indent--;
			appendIndent();
			code.append("}\n");
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(ExpressionStatement node) {
			appendIndent();
			code.append(node);
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(EndNode node) {
			code.append("END");
		}

		@Override
		public void visit(ColorNode node) {
			appendIndent();
			code.append(":");
			code.append(node);
		}

	}

    public AlgebraDefinitionFile getAlgebraDefinitionFile() {
        return algebraDefinitionFile;
    }

    public void setAlgebraDefinitionFile(AlgebraDefinitionFile algebraDefinitionFile) {
        this.algebraDefinitionFile = algebraDefinitionFile;
    }

    /**
     * Computes the order of the inputs.
     * If a syntax pragma is specified, then the order given by syntax is used, otherwise, a lexiographical ordering ignoring case is used.
     * @return The order of the inputs as list of variable names.
     */
    public StringList getInputs() {
        StringList list = new StringList();
        if (syntaxSpecified) {
            HashSet<Variable> inputVars = new HashSet<>(getInputVariables());
            for (String syntaxInput: syntaxInputs) {
                Variable syntaxInputVar = new Variable(syntaxInput);
                
                if (inputVars.contains(syntaxInputVar)) {
                    //add to list, if in inputVars
                    list.add(syntaxInput);
                    //remove from input Vars
                    inputVars.remove(syntaxInputVar);
                } else {
                    throw new IllegalStateException("Input "+syntaxInput+", specified in in2outPragma, is no input variable in the script!");
                }
            }
            if (inputVars.size() > 0) {
                throw new IllegalStateException("Input variables "+inputVars+", must be in in2outPragma!");
            }
        } else {
            for (Variable var : getInputVariables()) 
                list.add(var.getName());
            list.sortIgnoringCase();
        }
        return list;            
    }
    
    /**
     * Computes the order of the local variables.
     * @return The order of the locals as list of variable names.
     */
    public StringList getLocals() {
        StringList list = new StringList();
        for (Variable var : getLocalVariables()) 
            list.add(var.getName());
        list.sortIgnoringCase();
        return list;            
    }
    
    /**
     * Computes the order of the scalar variables.
     * @return The order of the scalars as list of variable names.
     */
    public StringList getScalars() {
        StringList list = new StringList();
        for (Variable var : getScalarVariables()) 
            list.add(var.getName());
        list.sortIgnoringCase();
        return list;            
    }
    
    /**
     * Computes the order of the outputs.
     * If a syntax pragma is specified, then the order given by syntax is used, otherwise, a lexiographical ordering ignoring case is used.
     * @return The order of the outputs as list of variable names.
     */
    public StringList getOutputs() {
        HashSet<String> outputs = OutputsCollector.getOutputsFromGraph(this);
        
        StringList list;
        if (syntaxSpecified) {
            list = new StringList();
            for (String syntaxOutput: syntaxOutputs) {
                if (outputs.contains(syntaxOutput)) {
                    list.add(syntaxOutput);
                } else {
                    throw new IllegalStateException("Output "+syntaxOutput+", specified in in2outPragma, must be leaded by a question sign!");
                }
            }
        } else {
            list = new StringList(outputs);
            list.sortIgnoringCase();
        }
        return list;
    }

}
