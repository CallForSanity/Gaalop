package de.gaalop.tba;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gappImporting.cfgimport.CFGImporter2;
import de.gaalop.gappImporting.cfgimport.ConstantFolding;

public class TBAOptStat implements OptimizationStrategy {

	@Override
	public void transform(ControlFlowGraph graph) throws OptimizationException {
            CFGImporter2 importer = new CFGImporter2();
            importer.importGraph(graph);
            ConstantFolding folding = new ConstantFolding();
            graph.accept(folding);
        }

}
