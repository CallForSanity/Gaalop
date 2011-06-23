package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;

public class CFGImporter {

	public ControlFlowGraph importGraph(ControlFlowGraph graph) {
		DFGVisitorImport vDFG = new DFGVisitorImport();
		ControlFlowVisitor vCFG = new CFGVisitorImport(vDFG);
		graph.accept(vCFG);
		return graph;
	}

}
