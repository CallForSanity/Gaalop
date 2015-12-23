package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.api.cfg.GetAllOutputBlades;
import de.gaalop.api.dfg.DFGNodeType;
import de.gaalop.api.dfg.DFGNodeTypeGetter;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.UseAlgebra;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Removes assignments that consists only of one Variable as value,
 * and those target variable are not outputted
 * @author Christian Steinmetz
 */
public class OneExpressionRemoval extends EmptyControlFlowVisitor {

    private boolean graphModified = false;
    private HashSet<VariableComponent> tabuVariables = new HashSet<VariableComponent>();
    private HashMap<VariableComponent, VariableComponent> mapOneExpressions = new HashMap<VariableComponent, VariableComponent>();
    private DFGVisitorUsedVariables dfgVisitorusedVariables = new DFGVisitorUsedVariables();
    private LinkedList<SequentialNode> nodeRemovals = new LinkedList<SequentialNode>();
    private UseAlgebra usedAlgebra;

    public OneExpressionRemoval(UseAlgebra usedAlgebra) {
        this.usedAlgebra = usedAlgebra;
    }

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
        tabuVariables.addAll(GetAllOutputBlades.getAllOutputBlades(node.getGraph(), usedAlgebra));
        // mark only evaluation vars as tabu
        int bladeCount = usedAlgebra.getBladeCount();
        for (String var: node.getGraph().getPragmaOnlyEvaluateVariables()) 
            for (int blade = 0; blade < bladeCount; blade++) 
                tabuVariables.add(new VariableComponent(var, blade, null));
        
        super.visit(node);
    }

    /**
     * Replace all expressions in a expression, that are listed in mapOneExpressions
     * @param value The value to search in
     * @return The result expression
     */
    private Expression performExpressionReplacements(Expression value) {

        dfgVisitorusedVariables.getVariables().clear();
        value.accept(dfgVisitorusedVariables);
        for (VariableComponent variable : dfgVisitorusedVariables.getVariables()) {
            if (mapOneExpressions.containsKey(variable)) {

                Expression toReplace = variable.getReferredExpression();
                Expression replacement = mapOneExpressions.get(variable).getReferredExpression();

                ExpressionReplacer replacer = new ExpressionReplacer(toReplace, replacement);
                value = replacer.replace(value);
                
                setGraphModified();

            }
        }

        return value;
    }

    @Override
    public void visit(AssignmentNode node) {

        // replace all variables that are in mapOneExpressions
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
                        getVariableComponent(node.getValue()));
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
            return new VariableComponent(comp.getName(), comp.getBladeIndex(), expression);
        }
        if (typeValue == DFGNodeType.Variable) {
            Variable comp = (Variable) expression;
            return new VariableComponent(comp.getName(), 0, expression);
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
