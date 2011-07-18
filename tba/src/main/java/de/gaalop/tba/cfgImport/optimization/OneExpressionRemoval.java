package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.api.dfg.DFGNodeType;
import de.gaalop.api.dfg.DFGNodeTypeGetter;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.cfgImport.optimization.VariableComponent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Removes assignments that consists only of one Variable as value,
 * and those target variable are not outputted
 * @author christian
 */
public class OneExpressionRemoval extends EmptyControlFlowVisitor {

    private boolean graphModified = false;

    private HashSet<VariableComponent> tabuVariables = new HashSet<VariableComponent>();
    private HashMap<VariableComponent,VariableComponent> mapOneExpressions = new HashMap<VariableComponent,VariableComponent>();
    private DFGVisitorUsedVariables dfgVisitorusedVariables = new DFGVisitorUsedVariables();
    private LinkedList<SequentialNode> nodeRemovals = new LinkedList<SequentialNode>();

    public LinkedList<SequentialNode> getNodeRemovals() {
        return nodeRemovals;
    }

    public boolean isGraphModified() {
        return graphModified;
    }

    private void setGraphModified() {
        graphModified = true;
    }

    @Override
    public void visit(StartNode node) {

        // mark output vars as tabu
        for (String output: node.getGraph().getPragmaOutputVariables()) {
            String[] parts = output.split("_");
            tabuVariables.add(new VariableComponent(parts[0],Integer.parseInt(parts[1]), null));
        }
        
        super.visit(node);
    }

    private Expression performExpressionReplacements(Expression value) {

        dfgVisitorusedVariables.getVariables().clear();
        value.accept(dfgVisitorusedVariables);
        for (VariableComponent variable: dfgVisitorusedVariables.getVariables())
            if (mapOneExpressions.containsKey(variable)) {
                
                Expression toReplace = variable.getReferredExpression();
                Expression replacement = mapOneExpressions.get(variable).getReferredExpression();
                
                if (value == toReplace) {
                    value = replacement;
                } else {
                    value.replaceExpression(toReplace,replacement);
                }

                setGraphModified();

            }

        return value;
    }

    @Override
    public void visit(AssignmentNode node) {
    
        // replace all variables which are in mapOneExpressions
        Expression value = node.getValue();
        node.setValue(performExpressionReplacements(value)); 
        mapOneExpressions.remove(getVariableComponent(node.getVariable()));

        // if this is no output variable assignment
        if (!tabuVariables.contains(getVariableComponent(node.getVariable()))) {
            // if the value is only a Variable then put it in mapOneExpressions and remove this AssignmentNode
            DFGNodeType typeValue = DFGNodeTypeGetter.getTypeOfDFGNode(node.getValue());
            if (typeValue == DFGNodeType.MultivectorComponent || typeValue == DFGNodeType.Variable) {
                mapOneExpressions.put(
                        getVariableComponent(node.getVariable()),
                        getVariableComponent(node.getValue())
                        );
                setGraphModified();
                nodeRemovals.add(node);
            }
        }

        super.visit(node);
    }

    /**
     * Returns the VariableComponent for a expression
     * @param expression The expression
     * @return The VariableComponent
     */
    private VariableComponent getVariableComponent(Expression expression) {
        
         DFGNodeType typeValue = DFGNodeTypeGetter.getTypeOfDFGNode(expression);
         if (typeValue == DFGNodeType.MultivectorComponent) {
             MultivectorComponent comp = (MultivectorComponent) expression;
            return new VariableComponent(comp.getName(),comp.getBladeIndex(),expression);
         }
         if (typeValue == DFGNodeType.Variable) {
            Variable comp = (Variable) expression;
            return new VariableComponent(comp.getName(),0,expression);
         }
         return null;
    }

    @Override
    public void visit(ColorNode node) {

        // replace all variables which are in mapOneExpressions
        node.setR(performExpressionReplacements(node.getR()));
        node.setG(performExpressionReplacements(node.getG()));
        node.setB(performExpressionReplacements(node.getB()));
        node.setAlpha(performExpressionReplacements(node.getAlpha()));
        
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {

        node.setExpression(performExpressionReplacements(node.getExpression()));

        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {

        node.setValue((Variable) performExpressionReplacements(node.getValue()));

        super.visit(node);
    }

}
