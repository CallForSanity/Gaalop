package de.gaalop.tba;


import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.cfgImport.CFGImporter;

public class TBAOptStat implements OptimizationStrategy {

	@Override
	public void transform(ControlFlowGraph graph) throws OptimizationException {
            CFGImporter importer = new CFGImporter();
            importer.importGraph(graph);
        }

}
