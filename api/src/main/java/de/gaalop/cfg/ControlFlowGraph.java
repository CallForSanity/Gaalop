package de.gaalop.cfg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gaalop.InputFile;
import de.gaalop.dfg.Expression;
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
 * @see de.gaalop.CodeGenerator
 * @see de.gaalop.CodeParser
 * @see de.gaalop.OptimizationStrategy
 * @see de.gaalop.cfg.StartNode
 * @see de.gaalop.cfg.EndNode
 */
public final class ControlFlowGraph {

	private Log log = LogFactory.getLog(ControlFlowGraph.class);

	private Set<Variable> localVariables = new HashSet<Variable>();
	private List<Variable> tempVariables = new ArrayList<Variable>();
	private Set<Variable> inputVariables = new HashSet<Variable>();

	private AlgebraSignature signature;

	private final StartNode startNode;

	private final EndNode endNode;

	private InputFile source;

	private Map<String, Macro> macros = new HashMap<String, Macro>();

	/* store information about the pragmas */
	private Set<String> pragmaOutputVariables = new HashSet<String>();

	private HashMap<String, String> pragmaMinValue = new HashMap<String, String>();
	private HashMap<String, String> pragmaMaxValue = new HashMap<String, String>();

	/**
	 * This field contains a list of blades that correspond to the indices of a multi vector that is represented by an
	 * array. This list can be modified, but whenever the underlying signature is changed, the blade list is
	 * automatically regenerated to a sane value.
	 */
	private Expression[] bladeList;

	public HashMap<String, String> getPragmaMaxValue() {
		return pragmaMaxValue;
	}

	public HashMap<String, String> getPragmaMinValue() {
		return pragmaMinValue;
	}

	public Set<String> getPragmaOutputVariables() {
		return pragmaOutputVariables;
	}

	public void addPragmaOutputVariable(String name) {
		pragmaOutputVariables.add(name);
	}
	
	public void addTempVariable(Variable tempVariable) {
		tempVariables.add(tempVariable);
	}
	
	public List<Variable> getTempVariables() {
		return tempVariables;
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
	 * Gets the list of blades this control flow graph is using.
	 * <p/>
	 * Once the representation of multivectors has been lowered, this array can be used to identify the blades the
	 * elements of a multivector array correspond to. This information can then be used to create a linear combination
	 * of the array elements with the content of this array to reverse the lowering.
	 * <p/>
	 * In a new graph this is equal to the default blade list of the underlying algebra signature.
	 * 
	 * @return A modifyable array of dataflow graphs that each models a blade.
	 * @see #getSignature()
	 */
	public Expression[] getBladeList() {
		return bladeList;
	}

	/**
	 * Gets the signature of the algebra used by this graph.
	 * 
	 * @return The algebra signature linked with this control flow graph.
	 */
	public AlgebraSignature getSignature() {
		return signature;
	}

	/**
	 * Changes the algebra signature associated with this control flow graph.
	 * <p/>
	 * This method will also change the blade list to the default blade list of the new algebra.
	 * 
	 * @param signature The new algebra signature that should be used.
	 * @see #getBladeList()
	 * @see AlgebraSignature#getDefaultBladeList()
	 */
	public void setSignature(AlgebraSignature signature) {
		this.signature = signature;
		this.bladeList = signature.getDefaultBladeList();
		log.debug("Changing blade list to " + Arrays.toString(bladeList));
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
		// Check that the given variable is not part of the localVariables set
		if (localVariables.contains(variable)) {
			throw new IllegalArgumentException("The Variable " + variable.getName()
					+ " cannot be a local variable and an input variable at the same time.");
		}

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
}
