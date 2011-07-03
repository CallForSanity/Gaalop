/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.OptimizationStrategyWithModifyFlag;

/**
 *
 * @author christian
 */
public class OptConstantPropagation implements OptimizationStrategyWithModifyFlag {

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra) {
        ConstantPropagation propagation = new ConstantPropagation();
        graph.accept(propagation);
        return propagation.isGraphModified();
    }

}
