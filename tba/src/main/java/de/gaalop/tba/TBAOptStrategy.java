package de.gaalop.tba;


import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.cfgImport.CFGImporterFacade;

public class TBAOptStrategy implements OptimizationStrategy {

        private Plugin plugin;

        public TBAOptStrategy(Plugin plugin) {
            this.plugin = plugin;
        }
        
	@Override
	public void transform(ControlFlowGraph graph) throws OptimizationException {
            CFGImporterFacade importer = new CFGImporterFacade(plugin);
            importer.importGraph(graph);
        }

}
