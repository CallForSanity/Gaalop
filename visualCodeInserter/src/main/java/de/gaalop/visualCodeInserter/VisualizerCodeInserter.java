/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.visualCodeInserter;

import de.gaalop.OptimizationException;
import de.gaalop.VisualizerStrategy;
import de.gaalop.cfg.ControlFlowGraph;

/**
 *
 * @author Christian Steinmetz
 */
public class VisualizerCodeInserter implements VisualizerStrategy {

    private Plugin plugin;

    public VisualizerCodeInserter(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        //TODO insert visualizing commands
    }

}
