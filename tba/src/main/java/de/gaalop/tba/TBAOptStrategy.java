package de.gaalop.tba;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.cfgImport.CFGImporterFacade;
import de.gaalop.LoggingListener;
import de.gaalop.LoggingListenerGroup;

/**
 * Defines a facade class for the table based approach
 * @author Christian Steinmetz
 */
public class TBAOptStrategy implements OptimizationStrategy {

    private Plugin plugin;
    private LoggingListenerGroup listeners;

    public TBAOptStrategy(Plugin plugin) {
        this.plugin = plugin;
        this.listeners = new LoggingListenerGroup();
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        CFGImporterFacade importer = new CFGImporterFacade(plugin);
        importer.setProgressListeners(listeners);
        importer.importGraph(graph);
        graph.tbaOptimized = true;
    }
    
    @Override
    public void addProgressListener(LoggingListener progressListener) {
    	listeners.add(progressListener);
    }
}