/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.BlockEndNode;
import de.gaalop.cfg.BreakNode;
import de.gaalop.cfg.ColorNode;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.IfThenElseNode;
import de.gaalop.cfg.LoopNode;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.tba.UseAlgebra;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Visitor to optimize a ControlFlowGraph via variable usage analysis
 * @author christian
 */
public class CFGVisitorUsedVariables implements ControlFlowVisitor {

    private VariableUsage variableUsage = new VariableUsage();
    private DFGVisitorUsedVariables dfgVisitorusedVariables = new DFGVisitorUsedVariables();
    private HashMap<String,LinkedList<Integer>> outputBlades;
    private UseAlgebra usedAlgebra;

    private LinkedList<SequentialNode> nodeRemovals = new LinkedList<SequentialNode>();

    public CFGVisitorUsedVariables(HashMap<String,LinkedList<Integer>> outputBlades, UseAlgebra usedAlgebra) {
        this.outputBlades = outputBlades;
        this.usedAlgebra = usedAlgebra;
    }

    public LinkedList<SequentialNode> getNodeRemovals() {
        return nodeRemovals;
    }

    @Override
    public void visit(AssignmentNode node) {

        // get the target variable
        dfgVisitorusedVariables.getVariables().clear();
        node.getVariable().accept(dfgVisitorusedVariables);
        VariableComponent variable = dfgVisitorusedVariables.getVariables().getFirst();

        // Here is the optimization: Check, if the variable is used later.
        // If it isn't used later, then remove this node, because the assignment is unnecessary.
        if (!variableUsage.isUsed(variable)) {
            nodeRemovals.addFirst(node);
        } else {
            // variable definition
            variableUsage.addDefinition(variable);

            // value usage
            dfgVisitorusedVariables.getVariables().clear();
            node.getValue().accept(dfgVisitorusedVariables);
            for (VariableComponent component: dfgVisitorusedVariables.getVariables())
                variableUsage.addUsage(component);
        }
    }

    @Override
    public void visit(StoreResultNode node) {
        String name = node.getValue().getName();
        if (outputBlades.containsKey(name)) {
            // only special blades are outputted
            for (Integer bladeIndex: outputBlades.get(name)) 
                variableUsage.addUsage(new VariableComponent(name, bladeIndex));
        } else {
            // all blades are outputted
            int bladeCount = usedAlgebra.getBladeCount();

            for (int blade = 0; blade<bladeCount; bladeCount++)
                variableUsage.addUsage(new VariableComponent(name, blade));
        }
        
    }

    @Override
    public void visit(ExpressionStatement node) {
        // expression usage
        dfgVisitorusedVariables.getVariables().clear();
        node.getExpression().accept(dfgVisitorusedVariables);
        for (VariableComponent component: dfgVisitorusedVariables.getVariables())
            variableUsage.addUsage(component);
    }

    @Override
    public void visit(ColorNode node) {
         // R usage
        dfgVisitorusedVariables.getVariables().clear();
        node.getR().accept(dfgVisitorusedVariables);
        for (VariableComponent component: dfgVisitorusedVariables.getVariables())
            variableUsage.addUsage(component);

         // G usage
        dfgVisitorusedVariables.getVariables().clear();
        node.getG().accept(dfgVisitorusedVariables);
        for (VariableComponent component: dfgVisitorusedVariables.getVariables())
            variableUsage.addUsage(component);

         // B usage
        dfgVisitorusedVariables.getVariables().clear();
        node.getB().accept(dfgVisitorusedVariables);
        for (VariableComponent component: dfgVisitorusedVariables.getVariables())
            variableUsage.addUsage(component);

         // Alpha usage
        dfgVisitorusedVariables.getVariables().clear();
        node.getAlpha().accept(dfgVisitorusedVariables);
        for (VariableComponent component: dfgVisitorusedVariables.getVariables())
            variableUsage.addUsage(component);
    }

    @Override
    public void visit(StartNode node) {
    }

    @Override
    public void visit(IfThenElseNode node) {
       
    }

    @Override
    public void visit(BlockEndNode node) {
        
    }

    @Override
    public void visit(LoopNode node) {
        
    }

    @Override
    public void visit(BreakNode node) {
        
    }

    @Override
    public void visit(Macro node) {
        
    }

    @Override
    public void visit(EndNode node) {
        
    }



}
