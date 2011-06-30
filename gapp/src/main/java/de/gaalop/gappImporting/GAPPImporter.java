package de.gaalop.gappImporting;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * Facade class to import the ControlFlowGraph in a GAPP ControlFlowGraph
 * @author christian
 */
public class GAPPImporter {

    	public ControlFlowGraph importGraph(ControlFlowGraph graph) {
            CFGImporter vCFG = new CFGImporter();
            graph.accept(vCFG);
            return graph;
	}

}
