package de.gaalop.visualizer;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.tba.cfgImport.optimization.ConstantFolding;
import java.util.LinkedList;

/**
 * Differentiates an Expression directly in Gaalop with respect to an
 * Multivector component
 * @author Christian
 */
public class CFGDifferentiater implements Differentiater {

    @Override
    public LinkedList<AssignmentNode> differentiate(LinkedList<AssignmentNode> toDerive, MultivectorComponent variable) {
        LinkedList<AssignmentNode> result = new LinkedList<AssignmentNode>();
        for (AssignmentNode node : toDerive) {
            Expression differentiated = DFGDifferentiater.differentiate(node.getValue(), variable);
            ConstantFolding cF = new ConstantFolding();
            differentiated.accept(cF);
            result.add(new AssignmentNode(null, node.getVariable(), cF.getResultExpr()));
        }
        return result;
    }

   
    
}
