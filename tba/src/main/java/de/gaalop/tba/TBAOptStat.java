package de.gaalop.tba;


import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.cfgImport.CFGImporterFacade;

public class TBAOptStat implements OptimizationStrategy {

        private Plugin plugin;

        public TBAOptStat(Plugin plugin) {
            this.plugin = plugin;
        }
        
	@Override
	public void transform(ControlFlowGraph graph) throws OptimizationException {
            CFGImporterFacade importer = new CFGImporterFacade(plugin);
            importer.importGraph(graph);
        }

}
