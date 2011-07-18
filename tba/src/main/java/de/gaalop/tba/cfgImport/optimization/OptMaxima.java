/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaOptimizer;
import de.gaalop.tba.cfgImport.optimization.maxima.ProcessBuilderMaximaConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author christian
 */
public class OptMaxima implements OptimizationStrategyWithModifyFlag {

    private MaximaOptimizer transformer;

    public OptMaxima() {
        transformer = new MaximaOptimizer(new ProcessBuilderMaximaConnection(ProcessBuilderMaximaConnection.CMD_MAXIMA_LINUX));
    }

    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra) {
        try {
          transformer.transformGraph(graph);
        } catch (RecognitionException ex) {
            Logger.getLogger(OptMaxima.class.getName()).log(Level.SEVERE, null, ex);
        }

       return false; // don't do a second step, if it was not necessary for other optimizations
    }

}
