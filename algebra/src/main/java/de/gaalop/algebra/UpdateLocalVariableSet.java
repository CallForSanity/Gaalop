package de.gaalop.algebra;

import de.gaalop.visitors.DFGTraversalVisitor;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * This class provide a method for updating the variable-sets in a graph
 * @author Christian Steinmetz
 */
public class UpdateLocalVariableSet extends EmptyControlFlowVisitor {

    private HashSet<String> inputVariables = new HashSet<String>();
    private HashSet<String> defined = new HashSet<String>();
    private HashSet<String> scalarVariables = new HashSet<String>();

    private DFGTraversalVisitor dfgVisitor = new DFGTraversalVisitor() {

        @Override
        public void visit(Variable node) {
            if (!defined.contains(node.getName()) && !scalarVariables.contains(node.getName()))
                inputVariables.add(node.getName());
            super.visit(node);
        }

        @Override
        public void visit(MultivectorComponent node) {
            if (!defined.contains(node.getName()) && !scalarVariables.contains(node.getName()))
                inputVariables.add(node.getName());
            super.visit(node);
        }
        
    };
    
    private UpdateLocalVariableSet() {
    }

    @Override
    public void visit(AssignmentNode node) {
        node.getValue().accept(dfgVisitor);
        
        if (node.getVariable() instanceof MultivectorComponent) {
            defined.add(node.getVariable().getName());
        } else {
            scalarVariables.add(node.getVariable().getName());
        }
              
        super.visit(node);
    }


    /**
     * Updates the variable-sets in a graph
     * @param graph The graph
     */
    public static void updateVariableSets(ControlFlowGraph graph) {
        UpdateLocalVariableSet visitor = new UpdateLocalVariableSet();
        graph.accept(visitor);

        LinkedList<Variable> inputVarList = new LinkedList<Variable>(graph.getInputVariables());
        LinkedList<Variable> localVarList = new LinkedList<Variable>(graph.getLocalVariables());
        LinkedList<Variable> scalarVarList = new LinkedList<Variable>(graph.getScalarVariables());

        //clear
        for (Variable inputVar: inputVarList)
            graph.removeInputVariable(inputVar);
        for (Variable defVar: localVarList)
            graph.removeLocalVariable(defVar);
        for (Variable scalVar: scalarVarList)
            graph.removeScalarVariable(scalVar);

        //add
        for (String inputVar: visitor.inputVariables)
            graph.addInputVariable(new Variable(inputVar));
        for (String defVar: visitor.defined)
            graph.addLocalVariable(new Variable(defVar));
        for (String scalVar: visitor.scalarVariables)
            graph.addScalarVariable(new Variable(scalVar));
    }

}
