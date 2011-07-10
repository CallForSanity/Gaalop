/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaTransformer;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;

/**
 *
 * @author christian
 */
public class OptMaxima implements OptimizationStrategyWithModifyFlag {

    private MaximaTransformer transformer;

    public OptMaxima() {
        transformer = new MaximaTransformer(new ProcessBuilderMaximaConnection(ProcessBuilderMaximaConnection.CMD_MAXIMA_LINUX));
    }

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra) {
       transformer.transformGraph(graph);
       return false; //TODO chs rückgabe wert ändern
    }

}
