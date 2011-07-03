/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.OptimizationStrategyWithModifyFlag;

/**
 *
 * @author christian
 */
public class OptOneExpressionsRemoval implements OptimizationStrategyWithModifyFlag {

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra) {
        OneExpressionRemoval oneExpressionRemoval = new OneExpressionRemoval();
        graph.accept(oneExpressionRemoval);

        // remove all nodes that are marked for removal
        for (SequentialNode node: oneExpressionRemoval.getNodeRemovals())
           graph.removeNode(node);

        return oneExpressionRemoval.isGraphModified();
    }

}
