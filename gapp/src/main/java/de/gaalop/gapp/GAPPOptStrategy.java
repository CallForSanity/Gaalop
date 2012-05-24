package de.gaalop.gapp;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.importing.GAPPDecoratingMain;

/**
 * Facade class for decorating the Control Flow Graph with GAPP instructions
 * @author Christian Steinmetz
 */
public class GAPPOptStrategy implements OptimizationStrategy {

    private Plugin plugin;

    public GAPPOptStrategy(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        GAPPDecoratingMain importer = new GAPPDecoratingMain();
        importer.decorateGraph(graph);
    }
}
