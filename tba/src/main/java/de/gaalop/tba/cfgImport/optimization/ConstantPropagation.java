package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.api.dfg.DFGNodeType;
import de.gaalop.api.dfg.DFGNodeTypeGetter;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import java.util.HashMap;

/**
 * This class tries to optimize a ControlFlowGraph
 * using the Constant Propagation and Constant Folding
 * @author Christian Steinmetz
 */
public class ConstantPropagation extends EmptyControlFlowVisitor {

    private ConstantFolding constantFolding = new ConstantFolding();
    private HashMap<VariableComponent, FloatConstant> mapConstants = new HashMap<VariableComponent, FloatConstant>();
    private DFGVisitorUsedVariables dfgVisitorUsedVariables = new DFGVisitorUsedVariables();
    private boolean graphModified = false;

    public boolean isGraphModified() {
        return graphModified;
    }

    private void setGraphModified() {
        graphModified = true;
    }

    /**
     * Performs constant propagtion on an expression and returns the result expression
     * @param expression the expression for constant propagtion
     * @return the result expression, where constant variables are constant
     */
    private Expression performConstantPropagationOnExpression(Expression expression) {
        dfgVisitorUsedVariables.getVariables().clear();
        expression.accept(dfgVisitorUsedVariables);

        for (VariableComponent varComp : dfgVisitorUsedVariables.getVariables()) {
            if (mapConstants.containsKey(varComp)) {
                // replace variable with constant

                if (expression == varComp.getReferredExpression()) { 
                    expression = mapConstants.get(varComp);
                } else {
                    expression.replaceExpression(varComp.getReferredExpression(), mapConstants.get(varComp));
                }

                setGraphModified();
            }
        }

        // do a constant folding on the value
        expression.accept(constantFolding);
        if (constantFolding.isGraphModified()) {
            setGraphModified();
        }

        return constantFolding.getResultExpr();
    }

    @Override
    public void visit(AssignmentNode node) {
        node.setValue(performConstantPropagationOnExpression(node.getValue()));

        // get variable component of target
        dfgVisitorUsedVariables.getVariables().clear();
        node.getVariable().accept(dfgVisitorUsedVariables);
        VariableComponent target = dfgVisitorUsedVariables.getVariables().getFirst();

        // remove variable component from mapConstants, because of re-assignment
        mapConstants.remove(target);

        if (isFloatConstant(node.getValue())) {
            mapConstants.put(target, (FloatConstant) node.getValue());
        }

        super.visit(node);
    }

    @Override
    public void visit(ColorNode node) {
        node.setR(performConstantPropagationOnExpression(node.getR()));
        node.setG(performConstantPropagationOnExpression(node.getG()));
        node.setB(performConstantPropagationOnExpression(node.getB()));
        node.setAlpha(performConstantPropagationOnExpression(node.getAlpha()));
        super.visit(node);
    }

    @Override
    public void visit(ExpressionStatement node) {
        //node.setExpression(performConstantPropagationOnExpression(node.getExpression()));
        super.visit(node);
    }

    @Override
    public void visit(StoreResultNode node) {
        // Do nothing, because node.getValue is of type Variable, not Expression
        super.visit(node);
    }

    /**
     * Determines if a expression is a FloatConstant
     * @param expression The expression to be checked
     * @return <value>true</value> if the expression is a FloatConstant, <value>false</value> otherwise
     */
    private boolean isFloatConstant(Expression expression) {
        return DFGNodeTypeGetter.getTypeOfDFGNode(expression) == DFGNodeType.FloatConstant;
    }
}
