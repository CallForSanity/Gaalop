package de.gaalop.tba.baseChange;

import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.productComputer.AlgebraPC;
import de.gaalop.tba.cfgImport.optimization.ConstantFolding;
import de.gaalop.visitors.CFGReplaceVisitor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements a method to changeToNormalBase() convert output variables into the plus-minus-base
 * @author Christian Steinmetz
 */
public class BaseChanger {

    /**
     * Convert output variables in a given Control Flow Graph into the plus-minus-base.
     * For this, the relevant variables are renamed at first and then Nodes are inserted that process the transformation. 
     * The created nodes use as target variable the original variable names.
     * @param graph The Control Flow Graph
     */
    public static void changeToNormalBase(ControlFlowGraph graph) {
        // To do the transformation, a mark & sweep algorithm is applied
        
        String prefix = "_coeff_In_e0einf_Basis_";

        AlgebraDefinitionFile alFile = graph.getAlgebraDefinitionFile();
        AlgebraPC alPC = new AlgebraPC(alFile);

        //1. Collect all relevant nodes for renaming and baseTransformation
        HashMap<String, LinkedList<AssignmentNode>> relevantNodes = collectRelevantNodes(graph, alFile, alPC);
        
        //2. Rename collected variables to those with prefix. Includes vars in Expressions, excludes ExpressionStatements and StoreResultNodes
        rename(prefix, relevantNodes, graph);
        
        //3. Do transformation of collected variables and insert the nodes after the last assignment in graph
        doBaseTransformation(relevantNodes, graph, alFile, alPC);
    }
    
    /**
     * Collects all nodes with relevant variables. A relevant variables is characterized by a used base element that is marked that has a map in the algebra definition,
     * e.g. in CGA the blades with einf or e0
     * @param graph The Control Flow Graph
     * @param alFile The AlgebraDefinitionFile of the graph
     * @param alPC The AlgebraPC of the graph
     * @return A map with the keys of the names of the relevant variables and as values the list of AssignmentNode which targets this the key variable.
     */
    private static HashMap<String, LinkedList<AssignmentNode>> collectRelevantNodes(ControlFlowGraph graph, AlgebraDefinitionFile alFile, AlgebraPC alPC) {
        //1. First collect all output variables
        OutputVarsCollector outputVarsCollector = new OutputVarsCollector();
        graph.accept(outputVarsCollector);
        
        //2. Iterate through output variables, determine if they contain blades with a base element einf or e0, and mark them in this case
        AssignedVarsToZIBaseCollector assignedVarsToZIBaseCollector = new AssignedVarsToZIBaseCollector(outputVarsCollector.variableNames, alFile, alPC);
        graph.accept(assignedVarsToZIBaseCollector);
        
        //3. Collect all assignment nodes for each variable in collected set
        RelevantAssignmentNodeCollector nodeCollector = new RelevantAssignmentNodeCollector(assignedVarsToZIBaseCollector.relevantVars);
        graph.accept(nodeCollector);
        return nodeCollector.relevantNodes;
    }
    
    /**
     * Renames the relevant variables by adding a prefix. 
     * Includes vars in Expressions.
     * Excludes ExpressionStatements and StoreResultNodes.
     * @param prefix The prefix to be used
     * @param relevantNodes A map with the keys of the names of the relevant variables and as values the list of AssignmentNode which targets this the key variable.
     * @param graph The Control Flow Graph
     */
    private static void rename(String prefix, HashMap<String, LinkedList<AssignmentNode>> relevantNodes, ControlFlowGraph graph) {
        Renamer renamer = new Renamer(relevantNodes, prefix);
        CFGReplaceVisitor replacer = new CFGReplaceVisitor(renamer) {
            @Override
            public void visit(ExpressionStatement node) {
                node.getSuccessor().accept(this);
            }

            @Override
            public void visit(StoreResultNode node) {
                node.getSuccessor().accept(this);
            }
        };
        graph.accept(replacer);
    }
    
    /**
     * Performs the transformation of relevant variables and inserts the nodes after the last assignment node in given graph
     * @param relevantNodes A map with the keys of the names of the relevant variables and as values the list of AssignmentNode which targets this the key variable.
     * @param graph The Control Flow Graph
     * @param alFile The AlgebraDefinitionFile of the graph
     * @param alPC The AlgebraPC of the graph
     */
    private static void doBaseTransformation(HashMap<String, LinkedList<AssignmentNode>> relevantNodes, ControlFlowGraph graph, AlgebraDefinitionFile alFile, AlgebraPC alPC) {
        BladeChanger bladeChanger = new BladeChanger(alPC, alFile);
        
        for (String varName: relevantNodes.keySet()) {
            HashMap<Integer, Expression> bladeExpressionsPlusMinus = new HashMap<Integer, Expression>();  
            LinkedList<AssignmentNode> nodes = relevantNodes.get(varName);
            
            // Do transformation of single variable
            for (AssignmentNode node: nodes) {
                int bladeIndex = ((MultivectorComponent) node.getVariable()).getBladeIndex();
                Expression value = node.getValue();
                
                LinkedList<PrefactoredBladeIndex> list = bladeChanger.transform(bladeIndex);

                for (PrefactoredBladeIndex p: list) {
                    Expression exprToAdd;
                    if ((value instanceof FloatConstant) && (Math.abs(((FloatConstant) value).getValue()-1)) < 0.001) {
                        exprToAdd = new FloatConstant(p.prefactor);
                    } else {
                        exprToAdd = (Math.abs(p.prefactor-1.0) < 0.0001) ? value : new Multiplication(new FloatConstant(p.prefactor), value);
                    }

                    bladeExpressionsPlusMinus.put(p.bladeIndex, 
                            (bladeExpressionsPlusMinus.containsKey(p.bladeIndex)) 
                                    ? new Addition(bladeExpressionsPlusMinus.get(p.bladeIndex), exprToAdd) 
                                    : exprToAdd
                    );
                }
            }
            
            // Export bladeExpressionPlusMinus to new nodes and insert them after last assignment node
            AssignmentNode last = nodes.getLast();
            
            
            // Sort to ascending indices
            Integer[] indices = bladeExpressionsPlusMinus.keySet().toArray(new Integer[0]);
            Arrays.sort(indices);

            // Create assignment nodes
            for (Integer bladeIndex: indices) {
                Expression value = bladeExpressionsPlusMinus.get(bladeIndex);

                ConstantFolding constantFolding = new ConstantFolding();
                // do a constant folding on the value
                value.accept(constantFolding);
                if (constantFolding.isGraphModified()) {
                    value = constantFolding.getResultExpr();
                }

                AssignmentNode newNode = new AssignmentNode(graph, new MultivectorComponent(varName, bladeIndex), value);
                last.insertAfter(newNode);
                last = newNode;
            }
        }
    }
}
